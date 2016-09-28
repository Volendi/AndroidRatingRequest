package com.volen.ratingraquest.samples;

import android.view.animation.AnimationUtils;

public class CustomStateSwitchAnimationExampleActivity extends BasicExampleActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_custom_switch_state_animation_example;
    }

    @Override
    protected void initRatingRequestView() {
        super.initRatingRequestView();

        //You can set switch state animation in code
        initSwitchStateAnimation();
    }

    private void initSwitchStateAnimation(){
        ratingRequestView.setSwitchStateInAnim(AnimationUtils.loadAnimation(this, R.anim.show_from_left));
        ratingRequestView.setSwitchStateOutAnim(AnimationUtils.loadAnimation(this, R.anim.hide_to_right));
    }
}
