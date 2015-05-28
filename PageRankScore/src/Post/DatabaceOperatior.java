package Post;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaceOperatior {
	private File databaseFileArray;
	private ArrayList<String> bufferedString;
	public DatabaceOperatior(File f,File dir) {
		this.databaseFileArray = f;
		try {
	        Class.forName("org.sqlite.JDBC");
			Connection connection=DriverManager.getConnection(f.getAbsolutePath(),"A","");

			Statement statement=connection.createStatement();
			String sql="show tables";

			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public DatabaceOperatior(File f){
		FileRead(f.getAbsolutePath());
	}
	private int FileRead(String F){
		try{
			File file = new File(F);
			BufferedReader br = new BufferedReader(new FileReader(file));
			bufferedString = new ArrayList<String>();
			String str;
			System.out.println("Input >> "+file.getName());
			while((str = br.readLine()) != null){
				//System.out.println(str);
				bufferedString.add(str);
				//System.out.println(str);
			}

			br.close();
		}catch(FileNotFoundException e){
			System.out.println(e);
		}catch(IOException e){
			System.out.println(e);
		}
		return 0;
	}
}
