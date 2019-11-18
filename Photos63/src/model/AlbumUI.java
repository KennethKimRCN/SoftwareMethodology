package model;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class AlbumUI extends User{
	private ArrayList<Album> albums;
	
	public AlbumUI(String name) {
		this.isAdmin = false;
		this.username = name;
		this.albums = new ArrayList<Album>();
	}
}
