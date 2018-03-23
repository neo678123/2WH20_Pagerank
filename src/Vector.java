// class for general n-dimensional vectors
public class Vector {
	
	/* Variables */
	
	public double[] v;
	protected int dim;
	
	/* Constructors */
	
	// Empty constructor
	public Vector() {}
	
	// Initializes vector as (1, 0, ..., 0)^T
	public Vector(int dim) {
		this.dim = dim;
		v = new double[dim];
		v[0] = 1;
	}
	
	// General purpose constructor
	public Vector(double... element) {
		dim = element.length;
		v = new double[dim];
		System.arraycopy(element, 0, v, 0, dim);
	}
	
	/* Getters/setters */
	
	public void set(Vector v) {
		dim = v.dim;
		this.v = new double[dim];
		System.arraycopy(v.v, 0, this.v, 0, dim);
	}
	
	/* Methods */
	
	public void print() {
		for(int i = 0; i < dim; i++) System.out.println(v[i]);
	}
	
	public static void printCompare(Vector v, Vector w) {
		Vector u = new Vector(v.dim);
		u.set(v);
		u.sub(w);
		
		System.out.println("random walk  |  eigenvector  |  difference");
		for(int i = 0; i < v.dim; i++) System.out.println(Math.round(1000*v.v[i])/1000d + "  |  " + 
														  Math.round(1000*w.v[i])/1000d + "  |  " + 
														  Math.round(1000*u.v[i])/1000d);
	}
	
	// Returns the sum of the components of this
	public double sum() {
		double sum = 0;
		for(double x : v) {
			sum += x;
		}
		return sum;
	}
	
	// Returns the sum of the absolute values of the difference
	// between the components of v1 and v2: 
	// $\sum_i |(v1)_i - (v2)_i| $
	public static double sumAbsDiff(Vector v1, Vector v2) {
		double out = 0;
		
		Vector delta = new Vector(v1.dim);
		delta.set(v1);
		delta.sub(v2);
		
		for(double x : delta.v) {
			out += Math.abs(x);
		}
		
		return out;
	}
	
	// Add v to this
	public void add(Vector v) {
		for(int i = 0; i < dim; i++) this.v[i] += v.v[i];
	}
	
	// Subtract v from this
	public void sub(Vector v) {
		for(int i = 0; i < dim; i++) this.v[i] -= v.v[i];
	}
	
	// Divide this by n
	public void divideBy(double n) {
		for(int i = 0; i < dim; i++) v[i] /= n;
	}
}
