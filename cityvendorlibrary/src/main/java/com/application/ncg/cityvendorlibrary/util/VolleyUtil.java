package com.application.ncg.cityvendorlibrary.util;

import android.content.Context;
import android.util.Log;

import com.android.volley.VolleyError;
import com.application.ncg.cityvendorlibrary.R;
import com.application.ncg.cityvendorlibrary.dto.transfer.RequestDTO;
import com.application.ncg.cityvendorlibrary.dto.transfer.ResponseDTO;
import com.application.ncg.cityvendorlibrary.toolbox.BaseVolley;

/**
 * Created by Chris on 2015-03-07.
 */
public class VolleyUtil {

    static final String LOG = VolleyUtil.class.getSimpleName();

    public static void sendRequest (final Context ctx, RequestDTO request,
                                    final WebSocketUtil.WebSocketListener webSocketListener) {
        Log.e(LOG, "sending http request");
        String suffix = Statics.GATEWAY_SERVLET + "?JSON=";
        BaseVolley.getRemoteData(suffix, request, ctx,
                new BaseVolley.BohaVolleyListener() {
                    @Override
                    public void onResponseReceived(ResponseDTO response) {
                        if (response.getStatusCode() > 0) {
                            webSocketListener.onError(response.getMessage());
                        } else {
                            webSocketListener.onMessage(response);
                        }
                    }

                    @Override
                    public void onVolleyError(VolleyError error) {
                        webSocketListener.onError(ctx.getString(R.string.network_error));
                    }
                } );
    }

}
