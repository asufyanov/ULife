package com.example.user.ulife;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.List;


public class MainActivityOld extends ActionBarActivity {



    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;

    ArrayList<ObjectDrawerItem> drawerItem;
    DrawerItemCustomAdapter nav_adapter;
    private ActionBarDrawerToggle mDrawerToggle;


    ParseUser curUser=ParseUser.getCurrentUser();
    ArrayList<Organization> myFollowedOrganizations;
    ArrayList<Organization> allOrganizations;
    ArrayList<UserOrgFollow> uOf;
    ArrayList<String> followedOrgIDs;
    List<UserOrgFollow> myUOF;
    Fragment lastFragment;







   Button btn;
   Button btn2;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);


        myFollowedOrganizations=new ArrayList<>();
        allOrganizations=new ArrayList<>();
        uOf=new ArrayList<>();
        followedOrgIDs=new ArrayList<>();
        myUOF=new ArrayList<>();






        setContentView(R.layout.activity_main_old);
        createDrawer();



    }
    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ParseUser user=ParseUser.getCurrentUser();

        if (user==null){
            Intent intent=new Intent(this, LoginActivity.class);
            startActivity(intent);

        }



        // updateUsingLocalDataStore();
    }

    private void createDrawer() {

        drawerItem=new ArrayList<ObjectDrawerItem>();

        ObjectDrawerItem drawerItem1 = new ObjectDrawerItem(R.drawable.events, "All Events");
        ObjectDrawerItem drawerItem2 = new ObjectDrawerItem(R.drawable.my_events, "My events");
        ObjectDrawerItem drawerItem3 = new ObjectDrawerItem(R.drawable.push_icon, "All Organizations");
        ObjectDrawerItem drawerItem4 = new ObjectDrawerItem(R.drawable.push_icon, "My Organizations");
        ObjectDrawerItem drawerItem5 = new ObjectDrawerItem(R.drawable.push_icon, "Log Out");




        //mNavigationDrawerItemTitles=getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        mDrawerList=(ListView)findViewById(R.id.left_drawer);

        drawerItem.add(drawerItem1);
        drawerItem.add(drawerItem2);
        drawerItem.add(drawerItem3);
        drawerItem.add(drawerItem4);
        drawerItem.add(drawerItem5);



        nav_adapter=new DrawerItemCustomAdapter(this, drawerItem);
        mDrawerList.setAdapter(nav_adapter);

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Fragment fragment=null;
                if (position==0)fragment=new EventListFragment();
                else if (position==1) fragment=new MyEventListFragment();
                else if (position==2 )fragment=new OrganizationListFragment();
                else if (position==3) fragment=new MyOrganizationListFragment();
                else if (position==4) logOut();


                if (position<=3)openFragment(fragment);
                //mDrawerList.setItemChecked(position, true);
                Log.d("clicked", drawerItem.get(position).name);

                getSupportActionBar().setTitle(drawerItem.get(position).name);
                mDrawerLayout.closeDrawer(mDrawerList);

            }
        });

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.drawable.email, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getSupportActionBar().setTitle("aza");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getSupportActionBar().setTitle("lolo");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);



        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);

    }

     void logOut() {
        ParseUser.logOut();
        Intent intent;
        intent=new Intent (this, LoginActivity.class);
        startActivity(intent);
    }

    public void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLay, fragment);
        lastFragment=fragment;
        fragmentTransaction.addToBackStack(null);



        fragmentTransaction.commit();

    }





    private void testQuery() {

        ParseUser testCurUser=ParseUser.getCurrentUser();
        ArrayList<Organization> putOrg=new ArrayList<>();
        putOrg.add((Organization)ParseObject.createWithoutData("Organization", "4b0MauqSWu"));
        putOrg.add((Organization)ParseObject.createWithoutData("Organization", "OQuAsMZFnD"));


        testCurUser.put("followedOrg", putOrg);
        testCurUser.put("email", "new@mail.ru");
        testCurUser.put("phone", "sdsdds");
        testCurUser.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null) Log.d("testLog", "OKAY");
                else Log.d("testLog", e.toString());
            }
        });




       /*ParseUser curUser=ParseUser.getCurrentUser();
        ParseRelation <Organization> followed=curUser.getRelation("followedOrganizations");

        followed.add((Organization) ParseObject.createWithoutData("Organization","OQuAsMZFnD"));
        followed.add((Organization) ParseObject.createWithoutData("Organization","4b0MauqSWu"));

        curUser.saveInBackground();*/



      /*  Events nnn=new Events();
        nnn.put("name","newname");
        nnn.put("organization",ParseObject.createWithoutData("Organization", "OQuAsMZFnD"));
        ParseRelation<ParseObject> relation=nnn.getRelation("secOrgs");
        relation.add(ParseObject.createWithoutData("Organization","OQuAsMZFnD"));
        relation.add(ParseObject.createWithoutData("Organization","4b0MauqSWu"));
        nnn.saveInBackground();


        ParseQuery<Events> query = ParseQuery.getQuery(Events.class);
        query.include("organization");
        query.include("secOrgs");

        query.findInBackground(new FindCallback<Events>() {
            public void done(List<Events> events, ParseException e) {
                if (e == null) {
                    Log.d("testq", "Retrieved " + events.size() + " eventss");

                    for (int i=0; i<events.size(); i++){
                        Log.d("testq","ololo Orgname is "+events.get(i).getParseObject("organization").getString("name"));
                        Log.d("testq","orgname is "+events.get(i).getRelation("secOrgs").toString());
                    }

                } else {
                    Log.d("score", "Error: " + e.getMessage());
                }
            }
        });*/

    }

    public void unfollow(Organization o){
        final String s=o.getName();
        for (UserOrgFollow u: myUOF){
            if (u.getUser()==curUser && u.getOrganization()==o){
                u.deleteInBackground(new DeleteCallback() {
                    @Override
                    public void done(ParseException e) {
                        Toast.makeText(getApplication(), s+" Unfollowed", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        }

    }
    public void follow(Organization o){
        final String s=o.getName();
        UserOrgFollow userOrgFollow=new UserOrgFollow();
        userOrgFollow.put("user", curUser);
        userOrgFollow.put("organization", o);
        userOrgFollow.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e==null) Toast.makeText(getApplication(), s+" followed", Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if (id==R.id.home){
            //NavUtils.navigateUpFromSameTask(this);
            //getFragmentManager().popBackStack();
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.popBackStack();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            Log.d("OMG", "OMGOMG");



            fragmentTransaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
            //additional code
        } else {
            getFragmentManager().popBackStack();
        }
    }

    public ArrayList<Organization> fillMyOrganizationsINCLUDE(List<UserOrgFollow> userOrgFollows) {
        ArrayList<Organization> o=new ArrayList<>();
        followedOrgIDs.clear();
        for (UserOrgFollow u : userOrgFollows){

           o.add(u.getOrganization());
           followedOrgIDs.add(u.getOrganization().getObjectId());
        }
        return o;
    }
}
