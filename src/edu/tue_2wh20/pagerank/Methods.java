package edu.tue_2wh20.pagerank;

public class Methods {
	
	// Efficient way of assigning 2d arrays to each other
	public static double[][] clone2dArray(double[][] src) {
		// Initialize output array
	    double[][] ret = new double[src.length][src[0].length];
	    
	    // Using System.arraycopy is more efficient than using a loop
	    // as this method copies bytes from memory
	    for (int i = 0; i < src.length; i++) {
	        System.arraycopy(src[i], 0, ret[i], 0, src[i].length);
	    }
	    return ret;
	}
	
	// Returns $ \sum_{i = min}^{max} a_i $ (compile this LaTeX mentally)
	public static double sum(int min, int max, double[] a) {
		double S = 0;
		for(int i = min; i <= max; i++) {
			S += a[i];
		}
		return S;
	}

	
	// gets the sum of the j-th column of a matrix
	public static double sumColumn(int j, double[][] m) {
		double out = 0;
		for(int i = 0; i < m.length; i++) {
			out += m[i][j];
		}
		
		return out;
	}

	public static float rnd(double d) {
		return Math.round(10000*d)/10000f;
	}
	
	
}
