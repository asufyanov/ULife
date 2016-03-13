package com.example.user.ulife;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;

/**
 * Created by user on 20.03.2015.
 */
@ParseClassName("Organization")
public class Organization extends ParseObject  {
    public String getName(){
        return  getString("name");
    }
    public void setName(String value){
        put("name",value);
    }
    public String getShortDescription() {return getString("shortDescription");}
    public String getDescription(){return getString("description");}
    public ParseFile getImage(){
        return getParseFile("image");
    }


}

