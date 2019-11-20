package model;

import java.io.Serializable;

/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
@SuppressWarnings("serial")
public class Tag implements Serializable{
	private String type, value;
	
	/**
	 * Basic Tag constructor
	 * @param type Type of the tag, such as Location, Person
	 * @param value Value of the tag, such as New Brunswick, Ken
	 */
	public Tag(String type, String value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Basic getter and setter for type and value
	 */
	public String getType() {
		return type;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String toString() {
		return type + ": " + value;
	}
	
	@Override
	public int hashCode() {
		return value.hashCode()+type.hashCode();
	}
	
	/**
	 * This function checks to see if 2 tags' value and type are equal
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj==null || !(obj instanceof Tag))
			   return false;

		Tag t =(Tag) obj;

        return t.getValue().equals(value) && t.getType().equals(type);
	}
}
