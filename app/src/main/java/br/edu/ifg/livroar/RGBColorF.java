package br.edu.ifg.livroar;

import java.io.Serializable;

/**
 * Created by leandro on 28/04/15.
 */
public class RGBColorF implements Serializable {

    private float red;
    private float green;
    private float blue;

    public RGBColorF() {
        red = 0;
        green = 0;
        blue = 0;
    }

    public RGBColorF(float red, float green, float blue) {
        checkValue(red);
        checkValue(green);
        checkValue(blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public float getRed() {
        return red;
    }

    public void setRed(float red) {
        checkValue(red);
        this.red = red;
    }

    public float getGreen() {
        return green;
    }

    public void setGreen(float green) {
        checkValue(green);
        this.green = green;
    }

    public float getBlue() {
        return blue;
    }

    public void setBlue(float blue) {
        checkValue(blue);
        this.blue = blue;
    }

    private void checkValue(float value) {
        if (value > 1 && value < 0)
            throw new IllegalArgumentException("O argumento deve ser de 0 a 1");
    }

    public float[] getFloatArray() {
        return new float[]{red, green, blue};
    }
}
