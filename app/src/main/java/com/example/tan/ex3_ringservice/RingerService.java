package com.example.tan.ex3_ringservice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import java.util.Timer;
import java.util.TimerTask;

public class RingerService extends Service {

    private Timer timer;
    private TimerTask timerTask;

    //Added the empty constructor because otherwise it will get a FATAL EXCEPTION.
    public RingerService(){ }

    public RingerService(Context context) { super(); }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        startTimer();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(this, RingerService.class);
        sendBroadcast(intent);
        stopTimerTask();
    }

    public void startTimer() {
        timer = new Timer();
        startTimerTask();
        timer.schedule(timerTask, 1000, 1000);
    }

    public void startTimerTask() {
        timerTask = new TimerTask() {
            public void run() { } };
    }

    public void stopTimerTask() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}