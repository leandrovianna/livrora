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

    public static float[] DoubleArrayToFloatArray(double[] array)
    {
        float[] floatArray = new float[array.length];
        for (int i = 0; i < array.length; i++)
        {
            floatArray[i] = (float) array[i];
        }
        return floatArray;
    }

    /**
     * @param p0 posicao de inicio da curva (postions[i]);
     * @param curX valor atual de x para o qual encontrar valor de y correspondente;
     * @param p1 posicao de fim da curva (positions[i+1]);
     * */
    public static float interpolateLinear(Vec2 p0, float curX, Vec2 p1)
    {
        float s = curX/(p1.x - p0.x);
        if(s>1) s=1;
        else if(s<0) s=0;
        return p0.y + (p1.y-p0.y)*s;
    }

    /**
     * @param p0 posicao de inicio da curva (postions[i]);
     * @param c0 primeiro ponto de controle da curva (outtangents[i]);
     * @param curX valor atual de x para o qual encontrar valor de y correspondente;
     * @param c1 segundo ponto de controle da curva (intangents[i+1]);
     * @param p1 posicao de fim da curva (positions[i+1]);
     * */
    public static float interpolateCubicBezier(Vec2 p0, Vec2 c0, float curX, Vec2 c1, Vec2 p1)
    {
        float s = curX/(p1.x - p0.x);
        if(s>1) s=1;
        else if(s<0) s=0;
        
        float i_s = 1-s;
        return p0.y * (i_s * i_s * i_s) +
                3 * c0.y * s * (i_s * i_s) +
                3 * c1.y * (s * s) * i_s +
                p1.y * (s*s*s);
    }
}
