package model;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Class UserList.
 */
@SuppressWarnings("serial")
public class UserList implements Serializable{
	
	/** The users. */
	private ArrayList<User> users;
	
	/**
	 * Instantiates a new user list.
	 */
	public UserList() {
		users = new ArrayList<User>();
	}
	
	/**
	 * Adds the user.
	 *
	 * @param user the user
	 */
	public void addUser(User user) {
		users.add(user);
	}
	
	/**
	 * Removes the user from list.
	 *
	 * @param user the user
	 */
	public void removeUserFromList(User user){
		users.remove(user);
	}
	
	/**
	 * Gets the users.
	 *
	 * @return the users
	 */
	public ArrayList<User> getUsers(){
		return users;
	}
	
	/** The Constant storeDir. */
	public static final String storeDir = "dat";
	
	/** The Constant storeFile. */
	public static final String storeFile = "users.dat";
	
	/**
	 * Write user list.
	 *
	 * @param list the list
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public static void writeUserList(UserList list) throws IOException{
		FileOutputStream fileOut = new FileOutputStream(storeDir + File.separator + storeFile);
		ObjectOutputStream output = new ObjectOutputStream(fileOut);
		output.writeObject(list);
		output.close();
		fileOut.close();
	}
	
	/**
	 * Read user list.
	 *
	 * @return the user list
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ClassNotFoundException the class not found exception
	 */
	public static UserList readUserList() throws IOException, ClassNotFoundException{
		FileInputStream fileIn = new FileInputStream(storeDir + File.separator + storeFile);
		ObjectInputStream input = new ObjectInputStream(fileIn);
		UserList users = (UserList) input.readObject();
		input.close();
		fileIn.close();
		return users;
	}
	
	/**
	 * User exists.
	 *
	 * @param user the user
	 * @return true, if successful
	 */
	public boolean userExists(String user){
		for(User u : users){
			if (u.getUsername().equals(user))
				return true;
		}
		return false;
	  }
	
	/**
	 * Gets the username.
	 *
	 * @param username the username
	 * @return the username
	 */
	public User getUsername(String username) {
		  for (User u : users){
			  if (u.getUsername().equals(username))
				  return u;
		  }
		  return null;
	  }
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		  if (users == null)
			  return "no users";
		  String output = "";
		  for(User u : users){
			  output += u.getUsername() + " ";
		  }
		  return output;
	  }
}
