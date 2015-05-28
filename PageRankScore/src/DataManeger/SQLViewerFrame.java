package DataManeger;

import javax.swing.JFrame;

public class SQLViewerFrame extends JFrame implements FrameWork{
	private String name;
	private SQLViewerPanel SQLVP;
	public SQLViewerFrame() {
		setBounds(30, 50, 600, 600);
		SQLVP = new SQLViewerPanel();
		add(SQLVP);
	}
	@Override
	public void run() {

		SQLVP.repaint();
	}

	@Override
	public MessagePack getmessage() {
		return null;
	}

	@Override
	public void setmessage(MessagePack param) {
		setVisible(true);
		String filename = "";
		String[] B = param.getMeta();
		if(B.length > 0){
			filename = B[0];
			SQLVP.setFile(filename);
		}
	}

	@Override
	public void removeMessage() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFrameName(String S) {

	}

	@Override
	public String getFrameName() {
		return "SQLViewer";
	}


}
