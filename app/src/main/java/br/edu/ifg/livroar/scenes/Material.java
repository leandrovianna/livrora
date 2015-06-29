package br.edu.ifg.livroar.scenes;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;
import android.util.Log;

import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import br.edu.ifg.livroar.App;
import br.edu.ifg.livroar.util.RGBColor;

/**
 * Created by JoaoPaulo on 20/04/2015.
 */
public class Material {

    private String name = "Null";
    private float shininess = 0;
    private RGBColor ambient = new RGBColor(1,0,1);
    private FloatBuffer ambientBuffer;
    private RGBColor diffuse = new RGBColor(1,0,1);
    public FloatBuffer diffuseBuffer;
    private RGBColor specular = new RGBColor(1,0,1);
    private FloatBuffer specularBuffer;
    private float transparency = 1;
    private int illum = 1;
    private int texureId = -1;
    private Bitmap texture = null;

    public Material(String name) {
        this.name = name;
        setAmbient(ambient.getRed(), ambient.getGreen(), ambient.getBlue());
        setDiffuse(diffuse.getRed(), diffuse.getGreen(), diffuse.getBlue());
        setSpecular(specular.getRed(), specular.getGreen(), specular.getBlue());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getShininess() {
        return shininess;
    }

    public FloatBuffer getAmbientBuffer() {
        return ambientBuffer;
    }

    public FloatBuffer getDiffuseBuffer() {
        return diffuseBuffer;
    }

    public FloatBuffer getSpecularBuffer() {
        return specularBuffer;
    }

    public void setShininess(float shininess) {
        this.shininess = shininess;
    }

    public RGBColor getAmbient() {
        return ambient;
    }

    public void setAmbient(float r, float g, float b) {
        ambient = new RGBColor(r,g,b);
        ambientBuffer = ByteBuffer
                .allocateDirect(12)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(new float[]{r, g, b});
        ambientBuffer.position(0);
    }

    public RGBColor getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(float r, float g, float b) {
        diffuse = new RGBColor(r,g,b);
        diffuseBuffer = ByteBuffer
                .allocateDirect(12)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(new float[]{r, g, b});
        diffuseBuffer.position(0);
    }

    public RGBColor getSpecular() {
        return specular;
    }

    public void setSpecular(float r, float g, float b) {
        specular = new RGBColor(r,g,b);
        specularBuffer = ByteBuffer
                .allocateDirect(12)
                .order(ByteOrder.nativeOrder())
                .asFloatBuffer()
                .put(new float[]{r, g, b});
        specularBuffer.position(0);
    }

    public float getTransparency() {
        return transparency;
    }

    public void setTransparency(float transparency) {
        this.transparency = transparency;
    }

    public int getIllum() {
        return illum;
    }

    public void setIllum(int illum) {
        this.illum = illum;
    }

    public int getTexureId() {
        return texureId;
    }

    public void setTexureId(GL10 gl) {
        if(texture!=null){
            int[] texIds = new int[1];
            gl.glGenTextures(1, texIds, 0);
            gl.glBindTexture(GL10.GL_TEXTURE_2D, texIds[0]);
            texureId = texIds[0];
            Log.d("Material", "Texture ID: " + texureId);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                    GL10.GL_TEXTURE_MIN_FILTER,
                    GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                    GL10.GL_TEXTURE_MAG_FILTER,
                    GL10.GL_NEAREST);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                    GL10.GL_TEXTURE_WRAP_S,
                    GL10.GL_REPEAT);
            gl.glTexParameterf(GL10.GL_TEXTURE_2D,
                    GL10.GL_TEXTURE_WRAP_T,
                    GL10.GL_REPEAT);
            GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, texture,0);
            texture.recycle();
        }
    }

    public boolean hasTexture() {
        return texureId != -1;
    }
	public Bitmap getTexture() {
        return texture;
    }

    public void setTexture(String texName) {
        try {
            InputStream texIn = App.getContext().getAssets().open("textures/"+texName);
            texture = BitmapFactory.decodeStream(texIn);
            texIn.close();
            Log.d("Material", "Bitmap " + texName + " carregado com sucesso");
        }catch (Exception e){
            Log.e("Material", "Falha ao carregar bitmap " + texName + ":");
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "Name: " + name +
                "\nShininess: " + shininess +
                "\nAmbientColor: " + ambient.getRed() + ", " + ambient.getGreen() + ", " + ambient.getBlue() +
                "\nDiffuseColor: " + diffuse.getRed() + ", " + diffuse.getGreen() + ", " + diffuse.getBlue() +
                "\nSpecularColor: " + specular.getRed() + ", " + specular.getGreen() + ", " + specular.getBlue() +
                "\nTransparency: " + transparency +
                "\nIllum: " + illum;
    }
}
