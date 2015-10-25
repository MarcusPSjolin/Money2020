package com.marcussjolin.money2020;

import android.app.Application;
import android.content.Context;

import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import io.fabric.sdk.android.Fabric;

public class Money2020Application extends Application {

    public static final String BASE_URL = "http://money2020.paulashbourne.com/";
    public static final String SOCIAL_URL = BASE_URL + "api/social";
    public static final String CAMPAIGNS_URL = BASE_URL + "api/campaigns";
    public static final String MONEY_2020 = "Money 20/20";
    public static final String NFL = "NFL";
    public static final String BREAST_CANCER = "Breast Cancer Awareness";
    public static final String HALLOWEEN = "Halloween";

    public static Context APPLICATION_CONTEXT;

    @Override
    public void onCreate() {
        super.onCreate();

        APPLICATION_CONTEXT = this;

        TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret));
        Fabric.with(this, new Twitter(authConfig), new TweetComposer());
    }
}
