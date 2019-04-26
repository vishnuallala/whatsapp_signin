package com.vishnu.whatsapp_signin;
import com.parse.Parse;
import android.app.Application;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("ULxLIopoW7JMSuAjwdhBidBkUvfjqRfXDU5ZPZcx")
                // if defined
                .clientKey("CGov8BDgvQ0ZQghKyViaKWSudzHMFFGYaB3Bq3zi")
                .server("https://parseapi.back4app.com/")
                .build()
        );
    }
}
