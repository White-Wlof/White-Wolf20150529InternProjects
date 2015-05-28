package DataManeger;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Post.DatabaseManager;


public class BinEditPanel extends JPanel implements ActionListener,DropTargetListener{

	private final boolean testSys_1 = true;

	private String result;			//出力結果
	private JTextField text1;//FileName 1Line

	private JButton button = new JButton("取得");
	private JTextField text2;//Index INT

	private JButton input_Index = new JButton("移動");

	private JButton button_UP = new JButton("△");
	private JButton button_DOWN = new JButton("▽");

	private MessagePack MP;

	private ArrayList<Integer> buff;

	private ArrayList<String> appbuff;

	private int apperIndex;
	private int appersize;
	private int appStrsize;

	private Point basePoint;

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

	public BinEditPanel() {
		setLayout(null);
		apperIndex = 0;
		appersize = 20;
		appStrsize = 8;

		text1 = new JTextField("ファイル名", 15);
		text2 = new JTextField("検索", 10);

		text1.setBounds(20, 20, 150, 20);
		text2.setBounds(20, 40, 150, 20);

		button.setText("取得");
		input_Index.setText("移動");

		button.setEnabled(false);

		button.setBounds(230, 20, 60, 20);
		input_Index.setBounds(230, 40, 60, 20);
		button_UP.setBounds(300, 20, 20, 20);
		button_DOWN.setBounds(300, 40, 20, 20);

		this.add(text1);
		this.add(text2);

		new DropTarget(this,this);
		button.addActionListener(this);
		input_Index.addActionListener(this);
		button_DOWN.addActionListener(this);
		button_UP.addActionListener(this);

		this.add(button);
		this.add(input_Index);
		this.add(button_UP);
		this.add(button_DOWN);

		basePoint = new Point(40,100);

	}
	public int run() {
		repaint();

		return 0;
	}
	/**
	 * コンポーネントの絵画場所
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
					if(dropItemChack(myFile.getAbsolutePath())){
						text1.setText(myFile.getAbsolutePath());
						if(fileCheck(myFile)){
							button.setEnabled(true);
						}
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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		System.out.println("Painting-panel");
		if(appbuff != null){
			for(int cnt = 0;cnt < appersize*appStrsize ;cnt++){
				if(appbuff.size() <= cnt){
					break;
				}
				if(cnt%appStrsize == 0){
					g.setColor(Color.RED);
					String S = Integer.toHexString(apperIndex+cnt);
					g.drawString(S, (int)basePoint.getX()+20-(S.length()*10), (int)basePoint.getY()+((cnt/appStrsize)*20));
					g.setColor(Color.BLACK);
				}
				g.drawString(appbuff.get(cnt), (int)basePoint.getX()+20+((cnt%appStrsize)*20), (int)basePoint.getY()+((cnt/appStrsize)*20));

			}
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == button){
			file_Read();
			printing(0);
		}
		if(e.getSource() == input_Index){
			if(text2.getText() != null & text2.getText().length() > 0){
				System.out.println("test-"+apperIndex);
				printing(Integer.parseInt(text2.getText()));
			}
		}
		if(e.getSource() == button_DOWN){
			move_line_DOUN();
		}
		if(e.getSource() == button_UP){
			move_line_UP();
		}
	}

	private boolean dropItemChack(String absolutePath) {
		// TODO Auto-generated method stub
		String[] rx = {".*"};
		for(String s:rx){
			if(absolutePath.matches(s)){
				return true;
			}
		}
		return false;
	}
//	private boolean file_Read_Text(){
//		TextReader TR = new TextReader(text1.getText());
//	}
	private boolean file_Read(){
		boolean result = false;
		File infile;
		FileInputStream in;
		try {
			infile = new File(text1.getText());
			if(fileCheck(infile)){
				in = new FileInputStream(infile);
				int C;
				buff = new ArrayList<Integer>();

				System.out.println("ファイルサイズ"+infile.length());
				System.out.println("Read-start");
				if(testSys_1){
					BinParser bp = new BinParser(in);
					byte[] buf = null;
					buf = bp.parseByteArray( 0, -1);
					//int cnt = 0 ;
					if(buf != null)
						for(byte memo:buf){
							//						System.out.print("\t"+Integer.parseInt(""+memo)+" ");
							//						if(cnt++%100000 == 99999){
							//							System.out.println();
							//						}
							buff.add(Integer.parseInt(""+memo));
						}
				}else{
					while ((C = in.read())>-1) {
						//				if(C/16 < 1 ){
						//					S = "0"+Integer.toHexString(C);
						//				}else{
						//					S = ""+Integer.toHexString(C);
						//				}
						buff.add(C);
						// System.out.print(S);
						//		                if((cnt%32)==31){
						//		                	System.out.println("");
						//		                }
					}
				}
				in.close();
				result = true;
			}else{
				result = false;
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		System.out.println("");
		System.out.println("Read-End");
		return result;
	}
	private boolean fileCheck(File infile) {
		if(infile.isFile() && infile.canRead()){
			return true;
		}
		return false;
	}
	private void move_line_UP(){
		printing(apperIndex-appStrsize);
		//		if(apperIndex > 0){
		//			apperIndex--;
		//			appbuff.remove(appbuff.size()-1);
		//			appbuff.add(0, buff.get(apperIndex));
		//		}
	}
	private void move_line_DOUN(){
		printing(apperIndex+appStrsize);
		//		if(apperIndex+appbuff.size() < buff.size()){
		//			apperIndex++;
		//			appbuff.remove(0);
		//			appbuff.add(buff.get(apperIndex+appbuff.size()));
		//		}
	}
	private void printing(int Index) {
		if(Index < 0){
			Index = 0;
		}
		apperIndex = Index;
		int lastIndex = Index+appersize+(appersize*appStrsize);

		if(buff.size() < lastIndex){
			lastIndex = buff.size();
		}
		appbuff = new ArrayList<String>();
		if(lastIndex < apperIndex){
			return ;
		}
		ArrayList<Integer> copybuff = new ArrayList<Integer>();
		copybuff = new ArrayList<Integer>(buff.subList(apperIndex,lastIndex));
		String S;
		int cnt = 0;
		for(int C:copybuff){
			System.out.print(C+" ");
			if(C < 0 ){
				S = easyPase10to16(C);
			}else{
				S = ""+Integer.toHexString(C);
			}
			appbuff.add(S);
		}

		text2.setText(""+apperIndex);
		repaint();

	}
	private String easyPase10to16(int C){
		String S = "";
		int A = C;
		int B = 0;
		int D = 2^0;
		System.out.println("10to16 "+C +" " +D);
		for(int cnt = 0;(C/(16^cnt)) != 0;cnt++){
			B = A&(15*(16^cnt));
			S += Integer.toHexString(B);
			A -= B;
		}
		return S;
	}

}