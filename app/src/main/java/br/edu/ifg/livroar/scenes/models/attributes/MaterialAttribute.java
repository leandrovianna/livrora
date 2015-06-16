package br.edu.ifg.livroar.scenes.models.attributes;

import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.scenes.models.Material;
import br.edu.ifg.livroar.scenes.models.ModelPart;
import br.edu.ifg.livroar.util.Vec2;

/**
 * Created by JoaoPaulo on 12/06/2015.
 */
public class MaterialAttribute extends ModelPart.Attribute
{
    private Material material;

    public MaterialAttribute(Material material, List<Vec2> uvs) {
        this.material = material;
        if(uvs.size()>0)
        {
            float[] elArray = new float[uvs.size()*2];
            int index = 0;
            for (Vec2 v : uvs)
            {
                elArray[index++] = v.x;
                elArray[index++] = v.y;
            }
            setBuffer(elArray);
        }
    }

    @Override
    public void init(GL10 gl) {
        material.setTexureId(gl);
    }

    @Override
    public void enable(GL10 gl)
    {
        if(material.hasTexture())
        {
            gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glEnable(GL10.GL_TEXTURE_2D);
        }
    }

    @Override
    public void use(GL10 gl)
    {
        if(material.hasTexture())
        {
            gl.glTexCoordPointer(2, GL10.GL_FLOAT, 8, buffer);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, material.getTexureId());
        }
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, material.getAmbientBuffer());
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, material.getDiffuseBuffer());
//        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, material.getSpecularBuffer());
//        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, material.getShininess());
    }

    @Override
    public void disable(GL10 gl)
    {
        if(material.hasTexture())
        {
            gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
            gl.glDisable(GL10.GL_TEXTURE_2D);
        }
    }
}
