package br.edu.ifg.livroar;

import javax.microedition.khronos.opengles.GL10;

import edu.dhbw.andar.ARObject;

/**
 * Created by leandro on 28/04/15.
 */
public class LivroObject extends ARObject {

    public LivroObject(String name, String patternName) {
        super(name, patternName, 80.0, new double[]{0,0});
    }
    @Override
    public void init(GL10 gl10) {

    }
}
