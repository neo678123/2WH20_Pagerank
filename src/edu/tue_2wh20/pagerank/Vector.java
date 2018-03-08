package edu.tue_2wh20.pagerank;

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
	
	public static Vector multiply(Matrix M, Vector v) {
		Vector w = new Vector(v.dim);
		for(int x = 0; x < M.sizeX; x++) {
			double sum = 0;
			for(int y = 0; y < M.sizeY; y++) {
				sum += M.M[y][x] * v.v[y];
			}
			w.v[x] = sum;
		}
		return w;
	}
	
	public void print() {
		for(int i = 0; i < dim; i++) System.out.println(v[i]);
	}
	
	public double sum() {
		return Methods.sum(0, dim-1, v);
	}
	
	public void divideBy(double n) {
		for(int i = 0; i < dim; i++) v[i] /= n;
	}
	
	public void add(Vector v) {
		for(int i = 0; i < dim; i++) this.v[i] += v.v[i];
	}
}
