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
import com.application.ncg.cityvendorlibrary.util.SharedUtil;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.L;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.acra.ACRA;

import java.io.File;
import java.util.HashMap;

/**
 * Created by Chris on 2015-04-01.
 */

/*@ReportsCrashes(
        formKey = Statics.CRASH_REPORTS_URL,
        customReportContent = {ReportField.APP_VERSION_NAME.APP_VERSION_CODE.
                ANDROID_VERSION.PHONE_MODEL.PHONE_MODEL.BRAND.STACK_TRACE
                .PACKAGE_NAME.CUSTOM_DATA.LOGCAT},
        socketTimeout = 10000
)
*/
public class VendorApp extends Application {

    public RequestQueue getRequestQueue() {
        return requestQueue;
    }
    public BitmapLruCache getBitmapLruCache() {
        return bitmapLruCache;
    }

    RequestQueue requestQueue;
    BitmapLruCache bitmapLruCache;
    static final String LOG = "VendorApp";


    @Override
    public void onCreate() {
        super.onCreate();
        StringBuilder sb = new StringBuilder();
        sb.append("------->");
        sb.append("-->");
        sb.append("Starting VendorApp");
        sb.append("-->");
        sb.append("------->");


        Log.d(LOG, sb.toString());

        ACRA.init(this);
        VendorDTO vendor = SharedUtil.getVendor(getApplicationContext());
        if (vendor != null) {
            ACRA.getErrorReporter().putCustomData("vendorID:", " " + vendor.getVendorID());

        }
        Log.e(LOG, "ACRA Crash Reporting has been initiated");
        initializeVolley(getApplicationContext());

        DisplayImageOptions defaultOptions =
                new DisplayImageOptions.Builder()
                        .cacheInMemory(true)
                        .cacheOnDisk(true)
                        .showImageOnFail(getApplicationContext().getResources().getDrawable(R.drawable.under_construction))
                        .showImageOnLoading(getApplicationContext().getResources().getDrawable(R.drawable.under_construction))
                        .build();

        File cacheDir = StorageUtils.getCacheDirectory(this, true);
        Log.d(LOG, "## onCreate, ImageLoader cacheDir, files: " + cacheDir.listFiles().length);
        //
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .denyCacheImageMultipleSizesInMemory()
                .diskCache(new UnlimitedDiscCache(cacheDir))
                .memoryCache(new LruMemoryCache(16 * 1024 * 1024))
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);
        L.writeDebugLogs(false);
        L.writeLogs(false);

        Log.w(LOG, "ImageLoaderConfiguration has been initialized");

    }

    public enum TrackerName {
        APP_TRACKER, //Tracker used for this app only
        GLOBAL_TRACKER, //Tracker used by all apps from company/organization
        ECCOMERCE_TRACKER //Tracker used by all ecommerce transaction from a company/organization
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
        Log.i(LOG, "analytics trackerID:" + trackerId.toString());
        return mTrackers.get(trackerId);
    }

    /**
     * Setting up Volley Networking
     * create RequestQueue
     * create ImageLoader
     * @param context
     */

    public void initializeVolley(Context context) {
        Log.e(LOG, "initializing Volley Networking");
        requestQueue = Volley.newRequestQueue(context);
        int memClass = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE))
                .getMemoryClass();

    //using an 1/8th of the available memory for this memory cache.
        int cacheSize = 1024 * 1024 * memClass / 8;
        bitmapLruCache = new BitmapLruCache(cacheSize);
        //imageLoader = new ImageLoader(requestQueue, bitmapLruCache);
        Log.i(LOG, "Volley Networking has been initialized, cache size: " + (cacheSize / 1024) + " KB ");

        //Create global configuration and initialize ImageLoader with this configuration
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .build();
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
                .defaultDisplayImageOptions(defaultOptions)
                .build();

        ImageLoader.getInstance().init(config);
    }


}
