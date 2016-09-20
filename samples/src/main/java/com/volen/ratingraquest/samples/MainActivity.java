package com.volen.ratingraquest.samples;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.volen.ratingrequest.RatingRequestView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RatingRequestView ratingRequest = (RatingRequestView)findViewById(R.id.rating_request);

        ratingRequest.setNudgeViewText("Enjoying App?", "Yes", "Not really");
        ratingRequest.setRatingViewText("How about a rating in the Google Play then?", "Yes, sure", "No, thanks");
        ratingRequest.setFeedbackViewText("Would you mind giving us some feedback?", "Yes, sure", "No, thanks");
    }
}
