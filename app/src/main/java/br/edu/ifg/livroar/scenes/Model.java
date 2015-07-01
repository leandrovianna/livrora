package br.edu.ifg.livroar.scenes;

import android.util.Log;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.util.RGBColor;
import br.edu.ifg.livroar.util.Utils;
import br.edu.ifg.livroar.util.Vec2;
import br.edu.ifg.livroar.util.Vec3;


/**
 * Created by JoaoPaulo on 22/06/2015.
 */
public class Model
{
	private static final String TAG           = "Model";
	public static final  int POS_DATA_SIZE    = 3;
	public static final  int NORMAL_DATA_SIZE = 3;
	public static final  int UV_DATA_SIZE     = 2;
	public static final  int COLOR_DATA_SIZE  = 3;

	public FloatBuffer dataBuffer;
	public boolean     hasNormals;
	public boolean     hasUvs;
	public boolean     hasColors;
	public boolean     initialized;

	public int stride;
	public int normalDataOffset;
	public int uvDataOffset;
	public int colorDataOffset;

	public List<Part> parts;

	public Model (List<Vec3> positions,
	              List<Vec3> normals,
	              List<Vec2> uvs,
	              List<RGBColor> colors,
	              List<Part> parts)
	{
		this.parts = parts;
		setDataBuffer(positions, normals, uvs, colors);
	}

	public void setDataBuffer (List<Vec3> positions,
	                           List<Vec3> normals,
	                           List<Vec2> uvs,
	                           List<RGBColor> colors)
	{
		hasNormals = normals.size() == positions.size();
		hasUvs = uvs.size() == positions.size();
		hasColors = colors.size() == positions.size();

		stride = POS_DATA_SIZE;
		if(hasNormals)
		{
			stride += NORMAL_DATA_SIZE;
			normalDataOffset = stride * 4;
		}
		if(hasUvs)
		{
			stride += UV_DATA_SIZE;
			uvDataOffset = stride * 4;
		}
		if(hasColors)
		{
			stride += COLOR_DATA_SIZE;
			colorDataOffset = stride * 4;
		}
		stride *= 4;

		float[] vertexData = new float[positions.size() * POS_DATA_SIZE +
		                               (hasNormals ? normals.size() * NORMAL_DATA_SIZE : 0) +
		                               (hasUvs ? uvs.size() * UV_DATA_SIZE : 0) +
		                               (hasColors ? colors.size() * COLOR_DATA_SIZE : 0)];
		Log.d(TAG, "data size: " + vertexData.length);

		int index = 0;
		for (int i = 0; i < positions.size(); i++)
		{
			vertexData[index++] = positions.get(i).x;
			vertexData[index++] = positions.get(i).y;
			vertexData[index++] = positions.get(i).z;
			if(hasNormals)
			{
				vertexData[index++] = normals.get(i).x;
				vertexData[index++] = normals.get(i).y;
				vertexData[index++] = normals.get(i).z;
			}
			if(hasUvs)
			{
				vertexData[index++] = uvs.get(i).x;
				vertexData[index++] = uvs.get(i).y;
			}
			if(hasColors)
			{
				vertexData[index++] = colors.get(i).getRed();
				vertexData[index++] = colors.get(i).getGreen();
				vertexData[index++] = colors.get(i).getBlue();
			}
		}

		dataBuffer = Utils.toBuffer(vertexData);
	}

	public void init(GL10 gl, Scene s)
	{
		for (Part p : parts)
			p.init(gl, s);
		initialized = true;
	}

	public void draw(GL10 gl, Scene s)
	{
//		Log.d(TAG, "draw");
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		dataBuffer.position(0);
		gl.glVertexPointer(POS_DATA_SIZE, GL10.GL_FLOAT, stride, dataBuffer);

		if(hasNormals)
		{
			gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);
			dataBuffer.position(normalDataOffset);
			gl.glNormalPointer(GL10.GL_FLOAT, stride, dataBuffer);
		}
		if(hasUvs)
		{
			gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
			dataBuffer.position(uvDataOffset);
			gl.glTexCoordPointer(UV_DATA_SIZE, GL10.GL_FLOAT, stride, dataBuffer);
		}
//		if(hasColors)
//		{
//			gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
//			dataBuffer.position(colorDataOffset);
//			gl.glColorPointer(COLOR_DATA_SIZE, GL10.GL_FLOAT, stride, dataBuffer);
//		}

		Material mat;
		for (Part p : parts)
		{
			mat = s.getMaterial(p.material);
			if(mat!=null)
			{
				if(mat.hasTexture())
				{
					gl.glEnable(GL10.GL_TEXTURE_2D);
					gl.glBindTexture(GL10.GL_TEXTURE_2D, mat.getTexureId());
				}
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_AMBIENT, mat.getAmbientBuffer());
				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, mat.getDiffuseBuffer());
//				gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, mat.getSpecularBuffer());
//				gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, mat.getShininess());
			}
			gl.glDrawElements(GL10.GL_TRIANGLES, p.vertexCount, GL10.GL_UNSIGNED_SHORT, p.indexBuffer);
		}

		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		if(hasNormals)
			gl.glDisableClientState(GL10.GL_NORMAL_ARRAY);
		if(hasUvs)
			gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
//		if(hasColors)
//			gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
	}

	public static class Part
	{
		public ShortBuffer indexBuffer;
		public int material = - 1;
		public int vertexCount;

		public Part (short[] indices)
		{
			indexBuffer = Utils.toBuffer(indices);
			vertexCount = indices.length;
		}

		public void init (GL10 gl, Scene s)
		{
			if(! s.getMaterial(material).hasTexture())
				s.getMaterial(material).setTexureId(gl);
		}

		public int getMaterial ()
		{
			return material;
		}

		public void setMaterialId (int material)
		{
			this.material = material;
		}
	}

}
