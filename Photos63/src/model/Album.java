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

/**
 * The Class Album.
 */
@SuppressWarnings("serial")
public class Album implements Serializable{
	
	/** The name. */
	private String name;
	
	/** The photos. */
	private List<Photo> photos;
	
	/** The oldest and earliest photo. */
	private Photo oldestPhoto, earliestPhoto;
	
	/**
	 * Instantiates a new album.
	 *
	 * @param name the name
	 */
	public Album(String name) {
		this.name = name;
		oldestPhoto = null;
		earliestPhoto = null;
		photos = new ArrayList<Photo>();
	}
	
	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		return name;
	}
	
	/**
	 * Gets the photo.
	 *
	 * @param index the index
	 * @return the photo
	 */
	public Photo getPhoto(int index) {
		return photos.get(index);
	}
	
	/**
	 * Adds the photo.
	 *
	 * @param photo the photo
	 */
	public void addPhoto(Photo photo) {
		photos.add(photo);
		findOldestPhoto();
		findEarliestPhoto();
	}
	
	/**
	 * Removes the photo.
	 *
	 * @param index the index
	 */
	public void removePhoto(int index) {
		photos.remove(index);
		findOldestPhoto();
		findEarliestPhoto();
	}
	
	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public int getCount() {
		return photos.size();
	}
	
	/**
	 * Gets the photos.
	 *
	 * @return the photos
	 */
	public List<Photo> getPhotos() {
		return photos;
	}
	
	/**
	 * Find oldest photo.
	 */
	public void findOldestPhoto() {
		if (photos.size() == 0)
			return;
		
		Photo temp = photos.get(0);
		
		for (Photo p : photos)
			if (p.getCalendar().compareTo(temp.getCalendar()) < 0)
				temp = p;
		
		oldestPhoto = temp;
	}
	
	/**
	 * Find earliest photo.
	 */
	public void findEarliestPhoto() {
		if (photos.size() == 0)
			return;
		
		Photo temp = photos.get(0);
		
		for (Photo p : photos)
			if (p.getCalendar().compareTo(temp.getCalendar()) > 0)
				temp = p;
		
		earliestPhoto = temp;
	}
	
	/**
	 * Gets the oldest photo date.
	 *
	 * @return the oldest photo date
	 */
	public String getOldestPhotoDate() {
		if (oldestPhoto == null)
			return "NA";
		return oldestPhoto.getDate();
	}
	
	/**
	 * Gets the earliest photo date.
	 *
	 * @return the earliest photo date
	 */
	public String getEarliestPhotoDate() {
		if (earliestPhoto == null)
			return "NA";
		return earliestPhoto.getDate();
	}
	
	/**
	 * Gets the date range.
	 *
	 * @return the date range
	 */
	public String getDateRange() {
		return getOldestPhotoDate() + " - " + getEarliestPhotoDate();
	}
	
	/**
	 * Gets the album photo.
	 *
	 * @return the album photo
	 */
	public Image getAlbumPhoto() {
		if (photos.isEmpty())
			return null;
		return photos.get(0).getImage();
	}
	
	/**
	 * Find index by photo.
	 *
	 * @param photo the photo
	 * @return the index
	 */
	public int findIndexByPhoto(Photo photo) {
		for (int i = 0; i < photos.size(); i++)
			if (photos.get(i).equals(photo))
				return i;
		return -1;
	}
}
