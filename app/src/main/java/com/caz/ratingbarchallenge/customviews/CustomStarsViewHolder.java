package com.caz.ratingbarchallenge.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.widget.LinearLayout;


import com.caz.ratingbarchallenge.R;

import java.util.ArrayList;

public class CustomStarsViewHolder extends LinearLayout {

    private ArrayList<StarsView> starViews = new ArrayList<>();     // holds N number of stars
    private int decreaseAni;
    private int increaseAni;
    private int emptyStar;
    private int onStartStar;
    private int maxStar;
    private int currentNumStar;
    int halfSide;
    private Context context;


    public CustomStarsViewHolder(@NonNull Context context) {
        super(context);
    }

    public CustomStarsViewHolder(@NonNull Context context, @Nullable AttributeSet attributes) {
        super(context, attributes);
        init(attributes);
    }

    public CustomStarsViewHolder(@NonNull Context context, @Nullable AttributeSet attributes, int defStyleAttr) {
        super(context, attributes, defStyleAttr);
        init(attributes);
    }

    public CustomStarsViewHolder(@NonNull Context context, @Nullable AttributeSet attributes, int defStyleAttr, int defStyleRes) {
        super(context, attributes, defStyleAttr, defStyleRes);
        init(attributes);
    }



    public void init(AttributeSet attributes) {

        dMetrics();
        if (attributes != null) {
            // initialize the star array
            TypedArray typedArray = getContext().obtainStyledAttributes(attributes, R.styleable.CustomStarsViewHolder);
            decreaseAni = typedArray.getResourceId(R.styleable.CustomStarsViewHolder_empty_anim, R.drawable.ic_launcher_background);
            increaseAni = typedArray.getResourceId(R.styleable.CustomStarsViewHolder_fill_anim, R.drawable.ic_launcher_foreground);
            emptyStar = typedArray.getResourceId(R.styleable.CustomStarsViewHolder_empty_star, R.color.colorPrimary);
            onStartStar = typedArray.getInt(R.styleable.CustomStarsViewHolder_initial_stars,2);
            maxStar = typedArray.getInt(R.styleable.CustomStarsViewHolder_max_stars,10);

            typedArray.recycle();
            currentNumStar = onStartStar;
            viewStar();

        }
    }

    public void starsSetting(int maxSet, int  initialSet) {
        // sets the initial stars for the view
        // remove all the views first
        removeAllViews();
        starViews.clear();

        maxStar = maxSet;
        onStartStar = initialSet;
        currentNumStar = initialSet;
        if (maxSet < initialSet) {
            initialSet = maxSet;
        }
        // loop through N stars and set newStartingStars as filled stars, rest as empty stars
        int j=0;
        while(j < maxSet) {
            StarsView sv = new StarsView(getContext());
            starViews.add(sv);
            if (j < initialSet) {
                sv.setFull(true);
                sv.setImageDrawable(getContext().getDrawable(increaseAni));
            } else {
                sv.setFull(false);
                sv.setImageDrawable(getContext().getDrawable(decreaseAni));
            }
            // once added, animate if the view is animatable
            Drawable drawable = sv.getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
            // add star to viewholder
            addView(sv);
            ++j;
        }
    }

    public void viewStar() {
        // for every star in our view holder, if the star is selected, make it selected else show empty star
        int i = 0;
        while(i < maxStar){
            StarsView sv = new StarsView(getContext());
            starViews.add(sv);
            if (i < onStartStar) {
                sv.setFull(true);
                sv.setImageDrawable(getContext().getDrawable(increaseAni));
            } else {
                sv.setFull(false);
                sv.setImageDrawable(getContext().getDrawable(decreaseAni));
            }
            // star is set, draw it as animation
            Drawable drawable = sv.getDrawable();
            if (drawable instanceof Animatable) {
                ((Animatable) drawable).start();
            }
            // add star to viewholder
            addView(sv);
            ++i;
        }
    }

    public void dMetrics() {
        // divide screen to left and right
        // increase/decrease of starts based on position where the user taps
        DisplayMetrics dm;
        dm = this.getResources().getDisplayMetrics();
        halfSide = (dm.widthPixels / 2);
    }

    private void starAnimation(int total) {
        // draw stars everytime hence remove existing stars from the view holder
        removeAllViews();
        starViews.clear();
        int i = 0;
        while(i < maxStar){
            StarsView star = new StarsView(getContext());
            if (i < total) {
                // fill the new number of stars
                star.setFull(true);
                star.setImageDrawable(getContext().getDrawable(increaseAni));
                // animate if able
                Drawable drawable = star.getDrawable();
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
            } else {
                star.setFull(false);
                star.setImageDrawable(getContext().getDrawable(emptyStar));
            }
            // add each star to the viewholder
            addView(star);
            ++i;
        }
        currentNumStar = total;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // Get the locaiton of the user's tap
        int userTap = (int) ev.getX();
        if(userTap < halfSide) {
            // if left side of star click, decrease star
            if(currentNumStar <= 0){
                // when hitting lower bound, do not decrease the current stars anymore
                return false;
            }
            starAnimation(--currentNumStar);
        } else {
            if(currentNumStar >= maxStar){
                // when hitting upper bound, do not increase the current stars anymore
                return false;
            }
            // if right side of star click, increase
            starAnimation(++currentNumStar);
        }
        return super.onInterceptTouchEvent(ev);
    }
}
