package com.volen.ratingrequest;

import android.content.Context;
import android.media.Rating;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class RatingRequestView extends FrameLayout {
    private View mainView;
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
        void onRating();
        void onRatingDeclined();
        void onFeedback();
        void onFeedbackDeclined();
    }

    public RatingRequestView(Context context) {
        super(context);
    }

    public RatingRequestView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    //region Init
    private void init(){
        mainView = inflate(getContext(), R.layout.layout_rating_request, this);

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
            public void onAccept() {
                setState(RATING);
            }

            @Override
            public void onDecline() {
                setState(FEEDBACK);
            }
        });

        ratingView.setOnDecisionListener(new RatingRequestDialogItem.OnDecisionListener() {
            @Override
            public void onAccept() {
                if (onRatingRequestResultListener != null)
                    onRatingRequestResultListener.onRating();
            }

            @Override
            public void onDecline() {
                if (onRatingRequestResultListener != null)
                    onRatingRequestResultListener.onFeedbackDeclined();
            }
        });

        feedbackView.setOnDecisionListener(new RatingRequestDialogItem.OnDecisionListener() {
            @Override
            public void onAccept() {
                if (onRatingRequestResultListener != null)
                    onRatingRequestResultListener.onFeedback();
            }

            @Override
            public void onDecline() {
                if (onRatingRequestResultListener != null)
                    onRatingRequestResultListener.onFeedbackDeclined();
            }
        });
    }
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
}
