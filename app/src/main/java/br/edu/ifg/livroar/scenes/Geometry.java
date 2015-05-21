package br.edu.ifg.livroar.scenes;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.util.Vec3;
import edu.dhbw.andar.ARObject;

/**
 * Created by JoaoPaulo on 05/05/2015.
 */
public class Geometry extends ARObject{

    public static final int SCALE = 20;

    private List<GeometryPart> parts;

    private Vec3 location;
    private Vec3 rotation;
    private Vec3 scale;
    private long curTime = 0;
    private List<Animation> animations;

    public Geometry(String name, String patternName, List<GeometryPart> parts) {
        this(name, patternName,
                new Vec3(0,0,0), new Vec3(0,0,0), new Vec3(1,1,1),
                parts, new ArrayList<Animation>());
    }

    public Geometry(String name, String patternName,
                    Vec3 location, Vec3 rotation, Vec3 scale,
                    List<GeometryPart> parts, List<Animation> animations) {
        super(name, patternName, 80.0, new double[]{0,0});
        this.parts = parts;
        this.animations = animations;
        this.location = location.mult(SCALE);
        this.rotation = rotation;
        this.scale = scale.mult(SCALE);
    }

    @Override
    public void init(GL10 gl) {
        for(GeometryPart gp : parts){
            gp.init(gl);
        }
    }

    private void animate(GL10 gl){
        curTime = System.currentTimeMillis();

        for (Animation a : animations) {
            float p = a.getP(curTime);
            switch (a.getType()){
                case LOC_X:
                    location.x = p * SCALE;
                    break;
                case LOC_Y:
                    location.y = p * SCALE;
                    break;
                case LOC_Z:
                    location.z = p * SCALE;
                    break;
                case ROT_X:
                    rotation.x = p;
                    break;
                case ROT_Y:
                    rotation.y = p;
                    break;
                case ROT_Z:
                    rotation.z = p;
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

    @Override
    public void draw(GL10 gl) {
        super.draw(gl);

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

    public List<GeometryPart> getParts() {
        return parts;
    }

    public void setParts(GeometryPart ... parts) {
        for (GeometryPart gp : parts){
            this.parts.add(gp);
        }
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
