package DataManeger;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;


public class mainPanel extends JPanel 
implements ActionListener,DropTargetListener{
	/**
	 * 情報ダイアログを表示するメソッドです．
	 * 
	 * @param message メッセージ
	 */
	public void showInformationMessageDialog(String message) {
		// 情報ダイアログの表示
		JOptionPane.showMessageDialog(this,new JLabel(message)
		,"Information...",JOptionPane.INFORMATION_MESSAGE);
		repaint();
	}

	/**
	 * 警告ダイアログを表示するメソッドです．
	 * 
	 * @param message 警告メッセージ
	 */
	public void showWarningMessageDialog(String message) {
		// 警告ダイアログの表示
		JOptionPane.showMessageDialog(this,new JLabel(message)
		,"Warning...",JOptionPane.WARNING_MESSAGE);
		repaint();
	}

	/**
	 * エラーダイアログを表示するメソッドです．
	 * 
	 * @param message エラーメッセージ
	 */
	public void showErrorMessageDialog(String message) {
		// エラーダイアログの表示
		JOptionPane.showMessageDialog(this,new JLabel(message)
		,"Error...",JOptionPane.ERROR_MESSAGE);
		repaint();
	}

	String result;			//出力結果
	private JTextField text1;//FileName 1Line

	JButton button = new JButton("取得");
	JButton button_X = new JButton("取得");
	JButton button_Y = new JButton("取得");
	JButton button_calc = new JButton("計算");
	String[] initData;
	JList list;
	
	String[] inputFile;
	
	
	MessagePack MP;

	mainFrame MF;

	JScrollPane sp;
	public mainPanel() {
		// TODO Auto-generated constructor stub
		text1 = new JTextField("ファイル名", 20);
		
		inputFile = new String[]{
				"Database/jawiki-20150512-pagelinks.sql",
				"Database/jawiki-20150512-page.sql"
				};
		initData = new String[1];
		
		button.setText("なか見");
		button_X.setText("Page.sql");
		button_Y.setText("Pagelinks.sql");
		button_calc.setText("計算");
		list = new JList(initData);
		sp = new JScrollPane();
		sp.getViewport().setView(list);
		sp.setPreferredSize(new Dimension(200, 80));


		this.add(button_X);
		this.add(button_Y);
		this.add(text1);

		this.add(sp);

		new DropTarget(this,this);
		button.addActionListener(this);

		button_X.addActionListener(this);
		button_Y.addActionListener(this);
		
		this.add(button);
		
		this.add(button_calc);
	}
	public mainPanel(mainFrame mainFrame) {
		// TODO Auto-generated constructor stub
		this();
		MF = mainFrame;
	}
	public void setList(String[] list){
		initData = list;
		this.list = new JList(initData);
		sp.getViewport().setView(this.list);
		sp.setPreferredSize(new Dimension(200, 80));

	}
	public int run() {
		// TODO Auto-generated method stub
		repaint();

		return 0;
	}
	/**
	 * コンポーネントの絵画場所
	 * 
	 * ミディファイルがあればそれの結果をペイント
	 * 
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		if(result != null)
			g.drawString(result, 0, 200);
	}
	@Override
	public void dragEnter(DropTargetDragEvent dtde) {
		dtde.acceptDrag(DnDConstants.ACTION_COPY);
	}

	@Override
	public void dragOver(DropTargetDragEvent dtde) {
		
	}

	@Override
	public void dropActionChanged(DropTargetDragEvent dtde) {

	}

	@Override
	public void dragExit(DropTargetEvent dte) {
	}

	@Override
	public void drop(DropTargetDropEvent dtde) {
		try {
			// 転送されたデータの取得
			Transferable tr=dtde.getTransferable();
			// 転送データがサポート可能なデータかどうか判定
			if(tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
				// ドロップ動作を受け入れる
				dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);

				// 転送データからファイルのリストを抽出
				java.util.List myList=(java.util.List)tr.getTransferData(
						DataFlavor.javaFileListFlavor);
				// リスト要素が単一かどうか判定
				// 複数ファイルのドロップを受け入れるのであれば，判定不要
				if(myList.size()==1) {
					// リストからファイルの取り出し
					File myFile=(File)myList.get(0);
					// ファイルの絶対パス名を表示
					// 実際には，この１行を変更すると良い
					//*TO-DO*/
					if(FileChack(myFile.getAbsolutePath())){
						text1.setText(myFile.getAbsolutePath());
					}else{
						text1.setText(""+myFile.getAbsolutePath());
					}
					//showInformationMessageDialog(myFile.getAbsolutePath());
					// ドロップ動作を正常終了
					dtde.getDropTargetContext().dropComplete(true);
				} else {
					// 「要素が多い」という警告を表示
					showWarningMessageDialog("Too much elements.");
					// ドロップ動作を異常終了
					dtde.getDropTargetContext().dropComplete(false);
				}
			} else {
				showWarningMessageDialog("Unsupported.");
				// ドロップ動作をはねつける
				dtde.rejectDrop();
			}
		} catch (IOException ioe) {
			showErrorMessageDialog("I/O exception.");
			// ドロップ動作をはねつける
			dtde.rejectDrop();
		} catch (UnsupportedFlavorException ufe) {
			showErrorMessageDialog("Unsupported");
			// ドロップ動作をはねつける
			dtde.rejectDrop();
		}

	}

	private boolean FileChack(String absolutePath) {
		// TODO Auto-generated method stub
		String[] rx = {"(.*.)(png|gif|bmp|PNG|GIF|BMP|jpg|JPG|jpeg|JPEG)"};
		for(String s:rx){
			if(absolutePath.matches(s)){
				return true;
			}
		}
		return false;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == button){
			System.out.println("ww"+(String)list.getSelectedValue()+text1.getText());
			MP = new MessagePack();
			MP.setMeta_A((String)list.getSelectedValue());
			String[] A = new String[1];
			A[0] = text1.getText();
			MP.setMeta(A);
			
			MF.getChain(MP);
		}
		if(e.getSource() == button_X){
			text1.setText(inputFile[1]);
			File f = new File(text1.getText());
			if(!f.canExecute())
				System.out.println("つかえねー");
			f = new File("Database");
			if(!f.canExecute()){
				System.out.println("フォルダがそもそも・・");
			}
		}
		if(e.getSource() == button_Y){

			text1.setText(inputFile[0]);
		}
	}

}
