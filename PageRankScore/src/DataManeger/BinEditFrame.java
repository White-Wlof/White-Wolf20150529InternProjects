package DataManeger;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class BinEditFrame extends JFrame implements FrameWork{

	private String name;
	public BinEditFrame() {

		setBounds(0, 0, 400, 600);
		
		BinEditPanel panel = new BinEditPanel();
		
		name = "ばいなりえでぃたー";
		
		getContentPane().add(panel);
		
	}
	
	@Override
	public void run() {
		
	}

	@Override
	public MessagePack getmessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setmessage(MessagePack param) {
		setVisible(true);
	}

	@Override
	public void removeMessage() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setFrameName(String S) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFrameName() {
		// TODO Auto-generated method stub
		return name;
	}

}
