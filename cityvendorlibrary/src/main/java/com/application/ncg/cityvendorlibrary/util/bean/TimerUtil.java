package com.application.ncg.cityvendorlibrary.util.bean;

/**
 * Created by Chris on 2015-03-07.
 */
public class TimerUtil {
/*
    public interface TimerListener {
        public void onSessionDisconnected();
    }
    static TimerListener listener;
    static Timer timer;
    static  final String LOG = TimerUtil.class.getSimpleName();
    static final long TEN_SECONDS = 10 * 1000;
    public static void startTimer(TimerListener timerListener) {
        Log.d(LOG, "Starting Websocket session timer");
        listener = timerListener;
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Log.e(LOG, "preparing to disconnect websocket session");
                WebSocketUtil.disconnectSession();
                listener.onSessionDisconnected();
            }
        }, TEN_SECONDS);
    }
    public static void killTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
            Log.w(LOG, "Websocket Session Timer has now been terminated");
        }
    }
*/
}
