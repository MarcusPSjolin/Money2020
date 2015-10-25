package com.marcussjolin.money2020;

import android.media.Image;

public class Campaign {

    private Image mImage;
    private String mTitle;
    private String mDescription;
    private String mTargetAddress;
    private float mDailyBudget;
    private float mRadius;

    public Campaign(Image image, String title, String description, String targetAddress, float dailyBudget, float radius) {
        this.mImage = image;
        this.mTitle = title;
        this.mDescription = description;
        this.mTargetAddress = targetAddress;
        this.mDailyBudget = dailyBudget;
        this.mRadius = radius;
    }

    public Image getImage() {
        return mImage;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public String getTargetAddress() {
        return mTargetAddress;
    }

    public float getDailyBudget() {
        return mDailyBudget;
    }

    public float getRadius() {
        return mRadius;
    }
}
