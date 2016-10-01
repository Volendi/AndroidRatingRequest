package com.volen.ratingraquest.samples;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.volen.ratingraquest.samples.util.RatingRequestActions;
import com.volen.ratingrequest.RatingRequestView;

public class BasicExampleActivity extends AppCompatActivity{
    protected RatingRequestView ratingRequestView;

    //Listener for rating requests
    protected RatingRequestView.OnRatingRequestResultListener ratingListener = new RatingRequestView.OnRatingRequestResultListener() {
        @Override
        public void onRating(RatingRequestView view) {
            //Hide rating request with animation or not if it is needed
            view.hideAnimate();

            RatingRequestActions.showMarketActivity(BasicExampleActivity.this);
        }

        @Override
        public void onRatingDeclined(RatingRequestView view) {
            //Hide rating request with animation or not if it is needed
            view.hideAnimate();

            Snackbar.make(findViewById(R.id.content), R.string.decline_rating, Snackbar.LENGTH_LONG).show();
        }

        @Override
        public void onFeedback(RatingRequestView view) {
            //Hide rating request with animation or not if it is needed
            view.hideAnimate();

            RatingRequestActions.showFeedback(BasicExampleActivity.this);
        }

        @Override
        public void onFeedbackDeclined(RatingRequestView view) {
            //Hide rating request with animation or not if it is needed
            view.hideAnimate();

            Snackbar.make(findViewById(R.id.content), R.string.decline_feedback, Snackbar.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        initRatingRequestView();
        initShowHideRatingRequestButton();
    }

    protected void initShowHideRatingRequestButton(){
        findViewById(R.id.content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingRequestView.toggleAnimate();
            }
        });
    }

    protected void initRatingRequestView(){
        ratingRequestView = (RatingRequestView)findViewById(R.id.rating_request);

        ratingRequestView.setOnRatingRequestResult(ratingListener);
    }

    protected int getLayoutId(){
        return R.layout.activity_basic_example;
    }
}
