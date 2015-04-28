package br.edu.ifg.livroar;

import java.io.Serializable;

/**
 * Created by leandro on 28/04/15.
 */
public class RGBColor implements Serializable {

    private int red;
    private int green;
    private int blue;

    public RGBColor() {
        red = 0;
        green = 0;
        blue = 0;
    }

    public RGBColor(int red, int green, int blue) {
        checkValue(red);
        checkValue(green);
        checkValue(blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
    }

    public int getRed() {
        return red;
    }

    public void setRed(int red) {
        checkValue(red);
        this.red = red;
    }

    public int getGreen() {
        return green;
    }

    public void setGreen(int green) {
        checkValue(green);
        this.green = green;
    }

    public int getBlue() {
        return blue;
    }

    public void setBlue(int blue) {
        checkValue(blue);
        this.blue = blue;
    }

    private void checkValue(int value) {
        if (value > 255 && value < 0)
            throw new IllegalArgumentException("O argumento deve ser de 0 a 255");
    }

    public float[] getFloatArray() {
        return new float[]{red, green, blue};
    }
}
