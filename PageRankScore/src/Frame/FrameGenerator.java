/* Copywright©2015 Hironari Kida
 * Java Projects
 * */
package Frame;

import javax.swing.JFrame;

/* 
 * Method to make window.
 *  */
public class FrameGenerator extends JFrame {
	public FrameGenerator(String title) {
		setVisible(true);
		setTitle(title);
		setSize(500, 600);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}