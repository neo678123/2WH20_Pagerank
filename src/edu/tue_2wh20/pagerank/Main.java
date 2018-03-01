package edu.tue_2wh20.pagerank;

import java.util.Random;

public class Main {
	public static void main(String args[]) {
		Random r = new Random(420691337);
		for(int k = 0; k < 100; k++) {			
			System.out.println(r.nextDouble());
		}
	}
}