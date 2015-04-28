package br.edu.ifg.livroar;

import javax.microedition.khronos.opengles.GL10;

import edu.dhbw.andar.pub.SimpleBox;

/**
 * Created by leandro on 28/04/15.
 */
public class CarObject extends LivroObject {

    private SimpleBox box;

    public CarObject() {
        super("car", "android.patt");
    }

    @Override
    public void init(GL10 gl10) {

    }

    @Override
    public synchronized void draw(GL10 gl) {
        super.draw(gl);
    }
}
