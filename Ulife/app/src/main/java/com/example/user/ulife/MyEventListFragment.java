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
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;


public class MyEventListFragment extends Fragment {


    List<Events> events;
    EventListAdapter adapter;
    ListView listView;
    MainActivity parentActivity;
   List<String> orgsID;
    ParseUser curUser;
    List<Organization> myOrganizations;
    ArrayList<String> followedOrgIDs;

    final int qLimit=7;
    int skip=0;
    Button btnLoadMore;



    public MyEventListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity=(MainActivity)getActivity();
        events=parentActivity.myEvents;
        curUser=parentActivity.curUser;
        myOrganizations=parentActivity.myFollowedOrganizations;
        followedOrgIDs=parentActivity.followedOrgIDs;


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
        listView.addFooterView(btnLoadMore);
        return v;
    }

    private void loadMore() {
        ParseQuery<UserOrgFollow> query=ParseQuery.getQuery(UserOrgFollow.class);
        //query.include("organization");
        query.whereEqualTo("user", curUser);
        ;

        query.findInBackground(new FindCallback<UserOrgFollow>() {
            @Override
            public void done(List<UserOrgFollow> userOrgFollows, ParseException e) {
                if (e==null) {
                    List<Organization> myOrgWithoutData=new ArrayList<Organization>();
                    parentActivity.myUOF=userOrgFollows;
                    followedOrgIDs.clear();
                    for (UserOrgFollow u: userOrgFollows){
                        followedOrgIDs.add(u.getOrganization().getObjectId());
                        myOrgWithoutData.add(ParseObject.createWithoutData(Organization.class,u.getOrganization().getObjectId()));

                    }

                    ParseQuery<Events> query=ParseQuery.getQuery(Events.class);
                    query.include("organization");
                    query.setLimit(qLimit);
                    query.setSkip(skip);
                    query.orderByDescending("createdAt");
                    query.whereContainedIn("organization", myOrgWithoutData);

                    query.findInBackground(new FindCallback<Events>() {
                        @Override
                        public void done(List<Events> eventsList, ParseException e) {
                            if (e==null) {
                                if (eventsList==null) eventsList=new ArrayList<Events>();
                                events.addAll(eventsList);

                                adapter.notifyDataSetChanged();

                                skip+=eventsList.size();
                            } else Log.d("ulifeExcept", e.toString());
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT);
                    Log.d("Radost", e.toString());
                }

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        updateMyEventFeed();
    }

    private void updateMyEventFeed() {
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


        ParseQuery<UserOrgFollow> query=ParseQuery.getQuery(UserOrgFollow.class);
        //query.include("organization");
        query.whereEqualTo("user", curUser);

        query.findInBackground(new FindCallback<UserOrgFollow>() {
            @Override
            public void done(List<UserOrgFollow> userOrgFollows, ParseException e) {
                if (e==null) {
                    List<Organization> myOrgWithoutData=new ArrayList<Organization>();
                    parentActivity.myUOF=userOrgFollows;
                    followedOrgIDs.clear();
                    for (UserOrgFollow u: userOrgFollows){
                        followedOrgIDs.add(u.getOrganization().getObjectId());
                        myOrgWithoutData.add(ParseObject.createWithoutData(Organization.class,u.getOrganization().getObjectId()));

                    }

                    ParseQuery<Events> query=ParseQuery.getQuery(Events.class);
                    query.setLimit(qLimit);
                    query.include("organization");
                    query.whereContainedIn("organization", myOrgWithoutData);

                    query.findInBackground(new FindCallback<Events>() {
                        @Override
                        public void done(List<Events> eventsList, ParseException e) {
                            if (e==null) {
                                events.clear();
                                events.addAll(eventsList);
                                adapter = new EventListAdapter(getView().getContext(), events);
                                listView.setAdapter(adapter);
                                skip+=eventsList.size();
                            } else Log.d("ulifeExcept", e.toString());
                        }
                    });

                } else {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT);
                    Log.d("Radost", e.toString());
                }
                progressDialog.dismiss();
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


}
