package com.application.ncg.cityvendorlibrary.activities;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.application.ncg.cityvendorlibrary.R;
import com.application.ncg.cityvendorlibrary.dto.VendorDTO;
import com.application.ncg.cityvendorlibrary.toolbox.BitmapLruCache;
import com.application.ncg.cityvendorlibrary.util.bean.SharedUtil;
import com.application.ncg.cityvendorlibrary.util.bean.Statics;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

import org.acra.ACRA;
import org.acra.ReportField;
import org.acra.annotation.ReportsCrashes;

import java.util.HashMap;

/**
 * Created by Chris on 2015-03-12.
 */
@ReportsCrashes(
        formKey =  "",
        formUri = Statics.CRASH_REPORTS_URL,
        customReportContent = {
                    ReportField.APP_VERSION_NAME,
                ReportField.APP_VERSION_CODE,
                ReportField.ANDROID_VERSION,
                ReportField.PHONE_MODEL,
                ReportField.BRAND,
                ReportField.STACK_TRACE,
                ReportField.PACKAGE_NAME,
                ReportField.CUSTOM_DATA,
                ReportField.LOGCAT},
        socketTimeout = 1000
)
public class VendorApp extends Application {


    RequestQueue requestQueue;
    BitmapLruCache bitmapLruCache;
    /*
    *Enum used to identify the tracker that needs to be used for tracking
    * one tracker is normal. if multiple trackers are needed, store the all in
    * Application object, it helps by making sure that they are only once per Application instance
     */
    public enum TrackerName {
        APP_TRACKER, //this tracker is only used in this app
        GLOBAL_TRACKER, //this tracker is used by all apps organization/company
        ECOMMERCE_TRACKER,  //this tracker is used by all ecommerce transactions from organization/company
    }

    static final String PROPERTY_ID = "UA-53661372-2";
    HashMap<TrackerName, Tracker> mTrackers = new HashMap<>();

    public synchronized Tracker getTracker(TrackerName trackerId) {
        if (!mTrackers.containsKey(trackerId)) {
            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
            Tracker t = null;
            if (trackerId == TrackerName.APP_TRACKER) {
                t = analytics.newTracker(PROPERTY_ID);
            }
            if (trackerId == TrackerName.GLOBAL_TRACKER) {
                t = analytics.newTracker(R.xml.global_tracker);
            }
            mTrackers.put(trackerId, t);
        }
        Log.i(LOG, "Analytics tracker ID: " + trackerId.toString());
        return mTrackers.get(trackerId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        StringBuilder sb = new StringBuilder();
        sb.append("\n\n\n######\n");
        sb.append("##\n");
        sb.append("#");
        sb.append("-->>>>>>>>>>VendorApp has started<<<<<<<<<<-");
        sb.append("#####\n");
                Log.d(LOG, sb.toString());
        ACRA.init(this);
        VendorDTO vendor = SharedUtil.getVendor(getApplicationContext());
        if (vendor != null) {
            ACRA.getErrorReporter().putCustomData("vendorID", " " + vendor.getVendorID());
           }

        Log.e(LOG, "ACRA crash Reporting has been initiated");
        initializeVolley(getApplicationContext());
/*
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .showImageOnFail(getApplicationContext().getResources().getDrawable(R.drawable.water_cam))
                .showImageOnLoading(getApplicationContext().getResources().getDrawable(R.drawable.under_construction1))
                .build();

        File cacheDir = StorageUtils.getCacheDirectory(this, true);
        Log.d(LOG, "onCreate, ImageLoader cacheDir, files: " + cacheDir.listFiles().length);

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .memoryCache(new LruMemoryCache(16 * 1024 * 1024))
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);
        L.writeDebugLogs(false);
        L.writeLogs(false);
*/
        Log.w(LOG, "ImageLoaderConfiguration has been initialised");
    }

    /*
    * This method sets up  volley networking;
    * creates RequestQueue and ImageLoader
    * @param context*/

    public void initializeVolley(Context context) {
        Log.e(LOG, "initializing volley networking");
        requestQueue = Volley.newRequestQueue(context);
        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();

        //using 1/8th of the available memory for this memory cache
        int cacheSize = 1024 * 1024 * memClass/8;
        bitmapLruCache = new BitmapLruCache(cacheSize);
        //imageLoader = new ImageLoader(requestQueue, bitmapLruCache);
        Log.i(LOG, "Volley Networking has been initialized, cache size:" + (cacheSize / 1024) + "KB");

        //Create global configuration and initialize ImageLoader with the following configuration
  /*      DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDispayImageOptions(defaultOptions)
                .build();
        ImageLoader.getInstance().init(config);
*/
    }

  //  public RequestQueue getRequestQueue() {return requestQue;}
    public BitmapLruCache getBitmapLruCache() {return bitmapLruCache;}

    static final String LOG = VendorApp.class.getSimpleName();

}
