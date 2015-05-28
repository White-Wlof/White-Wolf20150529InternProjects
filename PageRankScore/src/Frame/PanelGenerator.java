package Frame;

import java.awt.Graphics;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import Post.DatabaseManager;

public class PanelGenerator extends JPanel implements ActionListener{
	JLabel titleLabel, sqlLabel;
	JTextField textField;
	JButton button;
	public PanelGenerator() {
		setLayout(null);
		DatabaseManager databaseM = new DatabaseManager();
		titleLabel = new JLabel("PageRankScoreTop10");
		setTitleLabel();
		sqlLabel = new JLabel("InputSQL:");
		setSQLLabel();
		textField = new JTextField();
		setTextFild();
		button = new JButton("ボタン");
		button.addActionListener(this);
		add(titleLabel);
		add(sqlLabel);
		add(textField);
		add(button);
	}
	private void setTitleLabel() {
		titleLabel.setBounds(10, 10, 150, 30);
	}
	private void setSQLLabel() {
		sqlLabel.setBounds(10, 50, 100, 30);
	}
	private void setTextFild() {
		textField.setBounds(10, 100, 450, 400);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button){
			System.out.println("AAAAAAaa");
		}
	}
}
