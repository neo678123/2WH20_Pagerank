import java.util.Random;

/* General internet class
 * should be extended for
 * each exercise
 */

// abstract because this class has no meaning
public abstract class Internet {
	// protected because the variables
	// should be visible to class extensions
	protected Site[] sites;
	protected Vector pageRank;
	protected Random rand;
	
	protected double p;
	
	// Construct from a String[] for ease of use
	public Internet(String[] M, double p) {
		this.p = p;
		
		this.rand = new Random(420691337);
		this.pageRank = new Vector(M.length);
		this.sites = new Site[M.length];
		
		// Transpose the string array
		String T[] = new String[M[0].length()];
		for(int i = 0; i < M.length; i++) {
			String s = "";
			for(int j = 0; j < M.length; j++) {
				s += M[j].substring(i, i+1);
			}
			T[i] = s;
		}
		
		// Instantiate all sites
		for(int i = 0; i < M.length; i++) {
			sites[i] = new Site(T[i]);
		}
	}
	
	// Returns the pagerank 
	public Vector getPagerank() {
		return pageRank;
	}
	
	// Method which will not be implemented
	// until the class is extended (therefore abstract)
	public abstract void iterate();

	public void normalizePageRank() {
		pageRank.divideBy(pageRank.sum());		
	}
}
