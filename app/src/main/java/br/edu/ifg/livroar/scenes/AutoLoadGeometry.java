package br.edu.ifg.livroar.scenes;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by JoaoPaulo on 20/05/2015.
 */
public class AutoLoadGeometry {

    public static final int SCALE = 20;

    private List<GeometryPart> parts;

    public Vec3 location;
    public Vec3 rotation;
    public Vec3 scale;
    private long curTime = 0;
    private List<Animation> animations;

    private boolean initialized;

    public AutoLoadGeometry(List<GeometryPart> parts,
                            Vec3 location,
                            Vec3 rotation,
                            Vec3 scale,
                            List<Animation> animations) {
        this.parts = parts;
        this.location = location.mult(SCALE);
        this.location.x *= -1;
        this.location.y *= -1;
        this.rotation = rotation;
        this.rotation.z *= -1;
        this.scale = scale.mult(SCALE);
        this.animations = animations;
    }

    public void init(GL10 gl) {
        for(GeometryPart gp : parts){
            gp.init(gl);
        }
        initialized = true;
    }


    private void animate(GL10 gl){
        curTime = System.currentTimeMillis();

        for (Animation a : animations) {
            float p = a.getP(curTime);
            switch (a.getType()){
                case LOC_X:
                    location.x = -(p * SCALE);
                    break;
                case LOC_Y:
                    location.y = -(p * SCALE);
                    break;
                case LOC_Z:
                    location.z =  p * SCALE;
                    break;
                case ROT_X:
                    rotation.x = p;
                    break;
                case ROT_Y:
                    rotation.y = p;
                    break;
                case ROT_Z:
                    rotation.z = -p;
                    break;
                case SCALE_X:
                    scale.x = p * SCALE;
                    break;
                case SCALE_Y:
                    scale.y = p * SCALE;
                    break;
                case SCALE_Z:
                    scale.z = p * SCALE;
                    break;
            }
        }

        gl.glTranslatef(location.x, location.y, location.z);
        gl.glRotatef(rotation.x, 1, 0, 0);
        gl.glRotatef(rotation.y, 0, 1, 0);
        gl.glRotatef(rotation.z, 0, 0, 1);
        gl.glScalef(scale.x, scale.y, scale.z);
    }

    public void draw(GL10 gl){
        if(this.initialized)
        {
            animate(gl);

            gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

            for(GeometryPart p : parts) {
                p.draw(gl);
            }

            gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
            gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glDisable(GL10.GL_TEXTURE_2D);
        }
    }

    public boolean wasInitialized() {
        return initialized;
    }

    @Override
    public String toString() {
        int vCount = 0;
        for(GeometryPart p : parts){
            vCount += p.getVertexCount();
        }
        return "Geometry: part count:" + parts.size() +
                " ; vertex count: " + vCount +
                " ; animation count: " + animations.size() +
                " ; loc: " + location.toString() +
                " ; rot: " + rotation.toString() +
                " ; scale: " + scale.toString();
    }
}
