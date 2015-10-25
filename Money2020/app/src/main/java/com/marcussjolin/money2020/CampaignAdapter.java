package com.marcussjolin.money2020;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.ViewHolder> {

    List<Campaign> mCampaigns;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImage;
        public TextView mTitle;
        public TextView mFirstActionItem;
        public TextView mSecondActionItem;
        public TextView mDate;

        public ViewHolder(View view) {
            super(view);
            CardView cardView = (CardView) view.findViewById(R.id.card_view);
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
    public void onBindViewHolder(ViewHolder holder, int position) {
        Campaign campaign = mCampaigns.get(position);
        holder.mImage.setImageResource(R.mipmap.money_bag);
        holder.mTitle.setText(campaign.getTitle());
        holder.mFirstActionItem.setText("Click through rate");
        holder.mSecondActionItem.setText("Some other metric");
        holder.mDate.setText("Oct 18 - Present");
    }

}
