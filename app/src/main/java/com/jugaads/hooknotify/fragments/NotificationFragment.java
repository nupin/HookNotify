package com.jugaads.hooknotify.fragments;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.telephony.SmsMessage;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jugaads.hooknotify.R;
import com.jugaads.hooknotify.adapter.ListRowNotifyAdapter;
import com.jugaads.hooknotify.broadcast.SMSBroadcastReceiver;
import com.jugaads.hooknotify.fragments.dummy.DummyContent.DummyItem;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p/>*/
/* Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class NotificationFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;


    private RecyclerView listRowRecyclerView;
    private ListRowNotifyAdapter listRowAdapter;
    private RecyclerView.LayoutManager listRowLayoutManager;

    final private int REQUEST_CODE_ASK_PERMISSIONS = 123;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotificationFragment() {
    }



    private static NotificationFragment fragment ;

    public static NotificationFragment getInstance(){
        return fragment ;
    }
    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
//    public static NotificationFragment newInstance(int columnCount) {
//        NotificationFragment fragment = new NotificationFragment();
//        Bundle args = new Bundle();
//        args.putInt(ARG_COLUMN_COUNT, columnCount);
//        fragment.setArguments(args);
//        return fragment;
//    }


    @Override
    public void onStart() {
        super.onStart();
        fragment = this ;
        getActivity().registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(broadcastReceiver, filter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    IntentFilter filter = new IntentFilter("android.provider.Telephony.SMS_RECEIVED");
    private BroadcastReceiver broadcastReceiver ;


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

         broadcastReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context context, Intent intent) {
                Bundle intentExtras = intent.getExtras();
                if (intentExtras != null) {
                    Object[] sms = (Object[]) intentExtras.get(SMSBroadcastReceiver.SMS_BUNDLE);
                    String smsMessageStr = "";
                    for (int i = 0; i < sms.length; ++i) {
                        SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i]);

                        String smsBody = smsMessage.getMessageBody().toString();
                        String address = smsMessage.getOriginatingAddress();

                        smsMessageStr += "SMS From: " + address + "\n";
                        smsMessageStr += smsBody + "\n";
                    }
                    updateList(smsMessageStr);
                }
            }
        };
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        listRowRecyclerView = (RecyclerView) view.findViewById(R.id.listView);
        listRowRecyclerView.setHasFixedSize(true);
        listRowLayoutManager = new LinearLayoutManager(getContext());
        listRowRecyclerView.setLayoutManager(listRowLayoutManager);
        listRowAdapter = new ListRowNotifyAdapter();
        listRowRecyclerView.setAdapter(listRowAdapter);

        refreshSmsInbox();

        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                // callback for drag-n-drop, false to skip this feature
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // callback for swipe to dismiss, removing item from data and adapter

                listRowAdapter.deleteItem(viewHolder.getAdapterPosition());
                listRowAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(listRowRecyclerView);

        return view;
    }

    public void updateList(final String smsMessage) {
        System.out.println(listRowAdapter+"!!!!!--------------->>>>>>>>>>>"+smsMessage);
        listRowAdapter.insertItem(smsMessage);
        listRowAdapter.notifyDataSetChanged();
    }


//    private ArrayList<String> getList1() {
//        ArrayList results = new ArrayList<String>();
//        for (int index = 0; index < 20; index++) {
//            results.add(index, "Some Primary Sender name "+index);
//        }
//        return results;
//    }
//
//    private ArrayList<String> getList2() {
//        ArrayList results = new ArrayList<String>();
//        for (int index = 0; index < 20; index++) {
//            results.add(index, "Some Primary Sent Text ##############"+index+"@@@@"+index);
//        }
//        return results;
//    }

    public ArrayList<String> refreshSmsInbox() {
        ArrayList<String> messageList = new ArrayList<String>();
        int sms_receive_per = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            sms_receive_per = getActivity().checkSelfPermission(Manifest.permission.RECEIVE_SMS);
            if (sms_receive_per != PackageManager.PERMISSION_GRANTED) {
                getActivity().requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
        }
        ContentResolver contentResolver = getActivity().getContentResolver();
        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return messageList;
//        listRowAdapter.clear();
        do {
            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) +
                    "\n" + smsInboxCursor.getString(indexBody) + "\n";

            messageList.add(str);
        } while (smsInboxCursor.moveToNext());
        return  messageList ;
    }


}