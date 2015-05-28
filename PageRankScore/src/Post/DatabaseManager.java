package Post;

import java.io.File;

public class DatabaseManager {
	private String databasePath;
	private String[] dataList;
	private File dir;
	private File[] databaseFiles;
	
	public DatabaseManager() {
		setDatabasePath();
	}
	/* データベースのパスをセット */
	private void setDatabasePath() {
		String nowPath = new File(".").getAbsoluteFile().getParent();
		databasePath = nowPath + File.separator+ "Database";
		System.out.println(databasePath);
		dir = new File(databasePath);
		if(dir.exists()){ //ディレクトリが存在し，アクセスできるかかどうか
			System.out.println("Directory:Existence");
		}else{
			System.out.println("Directory:None or Can't acess");
		}
		databaseFiles = dir.listFiles();
		dataList = new String[databaseFiles.length];
		for(int cnt = 0;cnt < databaseFiles.length;cnt++){ //データベースの絶対パス確認
			File f = databaseFiles[cnt];
			System.out.println(f.getAbsolutePath());
			System.out.println(f.getName());
			dataList[cnt] = f.getName();
		}
	}
	public String[] getfileList(){
		return dataList;
	}
	public DatabaceOperatior readSQL(String sql){
		return new DatabaceOperatior(new File(dir.getPath()+File.separator+sql),dir);
	}
}
