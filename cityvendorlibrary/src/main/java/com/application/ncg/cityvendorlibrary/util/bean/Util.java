package com.application.ncg.cityvendorlibrary.util.bean;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ncg.cityvendorlibrary.R;

import java.io.File;
import java.math.BigDecimal;

/**
 * Created by Chris on 2015-03-07.
 */
public class Util {

    public static final double getElapsed(long start, long end) {
        BigDecimal bd = new BigDecimal(end - start).divide(new BigDecimal(1000));
        return bd.doubleValue();
    }

    public static void showErrorToast(Context ctx, String caption) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_vendor_generic, null);
        TextView txt = (TextView) view.findViewById(R.id.VENDORTOAST_text);
        TextView ind = (TextView) view.findViewById(R.id.VENDORTOAST_indicator);
        ind.setText("A");
        Statics.setRobotoFontLight(ctx,txt);
        ind.setBackground(ctx.getResources().getDrawable(R.drawable.xblack_oval));
        txt.setTextColor(ctx.getResources().getColor(R.color.white));
        txt.setText(caption);
        Toast customtoast = new Toast(ctx);

        customtoast.setView(view);
        customtoast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL,
                0,0);
        customtoast.setDuration(Toast.LENGTH_LONG);
        customtoast.show();
    }




    static public boolean hasStorage(boolean requireWriteAccess) {
        String state = Environment.getExternalStorageState();
        Log.w("Util", "--------- disk storage state is: " + state);

        if (Environment.MEDIA_MOUNTED.equals(state)) {
            if (requireWriteAccess) {
                boolean writable = checkFsWritable();
                Log.i("Util", "************ storage is writable: " + writable);
                return writable;
            } else {
                return true;
            }
        } else if (!requireWriteAccess && Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }
    private static boolean checkFsWritable() {
        /**
         * 1. creating a short - term file, checking if mass will be writeable
         * 2. do not put short term - file in root directory, there could be a limit on the numbber of files
         */
        String directoryName = Environment.getExternalStorageDirectory().toString() + "/DCIM";
        File directory = new File(directoryName);
        if (!directory.isDirectory()) {
            if (!directory.mkdirs()){
                return false;
            }
        }
        return directory.canWrite();
    }

}
