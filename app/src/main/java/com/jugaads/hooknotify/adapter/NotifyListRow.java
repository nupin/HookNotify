package com.jugaads.hooknotify.adapter;

import android.app.Activity;
import android.support.design.widget.CoordinatorLayout.LayoutParams;
import android.support.design.widget.SwipeDismissBehavior;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.jugaads.hooknotify.R;

/**
 * Created by npillai on 16-02-2016.
 */
public class NotifyListRow extends ArrayAdapter<String>{

    private final Activity context;
    private final String[] senderName;
    private final String[] sentText;

    public NotifyListRow(Activity context , String[] senderName , String[] sentText){
        super(context, R.layout.notify_list_row , senderName);

        this.context = context ;
        this.senderName = senderName ;
        this.sentText = sentText ;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater  =context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.notify_list_row, null, true);

        TextView senderNameTextView = (TextView) rowView.findViewById(R.id.sender_name);
        TextView sentTextTextView = (TextView) rowView.findViewById(R.id.sent_text);

        senderNameTextView.setText(senderName[position]);
        sentTextTextView.setText(sentText[position]);

        return rowView ;
    }


}
