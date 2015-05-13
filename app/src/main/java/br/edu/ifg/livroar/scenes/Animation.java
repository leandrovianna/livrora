package br.edu.ifg.livroar.scenes;

import android.util.Log;

import java.util.List;

import br.edu.ifg.livroar.util.Utils;
import br.edu.ifg.livroar.util.Vec2;
import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by JoaoPaulo on 11/05/2015.
 */
public class Animation {

    public enum InterpolationMethod {
        BEZIER{
            @Override
            public float interpolate(double curTime,
                                     double p0Time, float p0, Vec2 c0,
                                     double p1Time, float p1, Vec2 c1) {

                float s = (float) Utils.approxCubicBezierS(curTime, p0Time, c0.x, c1.x, p1Time);
                // Interpolacao bezier cubica: B(s) = P0 * (1-s)^3 + 3*C0*s * (1-s)^2 + 3*C1 * s^2 *(1-s) + P1 * s^3
                //TODO: Corrigir
                return (p0  * ((1-s)*(1-s)*(1-s)) + 3 * c0.y * s * ((1-s)*(1-s)) + 3 * c1.y * (s * s) * (1-s) + p1 * (s * s * s));
            }
        };
        //TODO: Estudar necessidade dos outros tipos de interpolacao
//        LINEAR{
//            @Override
//            public float interpolate(double curTime,
//                                              double p0Time, float p0, Vec2 c0,
//                                              double p1Time, float p1, Vec2 c1){
//                float s = (float) Utils.getNormalizedValue(p0Time, p1Time, curTime);
//                return p0 + (p1-p0) * s;
//            }
//        };
//        BSPLINE{
//            @Override
//            public Vec3f interpolate(double beginning, double currentTime, double duration, Vec3f p0, Vec3f p1) {
//                return null;
//            }
//        },
//        HERMITE{
//            @Override
//            public Vec3f interpolate(double beginning, double currentTime, double duration, Vec3f p0, Vec3f p1) {
//                return null;
//            }
//        };

        public abstract float interpolate(double curTime,
                                          double p0Time, float p0, Vec2 c0,
                                          double p1Time, float p1, Vec2 c1);
    }

    public enum Type {
        LOC_X,
        LOC_Y,
        LOC_Z,
        ROT_X,
        ROT_Y,
        ROT_Z,
        SCALE_X,
        SCALE_Y,
        SCALE_Z
    }

    private Type type;
    private List<Keyframe> keyframes;
    private List<Vec2> outTangents;
    private List<Vec2> inTangents;

    private boolean toLoop = true;
    private int curKeyframe = 0;
    private int nextKeyframe = 1;
    private double startTime = 0;

    public Animation(Type type, List<Keyframe> keyframes, List<Vec2> outTangents, List<Vec2> inTangents) {
        this.type = type;
        this.keyframes = keyframes;
        this.outTangents = outTangents;
        this.inTangents = inTangents;
    }

    public void update(long curTime){
        if(curTime-startTime >= keyframes.get(nextKeyframe).time ){
            if(nextKeyframe+1 > keyframes.size()-1){
                if(toLoop){
                    curKeyframe = 0;
                    startTime = curTime;
                }else{
                    curKeyframe = nextKeyframe;
                }
            }else {
                curKeyframe += 1;
            }
            nextKeyframe = curKeyframe+1;
//            Log.d("Animation", "CurKeyframe: " + curKeyframe +
//                    ", NextKeyframe: " + nextKeyframe);
        }
    }

    public float getP(long curTime) {
        update(curTime);
        return keyframes.get(curKeyframe).method.interpolate(curTime,
                keyframes.get(curKeyframe).time, keyframes.get(curKeyframe).p, outTangents.get(curKeyframe),
                keyframes.get(nextKeyframe).time, keyframes.get(nextKeyframe).p, inTangents.get(nextKeyframe));
    }

    public boolean toLoop() {
        return toLoop;
    }

    public void setToLoop(boolean toLoop) {
        this.toLoop = toLoop;
    }

    public Type getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Type: " + type.toString() +
                "; Keyframe count: " + keyframes.size() +
                "; First keyframe time: " + keyframes.get(0).time +
                "; Last keyframe time: " + keyframes.get(keyframes.size()-1).time;
    }

    public static class Keyframe {
        public InterpolationMethod method;
        public long time;
        public float p;

        public Keyframe(InterpolationMethod method, long time, float p) {
            this.method = method;
            this.time = time;
            this.p = p;
        }
    }

}
