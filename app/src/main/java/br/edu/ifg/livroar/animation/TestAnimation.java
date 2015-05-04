package br.edu.ifg.livroar.animation;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.MainActivity;
import br.edu.ifg.livroar.model.Object3D;
import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by JoaoPaulo on 04/05/2015.
 */
public class TestAnimation implements Animation {

    private int times = 0;
    private float zDisp = .9f;

    @Override
    public void update(Object3D object, GL10 gl) {
        times++;

        if(times % 30 == 0){
            zDisp *= -1;
        }

        gl.glScalef(.1f,.1f,.1f);

        object.getRotation().z += .5f;
        rotatePoint(object.getPosition(), object.getRotation().z);

        object.setPosition(object.getPosition().x, object.getPosition().y, object.getPosition().z );

        gl.glTranslatef(object.getPosition().x, object.getPosition().y, object.getPosition().z);
    }

    public void rotatePoint(Vec3 point, double angle){
        double angleRad = Math.toRadians(angle);
        point.x = (float)(point.x * Math.cos(angleRad) - point.y * Math.sin(angleRad));
        point.y = (float)(point.y * Math.cos(angleRad) + point.x * Math.sin(angleRad));
    }

}
