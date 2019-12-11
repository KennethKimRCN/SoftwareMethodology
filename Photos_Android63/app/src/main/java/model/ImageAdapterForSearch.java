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

public class ImageAdapterForSearch extends BaseAdapter {

    private Context context;
    private List<Photo> searchResults;

    public ImageAdapterForSearch(Context context, List<Photo> searchResults) {
        this.context = context;
        this.searchResults = searchResults;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

        if (convertView == null) {

            gridView = new View(context);

            gridView = inflater.inflate(R.layout.activity_photos_in_searched_list_view, null);

            ImageView imageView = (ImageView) gridView
                    .findViewById(R.id.searchedImageGridView);

            Uri imguri = Uri.parse(searchResults.get(position).getphotoPath());
            imageView.setImageURI(imguri);

        } else {
            gridView = (View) convertView;
        }

        return gridView;
    }

    @Override
    public int getCount() {
        return searchResults.size();
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
