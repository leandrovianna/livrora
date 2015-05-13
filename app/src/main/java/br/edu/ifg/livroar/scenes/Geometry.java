package br.edu.ifg.livroar.scenes;

import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.util.Vec2;
import br.edu.ifg.livroar.util.Vec3;
import edu.dhbw.andar.ARObject;

/**
 * Created by JoaoPaulo on 05/05/2015.
 */
public class Geometry extends ARObject{

    private List<GeometryPart> parts;

    private Vec3 position;
    private Vec3 rotation;
    private Vec3 scale;
    private long curTime = 0;
    private List<Animation> animations;

    public Geometry(String name, String patternName, List<GeometryPart> parts) {
        this(name, patternName, parts,
                new ArrayList<Animation>());
    }

    public Geometry(String name, String patternName,
                    List<GeometryPart> parts, List<Animation> animations) {
        super(name, patternName, 80.0, new double[]{0,0});
        this.parts = parts;
        this.animations = animations;
        position = new Vec3(0,0,0);
        rotation = new Vec3(0,0,0);
        scale = new Vec3(20,20,20);
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
            if(p!=0) Log.d("Geometry", "p: " + p);
            switch (a.getType()){
                case LOC_X:
                    position.x = p;
                    break;
                case LOC_Y:
                    position.y = p;
                    break;
                case LOC_Z:
                    position.z = p;
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
                    scale.x = p * 20;
                    break;
                case SCALE_Y:
                    scale.y = p * 20;
                    break;
                case SCALE_Z:
                    scale.z = p * 20;
                    break;
            }
        }

        gl.glTranslatef(position.x, position.y, position.z);
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

    public List<Animation> getAnimations() {
        return animations;
    }

    public void setAnimations(List<Animation> animations) {
        this.animations = animations;
    }

    @Override
    public String toString() {
        int vCount = 0;
        for(GeometryPart p : parts){
            vCount += p.getVertexCount();
        }
        return "Geometry: part count:" + parts.size() +
                " ; vertex count: " + vCount +
                " ; animation count: " + animations.size();
    }
}
