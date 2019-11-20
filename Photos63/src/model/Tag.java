package model;

import java.io.Serializable;

/**
 * The Class Tag.
 *
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
@SuppressWarnings("serial")
public class Tag implements Serializable{
	
	/** The value. */
	private String type, value;
	
	/**
	 * Basic Tag constructor.
	 *
	 * @param type Type of the tag, such as Location, Person
	 * @param value Value of the tag, such as New Brunswick, Ken
	 */
	public Tag(String type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Basic getter and setter for type and value.
	 *
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * Sets the value.
	 *
	 * @param value the new value
	 */
	public void setValue(String value) {
		this.value = value;
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		return type + ": " + value;
	}
	
	/**
	 * Hash code.
	 *
	 * @return the int
	 */
	@Override
	public int hashCode() {
		return value.hashCode()+type.hashCode();
	}
	
	/**
	 * This function checks to see if 2 tags' value and type are equal.
	 *
	 * @param obj the obj
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null || !(obj instanceof Tag))
			   return false;

		Tag t =(Tag) obj;

        return t.getValue().equals(value) && t.getType().equals(type);
	}
}
