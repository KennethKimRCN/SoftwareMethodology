package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class UserList implements Serializable{
	private ArrayList<User> users;
	
	public UserList() {
		users = new ArrayList<User>();
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}
	
	public ArrayList<User> getUsers(){
		return users;
	}
	
	public static final String storeDir = "dat";
	public static final String storeFile = "users.dat";
	
	public static void writeUserList(UserList list) throws IOException{
		FileOutputStream fileOut = new FileOutputStream(storeDir + File.separator + storeFile);
		ObjectOutputStream output = new ObjectOutputStream(fileOut);
		output.writeObject(list);
		output.close();
		fileOut.close();
	}
	
	public static UserList readUserList() throws IOException, ClassNotFoundException{
		FileInputStream fileIn = new FileInputStream(storeDir + File.separator + storeFile);
		ObjectInputStream input = new ObjectInputStream(fileIn);
		UserList users = (UserList) input.readObject();
		input.close();
		fileIn.close();
		return users;
	}
}
