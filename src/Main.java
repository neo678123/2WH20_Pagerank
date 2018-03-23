import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	/* Using enum to know what exercise should be done */
//	final State STATE = State.RANDOMWALK;
//	final State STATE = State.EIGENVECTOR;
	final State STATE = State.COMPARE;
	final double P = 0.85;
	
	public static void main(String[] args) {
		Main m = new Main();
		
		final int MAX_ITERATIONS = 400000;
		
		// 1E-15 is the smallest order of magnitude where 
		// ||pageRank - prevPagrank||_1 < epsilon in less
		// than 1000000 iterations
		double epsilon = 1E-15;
		Vector prevPageRank;
		
		if(m.STATE == State.RANDOMWALK) {
			Internet I = new InternetRandomWalk(readFile("test_matrix"), m.P);
			prevPageRank = new Vector(I.getPagerank().dim);
			
			for(int i = 0; i < MAX_ITERATIONS; i++) {
				I.iterate();
			}
			
			I.normalizePageRank();
			I.getPagerank().print();	
		}
		else if(m.STATE == State.EIGENVECTOR){	
			Internet I = new InternetEigenValues(readFile("test_matrix"), m.P);
			prevPageRank = new Vector(I.getPagerank().dim);
			
			for(int i = 0; i < MAX_ITERATIONS; i++) {
				I.iterate();
				
				if(Vector.sumAbsDiff(I.getPagerank(), prevPageRank) < epsilon) {					
					System.out.println("Amount of iterations: " + i);
					break;
				}
				
				prevPageRank.set(I.getPagerank());
			}
			
			I.normalizePageRank();
			I.getPagerank().print();	
		}
		else {
			String[] matrix = readFile("test_matrix");
			Internet Ir = new InternetRandomWalk(matrix, m.P);
			Internet Iv = new InternetEigenValues(matrix, m.P);
			
			for(int i = 0; i < MAX_ITERATIONS; i++) {
				Ir.iterate();
				Iv.iterate();
			}
			
			Ir.normalizePageRank();
			Iv.normalizePageRank();
			
			Vector.printCompare(Ir.getPagerank(), Iv.getPagerank());
		}
	}
	
	// Reads in a matrix file using the Scanner object
	public static String[] readFile(String fileName) {
		File f = new File(fileName);
		try {
			Scanner sc = new Scanner(f);
			
			// Check the first line to find the dimension of the matrix (as its square)
			String first = sc.nextLine().replaceAll("\\s+", ""); // <-- removes spaces from string
			String[] out = new String[first.length()];
			
			out[0] = first;
			for(int i = 1; i < first.length(); i++) {
				out[i] = sc.nextLine();
				out[i] = out[i].replaceAll("\\s+", "");
			}
			
			sc.close();
			return out;	
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
