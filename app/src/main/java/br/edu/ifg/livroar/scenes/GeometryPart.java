package br.edu.ifg.livroar.scenes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.model.Material;


/**
 * Created by JoaoPaulo on 06/05/2015.
 */
public class GeometryPart {

    private ShortBuffer indicesBuffer;
    private int vertexCount = 0;
    private Material material;

    public GeometryPart(Material material) {
        this.material = material;
    }

    public void init(GL10 gl){

    }

    public void draw(GL10 gl) {
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, material.getSpecularBuffer());
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, material.getAmbientBuffer());
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, material.getDiffuseBuffer());
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, material.getShininess());

        if(material.hasTexture()){
            gl.glEnable(GL10.GL_TEXTURE_2D);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, material.getTexureId());
        }

        gl.glDrawElements(GL10.GL_TRIANGLES, vertexCount, GL10.GL_SHORT, this.indicesBuffer);
    }

    public void setIndicesBuffer(short[] indices) {
        vertexCount = indices.length;

        indicesBuffer = ByteBuffer
                .allocateDirect(indices.length * 2)
                .order(ByteOrder.nativeOrder())
                .asShortBuffer()
                .put(indices);
        indicesBuffer.position(0);
    }

}
