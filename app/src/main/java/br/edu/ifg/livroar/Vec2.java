package br.edu.ifg.livroar;


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
        return (float) Math.sqrt((x*x) + (y*y));
    }

	public void normalize (){
		this.div(length());
	}
	
	public void setLength (float length) {
		normalize();
        mult(length);
	}
	
	public void rotateRad(float angle){
		
		double sen = Math.sin(angle);
		double cos = Math.cos(angle);
		
		x = (float)(x * cos - y * sen);
        y = (float)(x * sen + y * cos);
	}
	
	public void add(Vec2 v){
		x += v.x;
        y += v.y;
	}
	
	public void add(float n){
		x += n;
        y += n;
	}
	
	public void sub(Vec2 v){
		x -= v.x;
        y -= v.y;
	}
	
	public void sub(float n){
		x -= n;
        y -= n;
	}
	
	public void mult(Vec2 v){
		x *= v.x;
        y *= v.y;
	}
	
	public void mult(float n){
		x *= n;
        y *= n;
	}
	
	public void div(Vec2 v){
		x /= v.x;
        y /= v.y;
	}
	
	public void div(float n){
		x /= n;
        y /= n;
	}
	
	public String toString(){
		return "(" + x + "," + y + ")";
	}
	
}
