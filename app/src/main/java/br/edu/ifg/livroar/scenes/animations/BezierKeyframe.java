package br.edu.ifg.livroar.scenes.animations;

import br.edu.ifg.livroar.util.Vec2;

/**
 * Created by JoaoPaulo on 16/06/2015.
 */
public class BezierKeyframe extends Keyframe
{
    public Vec2 c0;
    public Vec2 c1;

    public BezierKeyframe(Vec2 pos, Vec2 c0, Vec2 c1)
    {
        super(pos);
        this.c0 = c0;
        this.c1 = c1;
    }

    @Override
    public Keyframe getCurPosition (float curTime, Keyframe next)
    {
//        Vec2 nextC1 = (next instanceof BezierKeyframe ?
//                ((BezierKeyframe)next).c1 : next.p);
//        return Utils.interpolateCubicBezier(p.x, p.y, c0.x, c0.y, curTime, nextC1.x, nextC1.y, next.p.x, next.p.y);
	    return null;
    }

}
