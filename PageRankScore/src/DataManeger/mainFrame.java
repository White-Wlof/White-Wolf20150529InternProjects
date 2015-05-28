package DataManeger;

import java.util.ArrayList;

import javax.swing.JFrame;

public class mainFrame extends JFrame implements FrameWork{
	String FrameName;
	mainPanel MainPanel;
	MessagePack MP;
	public mainFrame() {
		// TODO Auto-generated constructor stub
	MainPanel = new mainPanel(this);
		setVisible(true);
		setTitle("ツール確認");
		setBounds(100, 100, 400, 400);
		FrameName = "main";
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().add(MainPanel);
		
	}

	public void run() {
		
		MainPanel.run();
	}

	@Override
	public MessagePack getmessage() {
		// TODO Auto-generated method stub
		return MP;
	}
	
	@Override
	public void setmessage(MessagePack param) {
		if(param.getMeta_A().equalsIgnoreCase("FrameNameSet")){
			if(param.getPresent() instanceof String[]){
				MainPanel.setList((String[])param.getPresent());
			}
		}
	}

	@Override
	public void setFrameName(String S) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void removeMessage() {
		// TODO Auto-generated method stub
		MP = null;
	}
	@Override
	public String getFrameName() {
		// TODO Auto-generated method stub
		return FrameName;
	}
	public void getChain(MessagePack MP){
		this.MP = MP;
	}


}
