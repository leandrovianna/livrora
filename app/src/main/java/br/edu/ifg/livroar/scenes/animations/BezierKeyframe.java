package br.edu.ifg.livroar.scenes.animations;

import br.edu.ifg.livroar.util.Utils;
import br.edu.ifg.livroar.util.Vec2;

/**
 * Created by JoaoPaulo on 16/06/2015.
 */
public class BezierKeyframe extends Keyframe
{
    private Vec2 c;

    public BezierKeyframe(Vec2 p, Vec2 c)
    {
        super(p);
        this.c = c;
    }

    @Override
    public Keyframe getInterpolation(float curTime, Keyframe other)
    {
        Vec2 otherC = (other instanceof BezierKeyframe ?
                ((BezierKeyframe) other).getC() : other.getP());

        float curPos = Utils.interpolateCubicBezier(p, c, curTime, otherC, other.getP());
        Keyframe cur = new BezierKeyframe(new Vec2(curTime, curPos), null);
        return cur;
    }

    public Vec2 getC()
    {
        return c;
    }

    public void setC(Vec2 c)
    {
        this.c = c;
    }
}
