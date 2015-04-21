package com.application.ncg.cityvendorlibrary.util;

import android.content.Context;
import android.os.Vibrator;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.application.ncg.cityvendorlibrary.R;

/**
 * Created by Chris on 2015-04-13.
 */
public class ToastUtil {

    public static void toast (Context ctx, String message) {
        Vibrator vb = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
        vb.vibrate(30);
        LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View main = inf.inflate(R.layout.toast, null);

        TextView msg = (TextView) main.findViewById(R.id.TOAST_msg);
        msg.setText(message);

        CustomToast toast = new CustomToast(ctx, 3);
        toast.setGravity(Gravity.HORIZONTAL_GRAVITY_MASK, 50, 50);
        toast.setView(main);
        toast.show();
    }
}
