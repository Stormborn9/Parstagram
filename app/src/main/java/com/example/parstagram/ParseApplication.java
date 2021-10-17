package com.example.parstagram;

import android.app.Application;
import android.content.res.Configuration;

import androidx.annotation.NonNull;

import com.parse.Parse;

public class ParseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("uz5S1Szeos2ZwSDot3sgWGlmHEul6yYkL69iTUzR")
                .clientKey("CVZHR4wnxaQRBZpm2jKhmdCdB8Do8h67uarpypen")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
