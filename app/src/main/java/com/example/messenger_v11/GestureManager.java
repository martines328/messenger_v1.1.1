package com.example.messenger_v11;

import android.content.Context;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;

public class GestureManager extends SimpleOnGestureListener {

    Context context;
    private GestureDetector detector;
    GestureManager gestureManager;


    public GestureManager(Context context, GestureManager gestureManager) {
        this.context = context;
        detector = new GestureDetector(context, this);
        this.gestureManager = gestureManager;

    }


    //TODO create listener for return back swipe GestureManager


}
