package br.edu.ifg.livroar.scenes.models;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by JoaoPaulo on 11/06/2015.
 *
 *
 */
public class ModelPart
{
    private int vertexCount;
    private List<Attribute> attributes;

    public ModelPart(int vertexCount)
    {
        this.vertexCount = vertexCount;
        attributes = new ArrayList<>();
    }

    public void init(GL10 gl)
    {
        for (Attribute a : attributes)
            a.init(gl);
    }

    public void draw(GL10 gl)
    {
        for (Attribute a : attributes)
        {
            a.enable(gl);
            a.use(gl);
        }

        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, vertexCount);

        for (Attribute a : attributes)
            a.disable(gl);
    }

    public void addAttribute(Attribute a)
    {
        attributes.add(a);
    }

    public int getVertexCount()
    {
        return vertexCount;
    }

    public void setVertexCount(int vertexCount)
    {
        this.vertexCount = vertexCount;
    }

    public static abstract class Attribute
    {
        protected FloatBuffer buffer;

        public abstract void init(GL10 gl);
        public abstract void enable(GL10 gl);
        public abstract void use(GL10 gl);
        public abstract void disable(GL10 gl);

        public void setBuffer(float[] elementArray)
        {
            buffer = ByteBuffer
                    .allocateDirect(elementArray.length * 4)
                    .order(ByteOrder.nativeOrder())
                    .asFloatBuffer()
                    .put(elementArray);
            buffer.position(0);
        }

    }
}
