package com.example.user.ulife;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

public class OrganizationListFragment extends Fragment {

    List<Organization> organizations;


    OrganizationListAdapter adapter;
    ListView listView;
    ParseUser curUser;
    MainActivity parentActivity;
    ArrayList<String> followedOrgIDs;


    public OrganizationListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity=(MainActivity)getActivity();

        organizations=parentActivity.allOrganizations;

        curUser=parentActivity.curUser;
        followedOrgIDs=parentActivity.followedOrgIDs;


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_organization_list, container, false);
        listView=(ListView)v.findViewById(R.id.orgtListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openOrganization(organizations.get(position).getObjectId(), organizations.get(position).getName());
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateOrganizationFeed();

    }

    private void updateOrganizationFeed() {
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


                if (e == null) {
                    parentActivity.myUOF = userOrgFollows;
                    followedOrgIDs.clear();
                    for (UserOrgFollow u : userOrgFollows) {
                        followedOrgIDs.add(u.getOrganization().getObjectId());

                    }


                    ParseQuery<Organization> query2 = ParseQuery.getQuery(Organization.class);
                    query2.findInBackground(new FindCallback<Organization>() {
                        @Override
                        public void done(List<Organization> newOrgs, ParseException e) {
                            organizations.clear();
                            organizations.addAll(newOrgs);
                            adapter = new OrganizationListAdapter(getView().getContext(), organizations);
                            listView.setAdapter(adapter);
                        }
                    });


                } else {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT);
                }
                progressDialog.dismiss();
            }
        });

    }
    private void openOrganization(String event, String tName) {
        Fragment eventFragment=new DisplayOrganizationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("organization", event);

        eventFragment.setArguments(bundle);

        parentActivity.openFragment(eventFragment, tName);
    }


    // TODO: Rename method, update argument and hook method into UI event

}
