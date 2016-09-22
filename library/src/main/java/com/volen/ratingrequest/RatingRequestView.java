package com.volen.ratingrequest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.support.annotation.IntDef;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RatingRequestView extends FrameLayout {
    private RatingRequestDialogItem nudgeView;
    private RatingRequestDialogItem ratingView;
    private RatingRequestDialogItem feedbackView;

    private Animation showAnimation;
    private Animation hideAnimation;

    private final int UNSUPPORTED_RESOURCE = -1;

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
    }

    private void initUi(){
        nudgeView = (RatingRequestDialogItem)findViewById(R.id.nudge);
        ratingView = (RatingRequestDialogItem)findViewById(R.id.rating);
        feedbackView = (RatingRequestDialogItem)findViewById(R.id.feedback);
    }

    private void initActions(){
        nudgeView.setOnDecisionListener(new RatingRequestDialogItem.OnDecisionListener() {
            @Override
            public void onAccept(RatingRequestDialogItem view) {
                setState(RATING);
            }

            @Override
            public void onDecline(RatingRequestDialogItem view) {
                setState(FEEDBACK);
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
        nudgeView.parseStylingAttrs(attrs);
        ratingView.parseStylingAttrs(attrs);
        feedbackView.parseStylingAttrs(attrs);
    }

    private void parseAnimationAttrs(AttributeSet attrs){
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.RatingRequestAnimation);

        if (arr == null)
            return;

        showAnimation = loadAnimation(arr, R.styleable.RatingRequestAnimation_rr_showAnimation);
        hideAnimation = loadAnimation(arr, R.styleable.RatingRequestAnimation_rr_hideAnimation);

        arr.recycle();
    }

    private Animation loadAnimation(TypedArray arr, int styleableId){
        int resourceId = arr.getResourceId(styleableId, UNSUPPORTED_RESOURCE);
        return resourceId != UNSUPPORTED_RESOURCE ?
                AnimationUtils.loadAnimation(getContext(), resourceId) :
                null;
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

    public int getState(){
        return state;
    }

    public void setState(@State int state){
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

    public void setOnRatingRequestResult(OnRatingRequestResultListener listener){
        onRatingRequestResultListener = listener;
    }

    //region Animation
    public void setShowAnimation(Animation animation){
        showAnimation = animation;
    }

    public void setHideAnimation(Animation animation){
        hideAnimation = animation;
    }

    private void playShowAnimation() {
        hideAnimation.cancel();
        startAnimation(showAnimation);
    }

    private void playHideAnimation() {
        showAnimation.cancel();
        startAnimation(hideAnimation);
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
}
