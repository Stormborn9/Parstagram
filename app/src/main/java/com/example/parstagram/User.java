package com.example.parstagram;

import com.parse.Parse;
import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;

@ParseClassName("_User")
public class User extends ParseUser {
    public static final String KEY_FULLNAME = "FullName";
    public static final String KEY_BIO = "bio";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_PROFILE_IMAGE = "profileImage";



    public String getFullName(){
        return getString(KEY_FULLNAME);
    }

    public void setFullName(String FullName){
        put(KEY_FULLNAME, FullName);
    }

    public String getBio(){
        return getString(KEY_BIO);
    }

    public void setBio(String bio){put(KEY_BIO, bio);}

    public String getEmail(){return getString(KEY_EMAIL);}

    public void setEmail(String email){put(KEY_EMAIL, email);}

    public ParseFile getProfileImage(){
        return getParseFile(KEY_PROFILE_IMAGE);
    }

    public void setProfileImage(ParseFile image){
        put(KEY_PROFILE_IMAGE, image);
    }

}