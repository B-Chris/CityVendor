package com.application.ncg.cityvendorlibrary.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Chris on 2015-03-07.
 */
public class RequestSyncService extends IntentService {
    public RequestSyncService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    } /*
    static final String LOG = RequestSyncService.class.getSimpleName();
    int currentIndex;
    static final Gson gson = new Gson();
    RequestSyncListener requestSyncListener;
    RequestCache requestCache;


    public interface RequestSyncListener {
        public void onProductsSynced(int goodResponses, int badResponses);
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
            RequestCache cache = gson.fomJson(json, RequestCache.class);
            if (cache != null) {
                requestCache = cache;
                Log.i(LOG, "RequestCache returned from dis, with these entries: "
                 + requestCache.getRequestCacheEntryList().size());
                print();
                controlRequestUpload();
            } else {
                Log.e(LOG, "requestCache is null");
                requestSyncListener.onProductsSynced(0, 0);
            }
        } catch (FileNotFoundException e) {
            Log.i(LOG, " FileNotFoundException, requestCache does not currently exist");
            requestSyncListener.onProductsSynced(0, 0);
        } catch (Exception e) {
            Log.e(LOG, "having trouble with sync", e);
            requestSyncListener.onProductsSynced(0, 0);
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
                list.getRequests().add(wcr.getRequest());
            }
            if (list.getRequests().isEmpty()) {
                Log.d(LOG, "no requests have been cached");
                requestSyncListener.onProductsSynced(0, 0);
                return;
            }
            Log.w(LOG, "sending list of cached requests: " + list.getRequests().size());
            WebSocketUtilForRequests.sendRequest(getApplicationContext(), list, new WebSocketUtilForRequests().WebSocketListener() {

                @Override
                public void onMessage(ResponseDTO response) {
                    if (!ErrorUtil.checkServerError(getApplicationContext(), repsonse)) {
                        return;
                    }
                    Log.i(LOG, "cached requests sent up. good responses" + response.getGoodCount() +
                    "bad responses:" + response.getBadCount());
                    for (RequestCacheEntry rce : requestCache.getRequestCacheEntryList()) {
                        rce.setDateUploaded(new Date());
                    }
                    cleanupCache();
                    requestSyncListener.onProductsSynced(response.getGoodCount(), response.getBadCount);

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
        CacheUtil.cacheRequest(getApplicationContext(), requestCache);
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

    public interface RequestSyncListener {
        public void onProductsSynced(int goodResponses, int badResponses);
        public void onError(String message);
    }
*/
}
