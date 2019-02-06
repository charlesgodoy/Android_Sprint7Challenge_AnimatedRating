package com.caz.ratingbarchallenge.customviews;

import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

public class StarsView extends android.support.v7.widget.AppCompatImageView {

    public boolean isFull = false;
    // Indicates whether the star is filled or not

    public StarsView(Context context) {
        super(context);
        init(null);
    }

    public StarsView(Context context, @Nullable AttributeSet attributes) {
        super(context, attributes);
        init(attributes);
    }

    public StarsView(Context context, @Nullable AttributeSet attributes, int defStyleAttr) {
        super(context, attributes, defStyleAttr);
        init(attributes);

    }

    public boolean isFull(){
        return isFull;
    }

    public void setFull(boolean full) {
        isFull = full;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    protected void init(AttributeSet attributes) {
    }
}