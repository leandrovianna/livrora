package br.edu.ifg.livroar.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JoaoPaulo on 08/05/2015.
 */
public class Utils {

    public static float getNormalizedValue(float min, float max, float value) {
        return (value - min) / (max-min);
    }

    public static double getNormalizedValue(double min, double max, double value) {
        return ((value - min) / (max-min));
    }

    /**
     * @param posX variavel sobre a qual qual obter S;
     * @param p0X primeiro ponto do segmento curva
     * @param c0X primeiro ponto de controle da curva
     * @param c1X segundo ponto de controle da curva
     * @param p1X segundo ponto do segmento de curva
     * */
    public static double approxCubicBezierS(double posX, double p0X, double c0X, double c1X, double p1X) {

        double approxEpsilon = 1.0E-09;
        int maxIterations = 1000;

        if(posX - p0X < 1.0E-20)
            return 0.0;
        if(p1X - posX < 1.0E-20)
            return 1.0;

        int iterStep = 0;
        float u = 0, v = 1;
        // subdivide gradativamente para aproximar valor de T
        while(iterStep < maxIterations){

            double a = (p0X + c0X)*.5f;
            double b = (c0X + c1X)*.5f;
            double c = (c1X + p1X)*.5f;
            double d = (a + b)*.5f;
            double e = (b + c)*.5f;
            double f = (d + e)*.5f;

            if(Math.abs(f - posX) < approxEpsilon)
                return clamp(0,1, ((u + v)*0.5f));

            if(f < posX){
                p0X = f;
                c0X = e;
                c1X = c;
                u = (u + v)*.5f;
            }else {
                c0X = a;
                c1X = d;
                p1X = f;
                v = (u + v)*.5f;
            }

            iterStep++;
        }

        return clamp(0,1, ((u + v)*0.5f));
    }

    public static float clamp(float min, float max, float value){
        if(value < min)
            return min;
        else if(value > max)
            return max;
        else
            return value;
    }

    public static List<Vec2> stringArrayToVec2List(String[] array){
        List<Vec2> l = new ArrayList<>();

        for (int i = 0; i < array.length; i+=2) {
            l.add(new Vec2(Float.parseFloat(array[i]), Float.parseFloat(array[i+1])));
        }

        return l;
    }

}
