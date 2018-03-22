
public class InternetEigenValues extends Internet {

	// c_j
	private int[] columnSum;
	
	public InternetEigenValues(String[] M, double p) {
		super(M, p);
		
		columnSum = new int[M.length];
		
		for(int j = 0; j < M.length; j++) {
			columnSum[j] = sites[j].getNumOutlinks();
		}
		
		// Clear the array of sites because
		// it needs to be transposed
		sites = null;
		this.sites = new Site[M.length];
		
		// Instantiate all sites
		// but transposed (more efficient for summing)
		for(int i = 0; i < pageRank.dim; i++) {
			sites[i] = new Site(M[i]);
		}
	}

	@Override
	public void iterate() {
		Vector newPageRank = new Vector(pageRank.dim);
		
		double n = (double)pageRank.dim;
		
		for(int j = 0; j < pageRank.dim; j++) {
			newPageRank.v[j] = 0;
			for(int x : sites[j].getOutlinks()) {
				newPageRank.v[j] += p / columnSum[x] * pageRank.v[x];
			}
			newPageRank.v[j] += (1-p)/n;
		}
		
		this.pageRank.set(newPageRank);
		normalizePageRank();
	}
}
