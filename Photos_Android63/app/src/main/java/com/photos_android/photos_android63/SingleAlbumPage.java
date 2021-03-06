package com.photos_android.photos_android63;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Album;
import model.ImageAdapter1;
import model.Photo;
import model.PhotoAlbumManager;
/**
 * Created by Khangnyon Kim and Whitney Poh
 */

public class SingleAlbumPage extends AppCompatActivity {

    private static List<Photo> photosInAlbum = new ArrayList<Photo>();
    private FloatingActionButton addPhotoBtn;
    private static final int READ_REQUEST_CODE = 42;
    private ImageAdapter1 imageAdapter;
    private Button deletePhotoBtn, movePhotoBtn;
    final Context c = this;
    GridView gridview;

    private static final String TAG = "SingleAlbumPage";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_album_page);

        TextView AlbumNameText = (TextView) findViewById(R.id.albumName);
        AlbumNameText.setText(UserHomepage.manager.getcurrentAlbum().getAlbumName());

        populatePhotosList();

        gridview = (GridView) findViewById(R.id.gridView1);
        imageAdapter = new ImageAdapter1(this, photosInAlbum);
        gridview.setAdapter(imageAdapter);

        addPhotoBtn = (FloatingActionButton) findViewById(R.id.addPhotoBtn);
        addPhotoBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                performFileSearch();

            }

            /**
             * Fires an intent to spin up the "file chooser" UI and select an image.
             */
            public void performFileSearch() {

                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);

                intent.setType("image/*");

                startActivityForResult(intent, READ_REQUEST_CODE);
            }

        });

        deletePhotoBtn = (Button) findViewById(R.id.deletePhotoBtn);
        movePhotoBtn = (Button) findViewById(R.id.movePhotoBtn);
        deletePhotoBtn.setVisibility(View.INVISIBLE);
        movePhotoBtn.setVisibility(View.INVISIBLE);
        gridview.setLongClickable(true);


        gridview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                deletePhotoBtn.setVisibility(View.VISIBLE);
                movePhotoBtn.setVisibility(View.VISIBLE);
                final int photoindex = i;
                final Photo photo_at_pos = UserHomepage.manager.getcurrentAlbum().getPhotos().get(photoindex);

                deletePhotoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //remove photo at photoindex
                        UserHomepage.manager.getcurrentAlbum().removePhoto(photoindex);

                        Toast.makeText(SingleAlbumPage.this, "Photo Successfully Deleted", Toast.LENGTH_SHORT).show();

                        //serialize and refresh list
                        try {
                            PhotoAlbumManager.serialize(UserHomepage.manager);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        gridview = (GridView) findViewById(R.id.gridView1);
                        populatePhotosList();
                        imageAdapter.notifyDataSetChanged();
                        gridview.setAdapter(imageAdapter);
                        //refreshing ends here

                        deletePhotoBtn.setVisibility(View.INVISIBLE); //hide the delete button
                        movePhotoBtn.setVisibility(View.INVISIBLE); //hide the move button

                    }
                });

                movePhotoBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(c);
                        View mView = layoutInflaterAndroid.inflate(R.layout.activity_create_album_dialogbx, null);
                        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(c);
                        alertDialogBuilderUserInput.setView(mView);
                        TextView title = (TextView) mView.findViewById(R.id.title);
                        title.setText("Move Photo");
                        final EditText albumNameinDialog = (EditText) mView.findViewById(R.id.userInputDialog);
                        alertDialogBuilderUserInput
                                .setCancelable(false)
                                .setPositiveButton("Move", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialogBox, int id) {
                                        String albumname = albumNameinDialog.getText().toString();
                                        String photopath = photo_at_pos.getphotoPath();

                                        //check if album with name exists, and if photo already exists in destination album
                                        String toastmsg = doesAlbumandPhotoexist(albumname, photopath);
                                        if(toastmsg != "good"){
                                            Toast.makeText(getApplicationContext(), toastmsg, Toast.LENGTH_SHORT).show();
                                            return;
                                        }

                                        /*At this point, photo is good to be moved*/
                                        //First, add photo in destination album
                                        int destinationalbumindex = 0;
                                        for(int i = 0; i < UserHomepage.manager.getAlbums().size(); i++){
                                            if(UserHomepage.manager.getAlbums().get(i).getAlbumName().equals(albumname)){
                                                destinationalbumindex = i;
                                            }
                                        }
                                        UserHomepage.manager.getAlbums().get(destinationalbumindex).addPhoto(photopath);

                                        //copy all the location and person tags
                                        Photo srcphoto = UserHomepage.manager.getcurrentAlbum().getPhotos().get(photoindex);
                                        Album destphotoalbum = UserHomepage.manager.getAlbums().get(destinationalbumindex);
                                        int destphotoindex = destphotoalbum.getPhotos().size() - 1;
                                        Photo destphoto = destphotoalbum.getPhotos().get(destphotoindex);

                                        for(int i = 0; i < srcphoto.getlocationTags().size(); i++){
                                            destphoto.addLocationTag(srcphoto.getlocationTags().get(i));
                                        }
                                        for(int i = 0; i < srcphoto.getpersonTags().size(); i++){
                                            destphoto.addPersonTag(srcphoto.getpersonTags().get(i));
                                        }

                                        //Now, delete photo from current album
                                        UserHomepage.manager.getcurrentAlbum().removePhoto(photoindex);

                                        Toast.makeText(getApplicationContext(), "Photo Successfully Moved", Toast.LENGTH_SHORT).show();

                                        //serialize and refresh list
                                        try {
                                            PhotoAlbumManager.serialize(UserHomepage.manager);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                        gridview = (GridView) findViewById(R.id.gridView1);
                                        populatePhotosList();
                                        imageAdapter.notifyDataSetChanged();
                                        gridview.setAdapter(imageAdapter);
                                        //refreshing ends here

                                    }
                                })

                                .setNegativeButton("Cancel",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialogBox, int id) {
                                                dialogBox.cancel();
                                            }
                                        });

                        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
                        alertDialogAndroid.show();


                        deletePhotoBtn.setVisibility(View.INVISIBLE); //hide the delete button
                        movePhotoBtn.setVisibility(View.INVISIBLE); //hide the move button

                    }
                });

                return true;
            }
        });


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {

                //set onclicked photo as current photo
                Photo photo = UserHomepage.manager.getcurrentAlbum().getPhotos().get(position);
                UserHomepage.manager.getcurrentAlbum().setCurrentPhoto(photo);

                Intent slideShowPageIntent = new Intent(SingleAlbumPage.this, Slideshow.class);
                slideShowPageIntent.putExtra("imagePosition", position);
                startActivity(slideShowPageIntent);
            }
        });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent resultData) {

        if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Uri uri = null;
            if (resultData != null) {
                uri = resultData.getData();
                String image_uristr = uri.toString();

                for(int i = 0; i < photosInAlbum.size(); i++){
                    if(image_uristr.equals(photosInAlbum.get(i).getphotoPath())){ //duplicate photos in same album not allowed
                        Toast.makeText(getApplicationContext(), "This Photo Already Exists In Album", Toast.LENGTH_LONG).show();
                        return;
                    }
                }

                //At this point, we have the photopath, so add it in the current album
                UserHomepage.manager.getcurrentAlbum().addPhoto(image_uristr);

                //serialize and refresh list
                try {
                    PhotoAlbumManager.serialize(UserHomepage.manager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                gridview = (GridView) findViewById(R.id.gridView1);
                populatePhotosList();
                imageAdapter.notifyDataSetChanged();
                gridview.setAdapter(imageAdapter);
                //refreshing ends here
            }
        }
    }


    private String doesAlbumandPhotoexist(String albumname, String photopath){
        for(int i = 0; i < UserHomepage.manager.getAlbums().size(); i++){
            if(albumname.equals(UserHomepage.manager.getAlbums().get(i).getAlbumName())){
                for(int j = 0; j < UserHomepage.manager.getAlbums().get(i).getPhotos().size(); j++){
                    if(UserHomepage.manager.getAlbums().get(i).getPhotos().get(j).getphotoPath().equals(photopath)){
                        return "Photo Already Exists in Destination Album";
                    }
                }
                return "good";
            }
        }
        return "Album With Entered Name Does Not Exist. First Create Album";
    }

    private static void populatePhotosList() {
        photosInAlbum.clear();
        photosInAlbum.addAll(UserHomepage.manager.getcurrentAlbum().getPhotos());
    }

}
