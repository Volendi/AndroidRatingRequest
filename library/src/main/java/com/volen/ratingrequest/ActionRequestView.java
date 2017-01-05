package com.volen.ratingrequest;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class ActionRequestView extends FrameLayout {

    private View handler;
    private SingleQuestionView nudgeQuestion;
    private SingleQuestionView questionAfterAccepting;
    private SingleQuestionView questionAfterDeclining;

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

    @IntDef({NUDGE_STATE, AFTER_DECLINING_STATE, AFTER_ACCEPTING_STATE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface State {}

    public static final int NUDGE_STATE = 1;
    public static final int AFTER_ACCEPTING_STATE = 2;
    public static final int AFTER_DECLINING_STATE = 3;

    private @ActionRequestView.State int state;

    private OnRequestResultListener onRequestResultListener;

    public interface OnRequestResultListener {
        void onPositiveAccepted(ActionRequestView view);
        void onPositiveDeclined(ActionRequestView view);
        void onNegativeAccepted(ActionRequestView view);
        void onNegativeDeclined(ActionRequestView view);
    }

    public ActionRequestView(Context context) {
        super(context);
    }

    public ActionRequestView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
        parseAttrs(attrs);
    }

    //region Init
    private void init(){
        inflate(getContext(), R.layout.layout_action_request, this);

        state = NUDGE_STATE;

        handler = findViewById(R.id.handler);
        initNudgeQuestion();
        initQuestionAfterAccepting();
        initQuestionAfterDeclining();

        initDefaultAnimations();
    }

    // region Init question views
    private void initNudgeQuestion() {
        nudgeQuestion = (SingleQuestionView)findViewById(R.id.nudge);
        nudgeQuestion.setOnDecisionListener(new SingleQuestionView.OnDecisionListener() {
            @Override
            public void onAccept(SingleQuestionView view) {
                switchStateAnimate(AFTER_ACCEPTING_STATE);
            }

            @Override
            public void onDecline(SingleQuestionView view) {
                switchStateAnimate(AFTER_DECLINING_STATE);
            }
        });
    }

    private void initQuestionAfterAccepting() {
        questionAfterAccepting = (SingleQuestionView)findViewById(R.id.single_question_view);
        questionAfterAccepting.setOnDecisionListener(new SingleQuestionView.OnDecisionListener() {
            @Override
            public void onAccept(SingleQuestionView view) {
                if (onRequestResultListener != null) {
                    onRequestResultListener.onPositiveAccepted(getThisView());
                }
            }

            @Override
            public void onDecline(SingleQuestionView view) {
                if (onRequestResultListener != null) {
                    onRequestResultListener.onPositiveDeclined(getThisView());
                }
            }
        });
    }

    private void initQuestionAfterDeclining() {
        questionAfterDeclining = (SingleQuestionView)findViewById(R.id.feedback);
        questionAfterDeclining.setOnDecisionListener(new SingleQuestionView.OnDecisionListener() {
            @Override
            public void onAccept(SingleQuestionView view) {
                if (onRequestResultListener != null) {
                    onRequestResultListener.onNegativeAccepted(getThisView());
                }
            }

            @Override
            public void onDecline(SingleQuestionView view) {
                if (onRequestResultListener != null) {
                    onRequestResultListener.onNegativeDeclined(getThisView());
                }
            }
        });
    }

    private void initDefaultAnimations(){
        showAnimation = AnimationUtils.loadAnimation(getContext(), DEFAULT_SHOW_ANIM_RES_ID);
        hideAnimation = AnimationUtils.loadAnimation(getContext(), DEFAULT_HIDE_ANIM_RES_ID);

        switchStateAnimationDelay = getContext().getResources().getInteger(R.integer.default_switch_state_anim_delay);
    }
    // endregion Init question views

    //region Attrs
    private void parseAttrs(AttributeSet attrs){
        if (attrs == null) {
            return;
        }

        parseTextAttrs(attrs);
        parseStylingAttrs(attrs);
        parseAnimationAttrs(attrs);
    }

    private void parseTextAttrs(AttributeSet attrs){
        TypedArray arr = getContext().obtainStyledAttributes(attrs, R.styleable.RatingRequestViewText);

        if (arr == null) {
            return;
        }

        setNudgeQuestionTexts(arr.getString(R.styleable.RatingRequestViewText_rr_nudgeMainText),
                arr.getString(R.styleable.RatingRequestViewText_rr_nudgeAcceptBtnText),
                arr.getString(R.styleable.RatingRequestViewText_rr_nudgeDeclineBtnText));

        setAfterAcceptingQuestionTexts(arr.getString(R.styleable.RatingRequestViewText_rr_ratingMainText),
                arr.getString(R.styleable.RatingRequestViewText_rr_ratingAcceptBtnText),
                arr.getString(R.styleable.RatingRequestViewText_rr_ratingDeclineBtnText));

        setAfterDecliningQuestionTexts(arr.getString(R.styleable.RatingRequestViewText_rr_feedbackMainText),
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
        setTextsColor(arr.getColor(R.styleable.RatingRequestStyling_rr_textColor,
                UNSUPPORTED_RESOURCE));

        setAcceptButtonsBackground(arr.getDrawable(R.styleable.RatingRequestStyling_rr_acceptButtonBackground));
        setAcceptButtonsTextColor(arr.getColor(R.styleable.RatingRequestStyling_rr_acceptButtonTextColor,
                UNSUPPORTED_RESOURCE));

        setDeclineButtonsBackground(arr.getDrawable(R.styleable.RatingRequestStyling_rr_declineButtonBackground));
        setDeclineButtonsTextColor(arr.getColor(R.styleable.RatingRequestStyling_rr_declineButtonTextColor,
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

    public void setOnRequestResultListener(OnRequestResultListener listener){
        onRequestResultListener = listener;
    }

    //region Text
    public void setAllQuestionsTexts(QuestionsTextsSet questionsTexts) {
        setNudgeQuestionTexts(questionsTexts.nudgeMainText, questionsTexts.nudgeAcceptButtonText,
                questionsTexts.nudgeDeclineButtonText);
        setAfterAcceptingQuestionTexts(questionsTexts.afterAcceptQuestionMainText,
                questionsTexts.positiveAcceptButtonText, questionsTexts.positiveDeclineButtonText);
        setAfterDecliningQuestionTexts(questionsTexts.afterDeclineQuestionMainText,
                questionsTexts.negativeAcceptButtonText, questionsTexts.negativeDeclineButtonText);
    }

    public void setNudgeQuestionTexts(@Nullable String mainText, @Nullable String acceptButtonText,
                                      @Nullable String declineButtonText){
        nudgeQuestion.setViewTexts(mainText, acceptButtonText, declineButtonText);
    }

    public void setAfterAcceptingQuestionTexts(@Nullable String mainText, @Nullable String acceptButtonText,
                                               @Nullable String declineButtonText){
        questionAfterAccepting.setViewTexts(mainText, acceptButtonText, declineButtonText);
    }

    public void setAfterDecliningQuestionTexts(@Nullable String mainText, @Nullable String acceptButtonText,
                                               @Nullable String declineButtonText){
        questionAfterDeclining.setViewTexts(mainText, acceptButtonText, declineButtonText);
    }
    //endregion Text

    //region Styling
    public void setBackgroundColor(int color){
        if (color == UNSUPPORTED_RESOURCE)
            return;

        handler.setBackgroundColor(color);

        nudgeQuestion.setBackgroundColor(color);
        questionAfterDeclining.setBackgroundColor(color);
        questionAfterAccepting.setBackgroundColor(color);
    }

    public void setTextsColor(int color){
        if (color == UNSUPPORTED_RESOURCE) {
            return;
        }

        nudgeQuestion.setTextColor(color);
        questionAfterAccepting.setTextColor(color);
        questionAfterDeclining.setTextColor(color);
    }

    public void setAcceptButtonsBackground(Drawable drawable){
        nudgeQuestion.setAcceptButtonBackgroundDrawable(drawable);
        questionAfterAccepting.setAcceptButtonBackgroundDrawable(drawable);
        questionAfterDeclining.setAcceptButtonBackgroundDrawable(drawable);
    }

    public void setAcceptButtonsTextColor(int color){
        if (color == UNSUPPORTED_RESOURCE) {
            return;
        }

        nudgeQuestion.setAcceptButtonTextColor(color);
        questionAfterAccepting.setAcceptButtonTextColor(color);
        questionAfterDeclining.setAcceptButtonTextColor(color);
    }

    public void setDeclineButtonsBackground(Drawable drawable){
        nudgeQuestion.setDeclineButtonBackgroundDrawable(drawable);
        questionAfterAccepting.setDeclineButtonBackgroundDrawable(drawable);
        questionAfterDeclining.setDeclineButtonBackgroundDrawable(drawable);
    }

    public void setDeclineButtonsTextColor(int color){
        if (color == UNSUPPORTED_RESOURCE) {
            return;
        }

        nudgeQuestion.setDeclineButtonTextColor(color);
        questionAfterAccepting.setDeclineButtonTextColor(color);
        questionAfterDeclining.setDeclineButtonTextColor(color);
    }
    //endregion Styling

    //region State
    public int getState(){
        return state;
    }

    public void switchState(@State int state){
        if (this.state == state) {
            return;
        }

        nudgeQuestion.hide();
        questionAfterDeclining.hide();
        questionAfterAccepting.hide();

        switch (state){
            case NUDGE_STATE:
                nudgeQuestion.show();
                break;
            case AFTER_DECLINING_STATE:
                questionAfterDeclining.show();
                break;
            case AFTER_ACCEPTING_STATE:
                questionAfterAccepting.show();
                break;
        }

        this.state = state;
    }

    public void switchStateAnimate(@State int state){
        if (this.state == state) {
            return;
        }

        playSwitchStateOutAnimation(state);

        this.state = state;
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

        nudgeQuestion.setHideAnimation(animation);
        questionAfterAccepting.setHideAnimation(animation);
        questionAfterDeclining.setHideAnimation(animation);
    }

    public void setSwitchStateInAnim(Animation animation){
        switchStateInAnimation = animation;

        nudgeQuestion.setShowAnimation(animation);
        questionAfterAccepting.setShowAnimation(animation);
        questionAfterDeclining.setShowAnimation(animation);
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
        nudgeQuestion.hideAnimate();
        questionAfterDeclining.hideAnimate();
        questionAfterAccepting.hideAnimate();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                playSwitchStateInAnimation(newState);
            }
        }, switchStateAnimationDelay);
    }

    private void playSwitchStateInAnimation(@State int newState){
        switch (newState){
            case NUDGE_STATE:
                nudgeQuestion.showAnimate();
                break;
            case AFTER_DECLINING_STATE:
                questionAfterDeclining.showAnimate();
                break;
            case AFTER_ACCEPTING_STATE:
                questionAfterAccepting.showAnimate();
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

    private ActionRequestView getThisView(){
        return this;
    }


    public static class QuestionsTextsSet {

        public String nudgeMainText;
        public String nudgeAcceptButtonText;
        public String nudgeDeclineButtonText;
        public String afterAcceptQuestionMainText;
        public String positiveAcceptButtonText;
        public String positiveDeclineButtonText;
        public String afterDeclineQuestionMainText;
        public String negativeAcceptButtonText;
        public String negativeDeclineButtonText;

        public QuestionsTextsSet(@Nullable String nudgeMainText,
                                 @Nullable String nudgeAcceptButtonText,
                                 @Nullable String nudgeDeclineButtonText,
                                 @Nullable String afterAcceptQuestionMainText,
                                 @Nullable String positiveAcceptButtonText,
                                 @Nullable String positiveDeclineButtonText,
                                 @Nullable String afterDeclineQuestionMainText,
                                 @Nullable String negativeAcceptButtonText,
                                 @Nullable String negativeDeclineButtonText) {
            this.nudgeMainText = nudgeMainText;
            this.nudgeAcceptButtonText = nudgeAcceptButtonText;
            this.nudgeDeclineButtonText = nudgeDeclineButtonText;
            this.afterAcceptQuestionMainText = afterAcceptQuestionMainText;
            this.positiveAcceptButtonText = positiveAcceptButtonText;
            this.positiveDeclineButtonText = positiveDeclineButtonText;
            this.afterDeclineQuestionMainText = afterDeclineQuestionMainText;
            this.negativeAcceptButtonText = negativeAcceptButtonText;
            this.negativeDeclineButtonText = negativeDeclineButtonText;
        }
    }
}
