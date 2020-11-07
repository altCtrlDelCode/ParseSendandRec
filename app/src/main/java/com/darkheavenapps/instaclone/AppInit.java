package com.darkheavenapps.instaclone;

import android.app.Application;

import com.parse.Parse;

public class AppInit extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("XjlLAq27GNfbQxlaEiejSqXNkfOGB6J9hg0mNwmM")
                // if defined
                .clientKey("I2Ou4e71RXhlLg9j7PKrsWQeL5FD12PsIBf18P2H")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}
