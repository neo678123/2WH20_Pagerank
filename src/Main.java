import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
	/* Using enum to know what exercise should be done */
//	final State s = State.RANDOMWALK;
	final State STATE = State.EIGENVECTOR;
	final double P = 0.85;
	
	public static void main(String[] args) {
		Main m = new Main();
		
		final int MAX_ITERATIONS = 1000000;
		
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
		else {
			Internet I = new InternetEigenValues(readFile("test_matrix"), m.P);
			prevPageRank = new Vector(I.getPagerank().dim);
			
			// Checking how many nanoseconds it took to find the pagerank
			Long T1 = System.nanoTime();
			Long T2 = 0l;
			
			for(int i = 0; i < MAX_ITERATIONS; i++) {
				I.iterate();
				
				if(Vector.sumAbsDiff(I.getPagerank(), prevPageRank) < epsilon) {
					// T2 is set here as a call to println probably takes more time
					// than finding the pagerank
					T2 = System.nanoTime();
					
					System.out.println("Amount of iterations: " + i);
					System.out.println("Time taken: " + (T2-T1)/1E6d + "ms \n"); // ms are more understandable than ns
					break;
				}
				
				prevPageRank.set(I.getPagerank());
			}
			
			I.normalizePageRank();
			I.getPagerank().print();	
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
