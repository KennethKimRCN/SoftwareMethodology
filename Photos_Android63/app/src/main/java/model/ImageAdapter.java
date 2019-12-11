package model;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.photos_android.photos_android63.R;

import java.util.*;

/**
 * Created by Khangnyon Kim and Whitney Poh
 */


public class ImageAdapter extends BaseAdapter {

    private Context mContext;
    private List<Photo> photosInAlbum;

    public ImageAdapter(Context c, List<Photo> photosInAlbum) {
        mContext = c;
        this.photosInAlbum = photosInAlbum;
    }

    public int getCount() {
        return photosInAlbum.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        ImageView imgview = null;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
            imgview = imageView.findViewById(R.id.grid_item_image);
        } else {
            imageView = (ImageView) convertView;
        }

        Uri imguri = Uri.parse(photosInAlbum.get(position).getphotoPath());
        imgview.setImageURI(imguri);
        return imageView;
    }
}
