package com.example.user.ulife;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.squareup.picasso.Picasso;


public class DisplayEvent extends ActionBarActivity {

    TextView dateView;
    TextView nameView;
    TextView priceView;
    TextView organizationView;
    ImageView imageView;
    TextView addressView;

    ParseFile image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_event);
        Bundle extras=getIntent().getExtras();



        dateView=(TextView)findViewById(R.id.dateView);
        nameView=(TextView)findViewById(R.id.addressView);
        priceView=(TextView)findViewById(R.id.priceView);
        organizationView=(TextView)findViewById(R.id.organizationView);
        imageView=(ImageView)findViewById(R.id.imageView);
        addressView=(TextView)findViewById(R.id.addressView);
        //priceView.setText(extras.getString("event"));


        ParseQuery<Events> query = ParseQuery.getQuery("Events");
        query.fromLocalDatastore();
        query.getInBackground(extras.getString("event"), new GetCallback<Events>() {
            public void done(Events object, ParseException e) {
                if (e == null) {
                    image=object.getImage();

                    Picasso.with(getApplicationContext()).load(image.getUrl()).into(imageView);

                    //new DownloadImageTask((ImageView) imageView).execute(image.getUrl());

                    priceView.setText(""+object.getPrice()+" tenge");
                    nameView.setText(object.getName());
                    dateView.setText(object.getDate().toLocaleString());
                    addressView.setText(object.getAddress());

                    object.getParseObject("organization").fetchIfNeededInBackground(new GetCallback<Organization>() {

                        @Override
                        public void done(Organization parseObject, ParseException e) {
                            organizationView.setText(parseObject.getName()+"bu");

                        }

                    });


                } else {
                    // something went wrong
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_display_event, menu);
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
        }

        return super.onOptionsItemSelected(item);
    }
}
