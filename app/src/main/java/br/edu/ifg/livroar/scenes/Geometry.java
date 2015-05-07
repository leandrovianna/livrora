package br.edu.ifg.livroar.scenes;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by JoaoPaulo on 05/05/2015.
 */
public class Geometry {

    private static final int POS_DATA_SIZE = 3;
    private static final int NORMAL_OFFSET = POS_DATA_SIZE;
    private static final int NORMAL_DATA_SIZE = 3;
    private static final int UV_OFFSET = POS_DATA_SIZE + NORMAL_DATA_SIZE;
    private static final int UV_DATA_SIZE = 2;
    private static final int STRIDE = (POS_DATA_SIZE + NORMAL_DATA_SIZE + UV_DATA_SIZE) * 4;

    private List<GeometryPart> parts;
    private FloatBuffer verticesBuffer;

    private String animationName = "null";
    private double animationStartTimeMillis = 0;
    private int curKeyframe;

    public Geometry() {
        this.parts = new ArrayList<>();
    }

    public void init(GL10 gl) {
        for(GeometryPart gp : parts){
            gp.init(gl);
        }
    }

    public void draw(GL10 gl) {

        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        verticesBuffer.position(0);
        gl.glVertexPointer(3, GL10.GL_FLOAT, STRIDE, verticesBuffer);

        verticesBuffer.position(NORMAL_OFFSET);
        gl.glNormalPointer(GL10.GL_FLOAT, STRIDE, verticesBuffer);

        verticesBuffer.position(UV_OFFSET);
        gl.glTexCoordPointer(2, GL10.GL_FLOAT, STRIDE, verticesBuffer);

        for(GeometryPart p : parts) {
            p.draw(gl);
        }

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
        gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
        gl.glDisable(GL10.GL_TEXTURE_2D);

    }

    public void draw(GL10 gl, LRSAnimation animation, double time, double deltaTime) {
//        LRSAnimation.Keyframe posRotSca = animation.getNextPosRotSca(animationStartTimeMillis, time, deltaTime, curKeyframe);
//        gl.glTranslatef(posRotSca.loc.x, posRotSca.loc.y, posRotSca.loc.z);
//        gl.glRotatef(posRotSca.rot.x, 1, 0, 0);
//        gl.glRotatef(posRotSca.rot.y, 0, 1, 0);
//        gl.glRotatef(posRotSca.rot.z, 0, 0, 1);
//        gl.glScalef(posRotSca.sca.x, posRotSca.sca.y, posRotSca.sca.z);

        draw(gl);
    }

    public List<GeometryPart> getParts() {
        return parts;
    }

    public void setParts(GeometryPart ... parts) {
        for (GeometryPart gp : parts){
            this.parts.add(gp);
        }
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

    public String getAnimationName() {
        return animationName;
    }

    public void setAnimationName(String animationName) {
        this.animationName = animationName;
    }

    public double getAnimationStartTimeMillis() {
        return animationStartTimeMillis;
    }

    public void setAnimationStartTimeMillis(double animationStartTimeMillis) {
        this.animationStartTimeMillis = animationStartTimeMillis;
    }
}
