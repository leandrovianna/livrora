package br.edu.ifg.livroar.util;

/**
 * Created by JoaoPaulo on 02/04/2015.
 */
public class Vec3 {

    public float x;
    public float y;
    public float z;

    public Vec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3 copy(){
        return new Vec3(x, y, z);
    }

    public void set(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void set(Vec3 v) {
        this.x = v.x;
        this.x = v.x;
        this.z = v.z;
    }

    public float dot(Vec3 v){
        return x * v.x + y * v.y + z * v.z;
    }

    public float length(){
        return (float) Math.sqrt((x * x) + (y * y) + (z * z));
    }

    public Vec3 normalize (){
        this.div(length());
        return this;
    }

    public Vec3 setLength (float length) {
        normalize();
        mult(length);
        return this;
    }

    public Vec3 add(Vec3 v){
        x += v.x;
        y += v.y;
        z += v.z;
        return this;
    }

    public Vec3 add(float n){
        x += n;
        y += n;
        z += n;
        return this;
    }

    public Vec3 add(Vec2 v) {
        x += v.x;
        y += v.y;
        return this;
    }

    public Vec3 sub(Vec3 v){
        x -= v.x;
        y -= v.y;
        z -= v.z;
        return this;
    }

    public Vec3 sub(float n){
        x -= n;
        y -= n;
        z -= n;
        return this;
    }

    public Vec3 mult(Vec3 v){
        x *= v.x;
        y *= v.y;
        z *= v.z;
        return this;
    }

    public Vec3 mult(float n){
        x *= n;
        y *= n;
        z *= n;
        return this;
    }

    public Vec3 div(Vec3 v){
        x /= v.x;
        y /= v.y;
        z /= v.z;
        return this;
    }

    public Vec3 div(float n){
        x /= n;
        y /= n;
        z /= n;
        return this;
    }


    public String toString(){
        return "(" + x + "," + y + "," + z + ")";
    }
}
