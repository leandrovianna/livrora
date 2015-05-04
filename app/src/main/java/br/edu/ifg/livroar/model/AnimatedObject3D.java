package br.edu.ifg.livroar.model;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.animation.Animation;

/**
 * Created by JoaoPaulo on 04/05/2015.
 */
public class AnimatedObject3D extends Object3D{

    private int curAnim;
    private List<Animation> animations;

    public AnimatedObject3D(String name, String patternName,
                            Model model, Animation ... animations) {
        super(name, patternName, model);

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
        super.init(gl);
    }

    @Override
    public synchronized void draw(GL10 gl) {
        super.draw(gl);
        animations.get(curAnim).update(this, gl);
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
}
