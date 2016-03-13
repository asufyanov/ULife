package com.example.user.ulife;

import android.app.Application;
import android.util.Log;

import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParsePush;
import com.parse.SaveCallback;

/**
 * Created by user on 31.03.2015.
 */
public class main extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Events.class);
        ParseObject.registerSubclass(Organization.class);
        ParseObject.registerSubclass(UserOrgFollow.class);
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "ZjrB9Xc3J1qhuFrB38VW95KU2O2rWhZrQwM6NpA5", "UxsN0ZCrSx2sIXIGdHJ4S6z5UaJLnir2k9fSbGeY");



        ParsePush.subscribeInBackground("", new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null) {
                    Log.d("com.parse.push", "successfully subscribed to the broadcast channel.");
                } else {
                    Log.e("com.parse.push", "failed to subscribe for push", e);
                }
            }
        });
    }
}
