package com.volen.ratingrequest;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RatingRequestDialogItem extends LinearLayout {
    private View mainView;
    private TextView text;
    private Button acceptButton;
    private Button declineButton;

    public RatingRequestDialogItem(Context context) {
        this(context, null);
    }

    public RatingRequestDialogItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
    }

    //region Init
    private void init(){
        mainView = inflate(getContext(), R.layout.rating_request_dialog_item, this);

        initUi();
    }

    private void initUi(){
        text = (TextView) findViewById(R.id.text);
        acceptButton = (Button) findViewById(R.id.accept_btn);
        declineButton = (Button) findViewById(R.id.decline_btn);
    }
    //endregion Init
}
