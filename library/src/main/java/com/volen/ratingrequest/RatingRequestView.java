package com.volen.ratingrequest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

public class RatingRequestView extends FrameLayout {
    private View mainView;
    private RatingRequestDialogItem nudgeView;
    private RatingRequestDialogItem ratingView;
    private RatingRequestDialogItem feedbackView;

    public RatingRequestView(Context context) {
        super(context);
    }

    public RatingRequestView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    //region Init
    private void init(){
        mainView = inflate(getContext(), R.layout.rating_request_dialog_item, this);

        initUi();
    }

    private void initUi(){
        nudgeView = (RatingRequestDialogItem)findViewById(R.id.nudge);
        ratingView = (RatingRequestDialogItem)findViewById(R.id.rating);
        feedbackView = (RatingRequestDialogItem)findViewById(R.id.feedback);
    }
    //endregion Init
}
