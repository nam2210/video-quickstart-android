package com.twilio.video.quickstart.activity;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.twilio.video.quickstart.R;
import com.twilio.video.quickstart.SoundPoolManager;

import java.util.Timer;

public class InComeActivity extends AppCompatActivity {
    private static final String TAG = InComeActivity.class.getSimpleName();



    private SoundPoolManager soundPoolManager;
    private NotificationManager notificationManager;
    /*
     * Intent keys used to provide information about a video notification
     */
    public static final String ACTION_VIDEO_NOTIFICATION = "VIDEO_NOTIFICATION";
    public static final String VIDEO_NOTIFICATION_ROOM_NAME = "VIDEO_NOTIFICATION_ROOM_NAME";
    public static final String VIDEO_NOTIFICATION_TITLE = "VIDEO_NOTIFICATION_TITLE";
    public static final String VIDEO_NOTIFICATION_TOKEN = "VIDEO_NOTIFICATION_TOKEN";
    public static final String VIDEO_NOTIFICATION_ID = "VIDEO_NOTIFICATION_ID";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_come);
        Log.e(TAG,"on start");
        soundPoolManager = SoundPoolManager.getInstance(this);
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        soundPoolManager.playRinging();
        Button btnCancel = findViewById(R.id.btn_cancel);
        Button btnCall = findViewById(R.id.btn_call);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPoolManager.stopRinging();

            }
        });
        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundPoolManager.stopRinging();
            }
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.e(TAG,">>>>>>> onNewIntent");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e(TAG,"on Destroy");
    }

    @Override //to turn on screen while its lock when call is arriving
    public void onAttachedToWindow() {
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
    }
}
