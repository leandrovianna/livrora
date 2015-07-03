package br.edu.ifg.livroar.scenes.animations;

import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by JoaoPaulo on 16/06/2015.
 */
public class LinearKeyframe extends Keyframe
{
	public LinearKeyframe (float time,
	                       Vec3 loc,
	                       Vec3 rot,
	                       Vec3 scl)
	{
		super(time, loc, rot, scl);
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
