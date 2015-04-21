package com.application.ncg.cityvendorlibrary.util;

import android.content.Context;

import com.application.ncg.cityvendorlibrary.dto.transfer.ResponseDTO;

/**
 * Created by Chris on 2015-03-07.
 */
public class ErrorUtil {
    public static boolean checkServerError(Context ctx, ResponseDTO response) {
        if (response.getStatusCode() > 0) {
           Util.showErrorToast(ctx, response.getMessage());
            return false;
        }
        return true;
    }

    public static void handleErrors(Context ctx, int errCode) {
        switch (errCode) {
            case Constants.ERROR_DATABASE:
               Util.showErrorToast(ctx, "Database error, please contact CityVendor support team");
                break;
            case Constants.ERROR_NETWORK_UNAVAILABLE:
                Util.showErrorToast(ctx, "Network unavailable, check signal and signal strength");
                break;
            case Constants.ERROR_ENCODING:
                Util.showErrorToast(ctx, "Error encoding request. contact CityVendor support team");
                break;
            case Constants.ERROR_SERVER_COMMS:
               Util.showErrorToast(ctx, "Error communiating with  the server, contact the support team");
                break;
            case Constants.ERROR_DUPLICATE:
                Util.showErrorToast(ctx, "you have tried inserting a duplicate entry in database, therefore request ignored");
        }
    }
}
