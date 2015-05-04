package br.edu.ifg.livroar.model;

import android.graphics.AvoidXfermode;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.animation.Animation;
import br.edu.ifg.livroar.util.Vec3;
import edu.dhbw.andar.ARObject;

/**
 * Created by JoaoPaulo on 04/05/2015.
 */
public class AnimatedObject3D extends ARObject{

    private static final String TAG = "AnimatedObject3D";

    private Model model;
    private Vec3 position = new Vec3(0,0,0);
    private Vec3 rotation = new Vec3(0,0,0);

    private int curAnim;
    private List<Animation> animations;

    public AnimatedObject3D(String name, String patternName,
                            Model model, Animation ... animations) {
        super(name, patternName, 80.0, new double[]{0,0});

        this.model = model;

        this.animations = new ArrayList<>();
        if(animations != null){
            for (Animation a : animations){
                this.animations.add(a);
            }
            curAnim = 0;
        }
    }

    @Override
    public void init(GL10 gl) {
    }

    @Override
    public synchronized void draw(GL10 gl) {
        super.draw(gl);
        animations.get(curAnim).update(position, rotation, gl);
        model.draw(gl);
    }

    public Animation getCurrentAnimation() {
        return animations.get(curAnim);
    }

    public void setCurAnim(int curAnim) {
        if(curAnim>=0 && curAnim <= animations.size()-1)
            this.curAnim = curAnim;
    }

    public void addAnimations(Animation ... animations){
        if(animations != null){
            for (Animation a : animations){
                this.animations.add(a);
            }
        }
    }


    public Vec3 getPosition() {
        return position;
    }

    public void setPosition(Vec3 position) {
        this.position = position;
    }

    public void setPosition(float x, float y, float z){
        position.x = x;
        position.y = y;
        position.z = z;
    }

    public Vec3 getRotation() {
        return rotation;
    }

    public void setRotation(Vec3 rotation) {
        this.rotation = rotation;
    }

    public void setRotation(float angleX, float angleY, float angleZ){
        rotation.x = angleX;
        rotation.y = angleY;
        rotation.z = angleZ;
    }
}
