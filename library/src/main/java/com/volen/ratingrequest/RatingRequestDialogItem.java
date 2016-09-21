package com.volen.ratingrequest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RatingRequestDialogItem extends LinearLayout {;
    private View handler;
    private TextView text;
    private Button acceptButton;
    private Button declineButton;

    private OnDecisionListener onDecisionListener;

    public interface OnDecisionListener{
        void onAccept(RatingRequestDialogItem view);
        void onDecline(RatingRequestDialogItem view);
    }

    public RatingRequestDialogItem(Context context) {
        this(context, null);
    }

    public RatingRequestDialogItem(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
        parseAttrs(attrs);
    }

    //region Init
    private void init(){
        inflate(getContext(), R.layout.rating_request_dialog_item, this);

        initUi();
        initActions();
    }

    private void initUi(){
        handler = findViewById(R.id.handler);
        text = (TextView) findViewById(R.id.text);
        acceptButton = (Button) findViewById(R.id.accept_btn);
        declineButton = (Button) findViewById(R.id.decline_btn);
    }

    private void initActions(){
        acceptButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDecisionListener != null)
                    onDecisionListener.onAccept(getView());
            }
        });

        declineButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDecisionListener != null)
                    onDecisionListener.onDecline(getView());
            }
        });
    }

    //region Attrs
    private void parseAttrs(AttributeSet attrs){
        if (attrs == null)
            return;

        parseTextAttrs(attrs);
        parseStylingAttrs(attrs);
    }

    private void parseTextAttrs(AttributeSet attrs){
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.RatingRequestDialogItemText);

        if (arr == null)
            return;

        setText(arr.getString(R.styleable.RatingRequestDialogItemText_rr_mainText),
                arr.getString(R.styleable.RatingRequestDialogItemText_rr_acceptBtnText),
                arr.getString(R.styleable.RatingRequestDialogItemText_rr_declineBtnText));

        arr.recycle();
    }

    private void parseStylingAttrs(AttributeSet attrs){
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.RatingRequestStyling);

        if (arr == null)
            return;

        setBackgroundColor(arr.getColor(R.styleable.RatingRequestStyling_rr_backgroundColor,
                getContext().getResources().getColor(R.color.default_background)));
        setTextColor(arr.getColor(R.styleable.RatingRequestStyling_rr_textColor,
                getContext().getResources().getColor(R.color.default_text)));

        arr.recycle();
    }
    //endregion Attrs
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

    //region Styling
    public void setBackgroundColor(int color){
        handler.setBackgroundColor(color);
        acceptButton.setTextColor(color);
    }

    public void setTextColor(int color){
        text.setTextColor(color);
    }
    //endregion Styling

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

    private RatingRequestDialogItem getView(){
        return this;
    }
}
