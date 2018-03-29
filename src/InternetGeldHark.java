
public class InternetGeldHark extends Internet {

	// We making money boyz
	// added site weight to give an advantage to paying websites
	int[] weights;
	
	// c_j
	private int[] columnSum;
	
	// payingCustomers[j] should be formated as:
	// "[siteNumber] : [desiredWeight]"
	public InternetGeldHark(String[] M, double p, String[] payingCustomers) {
		super(M, p);
		
		weights = new int[sites.length];
		for(int i = 0; i < sites.length; i++) {
			weights[i] = 1;
		}
		
		if(payingCustomers != null ) {
			for(String s : payingCustomers) {
				String[] splitted = s.split(":");
				weights[Integer.parseInt(splitted[0].trim())-1] = Integer.parseInt(splitted[1].trim());
			}
		}
		
		columnSum = new int[M.length];
		
		for(int j = 0; j < M.length; j++) {
			columnSum[j] = sites[j].getNumOutlinks();
			for(int x : sites[j].getOutlinks()) {
				columnSum[j] += weights[x] - 1;
			}
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
		
		double n = 0;
		for(int w : weights) {
			n += w;
		}
		
		for(int j = 0; j < pageRank.dim; j++) {
			newPageRank.v[j] = 0;
			for(int x : sites[j].getOutlinks()) {
				newPageRank.v[j] += weights[j] * p / columnSum[x] * pageRank.v[x];
			}
			newPageRank.v[j] += (1-p) * weights[j] / n;
		}
		
		this.pageRank.set(newPageRank);
		normalizePageRank();
	}

}
