package com.dk.youkol.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.dk.youkol.roomdb.RepositoryData;
import com.dk.youkol.roomdb.RoomDataModel;
import com.dk.youkol.utils.Const;

import java.util.List;

public class VolumeChangeReceiver extends BroadcastReceiver {
    RepositoryData repositoryData;
    AudioManager audioManager;
    HeadsetReceiver headsetReceiver;

    @Override
    public void onReceive(Context context, Intent intent) {
        int newVolume = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
        int oldVolume = intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", 0);

        repositoryData = new RepositoryData(context);
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        boolean microphone_plugged_in = HeadsetReceiver.Microphone_Plugged_in;

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RoomDataModel> roomDataModelList = repositoryData.getAllList();
                for (int roomModelPosition = 0; roomModelPosition < roomDataModelList.size(); roomModelPosition++) {
                    RoomDataModel roomDataModel = roomDataModelList.get(roomModelPosition);
                    if (roomDataModel.getType().equals(Const.Driving)) {
                        String[] devices = roomDataModel.getDeviceAllow().split(",");

                        if (devices.length > 1) {
                            if (devices[2].equals("true")) {
                                if (!microphone_plugged_in) {
                                    headPhone();
                                }
                            } else if (devices[1].equals("true")) {
                                speaker();
                            }
                        }
                    } else {
                        audioManager.setMicrophoneMute(false);
                    }

                }

            }
        }).start();


       /* if (newVolume != oldVolume) {
            Toast.makeText(context ,"newVolume" +newVolume + " oldVolume" + oldVolume, Toast.LENGTH_SHORT).show();
//            System.out.println("In onReceive" + "newVolume" +newVolume + " oldVolume" + oldVolume );

        }*/

    }

    private void speaker() {

    }

    private void volumeLow(){
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
    }

    private void callVolumeLowandMute(){
        audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 0, AudioManager.ADJUST_MUTE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            audioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.STREAM_VOICE_CALL);
        } else {
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.STREAM_VOICE_CALL);
        }
    }

    private void systemVolumeLow(){
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0);
    }


    private void headPhone() {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
        audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 0, AudioManager.ADJUST_MUTE);
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            audioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.STREAM_VOICE_CALL);
        } else {
            audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.STREAM_VOICE_CALL);
        }
    }
}
