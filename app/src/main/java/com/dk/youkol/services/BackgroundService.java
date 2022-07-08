package com.dk.youkol.services;

import static android.media.AudioManager.USE_DEFAULT_STREAM_TYPE;

import static com.dk.youkol.receivers.HeadsetReceiver.Microphone_Plugged_in;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.dk.youkol.Activitys.MainActivity;
import com.dk.youkol.R;
import com.dk.youkol.roomdb.RepositoryData;
import com.dk.youkol.roomdb.RoomDataModel;
import com.dk.youkol.utils.Const;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class BackgroundService extends Service {
    private static BackgroundService backgroundService;
    String status;
    private final static int FOREGROUND_ID = 1000;
    private RepositoryData repositoryData;
    AudioManager audioManager;
    private boolean isSWEarphone, isSWBleConnected, isSWSpeaker;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        backgroundService = this;
        String id = "test_channel";
        Context context = this;
        createchannel(this);
        repositoryData = new RepositoryData(context);
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        int delay = 1000; // delay for 1 sec.
        int period = 1000; // repeat every 10 sec.
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                Log.e("TAG", "run: " + new Date());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        int i = 0;
                        List<RoomDataModel> roomDataModelList = repositoryData.getAllList();
                        for (int roomModelPosition = 0; roomModelPosition < roomDataModelList.size(); roomModelPosition++) {
                            RoomDataModel roomDataModel = roomDataModelList.get(roomModelPosition);
                            if (roomDataModel.getType().equals(Const.Driving)) {
                                String[] devices = roomDataModel.getDeviceAllow().split(",");
                                if (devices.length > 1) {
                                    if (devices[2].equals("true")) {
                                        i++;
                                        isSWEarphone = true;
                                    } else {
                                        isSWEarphone = false;
                                    }
                                    if (devices[1].equals("true")) {
                                        i++;
                                        isSWSpeaker = true;
                                    } else {
                                        isSWSpeaker = false;
                                    }
                                    if (devices[3].equals("true")) {
                                        i++;
                                        isSWBleConnected = true;
                                    } else {
                                        isSWBleConnected = false;
                                    }
                                    manage(i);

                                }
                            }
                        }
                    }
                }).start();
            }
        }, delay, period);

        startForeground(FOREGROUND_ID, getNotification(context, true));
        return super.onStartCommand(intent, flags, startId);
    }

    public void manage(int i) {
        if (i == 1) {
            if (isSWSpeaker) {
                if (Microphone_Plugged_in || audioManager.isBluetoothA2dpOn()) {
                    Log.e("TAG", "onReceive:isSWSpeaker   Microphone_Plugged_in,  BluetoothScoOn ");
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                    audioManager.startBluetoothSco();
                    audioManager.setBluetoothScoOn(true);
                } else {
                    Log.e("TAG", "onReceive HeadsetReceiver: Speaker Mute");
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                    audioManager.startBluetoothSco();
                    audioManager.setBluetoothScoOn(true);
                }
            } /*else {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                audioManager.startBluetoothSco();
                audioManager.setBluetoothScoOn(true);
            }*/

            if (isSWEarphone) {
                if (Microphone_Plugged_in) {
                    Log.e("TAG", "onReceive HeadsetReceiver: Earphone Mute");
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                    audioManager.startBluetoothSco();
                    audioManager.setBluetoothScoOn(true);
                } else {
                    Log.e("TAG", "onReceive HeadsetReceiver: Earphone UnMute 0");
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                    audioManager.startBluetoothSco();
                    audioManager.setBluetoothScoOn(true);
                }
            }

            if (isSWBleConnected) {
                if (audioManager.isBluetoothA2dpOn()) {
                    Log.e("TAG", "onReceive HeadsetReceiver: BleConnected Mute");
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                    audioManager.stopBluetoothSco();
                    audioManager.setBluetoothScoOn(false);
                } else {
                    Log.e("TAG", "onReceive HeadsetReceiver: BleConnected UnMute");
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                    audioManager.startBluetoothSco();
                    audioManager.setBluetoothScoOn(true);
                }
            } else {
//                audioManager.startBluetoothSco();
//                audioManager.setBluetoothScoOn(true);
            }
        } else if (i == 2) {
            if (isSWSpeaker && isSWEarphone) {
//                audioManager.startBluetoothSco();
//                audioManager.setBluetoothScoOn(true);

                if (Microphone_Plugged_in) {
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                } else {
                    if (audioManager.isBluetoothA2dpOn()) {
                        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                        audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                        audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                        audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                    } else {
                        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                        audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                        audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                        audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                    }
                }

            } 

            if (isSWSpeaker && isSWBleConnected) {
                if (audioManager.isBluetoothA2dpOn()) {
                    Log.e("TAG", "onReceive HeadsetReceiver: BleConnected Mute");
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                    audioManager.stopBluetoothSco();
                    audioManager.setBluetoothScoOn(false);
                } else if (!Microphone_Plugged_in) {

                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                    audioManager.stopBluetoothSco();
                    audioManager.setBluetoothScoOn(false);

                } else {
                    Log.e("TAG", "onReceive HeadsetReceiver: BleConnected UnMute");
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                    audioManager.stopBluetoothSco();
                    audioManager.setBluetoothScoOn(false);
                }
            } else {
//                audioManager.startBluetoothSco();
//                audioManager.setBluetoothScoOn(true);
            }

            /*else {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                audioManager.startBluetoothSco();
                audioManager.setBluetoothScoOn(true);
            }*/

            if (isSWEarphone && isSWBleConnected) {

                if (Microphone_Plugged_in || audioManager.isBluetoothA2dpOn()) {
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                    audioManager.stopBluetoothSco();
                    audioManager.setBluetoothScoOn(false);
                } else {
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                    audioManager.startBluetoothSco();
                    audioManager.setBluetoothScoOn(true);
                }

            } else {
//                audioManager.startBluetoothSco();
//                audioManager.setBluetoothScoOn(true);
                /*else {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                audioManager.startBluetoothSco();
                audioManager.setBluetoothScoOn(true);
            }*/
            }
        } else if (i >= 3) {
            if (isSWSpeaker && isSWEarphone && isSWBleConnected) {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
            } else {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
            }
        }
    }

        private void createchannel (Context context){
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

        public Notification getNotification (Context context,boolean isConnected){

            String status = "Disconnected";
            if (isConnected) {
                status = "Connected";
            }
            Intent viewIntent = new Intent(context, MainActivity.class);
            viewIntent.putExtra("NotiID", "Notification ID is " + 11);
            PendingIntent viewPendingIntent = PendingIntent.getActivity(context, 0, viewIntent, PendingIntent.FLAG_IMMUTABLE);

            return new NotificationCompat.Builder(context, "test_channel")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentTitle(context.getString(R.string.app_name))
                    .setContentText("Running...")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(viewPendingIntent)
                    .build();
        }

        public static BackgroundService getInstance () {
            return backgroundService;
        }
    }
