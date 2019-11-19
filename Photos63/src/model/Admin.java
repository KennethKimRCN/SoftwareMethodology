package model;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
@SuppressWarnings("serial")
public class Admin extends User{
	public Admin() {
		this.isAdmin = true;
		this.username = "admin";
	}
	
	public Admin(String name) {
		this.isAdmin = true;
		this.username = name;
	}
}
