package model;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.photos_android.photos_android63.R;

import java.util.*;
/**
 * Created by Khangnyon Kim and Whitney Poh
 */


public class ImageAdapter1 extends BaseAdapter {

        private Context context;
        private List<Photo> photosInAlbum;

        public ImageAdapter1(Context context, List<Photo> photosInAlbum) {
            this.context = context;
            this.photosInAlbum = photosInAlbum;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View gridView;

            if (convertView == null) {

                gridView = new View(context);

                // get layout from mobile.xml
                gridView = inflater.inflate(R.layout.activity_photos_in_album_list, null);

                // set image into imageview
                ImageView imageView = (ImageView) gridView
                        .findViewById(R.id.grid_item_image);

                Uri imguri = Uri.parse(photosInAlbum.get(position).getphotoPath());
                imageView.setImageURI(imguri);

            } else {
                gridView = (View) convertView;
            }

            return gridView;
        }

        @Override
        public int getCount() {
            return photosInAlbum.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }
}
