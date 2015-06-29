package br.edu.ifg.livroar.scenes;

import android.util.Log;

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

	public void init (GL10 gl, Scene s, float curTimeNano)
	{
//		animations.get(curAnimIndex).play(Animation.PLAY_MODE_LOOP);
		if(! s.getModel(model).initialized)
			s.getModel(model).init(gl, s);
	}

	public void draw (GL10 gl, Scene s, float curTimeNano)
	{
//		if(animations.size() > 0 && curAnimIndex < animations.size())
//			animations.get(curAnimIndex).update(curTimeNano,loc,rot,scl);
		gl.glTranslatef(loc.x, loc.y, loc.z);
		gl.glRotatef(rot.x, 1, 0, 0);
		gl.glRotatef(rot.y, 0, 1, 0);
		gl.glRotatef(rot.z, 0, 0, 1);
		gl.glScalef(scl.x, scl.y, scl.z);

		Model m = s.getModel(model);
		if(m != null)
		{
			if(m.initialized)
				m.draw(gl,s);
			else
				m.init(gl,s);
		}
	}

	public void addAnimation(Animation a){
		animations.add(a);
	}

	public int getCurAnimIndex() {
		return curAnimIndex;
	}

//	public void setCurAnimIndex(int curAnimIndex) {
//		if(animations.size() > curAnimIndex)
//		{
//			this.curAnimIndex = curAnimIndex;
//			animations.get(curAnimIndex).setStartTime(System.nanoTime());
//		}
//		else
//			Log.w(TAG, "Falha ao setar animacao atual. Animations size: "
//			           + animations.size() + ", index inserido: " + curAnimIndex);
//	}

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

	public int getMeshId ()
	{
		return model;
	}

	public void setMeshId (int id)
	{
		this.model = id;
	}

	public List<Animation> getAnimations ()
	{
		return animations;
	}

	public void setAnimations (List<Animation> animations)
	{
		this.animations = animations;
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
