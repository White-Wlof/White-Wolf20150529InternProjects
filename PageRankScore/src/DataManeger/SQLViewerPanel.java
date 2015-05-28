package DataManeger;

import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.JPanel;

public class SQLViewerPanel extends JPanel implements MouseWheelListener{
	private String file;
	private String[] data;
	private ArrayList<String> appString;
	private int startIndex;
	private int applength;
	
	public SQLViewerPanel() {

		startIndex = 0;
		applength = 25;
		this.addMouseWheelListener(this);
	
	}
	public void setFile(String file) {
		this.file = file;
		File f = new File(file);
//		if(!f.canExecute())
//			System.out.println("これが使えないそうです→"+file);
		TextReader TR = new TextReader(file,".*\\(\\w,");
		data = TR.getTextData();
		reflesh(0, 10);
		System.out.println(data.length);
	}
	public void reflesh(int startIndex,int Length){
		appString = new ArrayList<String>();
		if(data != null){
		for(int cnt = startIndex;cnt < data.length-startIndex;cnt++){
			appString.add(data[cnt]);
			if(cnt >= startIndex+Length){
				break;
			}
		}
		}else{
			System.out.println("dataねえ");
		}
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(appString != null)
		for(int cnt = 0;cnt < appString.size();cnt++){
		g.drawString(appString.get(cnt), 50, 30+(20*cnt));
		}
	}
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		startIndex = startIndex+e.getWheelRotation();
		if(startIndex < 0)
			startIndex = 0;
		reflesh(startIndex, applength);
	}
}
