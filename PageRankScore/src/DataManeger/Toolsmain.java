package DataManeger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;

import FpsSetting.FpsManager;


public class Toolsmain implements Runnable{
	Thread myThread;
	LinkedList<FrameWork> frame;
	FpsManager fpsM;
	LinkedList<MessagePack> messageManager;
	String[] FrameName;
	HashMap<String,FrameWork> FrameMap;
	
	public Toolsmain() {
		MessagePack MP = new MessagePack();
		fpsM = new FpsManager(120, 0);
		
		frame = new LinkedList<FrameWork>();
		mainFrame MF = new mainFrame();
		
		frame.add(MF);
		//frame.add(new MMDreadFrame());
		//frame.add(new matEditor());
		frame.add(new BinEditFrame());
		
		frame.add(new SQLViewerFrame());
		
		myThread = new Thread(this);
		
		FrameMap  =new HashMap<String,FrameWork>();
		FrameName = new String[frame.size()];
		
		for(int cnt = 0; cnt < frame.size();cnt++){
			FrameName[cnt] = frame.get(cnt).getFrameName();
			FrameMap.put(frame.get(cnt).getFrameName(), frame.get(cnt));
			System.out.println(frame.get(cnt).getFrameName());
		}
		MP.setMeta_A("FrameNameSet");
		MP.setPresent(FrameName);
		
		MF.setmessage(MP);
		
		messageManager  =new LinkedList<MessagePack>();
		
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			try {
				for(int cnt = 0 ; cnt < frame.size(); cnt++){
					frame.get(cnt).run();
				
					MessagePack MP;
					
					MP = frame.get(cnt).getmessage();
					if(MP != null){
						System.out.println("MP_create");
						System.out.println(MP.getMeta_A());
						if(messageManager.indexOf(MP) < 0){
							messageManager.add(MP);
							System.out.println("Message_add");
						}
						frame.get(cnt).removeMessage();
					}
				}
				for(int cnt = 0;cnt < messageManager.size();cnt++){
					String S = messageManager.get(cnt).getMeta_A();
					if(FrameMap.containsKey(S)){
						FrameMap.get(S).setmessage(messageManager.get(cnt));
					}
				}
				messageManager.removeAll(messageManager);
				TimeUnit.NANOSECONDS.sleep(fpsM.fpsClc());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (fpsM.getovertimeflag()){

				Thread.yield();
			}
		}
	}
	public void start() {
		myThread.start();
	}

}