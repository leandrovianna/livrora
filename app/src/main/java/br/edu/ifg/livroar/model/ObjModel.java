package br.edu.ifg.livroar.model;

import android.util.Log;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by leandro on 30/04/15.
 */
public class ObjModel implements Model {

    private FloatBuffer bufferPositions;
    private FloatBuffer bufferUvs;
    private FloatBuffer bufferNormals;
    private FloatBuffer bufferColors;
    private int vertexCount;

    @Override
    public void init(GL10 gl) {

    }

    @Override
    public void draw(GL10 gl) {

        gl.glScalef(20,20,20);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.bufferPositions);

        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glNormalPointer(GL10.GL_FLOAT, 0, this.bufferNormals);

        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColorPointer(3, GL10.GL_FLOAT, 0, this.bufferColors);
        //Fazer o bind da texture
//        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//        gl.glVertexPointer(2, GL10.GL_FLOAT, 2, this.bufferUvs);

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, this.vertexCount);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        //        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        Log.i("ObjModel", "draw()");
    }

    public FloatBuffer getBufferPositions() {
        return bufferPositions;
    }

    public void setBufferPositions(FloatBuffer bufferPositions) {
        this.bufferPositions = bufferPositions;
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

    public FloatBuffer getBufferColors() {
        return bufferColors;
    }

    public void setBufferColors(FloatBuffer bufferColors) {
        this.bufferColors = bufferColors;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }
}
