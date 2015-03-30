package com.application.ncg.cityvendorlibrary.toolbox;

/**
 * Created by Chris on 2015-03-07.
 */
public class BaseVolley {

/*
    private static ResponseDTO response;
    private static Context ctx;
    protected static BohaRequest bohaRequest;
    protected static RequestQueue requestQueue;
    protected ImageLoader imageLoader;
    protected static String suff;
    static final String LOG = "BaseVolley";
    static final int MAX_RETRIES = 10;
    static final long SLEEP_TIME = 300;
    static int retries;
    static BohaVolleyListener bohaVolleyListener;

    public interface BohaVolleyListener {

        public void onResponseReceived(ResponseDTO response);
        public void onVolleyError(VolleyError error);

    }

    public static boolean checkNetworkOnDevice(Context context) {
        ctx = context;
        WebCheckResult wcr = WebCheck.checkNetworkAvailability(ctx);
        if (wcr.isNetworkUnavailable()) {
            Util.showErrorToast(ctx.getResources().getString(R.string.error_network_unavailable));
            return false;
        }
        return true;
    }



    public static void getRemoteData(String suffix, RequestDTO request,
                                     Context context, app application, BohaVolleyListener listener) {
        ctx = context;
        bohaVolleyListener = listener;
        if (requestQueue == null) {
            Log.w(LOG, "getRemoteData requestQueue is null - getting requested RemoteData");
            requestQueue = application.getRequestQueue(ctx);
        } else {
            Log.e(LOG, "getRemoteData requestQueue isn't null");
        }
        String json = null, jj = null;
        Gson gson = new Gson();
        try{
            jj = gson.toJson(request);
            json = URLEncoder.encode(jj, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        retries = 0;
        String x = Statics.URL + suffix + json;
        Log.i(LOG, "sending remote request. size: " + x.length() + "-->\n" +
        suffix + jj);
        bohaRequest = new BohaRequest(Method.POST, x,
                onSuccessListener(), onErrorListener());
        bohaRequest.setRetryPolicy(new DefaultRetryPolicy((int) TimeUnit.SECONDS.toMillis(120),
                0, 0));
        requestQueue.add(bohaRequest);

    }

    private static Response.Listener<ResponseDTO> onSuccessListener() {
        return new Response.Listener<ResponseDTO>(){
            @Override
        public void onResponse(ResponseDTO r) {
                response = r;
                Log.e(LOG, "response object has been received, statusCode: " + r.getStatusCode());
                if (r.getStatusCode() > 0) {
                    try {
                        Log.w(LOG, response.getMessage());
                    } catch (Exception e) {

                    }
                }
                bohaVolleyListener.onResponseReceived(response);
            }
        };
    }

    private static Response.ErrorListener onErrorListener() {
        return new Response.ErrorListener() {
            @Override
        public void onErrorResponse(VolleyError error) {
                if (error instanceof TimeoutError) {
                    retries++;
                    if (retries < MAX_RETRIES) {
                        waitABit();
                        Log.e(LOG, "onErrorResponse: retrying after timeout error. retries:" + retries);
                        requestQueue.add(bohaRequest);
                        return;
                    }
                }

                if (error instanceof NetworkError) {
                    Log.w(LOG, "onErrorResponse Network Error");
                    NetworkError ne = (NetworkError) error;
                    if (ne.networkResponse != null) {
                        Log.e(LOG, "volley networkResponse status code: " + ne.networkResponse.statusCode);
                    }
                    retries++;
                    if (retries < MAX_RETRIES) {
                        waitABit();
                        Log.e(LOG, "onErrorResponse: Retrying after NetworkError. retries:" + retries);
                        requestQueue.add(bohaRequest);
                        return;
                    }
                    Log.e(LOG, ctx.getResources().getString(R.string.error_network_unavailable) + error.toString());
                } else {
                    Log.e(LOG, ctx.getResources().getString(R.string.error_server_comms) + error.toString());

                }
                bohaVolleyListener.onVolleyError(error);
            }
        };
    }
    private static void waitABit() {
        Log.d(LOG, "going to sleep for: " + (SLEEP_TIME/1000) + "seconds before retrying");
        try {
            Thread.sleep(SLEEP_TIME);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
    }
}
*/

    }

