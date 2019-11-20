package model;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.scene.image.Image;

/**
 * In the Photo model class we can
 * 1. Add/Edit/Delete and get Tags
 * 2. set and get Captions
 * 3. get the date of the Image
 */
@SuppressWarnings("serial")
public class Photo implements Serializable {
	
	/** The image. */
	private SerializableImage image;
	
	/** The caption. */
	private String caption;
	
	/** The tags. */
	private List<Tag> tags;
	
	/** The date. */
	private Calendar cal;
	
	/**
	 * Instantiates a new default photo.
	 */
	public Photo() {
		caption = "";
		tags = new ArrayList<Tag>();
		cal = Calendar.getInstance();
		cal.set(Calendar.MILLISECOND, 0);
		image = new SerializableImage();
	}

	/**
	 * Instantiates a new specific photo.
	 *
	 * @param i the i
	 */
	public Photo(Image i) {
		this();
		image.setImage(i);
	}
		
	/**
	 * Adds the tag.
	 *
	 * @param type the type
	 * @param value the value
	 */
	public void addTag(String type, String value) {
		tags.add(new Tag(type, value));
	}
	
	/**
	 * Edits the tag.
	 *
	 * @param index the index
	 * @param type the type
	 * @param value the value
	 */
	public void editTag(int index, String type, String value) {
		tags.get(index).setType(type);
		tags.get(index).setValue(value);
	}
	
	/**
	 * Removes the tag.
	 *
	 * @param index the index
	 */
	public void removeTag(int index) {
		tags.remove(index);
	}
	
	/**
	 * Gets the tag.
	 *
	 * @param index the index
	 * @return the tag
	 */
	public Tag getTag(int index) {
		return tags.get(index);
	}
	
	/**
	 * Sets the caption.
	 *
	 * @param caption the new caption
	 */
	public void setCaption(String caption) {
		this.caption = caption;
	}
	
	/**
	 * Gets the caption.
	 *
	 * @return the caption
	 */
	public String getCaption() {
		return caption;
	}
	
	/**
	 * Gets the calendar.
	 *
	 * @return the calendar
	 */
	public Calendar getCalendar() {
		return cal;
	}
	
	/**
	 * Gets the date.
	 *
	 * @return the date
	 */
	public String getDate() {
		String[] str = cal.getTime().toString().split("\\s+");
		return str[0] + " " + str[1] + " " + str[2] + ", " + str[5];
	}
	
	/**
	 * Gets the image.
	 *
	 * @return the image
	 */
	public Image getImage() {
		return image.getImage();
	}
	
	/**
	 * Gets the serializable image.
	 *
	 * @return the serializable image
	 */
	public SerializableImage getSerializableImage() {
		return image;
	}
	
	/**
	 * Checks for subset.
	 *
	 * @param tlist the tlist
	 * @return true, if successful
	 */
	public boolean hasSubset(List<Tag> tlist) {
		Set<Tag> allTags = new HashSet<Tag>();
		allTags.addAll(tags);
		
		for (Tag t: tlist) {
			if (!allTags.contains(t))
				return false;
		}
		
		return true;
	}
	
	/**
	 * Checks if is within date range.
	 *
	 * @param fromDate the from date
	 * @param toDate the to date
	 * @return true, if is within date range
	 */
	public boolean isWithinDateRange(LocalDate fromDate, LocalDate toDate) {
		LocalDate date = cal.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		
		return date.isBefore(toDate) && date.isAfter(fromDate) || date.equals(fromDate) || date.equals(toDate);
	}
	
	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	public List<Tag> getTags() {
		return tags;
	}
}
