package br.edu.ifg.livroar.util;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
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
     * @param p0x posicao de inicio da curva em x(postions[i].x);
     * @param p0y posicao de inicio da curva em y(postions[i].y);
     * @param c0x primeiro ponto de controle da curva em x(outtangents[i].x);
     * @param c0x primeiro ponto de controle da curva em y(outtangents[i].y);
     * @param curX valor atual de x para o qual encontrar valor de y correspondente;
     * @param c1x segundo ponto de controle da curva em x(intangents[i+1].x);
     * @param c1y segundo ponto de controle da curva em y(intangents[i+1].y);
     * @param p1x posicao de fim da curva em x(positions[i+1].x);
     * @param p1x posicao de fim da curva em y(positions[i+1].y);
     * */
    public static float interpolateCubicBezier(float p0x, float p0y,
                                                float c0x, float c0y,
                                                float curX,
                                                float c1x, float c1y,
                                                float p1x, float p1y)
    {
        float s = getCubicBezierS(curX, p0x, c0x, c1x, p1x);
        float i_s = 1-s;
        return p0y * (i_s * i_s * i_s) +
                3 * c0y * s * (i_s * i_s) +
                3 * c1y * (s * s) * i_s +
                p1y * (s*s*s);
    }

    private static float getCubicBezierS (float curX, float p0x, float c0x, float c1x, float p1x)
    {
        // Diferencas minimas
        if (curX - p0x < 1.0e-20)
            return 0.0f;
        if (p1x - curX < 1.0e-20)
            return 1.0f;

        int step = 0;
        float u = 0.0f; float v = 1.0f;

        while (step < 100) // Iteracao maxima
        {
            // Algoritmo de De-Casteljau
            double a = (p0x + c0x)*0.5f;
            double b = (c0x + c1x)*0.5f;
            double c = (c1x + p1x)*0.5f;
            double d = (a + b)*0.5f;
            double e = (b + c)*0.5f;
            double f = (d + e)*0.5f;

            if (Math.abs(f - curX) < 1.0e-09) // valor proximo ao desejado
            {
                float r = ((u + v)*0.5f);
                return r;
            }

            if (f < curX)
            {
                p0x = (float) f;
                c0x = (float) e;
                c1x = (float) c;
                u = (u + v)*0.5f;
            }
            else
            {
                c0x = (float) a; c1x = (float) d; p1x = (float) f; v = (u + v)*0.5f;
            }

            step++;
        }

        float r = ((u + v)*0.5f);
        if(r > 1) r = 1;
        else if(r < 0) r = 0;
        return r;
    }

	public static FloatBuffer toBuffer(float[] data)
	{
		FloatBuffer buffer = ByteBuffer
				.allocateDirect(data.length * 4)
				.order(ByteOrder.nativeOrder())
				.asFloatBuffer()
				.put(data);
		buffer.position(0);
		return buffer;
	}

	public static ShortBuffer toBuffer(short[] data)
	{
		ShortBuffer buffer = ByteBuffer
				.allocateDirect(data.length * 2)
				.order(ByteOrder.nativeOrder())
				.asShortBuffer ()
				.put(data);
		buffer.position(0);
		return buffer;
	}
}
