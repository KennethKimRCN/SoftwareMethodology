package model;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
import java.io.Serializable;
import java.util.ArrayList;

import model.Photo;

@SuppressWarnings("serial")
public class Album implements Serializable{
	private String name;
	private ArrayList<Photo> photos;
	private int photoCount;
	
	public Album(String name) {
		this.name = name;
		this.photos = new ArrayList<Photo>();
		this.photoCount = 0;
	}
	
	public Album() {
		this.name = null;
		this.photos = new ArrayList<Photo>();
		this.photoCount = 0;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void addPhoto(Photo newPhoto) {
		this.photos.add(newPhoto);
		this.photoCount++;
	}
	
	public void removePhoto(Photo removePhoto) {
		for(Photo p: this.photos) {
			if(p.equals(removePhoto)) {
				this.photos.remove(p);
				this.photoCount--;
				return;
			}
		}
	}
	
	public ArrayList<Photo> getPhotoList(){
		return this.photos;
	}
	
	public int getPhotoCount() {
		return this.photoCount;
	}
}
