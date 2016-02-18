package com.jugaads.hooknotify.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jugaads.hooknotify.R;
import com.jugaads.hooknotify.adapter.ListRowNotifyAdapter;
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

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public NotificationFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static NotificationFragment newInstance(int columnCount) {
        NotificationFragment fragment = new NotificationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notification, container, false);



//        String[] favouritefoods = {"@@@", "!!!!", "aaa", "kkk"};
//        String[] favouritefoods1 = {"@@@@@@@@@@@@@", "!!!!!!!!!!!!", "aaaaaaaaa", "kkkkkkkkkkkk"};
//

//        //Build Adapter
//        NotifyListRow notifyAdapter = new NotifyListRow(getActivity(),favouritefoods,favouritefoods1);
//
//        final ListView listView = (ListView) view.findViewById(R.id.listView);
//        listView.setAdapter(notifyAdapter);
//
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Intent myIntent = new Intent(getActivity(), NotificationDetailActivity.class);
//                myIntent.putExtra("row_value", listView.getItemAtPosition(i).toString());
//                getActivity().startActivity(myIntent);
//            }
//        });

        listRowRecyclerView = (RecyclerView) view.findViewById(R.id.listView);
        listRowRecyclerView.setHasFixedSize(true);
        listRowLayoutManager = new LinearLayoutManager(getContext());
        listRowRecyclerView.setLayoutManager(listRowLayoutManager);
        listRowAdapter = new ListRowNotifyAdapter(getList1(),getList2());
        listRowRecyclerView.setAdapter(listRowAdapter);

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

//        mRecyclerView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//
//                Intent myIntent = new Intent(getActivity(), NotificationDetailActivity.class);
//                myIntent.putExtra("row_value", listView.getItemAtPosition(i).toString());
//                getActivity().startActivity(myIntent);
//            }
//        });


        return view;
    }




    private ArrayList<String> getList1() {
        ArrayList results = new ArrayList<String>();
        for (int index = 0; index < 20; index++) {
            results.add(index, "Some Primary Sender name "+index);
        }
        return results;
    }

    private ArrayList<String> getList2() {
        ArrayList results = new ArrayList<String>();
        for (int index = 0; index < 20; index++) {
            results.add(index, "Some Primary Sent Text ##############"+index+"@@@@"+index);
        }
        return results;
    }


}
