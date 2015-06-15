package br.edu.ifg.livroar.scenes;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.model.Material;
import br.edu.ifg.livroar.util.Vec2;
import br.edu.ifg.livroar.util.Vec3;


/**
 * Created by JoaoPaulo on 06/05/2015.
 */
public class GeometryPart {

    public int posDataSize = 3;
    public int normalOffset = posDataSize;
    public int normalDataSize = 3;
    public int uvOffset = posDataSize + normalDataSize;
    public int uvDataSize = 2;
    public int stride = (posDataSize + normalDataSize + uvDataSize) * 4;

    private int vertexCount = 0;
    private FloatBuffer verticesBuffer;
    private Material material;

    public GeometryPart(int vertexCount,
                        List<Vec3> positions,
                        List<Vec3> normals,
                        List<Vec2> uvs,
                        Material material) {
        this.material = material;
        this.vertexCount = vertexCount;
        this.setVerticesBuffer(getInterleavedVertexArray(positions, normals, uvs));
    }

    private float[] getInterleavedVertexArray(List<Vec3> positions,
                                              List<Vec3> normals,
                                              List<Vec2> uvs){
        float[] vertexArray = new float[(positions.size()*3)+
                (normals.size()*3) + (uvs.size()*2)];

        if(uvs.size() == 0){
            stride = (posDataSize + normalDataSize) * 4;
        }

        int index = 0;
        for(int i = 0; i < positions.size(); i++){
            vertexArray[index++] = positions.get(i).x;
            vertexArray[index++] = positions.get(i).y;
            vertexArray[index++] = positions.get(i).z;
            vertexArray[index++] = normals.get(i).x;
            vertexArray[index++] = normals.get(i).y;
            vertexArray[index++] = normals.get(i).z;
            if(uvs.size()>0){
                vertexArray[index++] = uvs.get(i).x;
                vertexArray[index++] = uvs.get(i).y;
            }
        }

        return vertexArray;
    }

    public void init(GL10 gl) {
        material.setTexureId(gl);
    }

    public void draw(GL10 gl) {
        verticesBuffer.position(0);
        gl.glVertexPointer(3, GL10.GL_FLOAT, stride, verticesBuffer);

        verticesBuffer.position(normalOffset);
        gl.glNormalPointer(GL10.GL_FLOAT, stride, verticesBuffer);

//        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, material.getSpecularBuffer());
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, material.getAmbientBuffer());
//        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, material.getShininess());

        if(material.hasTexture()){
            verticesBuffer.position(uvOffset);
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, stride, verticesBuffer);
            gl.glEnable(GL10.GL_TEXTURE_2D);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, material.getTexureId());
        }else{
            gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, material.getDiffuseBuffer());
        }

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertexCount);
    }

    public FloatBuffer getVerticesBuffer() {
        return verticesBuffer;
    }

    public void setVerticesBuffer(float[] vertices) {
        this.verticesBuffer = ByteBuffer
                .allocateDirect(vertices.length * 4)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(vertices);
        verticesBuffer.position(0);
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
