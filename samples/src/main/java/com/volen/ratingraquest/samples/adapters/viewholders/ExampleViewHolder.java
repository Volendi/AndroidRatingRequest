package com.volen.ratingraquest.samples.adapters.viewholders;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.volen.ratingraquest.samples.R;
import com.volen.ratingraquest.samples.models.Example;

public class ExampleViewHolder extends RecyclerView.ViewHolder {
    private Example example;

    private Context context;

    private View mainView;
    private TextView title;
    private TextView subtitle;

    public ExampleViewHolder(View itemView, Context context) {
        super(itemView);

        this.context = context;
        mainView = itemView;

        initUi();
        initOnItemSelected();
    }

    private void initUi(){
        title = (TextView)mainView.findViewById(R.id.title);
        subtitle = (TextView)mainView.findViewById(R.id.subtitle);
    }

    private void initOnItemSelected(){
        mainView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                example.startExampleActivity(context);
            }
        });
    }

    public void bind(Example example){
        this.example = example;

        title.setText(example.getTitle());
        subtitle.setText(example.getSubTitle());
    }
}
