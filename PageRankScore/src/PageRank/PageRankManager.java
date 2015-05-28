package PageRank;

public class PageRankManager {
	private int count;
	private double[] prArray;
	public PageRankManager(int c){
		count = c;
		LinkDigraph LD = new LinkDigraph();
		prArray = PageRank.calc(LD.getDigraph(), count);
	}
	public double[] getPRArray(){
		return prArray;
	}
}
