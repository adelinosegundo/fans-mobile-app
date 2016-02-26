package co.tootz.fans.domain;

import android.content.Context;
import android.content.SharedPreferences;

import co.tootz.fans.application.FansApplication;

/**
 * Created by second on 2/25/16.
 */
public class User {
    private static final String USERNAME_TAG = "username";
    private static final String USER_TOKEN_TAG = "user_token";


    public static void setUsername(Context context, String username){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FansApplication.SHARED_PREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USERNAME_TAG, username);
        editor.commit();
    }
    public static void setTokeen(Context context, String token){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FansApplication.SHARED_PREFERENCES, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_TOKEN_TAG, token);
        editor.commit();
    }
    public static String getUsername(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FansApplication.SHARED_PREFERENCES, context.MODE_PRIVATE);
        return sharedPreferences.getString(USERNAME_TAG, "");
    }
    public static String getTokeen(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(FansApplication.SHARED_PREFERENCES, context.MODE_PRIVATE);
        return sharedPreferences.getString(USER_TOKEN_TAG, "");
    }

}
