package com.marcussjolin.money2020;

import android.media.Image;

public class Card {

    public enum Action {
        TWEET
    }

    private Image mImage;
    private String mCaption;
    private String mFirstActionItem;
    private String mSecondActionItem;
    private Action mAction;

    public Card(Image image, String caption, String firstAction, String secondAction, Action action) {
        this.mImage = image;
        this.mCaption = caption;
        this.mFirstActionItem = firstAction;
        this.mSecondActionItem = secondAction;
        this.mAction = action;
    }

    public Image getImage() {
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

}
