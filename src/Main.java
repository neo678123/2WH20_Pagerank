import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
	/* Using enum to know what exercise should be done */
//	final State STATE = State.RANDOMWALK;
	final State STATE = State.EIGENVECTOR;
//	final State STATE = State.COMPARE;
//	final State STATE = State.P_VARY;
//	final State STATE = State.GEEF_GELD;
	
	final double P = 0.85;
	
	public static void main(String[] args) {
		Main m = new Main();
		
		final int MAX_ITERATIONS = 400000;
		
		// 1E-15 is the smallest order of magnitude where 
		// ||pageRank - prevPagrank||_1 < epsilon in less
		// than 1000000 iterations
		double epsilon = 1E-15;
		Vector prevPageRank;
		Internet I;
		
		// Switch because this makes adding states more easy
		switch(m.STATE) {
		case RANDOMWALK:
			I = new InternetRandomWalk(readMatrix("test_matrix"), m.P);
			prevPageRank = new Vector(I.getPagerank().dim);
			
			for(int i = 0; i < MAX_ITERATIONS; i++) {
				I.iterate();
			}
			
			I.normalizePageRank();
			I.getPagerank().print();	
		break;
		case EIGENVECTOR:
			I = new InternetEigenValues(readMatrix("test_matrix"), m.P);
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
		break;
		case GEEF_GELD:
			I = new InternetGeldHark(readMatrix("test_matrix"), m.P, readFile("betalende_sites.txt"));
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
		break;
		case COMPARE:
			String[] matrix = readMatrix("test_matrix");
			Internet Ir = new InternetRandomWalk(matrix, m.P);
			Internet Iv = new InternetEigenValues(matrix, m.P);
			
			for(int i = 0; i < MAX_ITERATIONS; i++) {
				Ir.iterate();
				Iv.iterate();
			}
			
			Ir.normalizePageRank();
			Iv.normalizePageRank();
			
			Vector.printCompare(Ir.getPagerank(), Iv.getPagerank());
		break;
		case P_VARY:
			try {
				Writer writer = new BufferedWriter(new OutputStreamWriter(
		              new FileOutputStream("p.txt"), "utf-8"));
				
				String S = "P, ";
				for(int i = 1; i< 20; i++) {
					S += i + ", ";
				}
				S += 20 + "\n";
				writer.write(S);
				
				for(double j = 0; j < 1-m.P; j+=0.01) {
					I = new InternetEigenValues(readMatrix("test_matrix"), m.P + j);
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
					
					S = m.P + j + ", ";
					for(int i = 0; i < I.getPagerank().dim-1; i++) {
						S += I.getPagerank().v[i] + ", ";
					}
					S += I.getPagerank().v[I.getPagerank().dim-1] + "\n";
					writer.write(S);
				}
				writer.close();
			} catch(Exception e) {
				e.printStackTrace();
			}
		break;
		default: break;
		}
	}
	
	// Reads in a matrix file using the Scanner object
	public static String[] readMatrix(String fileName) {
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
	
	// Reads in an arbitrary file (used for paying sites)
	public static String[] readFile(String fileName) {
		File f = new File(fileName);
		try {
			Scanner sc = new Scanner(f);
			
			List<String> out = new ArrayList<String>();

			while(sc.hasNextLine()) {
				out.add(sc.nextLine());
			}
			
			return out.toArray(new String[out.size()]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
