package br.edu.ifg.livroar.model;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by leandro on 30/04/15.
 */
public interface Model {

    public void init(GL10 gl);
    public void draw(GL10 gl);
}
