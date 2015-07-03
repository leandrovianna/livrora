package br.edu.ifg.livroar.scenes.animations;

import br.edu.ifg.livroar.util.Vec2;
import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by JoaoPaulo on 16/06/2015.
 */
public class BezierKeyframe extends Keyframe
{
    public Vec2 c0;
    public Vec2 c1;

	public BezierKeyframe (float time,
	                       Vec3 loc,
	                       Vec3 rot,
	                       Vec3 scl,
	                       Vec2 c0,
	                       Vec2 c1)
	{
		super(time, loc, rot, scl);
		this.c0 = c0;
		this.c1 = c1;
	}

	@Override
	public Vec3 getLocAt (float curTime, Keyframe next)
	{
		return null;
	}

	@Override
	public Vec3 getRotAt (float curTime, Keyframe next)
	{
		return null;
	}

	@Override
	public Vec3 getScaleAt (float curTime, Keyframe next)
	{
		return null;
	}
}
