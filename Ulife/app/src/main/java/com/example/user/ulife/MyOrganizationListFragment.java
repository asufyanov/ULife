package com.example.user.ulife;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
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

import java.util.List;


public class MyOrganizationListFragment extends Fragment {
    List<Organization> myOrganizations;

    OrganizationListAdapter adapter;
    ListView listView;
    ParseUser curUser;

    MainActivity parentActivity;


    public MyOrganizationListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parentActivity=(MainActivity)getActivity();
        myOrganizations=parentActivity.myFollowedOrganizations;
        curUser=parentActivity.curUser;



    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_organization_list, container, false);
        listView=(ListView)v.findViewById(R.id.orgtListView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openOrganization(myOrganizations.get(position).getObjectId(), myOrganizations.get(position).getName());
                Toast.makeText(getActivity(), "FCUK THIS", Toast.LENGTH_LONG).show();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openOrganization(myOrganizations.get(position).getObjectId(), myOrganizations.get(position).getName());
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
        query.include("organization");
        query.whereEqualTo("user", curUser);
        query.findInBackground(new FindCallback<UserOrgFollow>() {
            @Override
            public void done(List<UserOrgFollow> userOrgFollows, ParseException e) {
                if (e == null) {
                    parentActivity.myUOF = userOrgFollows;


                    Log.d("Radost", userOrgFollows.toString());
                    Toast.makeText(getActivity(), userOrgFollows.toString(), Toast.LENGTH_SHORT);
                    myOrganizations.clear();
                    myOrganizations.addAll(parentActivity.fillMyOrganizationsINCLUDE(userOrgFollows));
                    adapter = new OrganizationListAdapter(getView().getContext(), myOrganizations);
                    listView.setAdapter(adapter);
                } else {
                    Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_SHORT);
                    Log.d("Radost", e.toString());
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


}
