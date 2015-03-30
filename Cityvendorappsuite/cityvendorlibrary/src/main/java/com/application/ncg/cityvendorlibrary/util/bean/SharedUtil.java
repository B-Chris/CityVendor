package com.application.ncg.cityvendorlibrary.util.bean;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.preference.PreferenceManager;
import android.util.Log;

import com.application.ncg.cityvendorlibrary.dto.VendorDTO;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Chris on 2015-03-07.
 */
public class SharedUtil {
    static final Gson gson = new Gson();
    public static final String
    VENDOR_JSON = "vendor",
    LOG = "SharedUtil",
    VENDOR_ID = "vendorID",
    GCM_REGISTRATION_ID = "gcm",
    SESSION_ID ="sessionID",
    APP_VERSION = "appVersion",
    REMINDER_TIME = "reminderTime",
    VENDOR_LOCATION = "vendorLocation";

    /*
    * Method storeRegistrationID stores the RegistrationID and the application version in SharedPreferences
    * @param context applications context
    * @param regId registration ID
    * */

    public static void storeRegistrationId(Context ctx, String regId) {
        final SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        int appVersion = getAppVersion(ctx);
        Log.i(LOG, "saving regId on app version: " +appVersion);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(GCM_REGISTRATION_ID, regId);
        ed.putInt(APP_VERSION, appVersion);
        ed.commit();
        Log.e(LOG, "GCM registrationID has been saved sharedPreference ");
    }

    /*
    * Method gets the registration ID for the application on GCM service
    * first register the app so that the result is empty
    *
    * will return registrationID if the result is empty
    * */
    public static String getRegistrationId(Context context) {
        final SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(context);
        String registrationId = pref.getString(GCM_REGISTRATION_ID, null);
        if (registrationId == null){
            Log.i(LOG, "GCM Registration ID has not been found on the device");
            return null;
        }
        //check if app was updated, if it was it should clear the registrationID
        //because the existing regID is not certain to work with the newly updated app version
        int registeredVersion = pref.getInt(APP_VERSION, Integer.MIN_VALUE);
        int currentVersion = SharedUtil.getAppVersion(context);
        if (registeredVersion != currentVersion) {
            Log.i(LOG, "App version has successfully been changed");
            return null;
        }
        return registrationId;
    }

    public static void saveReminderTime(Context ctx, Date date) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor ed = sp.edit();
        ed.putLong(REMINDER_TIME, date.getTime());
        ed.commit();
        Log.e("SharedUtil", "reminderTime:" + date + "saved in SharedPreferences");

    }

    public Date getReminderTime(Context ctx) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        long l = sp.getLong(REMINDER_TIME, 0);
        if (l == 0) {
            Calendar cal = GregorianCalendar.getInstance();
            cal.roll(Calendar.DAY_OF_YEAR, false);
            return cal.getTime();
        }
        return new Date(l);
    }

     public static void saveVendor(Context ctx, VendorDTO ven) {
        VendorDTO xx = new VendorDTO();
        xx.setName(ven.getName());
        xx.setVendorID(ven.getVendorID());

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String x = gson.toJson(xx);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(VENDOR_JSON, x);
        ed.commit();
        Log.e("SharedUtil", "Vendor: " + ven.getName() + ven.getSurname() + "saved in SharedPreferences");
    }

    public static VendorDTO getVendor(Context ctx) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String s = sp.getString(VENDOR_JSON, null);
        VendorDTO ve = null;
        if (s != null) {
            ve = gson.fromJson(s, VendorDTO.class);
        }
        return ve;
    }

    public static void saveSessionID(Context ctx, String sessionID) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(SESSION_ID, sessionID);
        ed.commit();
        Log.e("SharedUtil", "sessionID: " + sessionID + "saved in SharedPreference");
    }

    public static String getSessionID(Context ctx) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        return sp.getString(SESSION_ID, null);
    }

    public static void saveVendorLocation(Context ctx, VendorDTO dto, Location loc) {
        VendorLocation vl = new VendorLocation();
        vl.setAccuracy(loc.getAccuracy());
        vl.setLatitude(loc.getLatitude());
        vl.setLongitude(loc.getLongitude());
        vl.setVendorID(dto.getVendorID());
        vl.setDateTaken(new Date());

        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString(VENDOR_LOCATION, gson.toJson(vl));
        ed.commit();
        Log.e("SharedUtil", "Vendors: " + dto.getName() + dto.getSurname() + "saved in SharedPreferences");
   }

    public static VendorLocation getVendorLocation(Context ctx) {
        SharedPreferences sp = PreferenceManager
                .getDefaultSharedPreferences(ctx);
        String s = sp.getString(VENDOR_LOCATION, null);
        if (s == null) {
            return null;
        }
        return gson.fromJson(s,VendorLocation.class);
    }
    /*
    * @return Applications version code from the {@code PackageManager}
    * Method to get the applications version code
    * */

    public static int getAppVersion(Context context) {
        try{
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch(PackageManager.NameNotFoundException e) {
            //not supposed to happen
            throw new RuntimeException("Unable to get package name:" + e);
        }

    }

}
