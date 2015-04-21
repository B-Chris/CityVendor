package com.application.ncg.cityvendorlibrary.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListPopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.application.ncg.cityvendorlibrary.R;
import com.application.ncg.cityvendorlibrary.adapter.PopupListAdapter;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

/**
 * Created by Chris on 2015-03-07.
 */
public class Util {

    static int count;

    public interface UtilPopupListener {
        public void onItemSelected(int index);
    }

    public interface UtilAnimationListener {
        public void onAnimationEnd();
    }
    public static final double getElapsed(long start, long end) {
        BigDecimal bd = new BigDecimal(end - start).divide(new BigDecimal(1000));
        return bd.doubleValue();
    }

    public static void flashOnce(View view, long duration, final UtilAnimationListener listener) {

        ObjectAnimator an = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        an.setRepeatMode(ObjectAnimator.REVERSE);
        an.setDuration(duration);
        an.setInterpolator(new AccelerateDecelerateInterpolator());
        an.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null)
                    listener.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        an.start();
    }

    public static void flashSeveralTimes(final View view, final long duration,
                                         final int max, final UtilAnimationListener listener) {

        final ObjectAnimator an = ObjectAnimator.ofFloat(view, "alpha", 0, 1);
        an.setRepeatMode(ObjectAnimator.REVERSE);
        an.setDuration(duration);
        an.setInterpolator(new AccelerateDecelerateInterpolator());
        an.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                count++;
                if (count > max) {
                    count = 0;
                    an.cancel();
                    if (listener != null)
                        listener.onAnimationEnd();
                    return;
                }
                flashSeveralTimes(view, duration, max, listener);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public static void collapse(final View view, int duration, final UtilAnimationListener listener) {
        int finalHeight = view.getHeight();

        ValueAnimator mAnimator = slideAnimator(view, finalHeight, 0);
        mAnimator.setDuration(duration);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        mAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
                if (listener != null)
                    listener.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        mAnimator.start();
    }

    private static ValueAnimator slideAnimator(final View view, int start, int end) {

        ValueAnimator animator = ValueAnimator.ofInt(start, end);

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                //update Height
                int value = (Integer) valueAnimator.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = value;
                view.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }

    public static void showErrorToast(Context ctx, String caption) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.toast_vendor_generic, null);
        TextView txt = (TextView) view.findViewById(R.id.VENDTOAST_text);
        TextView ind = (TextView) view.findViewById(R.id.VENDTOAST_indicator);
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

    public static void showToast(Context ctx, String caption) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.toast_vendor_generic, null);
        TextView text = (TextView) view.findViewById(R.id.VENDTOAST_text);
     //   Statics.setRobotoFontLight(ctx, text);
        TextView ind = (TextView) view.findViewById(R.id.VENDTOAST_indicator);
        ind.setText("M");
        ind.setBackground(ctx.getResources().getDrawable(R.drawable.xblue_oval));
        text.setTextColor(ctx.getResources().getColor(R.color.blue));
        text.setText(caption);

        Toast customToast = new Toast(ctx);

        customToast.setView(view);
        customToast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL, 0, 0);
        customToast.setDuration(Toast.LENGTH_SHORT);
        customToast.show();
    }

    public static void showPopupBasicWithHeroImage(Context ctx, Activity act,
                                                    List<String> list,
                                                    View anchorView, String caption,
                                                    final UtilPopupListener listener) {
    final ListPopupWindow pop = new ListPopupWindow(act);
    LayoutInflater inf = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View v = inf.inflate(R.layout.hero_image_popup, null);
    TextView txt = (TextView) v.findViewById(R.id.HERO_Caption);
        if (caption != null) {
            txt.setText(caption);
        } else {
            txt.setVisibility(View.GONE);
        }
        ImageView img = (ImageView) v.findViewById(R.id.HERO_Image);
        img.setImageDrawable(getRandomHeroImage(ctx));

        pop.setPromptView(v);
        pop.setPromptPosition(ListPopupWindow.POSITION_PROMPT_ABOVE);
        pop.setAdapter(new PopupListAdapter(ctx, R.layout.simple_spinner_item, list, false));
        pop.setAnchorView(anchorView);
        pop.setHorizontalOffset(getPopupHorizontalOffset(act));
        pop.setModal(true);
        pop.setWidth(getPopupWidth(act));
        pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pop.dismiss();
                if (listener != null) {
                    listener.onItemSelected(position);
                }
            }
        });
        pop.show();
    }

    public static int getPopupWidth(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Double d = Double.valueOf(" " + width);
        Double e = d / 1.5;
        Log.w(LOG, "popup width: " + e.intValue());
        return e.intValue();

    }

    public static int getPopupHorizontalOffset(Activity activity) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Double d = Double.valueOf(" " + width);
        Double e = d / 15;
        Log.w(LOG, "horizontalOfFSet: " + e.intValue());
        return e.intValue();
    }

    public static Drawable getRandomHeroImage(Context ctx) {
        random = new Random(System.currentTimeMillis());
        int index = random.nextInt(2);
        switch (index) {
            case 0:
                return ctx.getResources().getDrawable(
                        R.drawable.vendor_banner_1);
            case 1:
                return ctx.getResources().getDrawable(
                        R.drawable.vendor_banner_2);
        }
        return ctx.getResources().getDrawable(
                R.drawable.vendor_banner_1);
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



    static Random random = new Random(System.currentTimeMillis());
    private static String LOG = Util.class.getSimpleName();
}
