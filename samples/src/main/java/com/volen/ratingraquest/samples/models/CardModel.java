package com.volen.ratingraquest.samples.models;

import android.graphics.drawable.Drawable;

public class CardModel {
    private String title;
    private String description;
    private Drawable image;

    public CardModel() {
    }

    public CardModel(String title, String description, Drawable image) {
        this.title = title;
        this.description = description;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
        this.image = image;
    }
}
