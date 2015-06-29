package br.edu.ifg.livroar.scenes.animations;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by JoaoPaulo on 10/06/2015.
 */
public class Animation
{
	public static final byte STATE_PLAY     = 0x0;
	public static final byte STATE_PAUSE    = 0x1;
	public static final byte STATE_STOP     = 0x1;
	public static final byte PLAY_MODE_LOOP = 0xffffffff;

	private byte                 state;
	private int                  loopTimes;
	private int                  curLoopTimes;
	private List<List<Keyframe>> perElementKeys;
	private int[]                curKeys;
	private int[]                nextKeys;
	private float[]              keyStartTimes;

	public Animation (List<Keyframe> locX, List<Keyframe> locY, List<Keyframe> locZ,
	                  List<Keyframe> rotX, List<Keyframe> rotY, List<Keyframe> rotZ,
	                  List<Keyframe> scaleX, List<Keyframe> scaleY, List<Keyframe> scaleZ)
    {
        perElementKeys = new ArrayList<>(9);
        curKeys = new int[9];
        nextKeys = new int[9];
        keyStartTimes = new float[9];
        perElementKeys.add(locX);
        perElementKeys.add(locY);
        perElementKeys.add(locZ);
        perElementKeys.add(rotX);
        perElementKeys.add(rotY);
        perElementKeys.add(rotZ);
        perElementKeys.add(scaleX);
        perElementKeys.add(scaleY);
        perElementKeys.add(scaleZ);
    }

    public void update(float curTime,
                       Vec3 curLocation,
                       Vec3 curRotation,
                       Vec3 curScale)
    {
        if(state==STATE_PLAY)
        {
            if(!perElementKeys.get(0).isEmpty())
                curLocation.x = getCurValue(perElementKeys.get(0), curKeys[0], nextKeys[0], curTime);
            if(!perElementKeys.get(1).isEmpty())
                curLocation.y = getCurValue(perElementKeys.get(1), curKeys[1], nextKeys[0], curTime);
            if(!perElementKeys.get(2).isEmpty())
                curLocation.z = getCurValue(perElementKeys.get(2), curKeys[2], nextKeys[0], curTime);
            if(!perElementKeys.get(3).isEmpty())
                curRotation.x = getCurValue(perElementKeys.get(3), curKeys[3], nextKeys[0], curTime);
            if(!perElementKeys.get(4).isEmpty())
                curRotation.y = getCurValue(perElementKeys.get(4), curKeys[4], nextKeys[0], curTime);
            if(!perElementKeys.get(5).isEmpty())
                curRotation.z = getCurValue(perElementKeys.get(5), curKeys[5], nextKeys[0], curTime);
            if(!perElementKeys.get(6).isEmpty())
                curScale.x = getCurValue(perElementKeys.get(6), curKeys[6], nextKeys[0], curTime);
            if(!perElementKeys.get(7).isEmpty())
                curScale.y = getCurValue(perElementKeys.get(7), curKeys[7], nextKeys[0], curTime);
            if(!perElementKeys.get(8).isEmpty())
                curScale.z = getCurValue(perElementKeys.get(8), curKeys[8], nextKeys[0], curTime);
        }
    }

    private float getCurValue(List<Keyframe> keyframes, int curKeyframe, int nextKeyframe, float curTime)
    {
        //TODO: checar tempo
//        return keyframes.get(curKeyframe).getCurPosition(curTime, keyframes.get(nextKeyframe));
	    return 0;
    }

    public void play(int loopTimes)
    {
        this.state = STATE_PLAY;
        this.loopTimes = loopTimes;
    }

    public void pause(){
        this.state = STATE_PAUSE;
    }

    public void resume()
    {
        if(this.state == STATE_PAUSE)
        {
            this.state = STATE_PLAY;
            //TODO: setar startTime
        }
    }

    public void stop(){
        this.state = STATE_STOP;
    }

}
