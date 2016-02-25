package co.tootz.fans.application;

import android.app.Application;
import android.content.SharedPreferences;

public class FansApplication extends Application {
    public static String SHARED_PREFERENCES = "Fans";
    public static String SHARED_PREFERENCES_USERNAME = "username";

    @Override
    public void onCreate(){
        super.onCreate();

        startServices();
    }

    private void startServices(){

    }
}
