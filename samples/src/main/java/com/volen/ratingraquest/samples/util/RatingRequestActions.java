package com.volen.ratingraquest.samples.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class RatingRequestActions {
    public static void showFeedback(Context context){
        context.startActivity(new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://github.com/Volendi/AndroidRatingRequest/issues")));
    }

    public static void showMarketActivity(Context context){
        Uri uri = Uri.parse("market://details?id=" + context.getPackageName());
        Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);

        try {
            context.startActivity(goToMarket);
        } catch (ActivityNotFoundException e) {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://play.google.com/store/apps/details?id=" + context.getPackageName())));
        }
    }
}
