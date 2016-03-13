package com.example.user.ulife;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseObject;
import com.parse.ParseUser;

/**
 * Created by user on 29.07.15.
 */
@ParseClassName("UserOrgFollow")
public class UserOrgFollow extends ParseObject {
    public ParseUser getUser() {
        return (ParseUser)getParseObject("user");
    }
    public Organization getOrganization(){
        return (Organization)getParseObject("organization");
    }
}
