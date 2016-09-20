package com.volen.ratingrequest;

import android.content.Context;
import android.support.annotation.Nullable;
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

    private OnDecisionListener onDecisionListener;

    public interface OnDecisionListener{
        void onAccept();
        void onDecline();
    }

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
        initActions();
    }

    private void initUi(){
        text = (TextView) findViewById(R.id.text);
        acceptButton = (Button) findViewById(R.id.accept_btn);
        declineButton = (Button) findViewById(R.id.decline_btn);
    }

    private void initActions(){
        acceptButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDecisionListener != null)
                    onDecisionListener.onAccept();
            }
        });

        declineButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDecisionListener != null)
                    onDecisionListener.onDecline();
            }
        });
    }
    //endregion Init

    //region Text
    public void setText(@Nullable String mainText, @Nullable String acceptButtonText,
                        @Nullable String declineButtonText){
        if (mainText != null)
            setMainText(mainText);

        if (acceptButtonText != null)
            setAcceptButtonText(acceptButtonText);

        if (declineButtonText != null)
            setDeclineButtonText(declineButtonText);
    }

    public void setText(int mainTextResId, int acceptButtonTextResId,
                        int declineButtonTextResId){
        setMainText(mainTextResId);
        setAcceptButtonText(acceptButtonTextResId);
        setDeclineButtonText(declineButtonTextResId);
    }

    public void setMainText(CharSequence text){
        this.text.setText(text);
    }

    public void setMainText(int resId){
        this.text.setText(resId);
    }

    public CharSequence getMainText(){
        return text.getText();
    }

    public void setAcceptButtonText(CharSequence text){
        acceptButton.setText(text);
    }

    public void setAcceptButtonText(int resId){
        acceptButton.setText(resId);
    }

    public CharSequence getAcceptButtonText(){
        return acceptButton.getText();
    }

    public void setDeclineButtonText(CharSequence text){
        declineButton.setText(text);
    }

    public void setDeclineButtonText(int resId){
        declineButton.setText(resId);
    }

    public CharSequence getDeclineButtonText(){
        return declineButton.getText();
    }
    //endregion Text

    public boolean isShown(){
        return getVisibility() == VISIBLE;
    }

    public void show(){
        setVisibility(VISIBLE);
    }

    public void hide(){
        setVisibility(GONE);
    }

    public void setOnDecisionListener(OnDecisionListener onDecisionListener){
        this.onDecisionListener = onDecisionListener;
    }
}
