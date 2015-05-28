package DataManeger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.SplittableRandom;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextReader {
	String filename;
	ArrayList<String> TextData;
	File fileData;
	String pattern;
	public TextReader() {
		TextData = new ArrayList<String>();
		pattern = "";
	}
	public TextReader(String fileName){
		super();
		this.filename = fileName;
		readFile(fileName);
	}
	public TextReader(String fileName,String Reg){
		super();
		pattern = Reg;
		readFile(fileName);
	}
	public void readFile(String filename){
		fileData = new File(filename);
		if(!fileCheck(fileData)){
			System.out.println("FileReadError");
			return;
		}
		
		try {
			InputStreamReader ISR = new InputStreamReader(new FileInputStream(fileData),Charset.forName("UTF-8"));
			BufferedReader br = new BufferedReader(ISR);
			
			String str;
			TextData = new ArrayList<String>();
			System.out.println("Input >> "+fileData.getName());
			try {
				int cnt = 0;
				ArrayList<splitTask> Task = new ArrayList<splitTask>();
				ArrayList<Future<Long>> Taskfuture = new ArrayList<>();
				
				while((str = br.readLine()) != null){
					//TextData.add(str);
//					Matcher M = Pattern.compile(pattern).matcher(str);
//					while(M.find())
					if(str.length() > 100){
					TextData.add(str.substring(0, 100));
					}else{
						TextData.add(str);
							
					}
					Task.add(new splitTask(str));
					System.out.println(cnt++);
					//System.out.println(str);
				}
				ExecutorService exec = Executors.newFixedThreadPool(4);
				if(Task.size() > 0)
				for(splitTask sT:Task){
					Taskfuture.add((Future<Long>) exec.submit(sT));
				}
				System.out.println("しゅうりょー");
				if(Taskfuture.size() > 0)
				for(Future<Long> f:Taskfuture){
					try {
						f.get();
					} catch (InterruptedException | ExecutionException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				ISR.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public String[] getTextData(){
		String[] S = (String[])TextData.toArray(new String[0]);
		return S;
	}
	public ArrayList<String> getTextDataList() {
		return TextData;
	}

	private boolean fileCheck(File infile) {
		if(infile.isFile() && infile.canRead()){
			return true;
		}
		return false;
	}
}
