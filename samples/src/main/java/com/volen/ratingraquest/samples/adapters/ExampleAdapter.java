package com.volen.ratingraquest.samples.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.volen.ratingraquest.samples.R;
import com.volen.ratingraquest.samples.adapters.viewholders.ExampleViewHolder;
import com.volen.ratingraquest.samples.models.Example;

import java.util.ArrayList;
import java.util.List;

public class ExampleAdapter extends RecyclerView.Adapter<ExampleViewHolder> {
    private List<Example> examples;

    private Context context;

    public ExampleAdapter(Context context, List<Example> examples) {
        this.context = context;
        setData(examples);
    }

    @Override
    public ExampleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_holder_example, parent, false);
        return new ExampleViewHolder(v, context);
    }

    @Override
    public void onBindViewHolder(ExampleViewHolder holder, int position) {
        holder.bind(examples.get(position));
    }

    @Override
    public int getItemCount() {
        return examples.size();
    }

    public void setData(List<Example> examples){
        this.examples = new ArrayList<>(examples);

        notifyDataSetChanged();
    }
}
