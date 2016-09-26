package com.volen.ratingraquest.samples.models;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class Example {
    private String title;
    private String subTitle;
    private Class exampleActivity;

    //region Getters/Setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Class getExampleActivity() {
        return exampleActivity;
    }

    public void setExampleActivity(Class exampleActivity) {
        this.exampleActivity = exampleActivity;
    }
    //endregion Getters/Setters

    public Example(String title, String subTitle, Class exampleActivity) {
        this.title = title;
        this.exampleActivity = exampleActivity;
        this.subTitle = subTitle;
    }

    public void startExampleActivity(Context context){
        context.startActivity(new Intent(context, exampleActivity));
    }
}
