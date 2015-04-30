package br.edu.ifg.livroar;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by leandro on 30/04/15.
 */
public class ObjModel implements Model {

    private FloatBuffer bufferVertices;
    private FloatBuffer bufferUvs;
    private FloatBuffer bufferNormals;
    private int vertexCount;

    @Override
    public void init(GL10 gl) {

    }

    @Override
    public void draw(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 3, this.bufferVertices);

        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 3, this.bufferNormals);

        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glVertexPointer(2, GL10.GL_FLOAT, 2, this.bufferUvs);

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, this.vertexCount);
    }

    public FloatBuffer getBufferVertices() {
        return bufferVertices;
    }

    public void setBufferVertices(FloatBuffer bufferVertices) {
        this.bufferVertices = bufferVertices;
    }

    public FloatBuffer getBufferUvs() {
        return bufferUvs;
    }

    public void setBufferUvs(FloatBuffer bufferUvs) {
        this.bufferUvs = bufferUvs;
    }

    public FloatBuffer getBufferNormals() {
        return bufferNormals;
    }

    public void setBufferNormals(FloatBuffer bufferNormals) {
        this.bufferNormals = bufferNormals;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }
}
