package com.application.ncg.cityvendorappsuite;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.application.ncg.cityvendorappsuite.providers.VendorUtils;

/**
 * Created by Chris on 2015-03-15.
 */
public class VendorGCMUtils {

    //GCM Sir I.M sender_ID
    public final static String SENDER_ID = "553738964611";
    static final String GCM_REG_ID_KEY = "regId";


    /**
     * Method that helps to read cached GCM Registration ID from Shared - Preferences
     * @params context
     */
    public static String getMyRegistrationId(Context ctx){
        String gcmRegId = "";
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        gcmRegId = sp.getString(GCM_REG_ID_KEY,null);
        return gcmRegId;
    }

    public static void saveGCMRegistrationId(String regId, Context ctx){

        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(ctx);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(GCM_REG_ID_KEY, regId);
        editor.commit();

    }
//for server url check ur
    public static final String SERVER_URL = "http://192.168.56.1:3000/gcm/send";
    static final String TAG = VendorUtils.class.getSimpleName();
    public static void registerVendorGCM(Context context,String email, String regid, String app_name) {

        RequestQueue queue = Volley.newRequestQueue(context);


        Request<String> submitVendorRequest = new VendorMultiPartRequest(
                SERVER_URL, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try {
                    Log.w(TAG, "error uploading file: " + error.getLocalizedMessage());
                    Log.e(TAG, "server-error during upload: ", error);
                } catch (NullPointerException npe) {
                    Log.e(TAG, npe.getLocalizedMessage(), npe);
                }
            }
        },
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response);
                    }

                }, email,regid, app_name);
        queue.add(submitVendorRequest);

    }

}
