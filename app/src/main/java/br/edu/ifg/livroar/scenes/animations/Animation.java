package br.edu.ifg.livroar.scenes.animations;

import java.util.List;

import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by JoaoPaulo on 10/06/2015.
 */
public class Animation
{
    public static final byte STATE_PLAY = 0x0;
    public static final byte STATE_PAUSE= 0x1;
    public static final byte STATE_STOP= 0x1;

    private byte state;
    private int curLoopTimes;
    private int loopTimes;
    private float startTime;
    private List<Keyframe> keyframes;

    public Animation(List<Keyframe> keyframes)
    {
        this.keyframes = keyframes;
    }

    public void update(float curTime,
                       Vec3 curLocation,
                       Vec3 curRotation,
                       Vec3 curScale)
    {
        if(state==STATE_PLAY)
        {

        }
    }

    public void play(int loopTimes)
    {
        setStartTime(System.nanoTime());
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

    public void setStartTime(float startTimeNano) {
        this.startTime = startTimeNano;
    }

}
