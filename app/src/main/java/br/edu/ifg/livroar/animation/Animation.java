package br.edu.ifg.livroar.animation;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.model.Object3D;
import br.edu.ifg.livroar.util.Vec3;
import edu.dhbw.andar.ARObject;

/**
 * Created by JoaoPaulo on 04/05/2015.
 */
public interface Animation {

    public void update(Vec3 objectPosition, Vec3 objectRotation, GL10 gl);

}
