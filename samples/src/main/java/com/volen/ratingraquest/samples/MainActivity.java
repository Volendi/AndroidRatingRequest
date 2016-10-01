package com.volen.ratingraquest.samples;

import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.ActionMenuItemView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.volen.ratingraquest.samples.adapters.ExampleAdapter;
import com.volen.ratingraquest.samples.models.Example;
import com.volen.ratingrequest.RatingRequestView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolbar();
        initExamplesList();
    }

    private void initToolbar(){
        setSupportActionBar((Toolbar)findViewById(R.id.toolbar));
    }

    private void initExamplesList(){
        ArrayList<Example> examples = new ArrayList<>();

        examples.add(new Example(getString(R.string.basic_example_name), getString(R.string.basic_example_description),
                BasicExampleActivity.class));
        examples.add(new Example(getString(R.string.custom_show_hide_animation_example_name),
                getString(R.string.custom_show_hide_animation_example_description),
                CustomShowHideAnimationExample.class));
        examples.add(new Example(getString(R.string.custom_state_switch_animation_example_name),
                getString(R.string.custom_state_switch_animation_example_description),
                CustomStateSwitchAnimationExampleActivity.class));
        examples.add(new Example(getString(R.string.advanced_styling_example_name),
                getString(R.string.advanced_styling_example_description),
                AdvancedStylingExampleActivity.class));
        examples.add(new Example(getString(R.string.recycler_view_example_name),
                getString(R.string.advanced_styling_example_description), RecyclerViewExampleActivity.class));

        initExamplesRecyclerView(examples);
    }

    private void initExamplesRecyclerView(List<Example> examples){
        RecyclerView examplesRecyclerView = (RecyclerView)findViewById(R.id.examples_list);
        ExampleAdapter adapter = new ExampleAdapter(this, examples);
        examplesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        examplesRecyclerView.setAdapter(adapter);
    }
}
