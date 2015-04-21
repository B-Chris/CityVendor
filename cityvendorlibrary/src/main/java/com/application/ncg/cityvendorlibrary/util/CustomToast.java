package com.application.ncg.cityvendorlibrary.util;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.Toast;

/**
 * Created by Chris on 2015-04-13.
 */
public class CustomToast extends Toast {
    int mDuration;
    boolean mShowing = false;

    /**
     * Construct an empty Toast object.  You must call {@link #setView} before you
     * can call {@link #show}.
     *
     * @param context The context to use.  Usually your {@link android.app.Application}
     *                or {@link android.app.Activity} object.
     */
    public CustomToast(Context context) {
        super(context);
    }

    public CustomToast(Context context, int time) {
        super(context);
        mDuration = time;
    }

    /**
     * Setting time to display toast (seconds)
     * @param seconds Seconds to display the toast
     */

    @Override
    public void setDuration(int seconds) {
        super.setDuration(LENGTH_SHORT);
        if (seconds < 5); //Minimum time
        mDuration = seconds;
    }

    /**
     * Showing toast for set time
     */
   @Override
    public void show() {
       super.show();
       if (mShowing) {
           return;
       }

       mShowing = true;
       final Toast ThisToast = this;
       new CountDownTimer((mDuration - 5) * 1000, 1000) {
           public void onTick(long millisUntilFinished) {ThisToast.show();}

           @Override
           public void onFinish() {
               ThisToast.show(); mShowing = false;
           }

       }.start();
   }

}
