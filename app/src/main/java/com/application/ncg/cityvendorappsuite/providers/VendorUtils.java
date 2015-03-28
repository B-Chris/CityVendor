package com.application.ncg.cityvendorappsuite.providers;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.application.ncg.cityvendorappsuite.VendorMultiPartRequest;

/**
 * Created by Chris on 2015-03-21.
 */
public class VendorUtils {

    public static final String SERVER_URL = "http://192.168.1.108:8080";
    static final String TAG = VendorUtils.class.getSimpleName();
    public static void submitVendor(Context context,String email, String regid, String app_name) {

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
