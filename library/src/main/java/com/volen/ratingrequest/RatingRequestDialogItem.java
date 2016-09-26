package com.volen.ratingrequest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ImageFormat;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RatingRequestDialogItem extends LinearLayout {
    public static final int NO_SUCH_COLOR = -1;

    private View handler;
    private TextView text;
    private Button acceptButton;
    private Button declineButton;

    private Animation showAnimation;
    private Animation hideAnimation;

    //region DefaultResourcesIds
    protected final int DEFAULT_SHOW_ANIM_RES_ID = R.anim.rr_default_show_anim;
    protected final int DEFAULT_HIDE_ANIM_RES_ID = R.anim.rr_default_hide_anim;
    //endregion DefaultResourcesIds

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
        initDefaultAnimations();
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

    private void initDefaultAnimations(){
        showAnimation = AnimationUtils.loadAnimation(getContext(), DEFAULT_SHOW_ANIM_RES_ID);
        hideAnimation = AnimationUtils.loadAnimation(getContext(), DEFAULT_HIDE_ANIM_RES_ID);
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
                NO_SUCH_COLOR));
        setTextColor(arr.getColor(R.styleable.RatingRequestStyling_rr_textColor,
                NO_SUCH_COLOR));

        setAcceptButtonBackgroundDrawable(arr.getDrawable(R.styleable.RatingRequestStyling_rr_acceptButtonBackground));
        setAcceptButtonTextColor(arr.getColor(R.styleable.RatingRequestStyling_rr_acceptButtonTextColor,
                NO_SUCH_COLOR));

        setDeclineButtonBackgroundDrawable(arr.getDrawable(R.styleable.RatingRequestStyling_rr_declineButtonBackground));
        setDeclineButtonTextColor(arr.getColor(R.styleable.RatingRequestStyling_rr_declineButtonTextColor,
                NO_SUCH_COLOR));

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
        if (color == NO_SUCH_COLOR)
            return;

        handler.setBackgroundColor(color);
        acceptButton.setTextColor(color);
    }

    public void setTextColor(int color){
        if (color == NO_SUCH_COLOR)
            return;

        text.setTextColor(color);
    }

    public void setAcceptButtonBackgroundDrawable(Drawable drawable){
        if (drawable != null)
            acceptButton.setBackgroundDrawable(drawable);
    }

    public void setAcceptButtonTextColor(int color){
        if (color == NO_SUCH_COLOR)
            return;

        acceptButton.setTextColor(color);
    }

    public void setDeclineButtonBackgroundDrawable(Drawable drawable){
        if (drawable != null)
            declineButton.setBackgroundDrawable(drawable);
    }

    public void setDeclineButtonTextColor(int color){
        if (color == NO_SUCH_COLOR)
            return;

        declineButton.setTextColor(color);
    }
    //endregion Styling

    //region Animation
    public void setShowAnimation(Animation animation){
        showAnimation = animation;
    }

    public void setHideAnimation(Animation animation){
        hideAnimation = animation;
    }

    private void playShowAnimation() {
        startAnimation(showAnimation);
    }

    private void playHideAnimation() {
        startAnimation(hideAnimation);
    }
    //endregion Animation

    //region Visibility
    public boolean isShown(){
        return getVisibility() == VISIBLE;
    }

    public void show(){
        setVisibility(VISIBLE);
    }

    public void hide(){
        setVisibility(INVISIBLE);
    }

    public void toggle(){
        if (isShown()){
            hide();
        } else {
            show();
        }
    }

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
            hide();
        } else {
            show();
        }
    }
    //endregion Visibility

    public void setOnDecisionListener(OnDecisionListener onDecisionListener){
        this.onDecisionListener = onDecisionListener;
    }

    private RatingRequestDialogItem getView(){
        return this;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }
}
