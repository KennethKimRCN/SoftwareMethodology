package model;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import model.Photo;

@SuppressWarnings("serial")
public class Album implements Serializable{
	private String name;
	private List<Photo> photos;
	private Photo oldestPhoto, earliestPhoto;
	
	public Album(String name) {
		this.name = name;
		oldestPhoto = null;
		earliestPhoto = null;
		photos = new ArrayList<Photo>();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String toString() {
		return name;
	}
	
	public Photo getPhoto(int index) {
		return photos.get(index);
	}
	
	public void addPhoto(Photo photo) {
		photos.add(photo);
		findOldestPhoto();
		findEarliestPhoto();
	}
	
	public void removePhoto(int index) {
		photos.remove(index);
		findOldestPhoto();
		findEarliestPhoto();
	}
	
	public int getCount() {
		return photos.size();
	}
	
	public List<Photo> getPhotos() {
		return photos;
	}
	
	public void findOldestPhoto() {
		if (photos.size() == 0)
			return;
		
		Photo temp = photos.get(0);
		
		for (Photo p : photos)
			if (p.getCalendar().compareTo(temp.getCalendar()) < 0)
				temp = p;
		
		oldestPhoto = temp;
	}
	
	public void findEarliestPhoto() {
		if (photos.size() == 0)
			return;
		
		Photo temp = photos.get(0);
		
		for (Photo p : photos)
			if (p.getCalendar().compareTo(temp.getCalendar()) > 0)
				temp = p;
		
		earliestPhoto = temp;
	}
	
	public String getOldestPhotoDate() {
		if (oldestPhoto == null)
			return "NA";
		return oldestPhoto.getDate();
	}
	
	public String getEarliestPhotoDate() {
		if (earliestPhoto == null)
			return "NA";
		return earliestPhoto.getDate();
	}
	
	public String getDateRange() {
		return getOldestPhotoDate() + " - " + getEarliestPhotoDate();
	}
	
	public Image getAlbumPhoto() {
		if (photos.isEmpty())
			return null;
		return photos.get(0).getImage();
	}
	
	public int findIndexByPhoto(Photo photo) {
		for (int i = 0; i < photos.size(); i++)
			if (photos.get(i).equals(photo))
				return i;
		return -1;
	}
}
