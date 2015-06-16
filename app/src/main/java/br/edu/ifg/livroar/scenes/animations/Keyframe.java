package br.edu.ifg.livroar.scenes.animations;

/**
 * Created by JoaoPaulo on 12/06/2015.
 */
public abstract class Keyframe
{
    protected long time;

    public abstract Keyframe getInterpolation(float curTime, Keyframe other);

}
