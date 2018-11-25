package com.example.tan.ex3_ringservice;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnStartRinging, btnStopRinging;
    private static MediaPlayer mediaPlayer;
    private RingerService mRingerService;
    Intent mServiceIntent;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = this;
        btnStartRinging = findViewById(R.id.btnStartRinging);
        btnStopRinging = findViewById(R.id.btnStopRinging);
        mRingerService = new RingerService(getContext());
        mServiceIntent = new Intent(getContext(), mRingerService.getClass());

        if (!isMyServiceRunning(mRingerService.getClass())) { startService(mServiceIntent); }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(MainActivity.this, MainActivity.class));
            }
        }, 10000);
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager =
                (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service :
                manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public Context getContext() {
        return context;
    }

    @Override
    protected void onDestroy() {
        stopService(mServiceIntent);
        super.onDestroy();
    }

    public void onStartRinging(View view) {
        mediaPlayer = MediaPlayer.create(MainActivity.this,
                Settings.System.DEFAULT_RINGTONE_URI );
        mediaPlayer.start();
    }

    public void onStopRinging(View view) {
        mediaPlayer.stop();
    }
}
