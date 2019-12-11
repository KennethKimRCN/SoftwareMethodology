package model;

import android.content.Context;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatSpinner;

/**
 * Created by Khangnyon Kim and Whitney Poh
 */


public class MySpinner extends AppCompatSpinner {

    OnItemSelectedListener listener;

    public MySpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public void setSelection(int position) {
        super.setSelection(position);
        if (listener != null)
            listener.onItemSelected(null, null, position, 0);
    }

    public void setOnItemSelectedEvenIfUnchangedListener(OnItemSelectedListener listener) {
        this.listener = listener;
    }

}
