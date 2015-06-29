package br.edu.ifg.livroar.scenes.animations;

import br.edu.ifg.livroar.util.Vec2;

/**
 * Created by JoaoPaulo on 12/06/2015.
 */
public abstract class Keyframe
{
    public Vec2 p;

    public Keyframe(Vec2 p)
    {
        this.p = p;
    }

    public abstract Keyframe getCurPosition (float curTime, Keyframe other);

}
