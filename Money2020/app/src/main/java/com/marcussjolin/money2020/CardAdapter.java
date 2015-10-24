package com.marcussjolin.money2020;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    List<Card> mCards;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mCaption;
        public TextView mFirstActionItem;
        public TextView mSecondActionItem;
        public Button mActionButton;

        public ViewHolder(View view) {
            super(view);
            CardView cardView = (CardView) view.findViewById(R.id.card_view);
            LinearLayout cardContainer = (LinearLayout) cardView.findViewById(R.id.card_container);
            mImageView = (ImageView) cardContainer.findViewById(R.id.image);
            mCaption = (TextView) cardContainer.findViewById(R.id.caption);
            mFirstActionItem = (TextView) cardContainer.findViewById(R.id.first_action);
            mSecondActionItem = (TextView) cardContainer.findViewById(R.id.second_action);
            mActionButton = (Button) view.findViewById(R.id.action_button);
        }

    }

    public CardAdapter(List<Card> cards) {
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
        Card card = mCards.get(position);
        holder.mImageView.setImageResource(R.mipmap.toronto);
        holder.mCaption.setText(card.getCaption());
        holder.mFirstActionItem.setText(card.getFirstActionItem());
        holder.mSecondActionItem.setText(card.getSecondActionItem());
        holder.mActionButton.setTag(card.getAction());
    }
}
