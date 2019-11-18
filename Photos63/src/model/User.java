package model;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class User implements Serializable{
	String username;
	boolean isAdmin;
	
	public String getUsername() {
		return this.username;
	}
	
	public String toString() {
		return this.getUsername();
	}
	
	public boolean isAdmin() {
		return this.isAdmin;
	}
}
