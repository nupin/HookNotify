package com.jugaads.hooknotify.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jugaads.hooknotify.R;

import java.util.ArrayList;

/**
 * Created by npillai on 17-02-2016.
 */
public class ListRowNotifyAdapter extends RecyclerView.Adapter <ListRowNotifyAdapter.ListRowNotifyHolder>
{
    private ArrayList<String> senderNameList;
    private ArrayList<String> sentTextList;
    private static NotifyItemClickListener clickListener ;

    public static class ListRowNotifyHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView senderName;
        public TextView sentText;

        public ListRowNotifyHolder(View v){
            super(v);
            this.senderName  = (TextView) itemView.findViewById(R.id.sender_name);
            this.sentText = (TextView) itemView.findViewById(R.id.sent_text);
        }

        @Override
        public void onClick(View v) {
            clickListener.onItemClick(getAdapterPosition(), v);
        }
    }

    public void setOnItemClickListener(NotifyItemClickListener clickListener)
    {
        this.clickListener = clickListener;
    }

    public ListRowNotifyAdapter(ArrayList<String> senderNameList ,ArrayList<String> sentTextList )
    {
        this.senderNameList = senderNameList ;
        this.sentTextList = sentTextList ;
    }

    @Override
    public ListRowNotifyHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notify_list_row,parent,false);

        ListRowNotifyHolder myViewHolder = new ListRowNotifyHolder(v);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(ListRowNotifyHolder holder,int position){
        holder.senderName.setText(senderNameList.get(position));
        holder.sentText.setText(sentTextList.get(position));
    }


    public void deleteItem(int index) {
        senderNameList.remove(index);
        sentTextList.remove(index);
        notifyItemRemoved(index);
    }

    @Override
    public int getItemCount(){
        return senderNameList.size();
    }

    public interface NotifyItemClickListener {
        public void onItemClick(int position, View v);
    }

}
