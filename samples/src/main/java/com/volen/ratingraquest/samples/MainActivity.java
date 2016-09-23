package com.volen.ratingraquest.samples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.volen.ratingrequest.RatingRequestView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RatingRequestView ratingRequest = (RatingRequestView)findViewById(R.id.rating_request);

        Animation anim = new AlphaAnimation(0.0f, 1.0f);
        anim.setDuration(500);
        ratingRequest.setSwitchStateInAnim(anim);

        anim = new AlphaAnimation(1.0f, 0.0f);
        anim.setDuration(500);
        ratingRequest.setSwitchStateOutAnim(anim);

        ratingRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingRequest.hideAnimate();
            }
        });

        findViewById(R.id.root).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingRequest.toggleAnimate();
            }
        });

        /*ratingRequest.setNudgeViewText("Enjoying App?", "Yes", "Not really");
        ratingRequest.setRatingViewText("How about a rating in the Google Play then?", "Yes, sure", "No, thanks");
        ratingRequest.setFeedbackViewText("Would you mind giving us some feedback?", "Yes, sure", "No, thanks");*/
    }
}
