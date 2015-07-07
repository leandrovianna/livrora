package br.edu.ifg.livroar.scenes.animations;

import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by JoaoPaulo on 12/06/2015.
 */
public abstract class Keyframe
{
	public float time;

	public Vec3 loc;
	public Vec3 rot;
	public Vec3 scl;

	public Keyframe (float time, Vec3 loc, Vec3 rot, Vec3 scl)
	{
		this.time = time;
		this.loc = loc;
		this.rot = rot;
		this.scl = scl;
	}

	public void fix()
	{
		// Nao ideal
		if(loc == null) loc = new Vec3(0,0,0);
		if(rot == null) rot = new Vec3(0,0,0);
		if(scl == null) scl = new Vec3(0,0,0);
	}

	public abstract Vec3 getLocAt(float curTime, Keyframe next);
	public abstract Vec3 getRotAt(float curTime, Keyframe next);
	public abstract Vec3 getScaleAt(float curTime, Keyframe next);
}
