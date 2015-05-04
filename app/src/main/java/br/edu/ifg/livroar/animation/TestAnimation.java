package br.edu.ifg.livroar.animation;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.MainActivity;
import br.edu.ifg.livroar.model.Object3D;
import br.edu.ifg.livroar.util.Vec3;
import edu.dhbw.andar.ARObject;

/**
 * Created by JoaoPaulo on 04/05/2015.
 */
public class TestAnimation implements Animation {

    private int times = 0;
    private float zDisp = 3f;

    @Override
    public void update(Vec3 objectPosition, Vec3 objectRotation, GL10 gl) {
        times++;

        if(times % 30 == 0){
            zDisp *= -1;
        }

        objectPosition.z += zDisp;
        objectRotation.z += .05f;
        rotatePoint(objectPosition, objectRotation.z);

//        objectPosition.set(objectPosition.x, objectPosition.y, objectPosition.z);
        gl.glTranslatef(objectPosition.x, objectPosition.y, objectPosition.z);
        gl.glRotatef(objectRotation.x, 1, 0, 0);
        gl.glRotatef(objectRotation.y, 0, 1, 0);
        gl.glRotatef(objectRotation.z, 0, 0, 1);
    }

    public void rotatePoint(Vec3 point, double angle){
        double angleRad = Math.toRadians(angle);
        Vec3 copy = point.copy();
        point.x = (float)(copy.x * Math.cos(angleRad) - copy.y * Math.sin(angleRad));
        point.y = (float)(copy.y * Math.cos(angleRad) + copy.x * Math.sin(angleRad));
    }

}
