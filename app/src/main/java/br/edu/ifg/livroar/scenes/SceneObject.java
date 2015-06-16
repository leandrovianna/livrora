package br.edu.ifg.livroar.scenes;

import android.util.Log;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.scenes.animations.Animation;
import br.edu.ifg.livroar.scenes.models.ModelPart;
import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by JoaoPaulo on 10/06/2015.
 */
public class SceneObject
{
    public static final String TAG = "SceneObject";
    //Lookout geometry
    private Vec3 location;
    private Vec3 rotation;
    private Vec3 scale;
    private List<ModelPart> parts;
    private List<Animation> animations;
    private int curAnimIndex;
    private boolean initialized;

    public SceneObject(Vec3 location, Vec3 rotation, Vec3 scale,
                       List<ModelPart> parts, List<Animation> animations)
    {
        this.location = location;
        this.rotation = rotation;
        this.scale = scale;
        this.parts = parts;
        this.animations = animations;
    }

    public void init(GL10 gl, float curTimeNano)
    {
        for (ModelPart p : parts)
            p.init(gl);

        animations.get(curAnimIndex).play(-1);
        initialized = true;
    }

    public void draw(GL10 gl, float curTimeNano)
    {
        if(initialized)
        {
            if(animations.size() > 0 && curAnimIndex < animations.size())
                animations.get(curAnimIndex).update(curTimeNano,
                        location,rotation,scale);

            gl.glTranslatef(location.x, location.y, location.z);
            gl.glRotatef(rotation.x, 1, 0, 0);
            gl.glRotatef(rotation.y, 0, 1, 0);
            gl.glRotatef(rotation.z, 0, 0, 1);
            gl.glScalef(scale.x, scale.y, scale.z);

            for(ModelPart p : parts)
                p.draw(gl);
        }
        else
            init(gl, curTimeNano);
    }

    public void addAnimation(Animation a){
        animations.add(a);
    }

    public int getCurAnimIndex() {
        return curAnimIndex;
    }

    public void setCurAnimIndex(int curAnimIndex) {
        if(animations.size() > curAnimIndex)
        {
            this.curAnimIndex = curAnimIndex;
            animations.get(curAnimIndex).setStartTime(System.nanoTime());
        }
        else
            Log.w(TAG, "Falha ao setar animacao atual. Animations size: "
                    + animations.size() + ", index inserido: " + curAnimIndex);
    }

    public Vec3 getLocation()
    {
        return location;
    }

    public void setLocation(Vec3 location)
    {
        this.location = location;
    }

    public Vec3 getRotation()
    {
        return rotation;
    }

    public void setRotation(Vec3 rotation)
    {
        this.rotation = rotation;
    }

    public Vec3 getScale()
    {
        return scale;
    }

    public void setScale(Vec3 scale)
    {
        this.scale = scale;
    }

    @Override
    public String toString() {
        int vCount = 0;
        StringBuilder partsBuilder = new StringBuilder();
        for (int i = 0; i < parts.size(); i++)
        {
            vCount += parts.get(i).getVertexCount();
            partsBuilder.append("Part #" + i + ": "
                    + parts.get(i).toString() + "\n");
        }
        StringBuilder animsBuilder = new StringBuilder();
        for (int i = 0; i < animations.size(); i++)
        {
            animsBuilder.append("Animation #" + i + ": "
                    + animations.get(i).toString() + "\n");
        }

        return "SceneObject:" +
                " Total vertex count = " + vCount +
                " ;\n Parts: count = " + parts.size() +
                partsBuilder.toString() +
                " ; Animations: count = " + animations.size() +
                animsBuilder.toString() +
                " ; loc: " + location.toString() +
                " ; rot: " + rotation.toString() +
                " ; scale: " + scale.toString();
    }
}
