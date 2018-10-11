package com.twilio.video.quickstart.fcm;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.twilio.video.quickstart.R;
import com.twilio.video.quickstart.activity.InComeActivity;
import com.twilio.video.quickstart.activity.VideoActivity;

import java.util.Map;
import java.util.Objects;

public class FireMessagingService extends FirebaseMessagingService {
    private static final String TAG = FireMessagingService.class.getSimpleName();

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.e(TAG, "From: " + remoteMessage.getFrom());
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            String token = data.get("token");
            String room = data.get("room");
            final int notificationId = (int) System.currentTimeMillis();
            showNotification("title",token,room, notificationId);
            openVideoCallScreen(token,room, notificationId);

        }
    }


    private void showNotification(String title, String token, String roomName, int notificationId) {
        Intent intent = new Intent(this, InComeActivity.class);
        intent.setAction(InComeActivity.ACTION_VIDEO_NOTIFICATION);
        intent.putExtra(InComeActivity.VIDEO_NOTIFICATION_TITLE, title);
        intent.putExtra(InComeActivity.VIDEO_NOTIFICATION_ROOM_NAME, roomName);
        intent.putExtra(InComeActivity.VIDEO_NOTIFICATION_TOKEN, token);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        showNotification(getApplicationContext(), "title","name", pendingIntent, notificationId);
    }

    private void showNotification(Context context, String title,String msg, PendingIntent intent, int notificationId) {
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.nouslogic.app.medid.channel_01";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                    "My Notifications", NotificationManager.IMPORTANCE_HIGH);

            // Configure the notification channel.
            notificationChannel.setDescription("Channel description");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{0, 1000});
            Objects.requireNonNull(mNotificationManager).createNotificationChannel(notificationChannel);
        }

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .setLights(0xff00ff00, 1000, 1000)
                .setContentTitle(title)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(msg))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentText(msg);

        if (intent != null) {
            mBuilder.setContentIntent(intent);
        }

        if (Build.VERSION.SDK_INT >= 21){
            mBuilder.setVibrate(new long[0]);
        }
        mBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        StaticWakeLock.lockOn(getApplicationContext());
        mNotificationManager.notify(notificationId, mBuilder.build());
    }

    private void openVideoCallScreen(String token, String roomName, int notificationId) {
        Intent intent = new Intent(this, VideoActivity.class);
        intent.setAction(InComeActivity.ACTION_VIDEO_NOTIFICATION);
        intent.putExtra(InComeActivity.VIDEO_NOTIFICATION_TOKEN, token);
        intent.putExtra(InComeActivity.VIDEO_NOTIFICATION_ID, notificationId);
        intent.putExtra(InComeActivity.VIDEO_NOTIFICATION_ROOM_NAME, roomName);
        getApplicationContext().startActivity(intent);
    }
}
