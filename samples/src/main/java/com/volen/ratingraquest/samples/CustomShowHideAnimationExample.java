package com.volen.ratingraquest.samples;

import android.view.animation.AnimationUtils;

public class CustomShowHideAnimationExample extends BasicExampleActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_show_hide_animation_example;
    }

    @Override
    protected void initRatingRequestView() {
        super.initRatingRequestView();

        //You can set show and hide animation from code
        initShowHideAnimation();
    }

    private void initShowHideAnimation(){
        ratingRequestView.setShowAnimation(AnimationUtils.loadAnimation(this, R.anim.show_from_bottom));
        ratingRequestView.setHideAnimation(AnimationUtils.loadAnimation(this, R.anim.hide_to_bottom));
    }
}
