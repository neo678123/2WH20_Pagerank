public class InternetRandomWalk extends Internet {
	// private as other classes should not know
	// the current site
	private int curSite;
	
	public InternetRandomWalk(String[] M, double p) {
		super(M, p);
	}

	@Override
	public void iterate() {
		double R = rand.nextDouble();
		
		// probability p to go to an outlink
		if(R <= p) {
			if(sites[curSite].getNumOutlinks() == 0) {
				// Go to a random site
				curSite = rand.nextInt(pageRank.dim);
			} else {
				// Get the site associated with the
				// current site index
				Site currentSite = sites[curSite];
				
				int L = rand.nextInt(currentSite.getNumOutlinks());
				curSite = sites[curSite].getOutlink(L);
			}
		} else {
			// Go to a random site
			curSite = rand.nextInt(pageRank.dim);
		}
		
		// Increment the pageRank index
		pageRank.v[curSite]++;
	}
}
