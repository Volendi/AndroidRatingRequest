package com.volen.ratingraquest.samples.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.volen.ratingraquest.samples.R;
import com.volen.ratingraquest.samples.adapters.viewholders.RatingRequestViewHolder;
import com.volen.ratingraquest.samples.adapters.viewholders.SimpleCardViewHolder;
import com.volen.ratingraquest.samples.models.CardModel;
import com.volen.ratingraquest.samples.util.RatingRequestActions;
import com.volen.ratingrequest.RatingRequestView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class RatingRequestAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int RATING_TYPE = 0;
    private final int SIMPLE_CARD_TYPE = 1;

    private List<CardModel> cards;

    private int ratingRequestPosition;
    private boolean isRatingRequestShown;

    private Context context;

    private RatingRequestView.OnRatingRequestResultListener listener = new RatingRequestView.OnRatingRequestResultListener() {
        @Override
        public void onRating(RatingRequestView view) {
            hideRatingRequestItem();

            RatingRequestActions.showMarketActivity(context);
        }

        @Override
        public void onRatingDeclined(RatingRequestView view) {
            hideRatingRequestItem();
        }

        @Override
        public void onFeedback(RatingRequestView view) {
            hideRatingRequestItem();

            RatingRequestActions.showFeedback(context);
        }

        @Override
        public void onFeedbackDeclined(RatingRequestView view) {
            hideRatingRequestItem();
        }
    };

    public RatingRequestAdapter(Context context, List<CardModel> data){
        setData(data);
        this.context = context;
        calculateRatingRequestItemPosition();
    }

    private void calculateRatingRequestItemPosition(){
        Random random = new Random(new Date().getTime());
        ratingRequestPosition = random.nextInt(cards.size()) + 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        switch (viewType){
            case RATING_TYPE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_rating_request, parent, false);
                return new RatingRequestViewHolder(v, listener);
            case SIMPLE_CARD_TYPE:
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_simple_card, parent, false);
                return new SimpleCardViewHolder(v);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final int itemType = getItemViewType(position);

        if (itemType == SIMPLE_CARD_TYPE)
            ((SimpleCardViewHolder)holder).bind(cards.get(getActualPosition(position)));
    }

    @Override
    public int getItemCount() {
        return cards.size() + getOffset();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == ratingRequestPosition && isRatingRequestShown)
            return RATING_TYPE;
        else
            return SIMPLE_CARD_TYPE;
    }

    public void setData(List<CardModel> data){
        this.cards = new ArrayList<>(data);

        notifyDataSetChanged();
    }

    private int getActualPosition(int position){
        return position > ratingRequestPosition ? position - getOffset() : position;
    }

    private int getOffset(){
        return isRatingRequestShown ? 1 : 0;
    }

    public void hideRatingRequestItem(){
        notifyItemRemoved(ratingRequestPosition);
        isRatingRequestShown = false;
    }

    public void showRatingRequestItem(){
        isRatingRequestShown = true;
        notifyItemInserted(ratingRequestPosition);
    }
}
