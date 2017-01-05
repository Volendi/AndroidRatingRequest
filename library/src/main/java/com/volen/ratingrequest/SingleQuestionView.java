package com.volen.ratingrequest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SingleQuestionView extends LinearLayout {
    public static final int NO_SUCH_COLOR = -1;

    private View handler;
    private TextView questionTextView;
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
        void onAccept(SingleQuestionView view);
        void onDecline(SingleQuestionView view);
    }

    public SingleQuestionView(Context context) {
        this(context, null);
    }

    public SingleQuestionView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init();
        parseAttrs(attrs);
    }

    //region Init
    private void init(){
        inflate(getContext(), R.layout.single_question_dialog_item, this);

        initViews();
        initActionsForButtons();
        initDefaultAnimations();
    }

    private void initViews(){
        handler = findViewById(R.id.handler);
        questionTextView = (TextView) findViewById(R.id.text);
        acceptButton = (Button) findViewById(R.id.accept_btn);
        declineButton = (Button) findViewById(R.id.decline_btn);
    }

    private void initActionsForButtons(){
        acceptButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDecisionListener != null) {
                    onDecisionListener.onAccept(getThisView());
                }
            }
        });

        declineButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onDecisionListener != null) {
                    onDecisionListener.onDecline(getThisView());
                }
            }
        });
    }

    private void initDefaultAnimations(){
        showAnimation = AnimationUtils.loadAnimation(getContext(), DEFAULT_SHOW_ANIM_RES_ID);
        hideAnimation = AnimationUtils.loadAnimation(getContext(), DEFAULT_HIDE_ANIM_RES_ID);
    }

    //region Attrs
    private void parseAttrs(AttributeSet attrs){
        if (attrs == null) {
            return;
        }

        parseTextAttrs(attrs);
        parseStylingAttrs(attrs);
    }

    private void parseTextAttrs(AttributeSet attrs){
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.RatingRequestDialogItemText);

        if (arr == null)
            return;

        setViewTexts(arr.getString(R.styleable.RatingRequestDialogItemText_rr_mainText),
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

    //region Set views texts
    public void setViewTexts(@Nullable String mainText, @Nullable String acceptButtonText,
                             @Nullable String declineButtonText){
        if (mainText != null) {
            setMainText(mainText);
        }

        if (acceptButtonText != null) {
            setAcceptButtonText(acceptButtonText);
        }

        if (declineButtonText != null) {
            setDeclineButtonText(declineButtonText);
        }
    }

    public void setMainText(CharSequence text){
        this.questionTextView.setText(text);
    }

    public void setAcceptButtonText(CharSequence text){
        acceptButton.setText(text);
    }

    public void setDeclineButtonText(CharSequence text){
        declineButton.setText(text);
    }

    // region Set texts via resource ids.
    public void setViewTexts(int mainTextResId, int acceptButtonTextResId,
                             int declineButtonTextResId){
        setMainText(mainTextResId);
        setAcceptButtonText(acceptButtonTextResId);
        setDeclineButtonText(declineButtonTextResId);
    }

    public void setMainText(int resId){
        this.questionTextView.setText(resId);
    }

    public void setAcceptButtonText(int resId){
        acceptButton.setText(resId);
    }

    public void setDeclineButtonText(int resId){
        declineButton.setText(resId);
    }
    // endregion Set texts via resource ids.
    //endregion Set views texts

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

        questionTextView.setTextColor(color);
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

    private SingleQuestionView getThisView(){
        return this;
    }
}
