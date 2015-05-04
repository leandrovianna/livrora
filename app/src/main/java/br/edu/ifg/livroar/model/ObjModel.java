package br.edu.ifg.livroar.model;

import android.util.Log;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by leandro on 30/04/15.
 */
public class ObjModel implements Model {

    private FloatBuffer positionsBuffer;
    private FloatBuffer UVsBuffer;
    private FloatBuffer normalsBuffer;
    private FloatBuffer specularColorsBuffer;
    private FloatBuffer diffuseColorsBuffer;
    private FloatBuffer ambientColorsBuffer;
    private int vertexCount;

    @Override
    public void init(GL10 gl) {

    }

    @Override
    public void draw(GL10 gl) {

        gl.glScalef(20,20,20);

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, this.positionsBuffer);

        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glNormalPointer(GL10.GL_FLOAT, 0, this.normalsBuffer);

//        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, this.specularColorsBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, this.diffuseColorsBuffer);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, this.ambientColorsBuffer);

        //Fazer o bind da texture
//        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//        gl.glVertexPointer(2, GL10.GL_FLOAT, 2, this.UVsBuffer);

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, this.vertexCount);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
        //        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        Log.i("ObjModel", "draw()");
    }

    public FloatBuffer getPositionsBuffer() {
        return positionsBuffer;
    }

    public void setPositionsBuffer(FloatBuffer positionsBuffer) {
        this.positionsBuffer = positionsBuffer;
    }

    public FloatBuffer getUVsBuffer() {
        return UVsBuffer;
    }

    public void setUVsBuffer(FloatBuffer UVsBuffer) {
        this.UVsBuffer = UVsBuffer;
    }

    public FloatBuffer getNormalsBuffer() {
        return normalsBuffer;
    }

    public void setNormalsBuffer(FloatBuffer normalsBuffer) {
        this.normalsBuffer = normalsBuffer;
    }

    public FloatBuffer getDiffuseColorsBuffer() {
        return diffuseColorsBuffer;
    }

    public void setDiffuseColorsBuffer(FloatBuffer diffuseColorsBuffer) {
        this.diffuseColorsBuffer = diffuseColorsBuffer;
    }

    public FloatBuffer getSpecularColorsBuffer() {
        return specularColorsBuffer;
    }

    public void setSpecularColorsBuffer(FloatBuffer specularColorsBuffer) {
        this.specularColorsBuffer = specularColorsBuffer;
    }

    public FloatBuffer getAmbientColorsBuffer() {
        return ambientColorsBuffer;
    }

    public void setAmbientColorsBuffer(FloatBuffer ambientColorsBuffer) {
        this.ambientColorsBuffer = ambientColorsBuffer;
    }

    public int getVertexCount() {
        return vertexCount;
    }

    public void setVertexCount(int vertexCount) {
        this.vertexCount = vertexCount;
    }
}
