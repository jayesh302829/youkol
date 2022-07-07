package com.dk.youkol.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.dk.youkol.Activitys.MainActivity;
import com.dk.youkol.R;

import java.util.Random;

public class BackgroundService extends Service {
    private static BackgroundService backgroundService;
    String status;
    private final static int FOREGROUND_ID=1000;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        backgroundService=this;
        String id = "test_channel";
        Context context=this;
        createchannel(this);
        startForeground(FOREGROUND_ID,getNotification(context, true));
        return super.onStartCommand(intent, flags, startId);
    }
    private void createchannel(Context context) {
        String id = "test_channel";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel mChannel = new NotificationChannel(id,
                    context.getString(R.string.app_name),  //name of the channel
                    NotificationManager.IMPORTANCE_DEFAULT);   //importance level
            mChannel.setDescription(context.getString(R.string.app_name));
            mChannel.enableLights(true);
            mChannel.setLightColor(Color.RED);
            mChannel.setShowBadge(true);
            nm.createNotificationChannel(mChannel);

        }
    }

    public Notification getNotification(Context context, boolean isConnected){

        String status="Disconnected";
        if (isConnected){
            status="Connected";
        }
        Intent viewIntent = new Intent(context, MainActivity.class);
        viewIntent.putExtra("NotiID", "Notification ID is " + 11);
        PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent, PendingIntent.FLAG_IMMUTABLE);

        return new NotificationCompat.Builder(context, "test_channel")
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText("Running")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(viewPendingIntent)
                .build();
    }
    public static BackgroundService getInstance(){
        return backgroundService;
    }
}
