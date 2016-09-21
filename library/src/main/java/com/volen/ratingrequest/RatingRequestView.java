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
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RatingRequestView extends FrameLayout {
    private RatingRequestDialogItem nudgeView;
    private RatingRequestDialogItem ratingView;
    private RatingRequestDialogItem feedbackView;

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
                    onRatingRequestResultListener.onFeedbackDeclined(getView());
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

        parseStylingAttrs(attrs);
    }

    private void parseStylingAttrs(AttributeSet attrs){
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.RatingRequestStyling);

        if (arr == null)
            return;

        setBackgroundColor(arr.getColor(R.styleable.RatingRequestStyling_rr_backgroundColor,
                getContext().getResources().getColor(R.color.default_background)));
        setTextColor(arr.getColor(R.styleable.RatingRequestStyling_rr_textColor,
                getContext().getResources().getColor(R.color.default_text)));

        setAcceptButtonBackgroundDrawable(arr.getDrawable(R.styleable.RatingRequestStyling_rr_acceptButtonBackground));
        setAcceptButtonTextColor(arr.getColor(R.styleable.RatingRequestStyling_rr_acceptButtonTextColor,
                getContext().getResources().getColor(R.color.default_background)));

        setDeclineButtonBackgroundDrawable(arr.getDrawable(R.styleable.RatingRequestStyling_rr_declineButtonBackground));
        setDeclineButtonTextColor(arr.getColor(R.styleable.RatingRequestStyling_rr_declineButtonTextColor,
                getContext().getResources().getColor(R.color.default_buttons)));

        arr.recycle();
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
        nudgeView.setBackgroundColor(color);
        ratingView.setBackgroundColor(color);
        feedbackView.setBackgroundColor(color);
    }

    public void setTextColor(int color){
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
        nudgeView.setDeclineButtonTextColor(color);
        ratingView.setDeclineButtonTextColor(color);
        feedbackView.setDeclineButtonTextColor(color);
    }
    //endregion Styling

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

    public void show(){
        setVisibility(VISIBLE);
    }

    public void hide(){
        setVisibility(GONE);
    }

    public boolean isShown(){
        return getVisibility() == VISIBLE;
    }

    protected RatingRequestView getView(){
        return this;
    }
}
