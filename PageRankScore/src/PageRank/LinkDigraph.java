package PageRank;

public class LinkDigraph {
	
	private double[][] digraph;
	public LinkDigraph(){
//		this.digraph = new double[d.length][d[0].length];
//		this.digraph = d;
		for(int i = 0; i < digraph.length; i++){
			for(int j = 0; i < digraph[i].length; j++){
				setDigraph(i,j);
			}
		}
	}
	private void setDigraph(int i, int j) {
		if(true){  //リンクが存在するかどうか
			digraph[i][j] = 1;
		}else{
			digraph[i][j] = 0;
		}
	}
	public double[][] getDigraph(){
		return digraph;
	}
}