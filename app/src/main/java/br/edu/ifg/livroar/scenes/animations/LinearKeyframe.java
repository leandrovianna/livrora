package br.edu.ifg.livroar.scenes.animations;

import br.edu.ifg.livroar.util.Utils;
import br.edu.ifg.livroar.util.Vec2;

/**
 * Created by JoaoPaulo on 16/06/2015.
 */
public class LinearKeyframe extends Keyframe
{
    public LinearKeyframe(Vec2 p)
    {
        super(p);
    }

    @Override
    public Keyframe getInterpolation(float curTime, Keyframe other)
    {
        float curPos = Utils.interpolateLinear(p, curTime, other.getP());
        return new LinearKeyframe(new Vec2(curTime, curPos));
    }
}
