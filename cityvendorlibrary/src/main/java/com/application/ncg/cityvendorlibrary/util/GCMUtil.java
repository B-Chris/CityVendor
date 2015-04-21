package com.application.ncg.cityvendorlibrary.util;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.application.ncg.cityvendorlibrary.dto.transfer.RequestDTO;
import com.application.ncg.cityvendorlibrary.dto.transfer.ResponseDTO;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * Created by Chris on 2015-03-27.
 */
public class GCMUtil {


    public interface GCMUtilListener {
        public void onDeviceRegistered(String id);
        public void onGCMError();
    }

    static Context ctx;
    static GCMUtilListener gcmUtilListener;
    static String registrationID, msg;
    static final String LOG = GCMUtil.class.getSimpleName();
    static GoogleCloudMessaging gcm;

    public static void startGCMRegistration(Context context, GCMUtilListener listener) {
        ctx = context;
        gcmUtilListener = listener;
        new GCMTask().execute();
    }
        public static final String GCM_SENDER_ID = "get it from app";

        static class GCMTask extends AsyncTask<Void, Void, Integer> {

            @Override
            protected Integer doInBackground(Void... params) {
                Log.e(LOG, "starting GCM registration");
                try {
                    if (gcm == null) {
                        gcm = GoogleCloudMessaging.getInstance(ctx);
                    }
                    registrationID = gcm.register(GCM_SENDER_ID);
                    msg = "Device has been registered, registration ID: "+ registrationID;
                    SharedUtil.storeRegistrationId(ctx, registrationID);
                    RequestDTO r = new RequestDTO();
                    r.setRequestType(RequestDTO.SEND_GCM_REGISTRATION);
                    r.setGcmRegistrationID(registrationID);
                    WebSocketUtil.sendRequest(ctx, Statics.VENDOR_ENDPOINT, r, new WebSocketUtil.WebSocketListener() {
                        @Override
                        public void onMessage(ResponseDTO response) {
                            if (response.getStatusCode() == 0) {
                                Log.e(LOG, "Device has been registered on server GCM regime");
                            }
                        }

                        @Override
                        public void onClose() {

                        }

                        @Override
                        public void onError(String message) {
                            Log.e(LOG, "Device failed to register on server GCM regime" + message);

                        }
                    });
                    Log.i(LOG, msg);
                } catch (IOException e) {
                    return Constants.ERROR_SERVER_COMMS;
                }
                return 0;
            }
            @Override
            protected void onPostExecute(Integer result) {
                Log.i(LOG, "onPostExecute... GCM registration is now finishing");
                if (result > 0) {
                    gcmUtilListener.onGCMError();
                    ErrorUtil.handleErrors(ctx, result);
                    return;
                }
                gcmUtilListener.onDeviceRegistered(registrationID);
                Log.i(LOG, "onPostExecute GCM device has been registered");
            }

        }

}
