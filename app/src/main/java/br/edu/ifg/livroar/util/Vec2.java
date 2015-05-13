package br.edu.ifg.livroar.util;


public class Vec2 {

	public float x;
	public float y;
	
	public Vec2(float x, float y){
		this.x = x;
        this.y = y;
	}
	
	public Vec2 copy(){
		return new Vec2(x, y);
	}
	
	public void set(Vec2 v) {
		this.x = v.x;
		this.x = v.x;
	}
	
	public float dot(Vec2 v){
		return x * v.x + y * v.y;
	}

    public float length(){
        return (float) Math.sqrt((x * x) + (y * y));
    }

	public Vec2 normalize (){
		this.div(length());
		return this;
	}
	
	public Vec2 setLength (float length) {
		normalize();
        mult(length);
		return this;
	}
	
	public Vec2 rotateRad(float angle){
		double sen = Math.sin(angle);
		double cos = Math.cos(angle);
		
		x = (float)(x * cos - y * sen);
        y = (float)(x * sen + y * cos);

		return this;
	}
	
	public Vec2 add(Vec2 v){
		x += v.x;
        y += v.y;
		return this;
	}
	
	public Vec2 add(float n){
		x += n;
        y += n;
		return this;
	}
	
	public Vec2 sub(Vec2 v){
		x -= v.x;
        y -= v.y;
		return this;
	}
	
	public Vec2 sub(float n){
		x -= n;
        y -= n;
		return this;
	}
	
	public Vec2 mult(Vec2 v){
		x *= v.x;
        y *= v.y;
		return this;
	}
	
	public Vec2 mult(float n){
		x *= n;
        y *= n;
		return this;
	}
	
	public Vec2 div(Vec2 v){
		x /= v.x;
        y /= v.y;
		return this;
	}
	
	public Vec2 div(float n){
		x /= n;
        y /= n;
		return this;
	}
	
	public String toString(){
		return "(" + x + "," + y + ")";
	}
	
}
