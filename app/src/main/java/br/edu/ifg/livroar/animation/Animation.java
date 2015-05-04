package br.edu.ifg.livroar.animation;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.model.Object3D;

/**
 * Created by JoaoPaulo on 04/05/2015.
 */
public interface Animation {

    public void update(Object3D object, GL10 gl);

}
