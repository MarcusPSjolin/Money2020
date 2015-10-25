package com.marcussjolin.money2020;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    private Context mContext;

    List<Card> mCards;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public CardView mCardView;
        public ImageView mImage;
        public TextView mCaption;
        public TextView mFirstActionItem;
        public TextView mSecondActionItem;
        public Button mTweetButton;
        public Button mCampaignButton;

        public ViewHolder(View view) {
            super(view);
            CardView cardView = (CardView) view.findViewById(R.id.card_view);
            mCardView = cardView;
            LinearLayout cardContainer = (LinearLayout) cardView.findViewById(R.id.card_container);
            mImage = (ImageView) cardContainer.findViewById(R.id.image);
            mCaption = (TextView) cardContainer.findViewById(R.id.caption);
            mFirstActionItem = (TextView) cardContainer.findViewById(R.id.first_action);
            mSecondActionItem = (TextView) cardContainer.findViewById(R.id.second_action);
            mTweetButton = (Button) view.findViewById(R.id.tweet_button);
            mCampaignButton = (Button) view.findViewById(R.id.campaign_button);
        }

    }

    public CardAdapter(List<Card> cards, Context context) {
        mContext = context;
        mCards = cards;
    }

    @Override
    public int getItemCount() {
        return mCards.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Card card = mCards.get(position);

        Drawable background = card.getBackground();
        if (background != null) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) background;
            Bitmap bitmap = bitmapDrawable.getBitmap();
            holder.mImage.setImageBitmap(bitmap);
        }

        holder.mCaption.setText(card.getCaption());

        String firstAction = "Price: $" + card.getFirstActionItem();
        String secondAction = "";
        if (!card.getType().equals("new_items")) {
            firstAction = "Popular Mention: " + card.getFirstActionItem().replace(":", "");
            secondAction = "Popular Hashtag: " + card.getSecondActionItem();
        }

        holder.mFirstActionItem.setText(firstAction);
        holder.mSecondActionItem.setText(secondAction);
        holder.mTweetButton.setTag(card.getAction());

        holder.mTweetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TweetComposer.Builder builder = new TweetComposer.Builder(mContext);
                builder.show();
            }
        });

        holder.mCampaignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CreateCampaignActivity.class);
                intent.putExtra(MainActivity.TYPE, card.getType());
                if (card.getType().equals(MainActivity.NEW_ITEMS)) {
                    intent.putExtra(MainActivity.NAME, card.getCaption());
                } else {
                    intent.putExtra(MainActivity.TITLE, card.getCaption());
                }
                mContext.startActivity(intent);
            }
        });
    }
}