package com.volen.ratingraquest.samples.adapters.viewholders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.volen.ratingraquest.samples.R;
import com.volen.ratingraquest.samples.models.CardModel;

public class SimpleCardViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;
    private ImageView image;

    public SimpleCardViewHolder(View itemView) {
        super(itemView);

        title = (TextView) itemView.findViewById(R.id.title);
        description = (TextView) itemView.findViewById(R.id.description);
        image = (ImageView)itemView.findViewById(R.id.image);
    }

    public void bind(CardModel cardModel){
        title.setText(cardModel.getTitle());
        description.setText(cardModel.getDescription());
        image.setImageDrawable(cardModel.getImage());
    }
}
