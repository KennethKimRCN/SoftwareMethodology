package com.photos_android.photos_android63;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import model.Album;
import model.MySpinner;
import model.Photo;
import model.PhotoAlbumManager;
/**
 * Created by Khangnyon Kim and Whitney Poh
 */

public class Slideshow extends AppCompatActivity {
    private MySpinner spinner;
    private int spinnerIndexSelected;

    private EditText tagEntered;
    private Button addTagButton, backwardBtn, forwardBtn;

    private ListView tagsList;
    private ArrayAdapter<String> tagsAdapter;
    private static List<String> allTags = new ArrayList<String>();

    private TextView tagText, filename;
    private Button deleteTagButton;
    private String [] spinnerItems = {"Location", "Person"};

    private static Album currAlbum = null;
    private static int currindex;
    private static List<Photo> photosInAlbum = new ArrayList<Photo>();

    private int imagePosition;
    ImageView imageOnSlideShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slideshow);

        //avoids keyboard from automatically popping up at startup
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        Intent slideShowPageIntent = getIntent();
        this.imagePosition = slideShowPageIntent.getIntExtra("imagePosition", -1);

        imageOnSlideShow = (ImageView) findViewById(R.id.imageView);
        filename = (TextView) findViewById(R.id.filename);
        if(this.imagePosition >= 0){
            // Put image into ImageView
            Album currAlbum = UserHomepage.manager.getcurrentAlbum();
            Photo currPhoto = currAlbum.getPhotos().get(imagePosition);
            filename.setText(currPhoto.getfileName());
            Uri imgUri = Uri.parse(currPhoto.getphotoPath());
            imageOnSlideShow.setImageURI(imgUri);
        }
        else{
            Toast.makeText(getApplicationContext(), "Image Not Found because it's position < 0", Toast.LENGTH_LONG).show();
        }

        backwardBtn = (Button) findViewById(R.id.backwardBtn);
        forwardBtn = (Button) findViewById(R.id.forwardBtn);

        backwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                photosInAlbum.clear();
                int albumsize = UserHomepage.manager.getcurrentAlbum().getPhotos().size();

                for(int i = 0; i < albumsize; i++) {
                    photosInAlbum.add(UserHomepage.manager.getcurrentAlbum().getPhotos().get(i));
                }

                //photosInAlbum now consist of all the photos in current album

                if(currAlbum != UserHomepage.manager.getcurrentAlbum()) {
                    currindex = imagePosition;
                    currAlbum = UserHomepage.manager.getcurrentAlbum();
                }

                currindex--;

                if(currindex == -1) {
                    currindex = albumsize-1;
                }

                //set the previous pic as the current photo
                Photo newPhoto = UserHomepage.manager.getcurrentAlbum().getPhotos().get(currindex);
                UserHomepage.manager.getcurrentAlbum().setCurrentPhoto(newPhoto);

                //set the imageview of photo
                Album currAlbum = UserHomepage.manager.getcurrentAlbum();
                Photo currPhoto = currAlbum.getPhotos().get(currindex);
                filename.setText(currPhoto.getfileName());
                Uri imgUri = Uri.parse(currPhoto.getphotoPath());
                imageOnSlideShow.setImageURI(imgUri);

                //set all the tags of the photo
                tagsList = (ListView) findViewById(R.id.tagsOfPhotoSlideshow);
                allTags.clear();
                allTags.addAll(UserHomepage.manager.getcurrentAlbum().getcurrentPhoto().getAllTags());
                tagsAdapter.notifyDataSetChanged();
                tagsList.setAdapter(tagsAdapter);

            }
        });

        forwardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                photosInAlbum.clear();
                int albumsize = UserHomepage.manager.getcurrentAlbum().getPhotos().size();

                for(int i = 0; i < albumsize; i++) {
                    photosInAlbum.add(UserHomepage.manager.getcurrentAlbum().getPhotos().get(i));
                }

                //photosInAlbum now consist of all the photos in current album

                if(currAlbum != UserHomepage.manager.getcurrentAlbum()) { //different album, change of album
                    currindex = imagePosition;
                    currAlbum = UserHomepage.manager.getcurrentAlbum();
                }

                currindex++;

                if(currindex == albumsize) { //was at the very last pic, cannot go next anymore after this, so set currindex to first image
                    currindex = 0;
                }

                //set the next pic as the current photo
                Photo newPhoto = UserHomepage.manager.getcurrentAlbum().getPhotos().get(currindex);
                UserHomepage.manager.getcurrentAlbum().setCurrentPhoto(newPhoto);

                //set the imageview of photo
                Album currAlbum = UserHomepage.manager.getcurrentAlbum();
                Photo currPhoto = currAlbum.getPhotos().get(currindex);
                filename.setText(currPhoto.getfileName());
                Uri imgUri = Uri.parse(currPhoto.getphotoPath());
                imageOnSlideShow.setImageURI(imgUri);

                //set all the tags of the photo
                tagsList = (ListView) findViewById(R.id.tagsOfPhotoSlideshow);
                allTags.clear();
                allTags.addAll(UserHomepage.manager.getcurrentAlbum().getcurrentPhoto().getAllTags());
                tagsAdapter.notifyDataSetChanged();
                tagsList.setAdapter(tagsAdapter);

            }
        });

        tagsList = (ListView) findViewById(R.id.tagsOfPhotoSlideshow);
        allTags = UserHomepage.manager.getcurrentAlbum().getcurrentPhoto().getAllTags();
        tagsAdapter = new ArrayAdapter<String>(this, R.layout.activity_photo_tags_list_view, R.id.tagOfPhotoSlideshow, allTags);
        tagsList.setAdapter(tagsAdapter);

        spinner = (MySpinner) findViewById(R.id.addTagOptions);
        ArrayAdapter<CharSequence> itemAdapter = ArrayAdapter.createFromResource(this, R.array.search_options,android.R.layout.simple_spinner_item);
        itemAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(itemAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerIndexSelected = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        tagEntered = (EditText) findViewById(R.id.addTagValue);
        addTagButton = (Button) findViewById(R.id.addTagButtonSlideshow);

        addTagButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tagVal = tagEntered.getText().toString();
                if(tagVal.isEmpty()){
                    Toast.makeText(Slideshow.this, "Tag cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                Photo currPhoto = UserHomepage.manager.getcurrentAlbum().getcurrentPhoto();

                if(spinnerIndexSelected == 0){
                    currPhoto.addLocationTag(tagVal);
                }
                else if(spinnerIndexSelected == 1){
                    currPhoto.addPersonTag(tagVal);
                }

                tagEntered.setText("");

                // TODO Serialize
                //refresh and serialize list
                try {
                    PhotoAlbumManager.serialize(UserHomepage.manager);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                tagsList = (ListView) findViewById(R.id.tagsOfPhotoSlideshow);
                allTags.clear();
                allTags.addAll(UserHomepage.manager.getcurrentAlbum().getcurrentPhoto().getAllTags());
                tagsAdapter.notifyDataSetChanged();
                tagsList.setAdapter(tagsAdapter);
            }
        });


        deleteTagButton = (Button) findViewById(R.id.deleteTagButtonSlideshow);

        tagsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            deleteTagButton.setVisibility(View.VISIBLE);
            final String selectedTag = tagsList.getItemAtPosition(i).toString();
            deleteTagButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String[] splitList = selectedTag.split(": ");
                    String tagKey = splitList[0];
                    String tagVal = splitList[1];
                    Photo currPhoto = UserHomepage.manager.getcurrentAlbum().getcurrentPhoto();

                    if(tagKey.toLowerCase().equals(spinnerItems[0].toLowerCase())){
                        currPhoto.removeLocationTag(tagVal);
                    }
                    else if(tagKey.toLowerCase().equals(spinnerItems[1].toLowerCase())){
                        currPhoto.removePersonTag(tagVal);
                    }

                    // TODO Serialize
                    try {
                        PhotoAlbumManager.serialize(UserHomepage.manager);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    tagsList = (ListView) findViewById(R.id.tagsOfPhotoSlideshow);
                    allTags.clear();
                    allTags.addAll(UserHomepage.manager.getcurrentAlbum().getcurrentPhoto().getAllTags());
                    tagsAdapter.notifyDataSetChanged();
                    tagsList.setAdapter(tagsAdapter);
                     }
                });
            }
        });
    }
}
