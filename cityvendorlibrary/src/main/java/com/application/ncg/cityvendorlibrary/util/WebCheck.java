package com.application.ncg.cityvendorlibrary.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.util.Log;

/**
 * Created by Chris on 2015-03-07.
 */
public class WebCheck {

    static ConnectivityManager connectivity;

    public static void startWirelessSettings(Context ctx){
        Intent i = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        ctx.startActivity(i);
    }

    public static WebCheckResult checkNetworkAvailability(Context ctx, boolean noLogging) {
        if (connectivity == null) {
            connectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);

        }
        NetworkInfo wifiInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        WebCheckResult result = new WebCheckResult();
        if (wifiInfo.isAvailable()){
            result.setWifiAvailable(true);
        } else {
            result.setWifiAvailable(false);
        }
        if (wifiInfo.isConnected()) {
            result.setWifiConnected(true);
        } else {
            result.setWifiConnected(false);
        }

        if (mobileInfo != null){
            if (mobileInfo.isAvailable()) {
                result.setMobileAvailable(true);
            } else{
                Log.d(TAG, "mobile network not available on CHECK");
                result.setMobileAvailable(false);
            }
            if (mobileInfo.isConnected()) {
                result.setMobileConnected(true);
            } else {
                result.setMobileConnected(false);
            }
        } else {
            result.setMobileAvailable(false);
        }
        if (!result.isWifiConnected() && !result.isMobileConnected()) {
            result.setNetworkUnavailable(true);
        }
        long end = System.currentTimeMillis();
        return result;
    }

    public static WebCheckResult checkNetworkAvailability(Context ctx) {
        //CHECKING CONNECTION TO THE INTERNET
        long start = System.currentTimeMillis();
        if (connectivity == null) {
            connectivity = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        }
        NetworkInfo wifiInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileInfo = connectivity.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        WebCheckResult result = new WebCheckResult();
        if (wifiInfo.isAvailable()) {
            Log.w(TAG, "WIFI is available, checked");
            result.setWifiAvailable(true);
        } else{
            Log.e(TAG, "WIFI unavailable, checked");
            result.setWifiAvailable(false);
        }

        if (wifiInfo.isConnected()) {
            Log.w(TAG, "wifi connected when checked");
            result.setWifiConnected(true);
        } else {
            Log.e(TAG, "wifi is not connected when checked");
            result.setWifiConnected(false);
        }

        if (mobileInfo != null) {
            if (mobileInfo.isAvailable()) {
                Log.w(TAG, "mobile network available, when checking");
                result.setMobileAvailable(true);
            } else {
                Log.d(TAG, "mobile network unavailable when checking");
                result.setMobileAvailable(false);
            }
            if (mobileInfo.isConnected()) {
                Log.w(TAG, "mobile network connected when checked");
                result.setMobileConnected(true);
            } else {
                Log.d(TAG, "mobile network is not connected when checked");
                result.setMobileConnected(true);
            }
        } else {
            Log.d(TAG, "mobile network is not unavailable when checked");
            result.setMobileAvailable(false);
        }
        if (!result.isWifiConnected() && !result.isMobileConnected()) {
            result.setNetworkUnavailable(true);
        }
        long end = System.currentTimeMillis();
        return result;

    }
    public static final int WIFI_AVAILABLE_CONNECTED = 1;
    public static final int MOBILE_NETWORK_AVAILABLE = 2;
    public static final int BOTH_NETWORKS_AVAILABLE = 3;
    public static final int NETWORK_NOT_AVAILABLE = 5;
    public static final String TAG = "WebCheck";


}
