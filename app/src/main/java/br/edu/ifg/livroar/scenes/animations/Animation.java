package br.edu.ifg.livroar.scenes.animations;

import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by JoaoPaulo on 10/06/2015.
 */
public class Animation
{
	public static final byte STATE_PLAY    = 0x0;
	public static final byte STATE_PAUSE   = 0x1;
	public static final byte STATE_STOPPED = 0x1;

	public static final byte LOOP_FOREVER = 0xffffffff;

	private byte       state;
	private int        curLoop;
	private int        maxLoopTimes;
	private float      startTime;
	private Keyframe[] keyframes;
	private int        curKey;
	private int        nextKey;

	public Animation (Keyframe[] keyframes)
	{
		this.keyframes = keyframes;
	}

	public void play (float curTime, int loop)
	{
		state = STATE_PLAY;
		startTime = curTime;
		this.maxLoopTimes = loop;
		curKey = 0;
		nextKey = keyframes.length > 1 ? 1 : 0; // Implica minimo de 1 keyframe por anim.
	}

	public void update(float curTime, Vec3 loc, Vec3 rot, Vec3 scale)
	{
		//TODO: Logica de avanco de keyframe
		if(state == STATE_PLAY)
		{
			if(curTime - startTime >= keyframes[nextKey].time)
			{
				curKey++;
				nextKey++;
				if(curKey == keyframes.length-1)
				{

				}
				curKey = (curKey);
			}
			loc =   keyframes[curKey].getLocAt  (curTime, keyframes[nextKey]);
			rot =   keyframes[curKey].getRotAt  (curTime, keyframes[nextKey]);
			scale = keyframes[curKey].getScaleAt(curTime, keyframes[nextKey]);
		}
	}

	public void pause()
	{
		state = STATE_PAUSE;
	}

	public void stop()
	{
		state = STATE_STOPPED;
	}
}
