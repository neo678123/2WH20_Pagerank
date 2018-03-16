package edu.tue_2wh20.pagerank;

import java.io.*;

public class Main {
	
	/* Variables */
	public Matrix G;
	public Internet I;
	public double p;
	
	public void initialize() {
		I = new Internet(G, p);
	}
	
	public static void main(String args[]) {
		Main m = new Main();
		m.G = new Matrix(readFile("test_matrix"));
		m.p = 0.85;
	
		m.initialize();
		
		Long T1 = System.nanoTime();
		
		final int n = 1000000;
		
//		Vector v = new Vector();
//		v.set(m.I.x);
		
		for(int i = 0; i < n; i++) {
			m.I.iterateMatrixMult();
			m.I.iterateRandomWalk();
		}
		
//		v.print();
//		m.I.x.print();
		
		m.I.x.divideBy((double)n);
		
		Long T2 = System.nanoTime();
		
//		v.divideBy((double)-n);
//		m.I.x.add(v);
		Vector.printCompare(m.I.x, m.I.v);
		
		System.out.println((T2-T1)/1000000.0d + "ms");
	}
	
	
	// Just some standard file reading code
	// Not relevant to understand
	public static double[][] readFile(String fileName) {
		try {
			BufferedReader r = new BufferedReader(new FileReader(new File(fileName)));
			double[][] array = new double[20][20];
			String line = r.readLine();
			
			int i = 0, j = 0;
			while(line != null) {
				String[] strArray = line.split(" ");
				for(String s : strArray) {
					if(!s.trim().isEmpty()) 
						array[i][j++] = Double.parseDouble(s);
				}
				line = r.readLine();
				i++;
				j = 0;
			}
			r.close();
			return array;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}