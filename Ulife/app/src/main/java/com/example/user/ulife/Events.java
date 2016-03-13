package com.example.user.ulife;

import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseRelation;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 19.03.2015.
 */



/*

    organizations

    image

    geoPoint

*/

@ParseClassName("Events")
public class Events extends ParseObject {

    List <Organization> organizations=new ArrayList<>();

    public String getName() {
        return getString("name");
    }
    public Date getDate() {
        return getDate("date");
    }
    public String getAddress(){
        return getString("address");
    }
    public int getPrice(){
        return getInt("price");
    }
    public String getDescription(){
        return getString("description");
    }
    public ParseGeoPoint getGeoPoint(){
        return getParseGeoPoint("geoPoint");
    }
    public ParseObject getOrganization(){
        return getParseObject("organization");
    }
    public ParseFile getImage(){
        return getParseFile("image");
    }



    public void setName(String value) {
        put("name", value);
    }
    public void setDate(Date date){
        put("date", date);
    }
    public void setAddress(String address){
        put("address",address);
    }
    public void setPrice(int price){
        put("price", price);
    }
    public void setDescription(String description){
        put("description", description);
    }



}

