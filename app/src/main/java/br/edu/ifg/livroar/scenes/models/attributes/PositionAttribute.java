package br.edu.ifg.livroar.scenes.models.attributes;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.scenes.models.ModelPart;
import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by JoaoPaulo on 11/06/2015.
 */
public class PositionAttribute extends ModelPart.Attribute
{
    public PositionAttribute(List<Vec3> positions)
    {
        float[] attrArray = new float[positions.size() * 3];
        int index = 0;
        for (Vec3 v : positions)
        {
            attrArray[index++] = v.x;
            attrArray[index++] = v.y;
            attrArray[index++] = v.z;
        }
        this.setBuffer(attrArray);
    }

    @Override
    public void init(GL10 gl) {}

    @Override
    public void enable(GL10 gl) {
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
    }

    @Override
    public void use(GL10 gl)
    {
        gl.glVertexPointer(3, GL10.GL_FLOAT, 12, buffer);
    }

    @Override
    public void disable(GL10 gl) {
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
