package com.marcussjolin.money2020;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

public class Campaign implements Parcelable {

    private String mId;
    private Image mImage;
    private String mTitle;
    private String mDescription;
    private String mTargetAddress;
    private float mDailyBudget;
    private float mRadius;
    private String mStartDate;
    private String mEndDate;

    public Campaign(String id, Image image, String title, String description, String targetAddress,
                    float dailyBudget, float radius) {
        this.mId = id;
        this.mImage = image;
        this.mTitle = title;
        this.mDescription = description;
        this.mTargetAddress = targetAddress;
        this.mDailyBudget = dailyBudget;
        this.mRadius = radius;
    }

    public Campaign(Image image, String title, String description, String targetAddress,
                    float dailyBudget, float radius) {
        this.mImage = image;
        this.mTitle = title;
        this.mDescription = description;
        this.mTargetAddress = targetAddress;
        this.mDailyBudget = dailyBudget;
        this.mRadius = radius;
    }

    public String getId() {
        return mId;
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

    public void setImage(Image image) {
        this.mImage = image;
    }

    public String getStartDate() {
        return mStartDate;
    }

    public String getEndDate() {
        return mEndDate;
    }

    public void setStartDate(String startDate) {
        this.mStartDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.mEndDate = endDate;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flag) {
        out.writeString(mId);
        out.writeString(mTitle);
        out.writeString(mDescription);
        out.writeString(mTargetAddress);
        out.writeFloat(mDailyBudget);
        out.writeFloat(mRadius);
        out.writeString(mStartDate);
        out.writeString(mEndDate);
    }

    public static final Parcelable.Creator<Campaign> CREATOR
            = new Parcelable.Creator<Campaign>() {
        public Campaign createFromParcel(Parcel in) {
            return new Campaign(in);
        }

        public Campaign[] newArray(int size) {
            return new Campaign[size];
        }
    };

    private Campaign(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
        mTargetAddress = in.readString();
        mDailyBudget = in.readFloat();
        mRadius = in.readFloat();
        mStartDate = in.readString();
        mEndDate = in.readString();
    }
}
