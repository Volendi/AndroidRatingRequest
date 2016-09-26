package com.volen.ratingrequest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RatingRequestView extends FrameLayout {
    private View handler;
    private RatingRequestDialogItem nudgeView;
    private RatingRequestDialogItem ratingView;
    private RatingRequestDialogItem feedbackView;

    private Animation showAnimation;
    private Animation hideAnimation;
    private Animation switchStateInAnimation;
    private Animation switchStateOutAnimation;

    private int switchStateAnimationDelay;

    //region DefaultResourcesIds
    protected final int UNSUPPORTED_RESOURCE = -1;

    protected final int DEFAULT_SHOW_ANIM_RES_ID = R.anim.rr_default_show_anim;
    protected final int DEFAULT_HIDE_ANIM_RES_ID = R.anim.rr_default_hide_anim;
    //endregion DefaultResourcesIds

    @IntDef({ NUDGE, FEEDBACK, RATING })
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {}

    public static final int NUDGE = 1;
    public static final int RATING = 2;
    public static final int FEEDBACK = 3;

    private @RatingRequestView.State int state;

    private OnRatingRequestResultListener onRatingRequestResultListener;

    public interface OnRatingRequestResultListener{
        void onRating(RatingRequestView view);
        void onRatingDeclined(RatingRequestView view);
        void onFeedback(RatingRequestView view);
        void onFeedbackDeclined(RatingRequestView view);
    }

    public RatingRequestView(Context context) {
        super(context);
    }

    public RatingRequestView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
        parseAttrs(attrs);
    }

    //region Init
    private void init(){
        inflate(getContext(), R.layout.layout_rating_request, this);

        state = NUDGE;

        initUi();
        initActions();
        initDefaultAnimations();
    }

    private void initUi(){
        handler = findViewById(R.id.handler);
        nudgeView = (RatingRequestDialogItem)findViewById(R.id.nudge);
        ratingView = (RatingRequestDialogItem)findViewById(R.id.rating);
        feedbackView = (RatingRequestDialogItem)findViewById(R.id.feedback);
    }

    private void initActions(){
        nudgeView.setOnDecisionListener(new RatingRequestDialogItem.OnDecisionListener() {
            @Override
            public void onAccept(RatingRequestDialogItem view) {
                switchStateAnimate(RATING);
            }

            @Override
            public void onDecline(RatingRequestDialogItem view) {
                switchStateAnimate(FEEDBACK);
            }
        });

        ratingView.setOnDecisionListener(new RatingRequestDialogItem.OnDecisionListener() {
            @Override
            public void onAccept(RatingRequestDialogItem view) {
                if (onRatingRequestResultListener != null)
                    onRatingRequestResultListener.onRating(getView());
            }

            @Override
            public void onDecline(RatingRequestDialogItem view) {
                if (onRatingRequestResultListener != null)
                    onRatingRequestResultListener.onRatingDeclined(getView());
            }
        });

        feedbackView.setOnDecisionListener(new RatingRequestDialogItem.OnDecisionListener() {
            @Override
            public void onAccept(RatingRequestDialogItem view) {
                if (onRatingRequestResultListener != null)
                    onRatingRequestResultListener.onFeedback(getView());
            }

            @Override
            public void onDecline(RatingRequestDialogItem view) {
                if (onRatingRequestResultListener != null)
                    onRatingRequestResultListener.onFeedbackDeclined(getView());
            }
        });
    }

    private void initDefaultAnimations(){
        showAnimation = AnimationUtils.loadAnimation(getContext(), DEFAULT_SHOW_ANIM_RES_ID);
        hideAnimation = AnimationUtils.loadAnimation(getContext(), DEFAULT_HIDE_ANIM_RES_ID);

        switchStateAnimationDelay = getContext().getResources().getInteger(R.integer.default_switch_state_anim_delay);
    }

    //region Attrs
    private void parseAttrs(AttributeSet attrs){
        if (attrs == null)
            return;

        parseTextAttrs(attrs);
        parseStylingAttrs(attrs);
        parseAnimationAttrs(attrs);
    }

    private void parseTextAttrs(AttributeSet attrs){
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.RatingRequestViewText);

        if (arr == null)
            return;

        setNudgeViewText(arr.getString(R.styleable.RatingRequestViewText_rr_nudgeMainText),
                arr.getString(R.styleable.RatingRequestViewText_rr_nudgeAcceptBtnText),
                arr.getString(R.styleable.RatingRequestViewText_rr_nudgeDeclineBtnText));

        setRatingViewText(arr.getString(R.styleable.RatingRequestViewText_rr_ratingMainText),
                arr.getString(R.styleable.RatingRequestViewText_rr_ratingAcceptBtnText),
                arr.getString(R.styleable.RatingRequestViewText_rr_ratingDeclineBtnText));

        setFeedbackViewText(arr.getString(R.styleable.RatingRequestViewText_rr_feedbackMainText),
                arr.getString(R.styleable.RatingRequestViewText_rr_feedbackAcceptBtnText),
                arr.getString(R.styleable.RatingRequestViewText_rr_feedbackDeclineBtnText));

        arr.recycle();
    }

    private void parseStylingAttrs(AttributeSet attrs){
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.RatingRequestStyling);

        if (arr == null)
            return;

        setBackgroundColor(arr.getColor(R.styleable.RatingRequestStyling_rr_backgroundColor,
                UNSUPPORTED_RESOURCE));
        setTextColor(arr.getColor(R.styleable.RatingRequestStyling_rr_textColor,
                UNSUPPORTED_RESOURCE));

        setAcceptButtonBackgroundDrawable(arr.getDrawable(R.styleable.RatingRequestStyling_rr_acceptButtonBackground));
        setAcceptButtonTextColor(arr.getColor(R.styleable.RatingRequestStyling_rr_acceptButtonTextColor,
                UNSUPPORTED_RESOURCE));

        setDeclineButtonBackgroundDrawable(arr.getDrawable(R.styleable.RatingRequestStyling_rr_declineButtonBackground));
        setDeclineButtonTextColor(arr.getColor(R.styleable.RatingRequestStyling_rr_declineButtonTextColor,
                UNSUPPORTED_RESOURCE));

        arr.recycle();
    }

    private void parseAnimationAttrs(AttributeSet attrs){
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.RatingRequestAnimation);

        if (arr == null)
            return;

        showAnimation = loadAnimation(arr, R.styleable.RatingRequestAnimation_rr_showAnimation,
                DEFAULT_SHOW_ANIM_RES_ID);
        hideAnimation = loadAnimation(arr, R.styleable.RatingRequestAnimation_rr_hideAnimation,
                DEFAULT_HIDE_ANIM_RES_ID);

        setSwitchStateOutAnim(loadAnimation(arr, R.styleable.RatingRequestAnimation_rr_switchStateOutAnim,
                DEFAULT_HIDE_ANIM_RES_ID));
        setSwitchStateInAnim(loadAnimation(arr, R.styleable.RatingRequestAnimation_rr_switchStateInAnim,
                DEFAULT_SHOW_ANIM_RES_ID));

        switchStateAnimationDelay = arr.getInt(R.styleable.RatingRequestAnimation_rr_switchStateAnimationDelay,
                switchStateAnimationDelay);

        arr.recycle();
    }

    private Animation loadAnimation(TypedArray arr, int styleableId, int defaultAnimResId){
        int resourceId = arr.getResourceId(styleableId, defaultAnimResId);
        return AnimationUtils.loadAnimation(getContext(), resourceId);
    }
    //endregion Attrs
    //endregion Init

    //region Text
    public void setNudgeViewText(@Nullable String mainText, @Nullable String acceptButtonText,
                                 @Nullable String declineButtonText){
        nudgeView.setText(mainText, acceptButtonText, declineButtonText);
    }

    public void setRatingViewText(@Nullable String mainText, @Nullable String acceptButtonText,
                                 @Nullable String declineButtonText){
        ratingView.setText(mainText, acceptButtonText, declineButtonText);
    }

    public void setFeedbackViewText(@Nullable String mainText, @Nullable String acceptButtonText,
                                 @Nullable String declineButtonText){
        feedbackView.setText(mainText, acceptButtonText, declineButtonText);
    }
    //endregion Text

    //region Styling
    public void setBackgroundColor(int color){
        if (color == UNSUPPORTED_RESOURCE)
            return;

        handler.setBackgroundColor(color);

        nudgeView.setBackgroundColor(color);
        feedbackView.setBackgroundColor(color);
        ratingView.setBackgroundColor(color);
    }

    public void setTextColor(int color){
        if (color == UNSUPPORTED_RESOURCE)
            return;

        nudgeView.setTextColor(color);
        ratingView.setTextColor(color);
        feedbackView.setTextColor(color);
    }

    public void setAcceptButtonBackgroundDrawable(Drawable drawable){
        nudgeView.setAcceptButtonBackgroundDrawable(drawable);
        ratingView.setAcceptButtonBackgroundDrawable(drawable);
        feedbackView.setAcceptButtonBackgroundDrawable(drawable);
    }

    public void setAcceptButtonTextColor(int color){
        if (color == UNSUPPORTED_RESOURCE)
            return;

        nudgeView.setAcceptButtonTextColor(color);
        ratingView.setAcceptButtonTextColor(color);
        feedbackView.setAcceptButtonTextColor(color);
    }

    public void setDeclineButtonBackgroundDrawable(Drawable drawable){
        nudgeView.setDeclineButtonBackgroundDrawable(drawable);
        ratingView.setDeclineButtonBackgroundDrawable(drawable);
        feedbackView.setDeclineButtonBackgroundDrawable(drawable);
    }

    public void setDeclineButtonTextColor(int color){
        if (color == UNSUPPORTED_RESOURCE)
            return;

        nudgeView.setDeclineButtonTextColor(color);
        ratingView.setDeclineButtonTextColor(color);
        feedbackView.setDeclineButtonTextColor(color);
    }
    //endregion Styling

    //region State
    public int getState(){
        return state;
    }

    public void switchState(@State int state){
        if (this.state == state)
            return;

        nudgeView.hide();
        feedbackView.hide();
        ratingView.hide();

        switch (state){
            case NUDGE:
                nudgeView.show();
                break;
            case FEEDBACK:
                feedbackView.show();
                break;
            case RATING:
                ratingView.show();
                break;
        }

        this.state = state;
    }

    public void switchStateAnimate(@State int state){
        if (this.state == state)
            return;

        playSwitchStateOutAnimation(state);

        this.state = state;
    }

    public void setOnRatingRequestResult(OnRatingRequestResultListener listener){
        onRatingRequestResultListener = listener;
    }
    //endregion State

    //region Animation
    public void setShowAnimation(Animation animation){
        showAnimation = animation;
    }

    public void setHideAnimation(Animation animation){
        hideAnimation = animation;
    }

    public void setSwitchStateOutAnim(Animation animation){
        switchStateOutAnimation = animation;

        nudgeView.setHideAnimation(animation);
        ratingView.setHideAnimation(animation);
        feedbackView.setHideAnimation(animation);
    }

    public void setSwitchStateInAnim(Animation animation){
        switchStateInAnimation = animation;

        nudgeView.setShowAnimation(animation);
        ratingView.setShowAnimation(animation);
        feedbackView.setShowAnimation(animation);
    }

    private void playShowAnimation() {
        hideAnimation.cancel();
        startAnimation(showAnimation);
    }

    private void playHideAnimation() {
        showAnimation.cancel();
        startAnimation(hideAnimation);
    }

    private void playSwitchStateOutAnimation(@State final int newState){
        nudgeView.hideAnimate();
        feedbackView.hideAnimate();
        ratingView.hideAnimate();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                playSwitchStateInAnimation(newState);
            }
        }, switchStateAnimationDelay);
    }

    private void playSwitchStateInAnimation(@State int newState){
        switch (newState){
            case NUDGE:
                nudgeView.showAnimate();
                break;
            case FEEDBACK:
                feedbackView.showAnimate();
                break;
            case RATING:
                ratingView.showAnimate();
                break;
        }
    }
    //endregion Animation

    //region Visibility
    public void showAnimate(){
        if (!isShown()){
            playShowAnimation();
            show();
        }
    }

    public void hideAnimate(){
        if (isShown()){
            playHideAnimation();
            hide();
        }
    }

    public void toggleAnimate(){
        if (isShown()){
            hideAnimate();
        } else {
            showAnimate();
        }
    }

    public void show(){
        setVisibility(VISIBLE);
    }

    public void hide(){
        setVisibility(GONE);
    }

    public void toggle(){
        if (isShown()){
            hide();
        } else {
            show();
        }
    }

    public boolean isShown(){
        return getVisibility() == VISIBLE;
    }
    //endregion Visibility

    protected RatingRequestView getView(){
        return this;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
