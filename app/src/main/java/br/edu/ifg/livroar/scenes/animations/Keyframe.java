package br.edu.ifg.livroar.scenes.animations;

import br.edu.ifg.livroar.util.Vec2;

/**
 * Created by JoaoPaulo on 12/06/2015.
 */
public abstract class Keyframe
{
    protected Vec2 p;

    /**
     * @param p posicao do keyframe em que p.x corresponde ao tempo e p.y a posicao
     * */
    public Keyframe(Vec2 p)
    {
        this.p = p;
    }

    public abstract Keyframe getInterpolation(float curTime, Keyframe other);

    public Vec2 getP()
    {
        return p;
    }

    public void setP(Vec2 p)
    {
        this.p = p;
    }
}
