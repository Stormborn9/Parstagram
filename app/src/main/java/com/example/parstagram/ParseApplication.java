package com.example.parstagram;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ParseObject.registerSubclass(Post.class);
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("uz5S1Szeos2ZwSDot3sgWGlmHEul6yYkL69iTUzR")
                .clientKey("CVZHR4wnxaQRBZpm2jKhmdCdB8Do8h67uarpypen")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
