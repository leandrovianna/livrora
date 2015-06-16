package br.edu.ifg.livroar.scenes.models.attributes;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.scenes.models.ModelPart;
import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by JoaoPaulo on 12/06/2015.
 */
public class NormalAttribute extends ModelPart.Attribute
{
    public NormalAttribute(List<Vec3> normals)
    {
        float[] attrArray = new float[normals.size() * 3];
        int index = 0;
        for (Vec3 v : normals)
        {
            attrArray[index++] = v.x;
            attrArray[index++] = v.y;
            attrArray[index++] = v.z;
        }
        this.setBuffer(attrArray);
    }

    @Override
    public void init(GL10 gl)
    {}

    @Override
    public void enable(GL10 gl)
    {
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
    }

    @Override
    public void use(GL10 gl)
    {
        gl.glNormalPointer(GL10.GL_FLOAT, 12, buffer);
    }

    @Override
    public void disable(GL10 gl)
    {
        gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
    }
}
