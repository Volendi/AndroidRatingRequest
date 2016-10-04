package com.volen.ratingraquest.samples.rules;

import android.content.Context;
import android.content.SharedPreferences;

public class RatingSharedPrefHelper {
    private SharedPreferences pref;

    private final String RATING_PREF_NAME = "rating_pref";
    private static final String IS_USER_RATED_APP = "is_user_rate_app";
    private static final String APP_LAUNCH_COUNT = "app_launch_count";
    private static final String BAD_VERSION_CODE = "bad_version_code";

    private static RatingSharedPrefHelper instance;

    public static RatingSharedPrefHelper getInstance(Context context){
        return instance != null ? instance : (instance = new RatingSharedPrefHelper(context));
    }

    public RatingSharedPrefHelper(Context context){
        initPref(context);
    }

    private void initPref(Context context){
        pref = context.getSharedPreferences(RATING_PREF_NAME, Context.MODE_PRIVATE);
    }

    public boolean isUserRatedApp(){
        return pref.getBoolean(IS_USER_RATED_APP, false);
    }

    public void setUserRatedApp(boolean userRatedApp){
        SharedPreferences.Editor editor = pref.edit();
        editor.putBoolean(IS_USER_RATED_APP, userRatedApp);
        editor.apply();
    }

    public int getAppLaunchCount(){
        return pref.getInt(APP_LAUNCH_COUNT, 0);
    }

    public void incrementAppLaunch(){
        setAppLaunchCount(getAppLaunchCount() + 1);
    }

    public void setAppLaunchCount(int count){
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(APP_LAUNCH_COUNT, count);
        editor.apply();
    }

    public int getBadVersionCode(){
        return pref.getInt(BAD_VERSION_CODE, 0);
    }

    public void setBadVersionCode(int badVersionCode){
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt(BAD_VERSION_CODE, badVersionCode);
        editor.apply();
    }

    public void reset(){
        SharedPreferences.Editor editor = pref.edit();

        editor.putBoolean(IS_USER_RATED_APP, false);
        editor.putInt(APP_LAUNCH_COUNT, 0);
        editor.putInt(BAD_VERSION_CODE, 0);

        editor.apply();
    }
}
