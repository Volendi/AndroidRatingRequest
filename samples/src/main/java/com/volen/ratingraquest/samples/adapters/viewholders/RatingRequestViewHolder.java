package com.volen.ratingraquest.samples.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.volen.ratingraquest.samples.R;
import com.volen.ratingrequest.RatingRequestView;

public class RatingRequestViewHolder extends RecyclerView.ViewHolder {
    private RatingRequestView ratingRequestView;

    public RatingRequestViewHolder(View itemView, RatingRequestView.OnRatingRequestResultListener listener) {
        super(itemView);

        ratingRequestView = (RatingRequestView)itemView.findViewById(R.id.rating_request);
        ratingRequestView.setOnRatingRequestResult(listener);
    }
}
