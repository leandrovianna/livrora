package br.edu.ifg.livroar.scenes;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.scenes.animations.Animation;
import br.edu.ifg.livroar.util.Vec3;

/**
 * Created by JoaoPaulo on 10/06/2015.
 */
public class SceneObject
{
	public static final String TAG = "SceneObject";

	private Vec3 loc;
	private Vec3 rot;
	private Vec3 scl;

	private int model;

	private List<Animation> animations;
	private int             curAnimIndex;

	public SceneObject ()
	{
		animations = new ArrayList<>();
	}

	public void init (GL10 gl, Scene s, float curTimeNano)
	{
//		animations.get(curAnimIndex).play(Animation.PLAY_MODE_LOOP);
		if(! s.getModel(model).initialized)
			s.getModel(model).init(gl, s);
	}

	public void draw (GL10 gl, Scene s, float curTimeNano)
	{
		if(!animations.isEmpty())
			animations.get(curAnimIndex).update(curTimeNano / 1000000000L, loc,rot,scl);

		gl.glTranslatef(loc.x, loc.y, loc.z);
		gl.glRotatef(rot.x, 1, 0, 0);
		gl.glRotatef(rot.y, 0, 1, 0);
		gl.glRotatef(rot.z, 0, 0, 1);
		gl.glScalef(scl.x, scl.y, scl.z);

		Model m = s.getModel(model);
		if(m != null)
		{
			if(!m.initialized)
				m.init(gl,s);

			m.draw(gl,s);
		}
	}

	public Vec3 getLocation()
	{
		return loc;
	}

	public void setLocation(Vec3 location)
	{
		this.loc = location;
	}

	public Vec3 getRotation()
	{
		return rot;
	}

	public void setRotation(Vec3 rotation)
	{
		this.rot = rotation;
	}

	public Vec3 getScale()
	{
		return scl;
	}

	public void setScale(Vec3 scale)
	{
		this.scl = scale;
	}

	public int getModelId ()
	{
		return model;
	}

	public void setModelId (int id)
	{
		this.model = id;
	}

	public void addAnimation(Animation a)
	{
		animations.add(a);
	}

	public void setCurAnimation(int index)
	{
		if(index >= 0 && index < animations.size())
			curAnimIndex = index;
	}

	//	@Override
//	public String toString() {
//		int vCount = 0;
//		StringBuilder partsBuilder = new StringBuilder();
//		for (int i = 0; i < parts.size(); i++)
//		{
//			vCount += parts.get(i).getVertexCount();
//			partsBuilder.append("Part #" + i + ": "
//			                    + parts.get(i).toString() + "\n");
//		}
//		StringBuilder animsBuilder = new StringBuilder();
//		for (int i = 0; i < animations.size(); i++)
//		{
//			animsBuilder.append("Animation #" + i + ": "
//			                    + animations.get(i).toString() + "\n");
//		}
//
//		return "SceneObject:" +
//		       " Total vertex count = " + vCount +
//		       " ;\n Parts: count = " + parts.size() +
//		       partsBuilder.toString() +
//		       " ; Animations: count = " + animations.size() +
//		       animsBuilder.toString() +
//		       " ; loc: " + location.toString() +
//		       " ; rot: " + rotation.toString() +
//		       " ; scale: " + scale.toString();
//	}
}
