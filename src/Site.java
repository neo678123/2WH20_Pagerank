import java.util.*;

public class Site {
	private ArrayList<Integer> outlinks;
	private int numOutlinks;
	
	public Site(int... outlinks) {
		this.outlinks = new ArrayList<Integer>();
		this.numOutlinks = outlinks.length;
		
		// Instantiating the arrayList
		for(int x : outlinks) {
			this.outlinks.add(x);
		}
	}
	
	public Site(String matrixRow) {
		char[] row = matrixRow.toCharArray();
		this.numOutlinks = 0;
		
		outlinks = new ArrayList<Integer>();
		
		// Iterate through the string of 0s and 1s
		// add the index to the outlinks list and
		// increment the number outlinks of a 1 is found
		for(int i = 0; i < matrixRow.length(); i++) {
			if(row[i] == '1') {
				outlinks.add(i);
				numOutlinks++;
			}
		}
	}
	
	// returns the index-th outlink of this site
	public int getOutlink(int index) {
		if(index >= numOutlinks) {
			return -1;
		} else {
			return outlinks.get(index);
		}
	}
	
	// returns all outlinks
	public ArrayList<Integer> getOutlinks() {
		return outlinks;
	}
	
	// returns the number of outlinks
	public int getNumOutlinks() {
		return numOutlinks;
	}
}
