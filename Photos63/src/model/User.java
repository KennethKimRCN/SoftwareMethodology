package model;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * In the User model class you can
 * 1. get user name and user albums
 * 2. Add/Remove albums
 * 3. Add photos to album
 * 4. Retrieve album index number and album name
 * 5. Checks if album name already exists
 */
@SuppressWarnings("serial")
public class User implements Serializable{
	
	/** The username. */
	private String username;
	
	/** The albums. */
	private List<Album> albums;

	
	/**
	 * Basic constructor for User.
	 *
	 * @param username the username
	 */
	public User(String username) {
		this.username = username;
		albums = new ArrayList<Album>();
	}
	
	/**
	 * Gets the username.
	 *
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	
	/**
	 * Gets the albums.
	 *
	 * @return the albums
	 */
	public List<Album> getAlbums() {
		return albums;
	}
	
	/**
	 * Adds the album.
	 *
	 * @param albumName the album name
	 */
	public void addAlbum(String albumName) {
		albums.add(new Album(albumName));
	}
	
	
	/**
	 * Adds the album.
	 *
	 * @param a the a
	 */
	public void addAlbum(Album a) {
		albums.add(a);
	}
	
	/**
	 * Removes the album.
	 *
	 * @param album the album
	 */
	public void removeAlbum(Album album)
	{
		albums.remove(album);
	}
	
	/**
	 * Adds the photo to album.
	 *
	 * @param p the p
	 * @param albumIndex the album index
	 */
	public void addPhotoToAlbum(Photo p, int albumIndex) {
		albums.get(albumIndex).addPhoto(p);
	}
	
	/**
	 * This function checks if the album name already exists.
	 *
	 * @param albumName is the input from user
	 * @return true if album name is already in the list. return false otherwise
	 */
	public boolean albumNameExists(String albumName) {
		for (Album a: albums)
			if (a.getName().toLowerCase().equals(albumName.trim().toLowerCase()))
				return true;
		
		return false;
	}
	
	/**
	 * This finds the index of the album in the array list.
	 *
	 * @param a the a
	 * @return the index number of the array list in the album
	 */
	public int getAlbumIndexByAlbum(Album a) {
		for (int i = 0; i < albums.size(); i++)
			if (albums.get(i).getName().equals(a.getName()))
				return i;
		return -1;
	}
	
	/**
	 * This function searches for the name of the album.
	 *
	 * @param name the name
	 * @return the album name if exists. Returns null otherwise
	 */
	public Album getAlbumByName(String name) {
		for(Album a : albums)
		{
			if(a.getName().equals(name))
				return a;
		}
		return null;
	}
}
