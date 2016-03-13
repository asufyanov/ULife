package com.example.user.ulife;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;


public class EventListFragment extends Fragment {

    List<Events> events;
    EventListAdapter adapter;
    ListView listView;
    MainActivity parentActivity;
    final int qLimit=7;
    int skip=0;
    Button btnLoadMore;




    public EventListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity=(MainActivity)getActivity();
        skip=0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_event_list, container, false);
        listView=(ListView)v.findViewById(R.id.eventListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openEvent(events.get(position).getObjectId(), events.get(position).getName());
            }
        });

        btnLoadMore = new Button(getActivity());
        btnLoadMore.setGravity(Gravity.CENTER_HORIZONTAL);
        btnLoadMore.setText("Загрузить еще");

        btnLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadMore();
            }
        });


// Adding button to listview at footer

        listView.addFooterView(btnLoadMore);


        return v ;

    }


    @Override
    public void onResume() {
        super.onResume();

        updateEventFeed();

    }
    private void updateEventFeed(){
        final ProgressDialog progressDialog = new ProgressDialog(getActivity(),
                R.style.AppTheme_Dark_Dialog);

        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Немного магии...");
        progressDialog.show();
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        //onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 30000);



        ParseQuery<Events> query = ParseQuery.getQuery("Events");
        query.include("organization");
        query.setLimit(qLimit);
        query.orderByDescending("createdAt");
        query.findInBackground(new FindCallback<Events>() {
            public void done(final List<Events> eventList, ParseException e) {
                if (e != null) {
                    Log.d("score", "Errorrr: " + e.getMessage());
                    return;


                }
                events = new ArrayList<Events>();

                events.addAll(eventList);
                adapter = new EventListAdapter(getView().getContext(), events);
                listView.setAdapter(adapter);

                skip+=eventList.size();
                progressDialog.dismiss();


            }
        });


    }
    private void updateEventFeedWithCache(){



        ParseQuery<Events> query = ParseQuery.getQuery(Events.class);
        query.fromLocalDatastore();
        query.include("organization");
        query.findInBackground(new FindCallback<Events>() {

            @Override
            public void done(final List<Events> eventsList, ParseException e) {
                if (e==null){
                    events = new ArrayList<Events>();

                    events=eventsList;
                    Log.d("MyOrg", "array size of all events="+eventsList.size());
                    Log.d("LOCAL DB", "SUCCESS");

                    adapter=new EventListAdapter(getView().getContext(), eventsList);
                    listView.setAdapter(adapter);

                    Log.d("event ARRAY", events.toString());




                } else {

                    Log.d("LOCAL DB", "THERE IS NO FUCKING DB");
                }


                ParseQuery<Events> query = ParseQuery.getQuery("Events");
                query.include("organization");
                query.findInBackground(new FindCallback<Events>() {
                    public void done(final List<Events> eventList, ParseException e) {
                        if (e != null) {
                            Log.d("score", "Errorrr: " + e.getMessage());
                            return;


                        }
                        events = new ArrayList<Events>();

                        events = eventList;
                        adapter = new EventListAdapter(getView().getContext(), eventList);
                        listView.setAdapter(adapter);

                        if (eventsList != null)
                            ParseObject.unpinAllInBackground(eventsList, new DeleteCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null) {

                                        //error
                                        return;
                                    }
                                    ParseObject.pinAllInBackground(eventList);


                                }
                            });
                    }
                });

            }
        });


    }
    private void openEvent(String event, String tName) {
        Fragment eventFragment=new DisplayEventFragment();


        Bundle bundle = new Bundle();
        bundle.putString("event", event);

        eventFragment.setArguments(bundle);


       parentActivity.openFragment(eventFragment, tName);


    }

    private void loadMore() {
        ParseQuery<Events> query = ParseQuery.getQuery("Events");
        query.include("organization");
        query.setLimit(qLimit);
        query.setSkip(skip);
        query.findInBackground(new FindCallback<Events>() {
            public void done(final List<Events> eventList, ParseException e) {
                if (e != null) {
                    Log.d("score", "Errorrr: " + e.getMessage());
                    return;


                }
                if (events==null) events = new ArrayList<Events>();

                events.addAll(eventList);
                //Toast.makeText(getActivity(), ""+skip, Toast.LENGTH_SHORT);
                adapter.notifyDataSetChanged();
                skip+=eventList.size();


            }
        });
    }









}
