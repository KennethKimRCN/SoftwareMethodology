package model;
/**
 * @author Khangnyon Kim
 * @author Whiteny Poh
 */
import java.util.ArrayList;

@SuppressWarnings("serial")
public class AlbumUI extends User{
	private ArrayList<Album> albums;
	
	public AlbumUI(String name) {
		this.isAdmin = false;
		this.username = name;
		this.albums = new ArrayList<Album>();
	}
	
	public void addAlbum(Album album) {
		this.albums.add(album);
	}
	
	public void removeAlbum(Album album) {
		for(Album a: this.albums){
			if(a.equals(album)) {
				this.albums.remove(a);
				return;
			}
		}
	}
	
	public ArrayList<Album> getAlbumList(){
		return this.albums;
	}
}
