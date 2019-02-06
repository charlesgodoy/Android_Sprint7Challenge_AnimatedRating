package com.caz.ratingbarchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.caz.ratingbarchallenge.customviews.CustomStarsViewHolder;

public class MainActivity extends AppCompatActivity {

    // Sprint 7
    // Charles Godoy

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // create custom view holder object for the stars
        CustomStarsViewHolder viewHolder = findViewById(R.id.star_custom_view);
        // initialize the stars with 10 total stars and initial stars set to 5
        viewHolder.starsSetting(10, 5);
    }
}