package br.edu.ifg.livroar.scenes;

import java.util.List;

import br.edu.ifg.livroar.util.Vec2;
import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by JoaoPaulo on 05/05/2015.
 */
public class LRSAnimation {

    public enum InterpolationMode {
        BEZIER{
            @Override
            public Vec3 interpolate(double curTime, Keyframe p0, Keyframe p1) {
                return null;//TODO: Implementar interpolacao bezier cubica com algoritmo de iteracao de-Casteljau
            }
        };
        //TODO: Estudar necessidade dos outros tipos de interpolacao
//        LINEAR{
//            @Override
//            public Vec3f interpolate(double beginning, double currentTime, double duration, Vec3f p0, Vec3f p1) {
//                return null;
//            }
//        },
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

        public abstract Vec3 interpolate(double curTime, Keyframe p0, Keyframe p1);
    }

    private InterpolationMode interpolationMode;
    private List<Keyframe> keyframes;

    public LRSAnimation(InterpolationMode interpolationMode, List<Keyframe> keyframes) {
        this.interpolationMode = interpolationMode;
        this.keyframes = keyframes;
    }

    public Keyframe getCurPosRotScale(){
        return null;
    }

    public Vec3 getInterpolatedLoc(double s, double t) {
        return null;
    }

    public Vec3 getNextInterpolatedRot(double startTime, double time, double deltaTime, int curKeyframe) {
        return null;
    }

    public Vec3 getInterpolatedScale(double startTime, double time, double deltaTime, int curKeyframe) {
        return null;
    }

    public static class Keyframe {
        //Pontos de controle da curva, considerar implicacoes
        // para pre-calculo de loc/rot/scales
        public Vec2 outTangent;
        public Vec3 inTangent;

        public double timeMillis;
        public Vec3 loc;
        public Vec3 rot;
        public Vec3 sca;
    }

}
