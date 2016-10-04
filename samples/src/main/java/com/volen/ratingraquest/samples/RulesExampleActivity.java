package com.volen.ratingraquest.samples;

import android.view.View;

import com.volen.ratingraquest.samples.rules.RatingManager;
import com.volen.ratingraquest.samples.rules.RatingSharedPrefHelper;
import com.volen.ratingraquest.samples.util.RatingRequestActions;
import com.volen.ratingrequest.RatingRequestView;

public class RulesExampleActivity extends BasicExampleActivity {
    private RatingManager ratingManager;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rules_example;
    }

    //region Init
    @Override
    protected void initRatingRequestView() {
        ratingRequestView = (RatingRequestView)findViewById(R.id.rating_request);

        initRatingManager();
        initRatingResultsListener();

        findViewById(R.id.increment_app_launches).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                incrementAppLaunches();
            }
        });

        findViewById(R.id.clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ratingManager.reset();
            }
        });

        tryShowRatingRequest();
    }

    private void initRatingManager(){
        ratingManager = new RatingManager(this);
    }

    private void initRatingResultsListener(){
        ratingListener = new RatingRequestView.OnRatingRequestResultListener() {
            @Override
            public void onRating(RatingRequestView view) {
                view.hideAnimate();

                ratingManager.trackRate();

                RatingRequestActions.showMarketActivity(RulesExampleActivity.this);
            }

            @Override
            public void onRatingDeclined(RatingRequestView view) {
                view.hideAnimate();

                ratingManager.trackRateDeclined();
            }

            @Override
            public void onFeedback(RatingRequestView view) {
                view.hideAnimate();

                ratingManager.trackFeedback();

                RatingRequestActions.showFeedback(RulesExampleActivity.this);
            }

            @Override
            public void onFeedbackDeclined(RatingRequestView view) {
                view.hideAnimate();

                ratingManager.trackFeedbackDeclined();
            }
        };

        ratingRequestView.setOnRatingRequestResult(ratingListener);
    }
    //endregion Init

    private void tryShowRatingRequest(){
        if (ratingManager.toShowRatingRequest())
            ratingRequestView.showAnimate();
    }

    private void incrementAppLaunches(){
        ratingManager.trackAppLaunch();
        tryShowRatingRequest();
    }
}
