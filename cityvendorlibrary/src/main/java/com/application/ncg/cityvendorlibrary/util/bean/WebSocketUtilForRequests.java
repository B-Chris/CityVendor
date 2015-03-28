package com.application.ncg.cityvendorlibrary.util.bean;

/**
 * Created by Chris on 2015-03-07.
 */
public class WebSocketUtilForRequests {
    /*

    static WebSocketClient mWebSocketClient;
    static final String LOG = WebSocketUtilForRequests.class.getSimpleName();
    static final Gson gson = new Gson();


    public interface WebSocketListener {
        public void onMessage(ResponseDTO response);
        public void onClose();
        public void onError(String message);
    }

    static WebSocketListener webSocketListener;
    static RequestList requestList;
    static Context ctx;
    static long start, end;

    public static void disconnectSession() {
        if (mWebSocketClient != null) {
            mWebSocketClient.close();
            Log.e(LOG, "webSocket session disconnected");
        }
    }

    public static void sendRequest(Context c, RequestList req, WebSocketListener
                                   listener) {
        if (!BaseVolley.checkNetworkOnDevice(c)) {
            listener.onError(c.getString(R.string.no_network));
            return;
        }
        webSocketListener = listener;
        requestList = req;
        ctx = c;
        TimerUtil.startTimer(new TimerUtil.TimerListener() {
           @Override
            public void onSessionDisconnected() {
                try {
                    connectWebSocket();
                    return;
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            if (mWebSocketClient == null) {
                connectWebSocket();
            } else {
                String json = gson.toJson(requestList);
                start = System.currentTimeMillis();
                mWebSocketClient.send(json);
                Log.d(LOG, "web socket message sent\n" +json);
            }
        } catch (WebsocketNotConnectedException e) {
            try {
            Log.e(LOG, "WebSocketNotConnectedException,  Something wrong with web socket", e);
            connectWebSocket();
        } catch(URISyntaxException se) {
            Log.e(LOG, "problems with web socket", se);
                webSocketListener.onError("Struggling to start server socket communications\n" + se.getMessage());
            }
        }
    }

    private static void connectWebSocket() throws URISyntaxException {
        URI uri = new URI(Statics.WEBSOCKET_URL + Statics.REQUEST_ENDPOINT);
        Log.i(LOG, "connectWebSocket uri: " + uri.toString());
        start = System.currentTimeMillis();
        mWebSocketClient = new WebSocketClient(uri) {
            @Override
        public void onOpen(ServerHandshake serverHandshake) {
                Log.i(LOG, "WEBSOCKET opened:" + serverHandshake.getHttpStatusMessage);

            }
            @Override
        public void onMessage(String response) {
                TimerUtil.killTimer();
                end = System.currentTimeMillis();
                Log.i(LOG, "onMessage returning socket sessionID, length:"
                 + response.length() + "getElapsed: " + Util.getElapsed(start, end) +
                "seconds" + "\n" + response);
                try {
                    ResponseDTO r = gson.fromJson(response, ResponseDTO.class);
                    if (r.getStatusCode() == null || r.getStatusCode() = 0) {
                        if (r.getSessionID != null) {
                            SharedUtil.saveSessionID(ctx, r.getSessionID());
                            String json = gson.toJson(requestList);
                            mWebSocketClient.send(json);
                            Log.i(LOG, "web socket request sent after onOpen\n" + json);

                        }
                    }  else {
                        webSocketListener.onError(r.getMessage());
                    }
                }  catch (Exception e) {
                    Log.e(LOG, "Failed to parse response from server", e);
                    webSocketListener.onError("Failed to parse response from server");
                }
            }
            @Override
        public void onMessage(ByteBuffer bb) {
                TimerUtil.killTimer();
                end = System.currentTimeMillis();
                Log.i(LOG, "onMessage returning data, elapsed: " + Util.getElapsed(start, end) + "seconds");
                parseData(bb);
            }
            @Override
        public onClose(final int i, String s, boolean b) {
                Log.e(LOG, "WEBSOCKET onClose, status code:" + i);
                TimerUtil.killTimer();
                webSocketListener.onClose();
            }
            @Override
        public void onError(final Exception e) {
                Log.e(LOG, "onError", e);
                TimerUtil.killTimer();
                webSocketListener.onError(ctx.getString(R.string.server_comms_failed));
            }

        };
        Log.d(LOG, "Now starting mWebSocketClient.connect");

        mWebSocketClient.connect();
    }

    private static void parseData(ByteBuffer bb) {
        Log.i(LOG, "parseData ByteBuffer capacity:" + ZipUtil.getKilobytes(bb.getCapacity()));
        String content = null;
        start = System.currentTimeMillis();
        try {
            try{
                content = new String(bb.array());
                com.ncg.cityvendor.transfer.ResponseDTO response = gson.fromJson(content, com.ncg.cityvendor.transfer.ResponseDTO.class);
                if (response.getStatusCode() == 0) {
                    webSocketListener.onMessage(response);
                } else {
                    webSocketListener.onError(response.getMessage);
                }
                return;
            } catch (Exception e) {
                content = ZipUtil.uncompressGZip(bb);
            }
            if (content != null) {
                ResponseDTO response = gson.fromJson(content, ResponseDTO.class);
                if (response.getStatusCode() == 0) {
                    Log.w(LOG, "response status code is - OK");
                    webSocketListener.onMessage(response);
                } else {
                    Log.e(LOG, "response status code is > 0 - server found Error");
                    webSocketListener.onError(response.getMessage);
                }
            } else {
                Log.e(LOG, "failed to get content from server");
                webSocketListener.onError("Failed to get content from server");
            }
            end = System.currentTimeMillis();
            Log.d(LOG, "parseData finished, elapsed:" + Util.getElapsed(start, end) + "seconds");

        } catch (Exception e) {
            Log.e(LOG, "parseData Failed", e);
            webSocketListener.onError("Failed to unpack server response");
        }
    }


    public static String getElapsed() {
        BigDecimal bd = new BigDecimal(end - start).divide(new BigDecimal(1000));
        return " " + bd.doubleValue() + "seconds";
    }
*/
}
