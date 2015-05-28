package DataManeger;
import java.util.LinkedList;


public class splitTask extends Thread{

	private String[] task;
	private String S;
	public splitTask(String S) {
		this.S = S;
	}
	@Override
	public void run() {	
		super.run();
		task = S.split("\\),");
		System.out.println("End");
	}
	String[] getresult(){
		return task;
	}
}
