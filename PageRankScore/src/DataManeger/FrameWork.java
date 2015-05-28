package DataManeger;

public interface FrameWork {
//	String FrameName = "DefaultFrameName";
	public boolean shincRock = true;
	public void run();
	public MessagePack getmessage();
	public void setmessage(MessagePack param);
	public void removeMessage();
	public void setFrameName(String S);
	public String getFrameName();
}
