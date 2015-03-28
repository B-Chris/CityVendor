package com.application.ncg.cityvendorlibrary.services;

import android.app.NotificationManager;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.application.ncg.cityvendorlibrary.dto.VendorDTO;
import com.google.gson.Gson;

/**
 * Created by Chris on 2015-03-07.
 */
public class GcmIntentService/* extends GCMBaseIntentService */{
    public static final int NOTIFICATION_ID = 1;
    private NotificationManager mNotificationManager;
    NotificationCompat.Builder builder;
    static final String TAG = "GcmIntentService";

    public GcmIntentService() {
  //      super(GCMUtil.GCM_SENDER_ID);
    }
/*
    @Override
    protected void onError(Context argO, String arg1) {
        Log.i(TAG, "onError" + arg1);
    }

    @Override
    protected void onMessage(Context argO, Intent intent) {
        Log.w(TAG, "onMessage: gcm message" + intent.getExtras().toString());
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        Log.d(TAG, "GCM messageType: " + messageType);
        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR.equals(messageType)) {
                Log.e(TAG, "GoogleCloudMessaging - MESSAGE_TYPE_SEND_ERROR");
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED.equals(messageType)) {
                Log.e(TAG, "GoogleCloudMessaging - MESSAGE_TYPE_SEND_ERROR");
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE.equals(messageType)) {
                sendNotification(intent);
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);

    }

    @Override
    protected void onRegistered(Context argO, String arg1) {
        Log.i(TAG, "onRegistered" + arg1);
    }

    @Override
    protected void onUnregistered(Context argO, String arg1) {
        Log.i(TAG, "onUnRegistered" + arg1);
    }
*/
    Gson gson = new Gson();

    private void sendNotification(Intent msgIntent) {
      //  mNotificationManager = (NotificationManager) this.getSystemService(NOTIFICATION_SERVICE);
        String message = msgIntent.getExtras().getString("message");
        VendorDTO dto;
        try {
            dto = gson.fromJson(message, VendorDTO.class);
        } catch (Exception e) {
            Log.e(TAG, "gcm message:" + message);
            return;
        }
/*
        Intent resultIntent = new Intent(this, NotificationAdapter.class);
        resultIntent.putExtra("leaderBoard", dto);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentIntent(resultPendingIntent)
                .addAction(R.drawable.ic_action_refresh, "More", resultPendingIntent)
                .setSmallIcon(R.drawable.xblack_oval)
                .setContentTitle(dto.getName())
                .setContentText(dto.getVendorID() + "updated");

      mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
*/
    }
}



