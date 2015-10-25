package com.marcussjolin.money2020;

import android.graphics.drawable.Drawable;

public class Card {

    public enum Action {
        TWEET
    }

    private Drawable mImage;
    private Drawable mBackground;
    private String mType;
    private String mCaption;
    private String mFirstActionItem;
    private String mSecondActionItem;
    private Action mAction;

    public Card(Drawable image, String caption, String firstAction, String secondAction, Action action, String type) {
        this.mImage = image;
        this.mCaption = caption;
        this.mFirstActionItem = firstAction;
        this.mSecondActionItem = secondAction;
        this.mAction = action;
        this.mType = type;
    }

    public Drawable getImage() {
        return mImage;
    }

    public String getCaption() {
        return mCaption;
    }

    public String getFirstActionItem() {
        return mFirstActionItem;
    }

    public String getSecondActionItem() {
        return mSecondActionItem;
    }

    public Action getAction() {
        return mAction;
    }

    public String getType() {
        return mType;
    }

    public Drawable getBackground() {
        return mBackground;
    }

    public void setBackground(Drawable background) {
        this.mBackground = background;
    }

}
