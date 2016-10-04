package com.volen.ratingraquest.samples.rules;

import android.content.Context;
import android.content.pm.PackageManager;

public class RatingManager {
    public static final int APP_LAUNCH_COUNT_TO_SHOW_RATING_REQUEST = 3;

    private RatingSharedPrefHelper prefsHelper;
    private Context context;

    public RatingManager(Context context){
        this.context = context;
        prefsHelper = RatingSharedPrefHelper.getInstance(getContext());
    }

    public boolean toShowRatingRequest(){
        if (prefsHelper.isUserRatedApp())
            return false;

        return isCurrentVersionGood() && isEnoughAppLaunches();
    }

    private boolean isEnoughAppLaunches(){
        return prefsHelper.getAppLaunchCount() > APP_LAUNCH_COUNT_TO_SHOW_RATING_REQUEST;
    }

    private boolean isCurrentVersionGood(){
        return getCurrentVersionCode() > prefsHelper.getBadVersionCode();
    }

    public void trackAppLaunch(){
        prefsHelper.incrementAppLaunch();
    }

    public void reset(){
        prefsHelper.reset();
    }

    //region TrackRatingResults
    public void trackRate(){
        prefsHelper.setUserRatedApp(true);
    }

    public void trackFeedback(){
        prefsHelper.setBadVersionCode(getCurrentVersionCode());
    }

    public void trackRateDeclined(){
        prefsHelper.setAppLaunchCount(0);
    }

    public void trackFeedbackDeclined(){
        prefsHelper.setBadVersionCode(getCurrentVersionCode());
    }
    //endregion TrackRatingResults

    //region Helpers
    private int getCurrentVersionCode() {
        try {
            return getContext().getPackageManager().getPackageInfo(getContext().getPackageName(), 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            //Never happens
            return 0;
        }
    }

    private Context getContext(){
        return context;
    }
    //endregion Helpers
}

