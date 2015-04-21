package com.application.ncg.cityvendorlibrary.util;

import android.content.Context;
import android.util.Log;

import com.application.ncg.cityvendorlibrary.R;
import com.application.ncg.cityvendorlibrary.dto.transfer.RequestDTO;
import com.application.ncg.cityvendorlibrary.dto.transfer.ResponseDTO;
import com.google.gson.Gson;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.exceptions.WebsocketNotConnectedException;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

/**
 * Created by Chris on 2015-03-07.
 */
public class WebSocketUtil {

    public interface WebSocketListener {
        public void onMessage (ResponseDTO response);
        public void onClose();
        public void onError(String message);
    }
    static WebSocketListener webSocketListener;
    static WebSocketClient mWebSocketClient;
    static final Gson gson = new Gson();
    static RequestDTO request;
    static Context ctx;
    static long start, end;
    
    public static void disconnectSession() {
        if (mWebSocketClient != null) {
            mWebSocketClient.close();
            Log.e(LOG, "webSocket session has been disconnected");
        }
    }

    public static void sendRequest(Context c, final String suffix, RequestDTO req,
                                   WebSocketListener listener) {
        if (req.getUseHttp()) {
            VolleyUtil.sendRequest(c, req, listener);
            return;
        }
        webSocketListener = listener;
        request = req;
        ctx = c;
        TimerUtil.startTimer(new TimerUtil.TimerListener() {
            @Override
            public void onSessionDisconnected() {
                try {
                    connectWebSocket(suffix);
                    return;
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            if (mWebSocketClient == null) {
                connectWebSocket(suffix);
            } else {
                String json = gson.toJson(req);
                start = System.currentTimeMillis();
                mWebSocketClient.send(json);
                URI uri = new URI(Statics.WEBSOCKET_URL + suffix);
                Log.i(LOG, "request sent, uri:" + uri.toString() + "web socket message has been sent\n" + json);

            }
        } catch (WebsocketNotConnectedException e) {
            Log.e(LOG, "having trouble with web socket", e);
            webSocketListener.onError("Struggling to start server socket, not communicating well");

        } catch (URISyntaxException e) {
            Log.e(LOG, "struggling with web socket", e);
            webSocketListener.onError("Struggling to start server socket, not communicating well");

        }
    }

    private static void connectWebSocket(String socketSuffix) throws URISyntaxException {
        URI uri = new URI(Statics.WEBSOCKET_URL + socketSuffix);
        Log.i(LOG, "connectWebSocket uri: " + uri.toString());
        start = System.currentTimeMillis();
        mWebSocketClient = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake serverHandshake) {
                Log.i(LOG, "websocket opened: " + serverHandshake.getHttpStatusMessage());
            }
            @Override
            public void onMessage(String response) {
                TimerUtil.killTimer();
                end = System.currentTimeMillis();
                Log.i(LOG, "onMessage returning websocket sessionID, length:" + response.length() + "elapsed: "
                        + Util.getElapsed(start, end) + "seconds" + "\n" + response);
                try {
                    ResponseDTO r = gson.fromJson(response, ResponseDTO.class);
                    if (r.getStatusCode() == 0) {
                        if (r.getSessionID() != null) {
                            SharedUtil.saveSessionID(ctx, r.getSessionID());
                            String json = gson.toJson(request);
                            mWebSocketClient.send(json);
                            Log.i(LOG, "web socket request sent after onOpen\n" + json);
                        }
                    } else {
                        webSocketListener.onError(r.getMessage());
                    }
                } catch (Exception e) {
                    Log.e(LOG, "Failed to parse response from server", e);
                    webSocketListener.onError("Unable to parse response from server");
                }
            }
            @Override
            public void onMessage(ByteBuffer bb) {
                TimerUtil.killTimer();
                end = System.currentTimeMillis();
                Log.i(LOG, "onMessage returning data, elapsed:" + Util.getElapsed(start, end) + "seconds");
                parseData(bb);
            }

            @Override
            public void onClose(final int i, String s, boolean b) {
                Log.e(LOG, "WebSocket onClose, status code: " + i);
                webSocketListener.onClose();
            }
            @Override
            public void onError(final Exception e) {
                Log.e(LOG, "onError", e);
                TimerUtil.killTimer();
                webSocketListener.onError(ctx.getString(R.string.server_comms_failed));
            }
        };
        Log.d(LOG, "--> connecting mWebSocketClient");
        mWebSocketClient.connect();
    }

    private static void parseData(ByteBuffer bb) {
        Log.i(LOG, "parseData ByteBuffer capacity: " + ZipUtil.getKilobytes(bb.capacity()));
        String content = null;
        start = System.currentTimeMillis();
        try {
            try {
            content = new String(bb.array());
            end = System.currentTimeMillis();
            ResponseDTO response = gson.fromJson(content, ResponseDTO.class);
            if (response.getStatusCode() == 0) {
                webSocketListener.onMessage(response);
            } else {
                webSocketListener.onError(response.getMessage());
            }
            return;
        } catch (Exception e) {
            content = ZipUtil.uncompressGZip(bb);
        }

        if (content != null) {
            ResponseDTO response = gson.fromJson(content, ResponseDTO.class);
            if (response.getStatusCode() == 0) {
                Log.w(LOG, "response status code is 0");
                webSocketListener.onMessage(response);
            } else {
                Log.e(LOG, "response status code is > 0, the server has found ERROR");
                webSocketListener.onError(response.getMessage());
            }
        } else {
            Log.e(LOG, "Content from server has failed, response content is null");
            webSocketListener.onError("Content from server has failed, response content is null");

        }
    } catch (Exception e) {
        Log.e(LOG, "parseData Failed", e);
            webSocketListener.onError("Failed to unpack server response");
        }
    }

    static final String LOG = WebSocketUtil.class.getSimpleName();

}
