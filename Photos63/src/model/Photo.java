package model;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
import java.io.Serializable;

@SuppressWarnings("serial")
public class Photo implements Serializable {
	private String caption;
	private String path;
	private long lastModified;
	
	public Photo(String photoCaption, String path, long lastModified) {
		this.caption = photoCaption;
		this.path = path;
		this.lastModified = lastModified;
	}
	
	public String getCaption() {
		return this.caption;
	}
	
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	public String getPath() {
		return this.path;
	}
	
	public long getMod() {
		return this.lastModified;
	}
}
