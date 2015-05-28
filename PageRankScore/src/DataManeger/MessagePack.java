package DataManeger;

public class MessagePack {
	private String[] Meta;
	private String Meta_A;
	private Object Present;
	/**
	 * @return the meta
	 */
	public String[] getMeta() {
		return Meta;
	}
	/**
	 * @param meta the meta to set
	 */
	public void setMeta(String[] meta) {
		Meta = meta;
	}
	/**
	 * @return the meta_A
	 */
	public String getMeta_A() {
		return Meta_A;
	}
	/**
	 * @param meta_A the meta_A to set
	 */
	public void setMeta_A(String meta_A) {
		Meta_A = meta_A;
		
	}
	/**
	 * @return the present
	 */
	public Object getPresent() {
		return Present;
	}
	/**
	 * @param present the present to set
	 */
	public void setPresent(Object present) {
		Present = present;
	}
	
}