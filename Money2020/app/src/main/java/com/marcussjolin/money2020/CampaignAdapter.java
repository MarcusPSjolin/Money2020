package com.marcussjolin.money2020;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.ViewHolder> {

    private final static String ELLIPSIS = "\u2026";

    List<Campaign> mCampaigns;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public ImageView mImage;
        public TextView mTitle;
        public TextView mFirstActionItem;
        public TextView mSecondActionItem;
        public TextView mDate;

        public ViewHolder(View view) {
            super(view);
            CardView cardView = (CardView) view.findViewById(R.id.card_view);
            mCardView = cardView;
            RelativeLayout cardContainer = (RelativeLayout) cardView.findViewById(R.id.card_container);
            mImage = (ImageView) cardContainer.findViewById(R.id.image);
            mTitle = (TextView) cardContainer.findViewById(R.id.title);
            mFirstActionItem = (TextView) cardContainer.findViewById(R.id.first_action);
            mSecondActionItem = (TextView) cardContainer.findViewById(R.id.second_action);
            mDate = (TextView) cardContainer.findViewById(R.id.date);
        }
    }

    public CampaignAdapter(List<Campaign> campaigns) {
        mCampaigns = campaigns;
    }

    @Override
    public int getItemCount() {
        return mCampaigns.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.campaign_card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Campaign campaign = mCampaigns.get(position);
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Money2020Application.APPLICATION_CONTEXT, CampaignDetailsActivity.class);

                intent.putExtra(CampaignDetailsActivity.CAMPAIGN, campaign);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Money2020Application.APPLICATION_CONTEXT.startActivity(intent);
            }
        });
        holder.mImage.setImageDrawable(getCardBackground(campaign.getTitle()));
        holder.mTitle.setText(campaign.getTitle());
        holder.mFirstActionItem.setText(campaign.getDescription().substring(0, 20) + ELLIPSIS);
        holder.mSecondActionItem.setText(campaign.getTargetAddress().substring(0, 20) + ELLIPSIS);
        holder.mDate.setText("Oct 18 - Oct 24");
    }

    public Drawable getCardBackground(String title) {
        Drawable res;
        switch (title) {
            case Money2020Application.NFL:
                res = ContextCompat.getDrawable(Money2020Application.APPLICATION_CONTEXT, R.drawable.nfl);
                break;
            case Money2020Application.BREAST_CANCER:
                res = ContextCompat.getDrawable(Money2020Application.APPLICATION_CONTEXT, R.drawable.breast_cancer);
                break;
            case Money2020Application.HALLOWEEN:
                res = ContextCompat.getDrawable(Money2020Application.APPLICATION_CONTEXT, R.drawable.halloween_background);
                break;
            case Money2020Application.MONEY_2020:
                res = ContextCompat.getDrawable(Money2020Application.APPLICATION_CONTEXT, R.drawable.raining_cash);
                break;
            default:
                res = ContextCompat.getDrawable(Money2020Application.APPLICATION_CONTEXT, R.drawable.brick_and_mortar);
        }
        return res;
    }

}
