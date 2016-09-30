package com.volen.ratingraquest.samples;

public class AdvancedStylingExampleActivity extends BasicExampleActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_advanced_styling_example;
    }

    @Override
    protected void initRatingRequestView() {
        super.initRatingRequestView();

        //setStyling();
    }

    private void setStyling(){
        ratingRequestView.setBackgroundColor(getResources().getColor(R.color.rating_view_background_dark));
        ratingRequestView.setTextColor(getResources().getColor(R.color.white));
        ratingRequestView.setAcceptButtonTextColor(getResources().getColor(R.color.white));
        ratingRequestView.setDeclineButtonTextColor(getResources().getColor(R.color.white));
        ratingRequestView.setAcceptButtonBackgroundDrawable(getResources().getDrawable(R.drawable.accept_btn_bg));
        ratingRequestView.setDeclineButtonBackgroundDrawable(getResources().getDrawable(R.drawable.decline_btn_bg));
    }
}
