package edu.tue_2wh20.pagerank;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class Internet {
	/* Variables */
	
	// Variables for exercise 3
	public int dim;
	public HashMap<Integer, Integer[]> sites; // sites<currentsite, outlinks>
	int curSite = 0;
	
	// Variables for exercise 4
	public Matrix A;
	public Vector x;
	
	// Variables for both exercises
	private Matrix G;
	public double p;
	Random rand;
	
	/* Constructor */
	
	// Empty constructor
	public Internet() {}
	
	// General purpose constructor (exercise 3)
	// outLinksPerIndex is a 2d array where the row number is the current site
	public Internet(int size, double p, int[]... outLinksPerIndex) {
		rand = new Random(420691337);
		
		dim = size;
		this.p = p;
		
		for(int i = 0; i < dim; i++) {
						 // Converts int[] to Integer[] because java is weird
			sites.put(i, Arrays.stream(outLinksPerIndex[i]).boxed().toArray(Integer[]::new));
		}
	}
	
	// General purpose constructor (exercise 4)
	public Internet(Matrix G, double p) {
		rand = new Random(420691337);
		
		this.G = new Matrix(G.M);
		this.p = p;
		this.x = new Vector(G.sizeY);
		
		int n = G.sizeY;
		this.A = new Matrix(G.sizeX, G.sizeY);
		
		// Loop to initialize the probability matrix
		for(int j = 0; j < G.sizeY; j++) {
			for(int i = 0; i < G.sizeX; i++) {
				double c_j = Methods.sum(0, G.sizeX-1, G.M[j]);
				
				if(c_j == 0)
					A.M[j][i] = 1/(double)n;
				else
					A.M[j][j] = p*G.M[j][i]/c_j + (1-p)/(double)n;
			}
		}
	}
	
	
	/* Methods */
	
	public void iterateRandomWalk() {
		double R = rand.nextDouble();
		
		if(R <= p) {
			int r = rand.nextInt(sites.size()-1);
			int nextSite = sites.get(curSite)[r];
			x.v[nextSite] += 1;
			curSite = nextSite;
		}
		else {
			int i = rand.nextInt(dim-1);
			x.v[i] += 1;
		}
		
		this.x.divideBy(x.sum());
	}
	
	public void iterateMatrixMult() {
		x = Vector.multiply(A, x);
	}
}
