package com.application.ncg.cityvendorlibrary.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.application.ncg.cityvendorlibrary.dto.transfer.RequestList;
import com.application.ncg.cityvendorlibrary.dto.transfer.ResponseDTO;
import com.application.ncg.cityvendorlibrary.util.CacheUtil;
import com.application.ncg.cityvendorlibrary.util.ErrorUtil;
import com.application.ncg.cityvendorlibrary.util.WebCheck;
import com.application.ncg.cityvendorlibrary.util.WebCheckResult;
import com.application.ncg.cityvendorlibrary.util.WebSocketUtilForRequests;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Chris on 2015-03-07.
 */
public class RequestSyncService extends IntentService {
    public RequestSyncService(String name) {
        super(name);
    }


    static final String LOG = RequestSyncService.class.getSimpleName();
    int currentIndex;
    static final Gson gson = new Gson();
    RequestSyncListener requestSyncListener;
    RequestCache requestCache;


    public interface RequestSyncListener {
        public void onVendorSynced(int goodResponses, int badResponses);
        public void onError(String message);
    }

    public RequestSyncService() {
        super("RequestSyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.w(LOG, "RequestSyncService onHandleIntent START");
        FileInputStream stream;
        try{
            stream = getApplicationContext().openFileInput("requestCache.json");
            String json = getStringFromInputStream(stream);
            RequestCache cache = gson.fromJson(json, RequestCache.class);
            if (cache != null) {
                requestCache = cache;
                Log.i(LOG, "RequestCache returned from dis, with these entries: "
                 + requestCache.getRequestCacheEntryList().size());
                print();
                controlRequestUpload();
            } else {
                Log.e(LOG, "requestCache is null");
                requestSyncListener.onVendorSynced(0, 0);
            }
        } catch (FileNotFoundException e) {
            Log.i(LOG, " FileNotFoundException, requestCache does not currently exist");
            requestSyncListener.onVendorSynced(0, 0);
        } catch (Exception e) {
            Log.e(LOG, "having trouble with sync", e);
            requestSyncListener.onVendorSynced(0, 0);
        }

    }

    private static String getStringFromInputStream(InputStream is) throws IOException {
        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();
        String line;
        try{
            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            if (br != null) {
                br.close();
            }
        }
        String json = sb.toString();
        return json;
    }

    private void controlRequestUpload(){
        WebCheckResult wcr = WebCheck.checkNetworkAvailability(getApplicationContext());
        if (wcr.isWifiConnected()) {
            Log.i(LOG, "WIFI is connected and cached requests about to be sent to the cloud");
            RequestList list = new RequestList();
            for (RequestCacheEntry rce : requestCache.getRequestCacheEntryList()) {
                list.getRequests().add(rce.getRequest());
            }
            if (list.getRequests().isEmpty()) {
                Log.d(LOG, "no requests have been cached");
                requestSyncListener.onVendorSynced(0, 0);
                return;
            }
            Log.w(LOG, "sending list of cached requests: " + list.getRequests().size());
            WebSocketUtilForRequests.sendRequest(getApplicationContext(), list, new WebSocketUtilForRequests.WebSocketListener()
            {

                @Override
                public void onMessage(ResponseDTO response) {
                    if (!ErrorUtil.checkServerError(getApplicationContext(), response)) {
                        return;
                    }
                    Log.i(LOG, "cached requests sent up. good responses" + response.getGoodCount() +
                    "bad responses:" + response.getBadCount());
                    for (RequestCacheEntry rce : requestCache.getRequestCacheEntryList()) {
                        rce.setDateUploaded((java.sql.Date) new Date());
                    }
                    cleanupCache();
                    requestSyncListener.onVendorSynced(response.getGoodCount(), response.getBadCount());

                }
                @Override
                public void onClose() {

                }
                @Override
                public void onError(String message) {
                    requestSyncListener.onError(message);
                }
            });
        } else {
            Log.e(LOG, "WIFI is unable to connect, therefore cannot sync");
        }
    }
    private void cleanupCache() {
        List<RequestCacheEntry> list = new ArrayList<>();
        for (RequestCacheEntry rce : requestCache.getRequestCacheEntryList()) {
            if (rce.getDateUploaded() == null) {
                list.add(rce);
            }
        }
        Log.i(LOG, "preparing cache cleanUp: " + list.size());
        requestCache.setRequestCacheEntryList(list);
        CacheUtil.cacheRequest(getApplicationContext(), requestCache, null);
    }

    private void print() {
        for (RequestCacheEntry rce : requestCache.getRequestCacheEntryList()) {
            Log.w(LOG, "++" + rce.getDateRequested() + "request: " + rce.getRequest());

        }
    }
    public class LocalBinder extends Binder {
        public RequestSyncService getService() {
            return RequestSyncService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }
    private final IBinder mBinder = new LocalBinder();
    public void startSyncCachedRequests(RequestSyncListener rsl) {
        requestSyncListener = rsl;
        onHandleIntent(null);
    }

}
