package com.example.user.ulife;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {







    ParseUser curUser=ParseUser.getCurrentUser();
    ArrayList<Organization> myFollowedOrganizations=new ArrayList<>();
    ArrayList<Organization> allOrganizations=new ArrayList<>();
    ArrayList<UserOrgFollow> uOf=new ArrayList<>();
    ArrayList<String> followedOrgIDs=new ArrayList<>();
    List<UserOrgFollow> myUOF=new ArrayList<>();
    List<Events> myEvents=new ArrayList<>();

    Drawer result;
    Toolbar toolbar;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        result = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName("Все события").withIcon(R.drawable.events),
                        new PrimaryDrawerItem().withName("Все организации").withIcon(R.drawable.orgicon).setEnabled(true),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Мои События").withIcon(R.drawable.events),
                        new PrimaryDrawerItem().withName("Мои Организации").withIcon(R.drawable.orgicon),
                        new DividerDrawerItem(),
                        new PrimaryDrawerItem().withName("Выйти")

                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(AdapterView<?> adapterView, View view, int i, long l, IDrawerItem iDrawerItem) {
                        Fragment newFragment = null;
                        if (i == 0) newFragment = new EventListFragment();
                        else if (i == 1) newFragment = new OrganizationListFragment();
                        else if (i == 3) newFragment = new MyEventListFragment();
                        else if (i == 4) newFragment = new MyOrganizationListFragment();
                        else if (i==6) logOut();

                        if (newFragment != null) {
                            PrimaryDrawerItem pdi=(PrimaryDrawerItem)iDrawerItem;
                            openFragment(newFragment, pdi.getName());

                            result.closeDrawer();
                        }


                        return true;
                    }
                })
                .withOnDrawerListener(new Drawer.OnDrawerListener() {
                    @Override
                    public void onDrawerOpened(View drawerView) {
                        InputMethodManager inputMethodManager = (InputMethodManager) MainActivity.this.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(MainActivity.this.getCurrentFocus().getWindowToken(), 0);
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                    }

                    @Override
                    public void onDrawerSlide(View view, float v) {

                    }
                })
                .build();




    }

    void logOut() {
        ParseUser.logOut();
        Intent intent;
        intent=new Intent (this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void openFragment(Fragment fragment, String s) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLay, fragment);

        fragmentTransaction.addToBackStack("name");

        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out);

        fragmentTransaction.commit();
        toolbar.setTitle(s);

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
        if(result.isDrawerOpen()){
            result.closeDrawer();
        } else{
            int count = getFragmentManager().getBackStackEntryCount();

            if (count == 0) {
                super.onBackPressed();
                //additional code
            } else {

                getFragmentManager().popBackStack();

            }

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
