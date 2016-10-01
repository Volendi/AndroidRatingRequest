package com.volen.ratingraquest.samples;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.volen.ratingraquest.samples.adapters.RatingRequestAdapter;
import com.volen.ratingraquest.samples.models.CardModel;
import com.volen.ratingraquest.samples.util.RatingRequestActions;
import com.volen.ratingrequest.RatingRequestView;

import java.util.ArrayList;

public class RecyclerViewExampleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_example);

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ArrayList<CardModel> recyclerViewData = new ArrayList<>();
        recyclerViewData.add(new CardModel("Top 10 USA rivers", "Number 10\nLewis River", getResources().getDrawable(R.drawable.card1)));
        recyclerViewData.add(new CardModel("Barbarian Invasions of the Roman Empire", "Barbarian Invasions and the Fall of the Western Roman Empire. Many people ask why the Roman Empire ended, according to the historian, Gibbon, the ...", getResources().getDrawable(R.drawable.card2)));
        recyclerViewData.add(new CardModel("Top 10 places to rest", "Number 10\nLewis River", getResources().getDrawable(R.drawable.card3)));
        recyclerViewData.add(new CardModel("What we do with nature?", "Charles Eisenstein argues that climate change is not just about reducing carbon, it requires a revolution in our consciousness to understand ...", getResources().getDrawable(R.drawable.card4)));
        recyclerViewData.add(new CardModel("How to save Amur Tigers?", "Amur tigers were once found throughout the Russian Far East, northern China and the Korean peninsula. By the 1940s, hunting had driven the Amur tiger...", getResources().getDrawable(R.drawable.card5)));

        final RatingRequestAdapter adapter = new RatingRequestAdapter(this, recyclerViewData);
        recyclerView.setAdapter(adapter);
        adapter.showRatingRequestItem();
    }
}
