package br.edu.ifg.livroar.util;

public class Vec3
{
	public static final Vec3 ZERO = new Vec3(0, 0, 0);
	public float x, y, z;
	
	public Vec3(float x, float y, float z) 
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Vec3(Vec3 v)
	{
		this(v.x, v.y, v.z);
	}

	public void zero()
	{
		x = y = z = 0.0f;
	}
	
	public Vec3 add(Vec3 v)
	{
		return new Vec3(x+v.x, y+v.y, z+v.z);
	}

	public Vec3 add(float n)
	{
		return new Vec3(x+n, y+n, z+n);
	}

	public Vec3 sub(Vec3 v)
	{
		return new Vec3(x-v.x, y-v.y, z-v.z);
	}

	public Vec3 sub(float n)
	{
		return new Vec3(x-n, y-n, z-n);
	}

	public Vec3 mult(float n)
	{
		return new Vec3(x*n, y*n, z*n);
	}

	public Vec3 div(float n)
	{
		return new Vec3(x/n, y/n, z/n);
	}

	public float magnitude()
	{
		return (float) Math.sqrt((x*x)+(y*y)+(z*z));
	}

	public void normalize()
	{
		float mag = this.magnitude();
		if(mag > 0)
		{			
			Vec3 r = this.div(mag);
			x = r.x;
			y = r.y;
			z = r.z;
		}
	}

	public float dot(Vec3 v)
	{
		return (x*v.x) + (y*v.y) + (z*v.z);
	}
	
	public boolean equals(Vec3 v)
	{
		return (x==v.x) && (y==v.y) && (z==v.z);
	}

    public Vec2 toVec2()
    {
        return new Vec2(x, y);
    }

	public static float magnitude(Vec3 v)
	{
		return (float) Math.sqrt((v.x*v.x)+(v.y*v.y)+(v.z*v.z));
	}

	public static Vec3 cross(Vec3 a, Vec3 b)
	{
		float x = a.y*b.z - a.z*b.y;
		float y = a.z*b.x - a.x*b.z;
		float z = a.x*b.y - a.y*b.x;
		return new Vec3(x, y, z);
	}
	
	public static float distance(Vec3 a, Vec3 b)
	{
		float dx = a.x - b.x;
		float dy = a.y - b.y;
		float dz = a.z - b.z;
		return (float) Math.sqrt((dx*dx) + (dy*dy) + (dz*dz));
	}

	@Override
	public String toString ()
	{
		return "(" + x + ", " + y + ", " + z + ")";
	}
}
