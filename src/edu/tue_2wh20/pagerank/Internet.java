package edu.tue_2wh20.pagerank;

import java.util.*;

public class Internet {
	/* Variables */
	
	// Variables for exercise 3
	public int dim;
	private int curSite = 0;
	
	// Variables for exercise 4
	public Matrix A;
	public Vector x;
	
	// Variables for both exercises
	public Matrix G;
	public double p;
	Random rand;
	
	/* Constructor */
	
	// Empty constructor
	public Internet() {}

	// General purpose constructor
	public Internet(Matrix G, double p) {
		rand = new Random(420691337);
		
		this.G = new Matrix(G.M);
		this.p = p;
		this.x = new Vector(G.sizeY);
		this.dim = G.sizeY;
		
		int n = G.sizeY;
		this.A = new Matrix(G.sizeX, G.sizeY);
		
		// Loop to initialize the probability matrix
		for(int j = 0; j < G.sizeX; j++) {
			for(int i = 0; i < G.sizeY; i++) {
				double c_j = Methods.sumColumn(j, G.M);
				
				if(c_j == 0)
					this.A.M[i][j] = 1/(double)n;
				else
					this.A.M[i][j] = p*G.M[i][j]/c_j + (1-p)/(double)n;
			}
		}
	}
	
	
	/* Methods */
	
	public void iterateRandomWalk() {
		double R = rand.nextDouble();
		
		// probability p to go to an outlink
		if(R <= p) {
			int nextSite = nextSite();
			x.v[nextSite] += 1;
			curSite = nextSite;
		}
		// probability 1-p to choose a random site
		else {
			int i = rand.nextInt(dim);
			//while(i == curSite) i = rand.nextInt(dim);
			
			x.v[i] += 1;
			curSite = i;
		}
	}
	
	public void iterateMatrixMult() {
		x = Vector.multiply(A, x);
	}
	
	public int nextSite() {
		//indexList will contain the sites which are outlinks of curSite		
		ArrayList<Integer> indexList = new ArrayList<Integer>();
		for(int i = 0; i < dim; i++) {
			if(G.M[curSite][i] == 1) indexList.add(i);
		}
		
		if(indexList.size() == 0)
			return rand.nextInt(dim);
		
		/* debugging purposes */
		int i = rand.nextInt(indexList.size());
//		System.out.println(indexList.get(i) + " - lmao \n");
		
		//output the next site that should be visited
		return indexList.get(i);
	}
}
