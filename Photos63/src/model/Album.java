package model;

import java.io.Serializable;
import java.util.ArrayList;

import app.Photo;

public class Album implements Serializable{
	private String name;
	private ArrayList<Photo> photos;
	private int photoCount;
	
	public Album(String name) {
		this.name = name;
		this.photos = new ArrayList<Photo>();
		this.photoCount = 0;
	}
	
	public Album() {
		this.name = null;
		this.photos = new ArrayList<Photo>();
		this.photoCount = 0;
	}
}
