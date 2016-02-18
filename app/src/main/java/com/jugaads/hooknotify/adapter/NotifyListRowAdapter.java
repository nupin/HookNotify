package com.jugaads.hooknotify.adapter;

import android.app.Activity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jugaads.hooknotify.R;

/**
 * Created by npillai on 16-02-2016.
 */
public class NotifyListRowAdapter extends ArrayAdapter<View> {

    private final Activity context;
    private final View[] list_row_view ;

    public NotifyListRowAdapter(Activity context ,  View[] list_row_view ){
        super(context, R.layout.notify_list_row,list_row_view);
        this.context=context;
        this.list_row_view=list_row_view;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return list_row_view[position];
    }
}
