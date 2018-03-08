package edu.tue_2wh20.pagerank;

// A class for general n×m matrices of doubles.
public class Matrix {
	
	/* Variables */	
	
	// m_ij = M[j][i] which is not confusing at all
	public double[][] M;
	protected int sizeX, sizeY;
	
	/* Constructors */
	
	// Empty constructor
	public Matrix() {}
	
	// Constructor to initialize n×m matrix of zeroes
	public Matrix(int sizeX, int sizeY) {
		// Java initializes doubles as 0.0d by default
		// (no need to manually set everything to 0)
		M = new double[sizeY][sizeX];
		this.sizeX = sizeX;
		this.sizeY = sizeY;
	}
	
	// General purpose constructor
	public Matrix(double[]... element) {
		M = Methods.clone2dArray(element);
		sizeX = M[0].length;
		sizeY = M.length;
	}
	
	/* Getters/setters */
	
	// Assign matrix to another one without memory glitches
	protected void set(Matrix m) {
		M = Methods.clone2dArray(m.M);
		sizeX = M[0].length;
		sizeY = M.length;
	}
	
	/* Methods */
	
	// Multiplies matrices using the standard algorithm
	public static Matrix multiply(Matrix M1, Matrix M2) {
		// Return nothing when multiplication is impossible
		if(M1.sizeY != M2.sizeX)
			return null;
		
		// Calculate M1 * M2 using standard sum(row * collumn)
		Matrix out = new Matrix(M1.sizeX, M2.sizeY);
		for(int y = 0; y < out.sizeY; y++) {
			for(int x = 0; x < out.sizeX; x++) {
				for(int k = 0; k < M1.sizeY; k++) {
					out.M[x][y] += M1.M[y][k] * M2.M[k][x];
				}
			}
		}
		
		return out;
	}
	
	// Object oriented version of multiply
	public void multiplyBy(Matrix m) {
		this.set(Matrix.multiply(this, m));
	}	
	
	public void print() {
		System.out.println(sizeX + " " + sizeY);
		
		for(int y = 0; y < sizeY; y++) {
			for(int x = 0; x < sizeX; x++)
				System.out.print(Methods.rnd(M[y][x]) + " ");
			
			System.out.print("\n");
		}
	}
}
