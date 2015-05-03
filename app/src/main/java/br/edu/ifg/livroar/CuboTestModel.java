package br.edu.ifg.livroar;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.model.Model;
import br.edu.ifg.livroar.util.RGBColor;
import edu.dhbw.andar.pub.SimpleBox;
import edu.dhbw.andar.util.GraphicsUtil;

/**
 * Created by leandro on 28/04/15.
 */
public class CuboTestModel implements Model {

    private SimpleBox box;
    private final FloatBuffer matAmbiente;
    private final FloatBuffer matFlash;
    private final FloatBuffer matDifusao;
    private final FloatBuffer matFlashBrilho;

    public CuboTestModel(RGBColor color) {

        box = new SimpleBox();

        float matBrilho[] = {50.0f};

        matAmbiente = GraphicsUtil.makeFloatBuffer(color.getFloatArray());
        matFlash = GraphicsUtil.makeFloatBuffer(color.getFloatArray());
        matDifusao = GraphicsUtil.makeFloatBuffer(color.getFloatArray());
        matFlashBrilho = GraphicsUtil.makeFloatBuffer(matBrilho);
    }

    @Override
    public void init(GL10 gl) {

    }

    @Override
    public synchronized void draw(GL10 gl) {

        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, matFlash);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, matFlashBrilho);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, matDifusao);
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, matAmbiente);

        box.draw(gl);
    }
}
