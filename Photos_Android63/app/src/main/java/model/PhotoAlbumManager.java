package model;

import java.io.*;
import java.util.*;

/**
 * Created by Khangnyon Kim and Whitney Poh
 */


public class PhotoAlbumManager implements Serializable {

    public static final long serialVersionUID = 42L;

    private List<Album> albums;
    private Album currentAlbum;

    public PhotoAlbumManager() {
        albums = new ArrayList<Album>();
    }


    public void addAlbum(Album album) {
        albums.add(album);
    }


    public void removeAlbum(int index) {
        albums.remove(index);
    }

    public List<Photo> getPhotosWithTags(List<String> locationTags, List<String> personTags){
        Set<Photo> resultSet = new HashSet<Photo>();
        List<Photo> resultList = new ArrayList<Photo>();

        for(Album album : this.albums){
            for(Photo photo : album.getPhotos()){
                boolean alreadyAdded = false;

                for(String searchLocationTag : locationTags) {
                    for(String photoLocationTag : photo.getlocationTags()) {
                        if (photoLocationTag.toLowerCase().contains(searchLocationTag.toLowerCase())){
                            resultSet.add(photo);
                            alreadyAdded = true;
                            break;
                        }
                    }
                    if(alreadyAdded){
                        break;
                    }
                }
                if(!alreadyAdded) {
                    for (String searchPersonTag : personTags) {
                        for(String photoPersonTag : photo.getpersonTags()) {
                            if (photoPersonTag.toLowerCase().contains(searchPersonTag.toLowerCase())) {
                                resultSet.add(photo);
                                break;
                            }
                        }
                        if(alreadyAdded){
                            break;
                        }
                    }
                }
            }
        }

        resultList.addAll(resultSet);
        return resultList;
    }

    public static void serialize(PhotoAlbumManager userdata) throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("/data/data/com.photos_android.photos_android63/files/albums.dat"));
        oos.writeObject(userdata);
        oos.close();
    }

    public static PhotoAlbumManager deserialize() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("/data/data/com.photos_android.photos_android63/files/albums.dat"));
        PhotoAlbumManager userdata = (PhotoAlbumManager) ois.readObject();
        ois.close();
        return userdata;
    }

    public List<Album> getAlbums() {
        return albums;
    }

    public Album getcurrentAlbum() {
        return currentAlbum;
    }

    public void setcurrentAlbum(Album currentAlbum) {
        this.currentAlbum = currentAlbum;
    }
}
