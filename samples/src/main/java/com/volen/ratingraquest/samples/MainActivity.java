package com.volen.ratingraquest.samples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.volen.ratingrequest.RatingRequestView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final RatingRequestView ratingRequest = (RatingRequestView)findViewById(R.id.rating_request);

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

        //ratingRequest.setSwitchStateOutAnim(AnimationUtils.makeOutAnimation(this, true));
        //ratingRequest.setSwitchStateInAnim(AnimationUtils.makeInAnimation(this, true));

        ratingRequest.setOnRatingRequestResult(new RatingRequestView.OnRatingRequestResultListener() {
            @Override
            public void onRating(RatingRequestView view) {
                ratingRequest.switchStateAnimate(RatingRequestView.NUDGE);
            }

            @Override
            public void onRatingDeclined(RatingRequestView view) {
                ratingRequest.switchStateAnimate(RatingRequestView.NUDGE);
            }

            @Override
            public void onFeedback(RatingRequestView view) {
                ratingRequest.switchStateAnimate(RatingRequestView.NUDGE);
            }

            @Override
            public void onFeedbackDeclined(RatingRequestView view) {
                ratingRequest.switchStateAnimate(RatingRequestView.NUDGE);
            }
        });

        /*ratingRequest.setNudgeViewText("Enjoying App?", "Yes", "Not really");
        ratingRequest.setRatingViewText("How about a rating in the Google Play then?", "Yes, sure", "No, thanks");
        ratingRequest.setFeedbackViewText("Would you mind giving us some feedback?", "Yes, sure", "No, thanks");*/
    }
}
