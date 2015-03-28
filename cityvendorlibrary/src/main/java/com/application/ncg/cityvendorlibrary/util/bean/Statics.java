package com.application.ncg.cityvendorlibrary.util.bean;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by Chris on 2015-03-12.
 */
public class Statics {
    /*
    * REMOTE URL - ncg backend
    */

    //codetribe
    public static final String WEBSOCKET_URL = "ws://10.50.75.38:8080/cvp/";
    public static final String URL = "http://10.50.75.38:8080/cvp/";
    public static final String IMAGE_URL = "http://10.50.75.38:8080/";

    public static final String REQUEST_ENDPOINT = "wsrequest",
                               VENDOR_ENDPOINT = "wsvendor",
                               GATEWAY_SERVLET = "gate";

    public static final String SESSION_ID ="sessionID";
  //  public static final int CRASH_STRING = R.string.crash_toast;

    public static final String UPLOAD_URL_REQUEST = "uploadUrl?";
    public static final String CRASH_REPORTS_URL = URL + "crash?";

    public static void setDroidFontBold(Context ctx, TextView txt) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(), "DroidSerif-Bold");
        txt.setTypeface(font);
    }

    public static void setRobotoFontBoldCondensed(Context ctx, TextView txt) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(),"fonts/Roboto-BoldCondensed.ttf");
        txt.setTypeface(font);
    }

    public static void setRobotoFontRegular(Context ctx, TextView txt) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto-Regular.ttf");
        txt.setTypeface(font);
    }

    public static void setRobotoFontLight(Context ctx, TextView txt) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto-Light.ttf");
        txt.setTypeface(font);
    }

    public static void setRobotoFontBold(Context ctx, TextView txt) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto-Bold.ttf");
        txt.setTypeface(font);
    }

    public static void setRobotoItalic(Context ctx, TextView txt) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto-Italic.ttf");
        txt.setTypeface(font);
    }

    public static void setRobotoRegular(Context ctx, TextView txt) {
        Typeface font = Typeface.createFromAsset(ctx.getAssets(), "fonts/Roboto-Regular.ttf");
        txt.setTypeface(font);
    }






}
