package com.dk.youkol.receivers;

import android.app.AlarmManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioDeviceCallback;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.dk.youkol.Activitys.MainActivity;
import com.dk.youkol.roomdb.RepositoryData;
import com.dk.youkol.roomdb.RoomDataModel;
import com.dk.youkol.utils.AudioSystem;
import com.dk.youkol.utils.Const;
import com.dk.youkol.utils.Util;

import java.io.IOException;
import java.util.List;

public class VolumeChangeReceiver extends BroadcastReceiver {
    RepositoryData repositoryData;
    AudioManager audioManager;
    HeadsetReceiver headsetReceiver;
    private int newVolume, oldVolume;
    private boolean microphone_plugged_in;

    @Override
    public void onReceive(Context context, Intent intent) {
        newVolume = intent.getIntExtra("android.media.EXTRA_VOLUME_STREAM_VALUE", 0);
        oldVolume = intent.getIntExtra("android.media.EXTRA_PREV_VOLUME_STREAM_VALUE", 0);

        getDataAndSetVolume(context);

       /* if (newVolume != oldVolume) {
            Toast.makeText(context ,"newVolume" +newVolume + " oldVolume" + oldVolume, Toast.LENGTH_SHORT).show();
//            System.out.println("In onReceive" + "newVolume" +newVolume + " oldVolume" + oldVolume );

        }*/

    }

    private void getDataAndSetVolume(Context context) {

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        microphone_plugged_in = HeadsetReceiver.Microphone_Plugged_in;
        repositoryData = new RepositoryData(context);
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
                                headPhone();
                            }
                            if (devices[1].equals("true")) {
                                speaker();
                            }
                            if (devices[3].equals("true") || devices[0].equals("true") || devices[4].equals("true") || devices[5].equals("true")) {
                                bluetooth();
                            }
                            if (devices[1].equals("true") && devices[3].equals("true")) {
                                speakerAndBluetooth();
                            }
                            if (devices[2].equals("true") && devices[1].equals("true")) {
                                headPhoneAndSpeaker();
                            }
                            if (devices[2].equals("true") && devices[3].equals("true")){
                                headPhoneAndBle();
                            }
                        }
                    } else {
                        audioManager.setMicrophoneMute(false);
                    }

                }

            }
        }).start();

    }

    private void speakerAndBluetooth() {
        if (audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn() || !microphone_plugged_in ) {
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, false);
        } else {
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, true);
        }
    }

    private void headPhoneAndBle() {
        if (audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn() || microphone_plugged_in ) {
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, false);
        } else {
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, true);
        }
    }

    private void headPhoneAndSpeaker() {
        if (audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn()) {
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, true);
        } else {
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, false);
        }
    }

    private void bluetooth() {
//        if (MainActivity.isBleConnected){
            if (!audioManager.isBluetoothScoOn() || !audioManager.isBluetoothA2dpOn()) {
                audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, true);
            }else {
                audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, false);
            }
//        }else {
//            Log.e("TAG", "bluetooth: ELSE " );
//        }

    }

    private void volumeZero() {
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, 0);
    }

    private void callVolumeLowAndMute() {
        audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 0, AudioManager.ADJUST_MUTE);
        audioManager.adjustVolume(0, AudioManager.STREAM_VOICE_CALL);
    }

    private void callVolumeHighAndUnMute() {
//        audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 30, AudioManager.ADJUST_MUTE);
//        audioManager.adjustVolume(40, AudioManager.STREAM_VOICE_CALL);
    }

    private void systemVolumeLow() {
        audioManager.setStreamVolume(AudioManager.STREAM_SYSTEM, 0, 0);
    }


    /*private void headPhone() {
        if (!microphone_plugged_in) {
            volumeZero();
            callVolumeLowAndMute();
            systemVolumeLow();
        }
    }*/

    private void headPhone() {
        if (!microphone_plugged_in) {
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, true);
        }else {
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, false);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void speaker() {
//        Log.e("TAG", "isBluetoothScoOn ==>  "+ audioManager.isBluetoothScoOn());
//        Log.e("TAG", "isBluetoothA2dpOn ==>  "+ audioManager.isBluetoothA2dpOn());
//        Log.e("TAG", "speaker: isBluetoothHeadsetConnected ==> "+ Util.isBluetoothHeadsetConnected());
//        Log.e("TAG", "speaker: isWiredHeadsetConnected ==> "+ Util.isWiredHeadsetConnected(context));

        if (microphone_plugged_in) {
//            audioManager.setStreamVolume(AudioManager.STREAM_DTMF, 0, 0);
//            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.ADJUST_MUTE);
//            audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 0, AudioManager.ADJUST_MUTE);
//            audioManager.setStreamVolume(AudioManager.STREAM_DTMF, 0, AudioManager.ADJUST_MUTE);
//            audioManager.setMicrophoneMute(true);
//            audioManager.setStreamMute(AudioManager.STREAM_DTMF,true);
//            audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL,true);
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, true);
//            audioManager.adjustVolume(0, AudioManager.STREAM_VOICE_CALL);
        } else {
//            audioManager.setStreamMute(AudioManager.STREAM_DTMF,false);
//            audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL,false);
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, false);
            audioManager.setMicrophoneMute(false);
            callVolumeHighAndUnMute();
        }

        /*audioManager.setBluetoothA2dpOn(false);
        audioManager.setBluetoothScoOn(false);
        audioManager.setMicrophoneMute(true);
        callVolumeLowAndMute();*/
      /*  audioManager.setSpeakerphoneOn(true);
        if (audioManager.isSpeakerphoneOn()) {
            Log.e("LOG:", "AUDIO_ROUTE_SPEAKERPHONE");
        } else if (audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn()) {
            Log.e("LOG:", "AUDIO_ROUTE_HEADSETBLUETOOTH");
        } else if (audioManager.isWiredHeadsetOn()) {
            Log.e("LOG:", "AUDIO_ROUTE_HEADSET");
        } else {
            Log.e("LOG:", "AUDIO_ROUTE_EARPIECE");
            // Call setEnableSpeakerphone here to route the audio output to the speaker or earpiece
        }
        audioSystem.setSpeakerOn(true);
        audioManager.setRouting(AudioManager.MODE_CURRENT,  AudioManager.ROUTE_EARPIECE, AudioManager.ROUTE_SPEAKER);

        Log.e("TAG", String.valueOf(audioManager.getMode()));
        if (microphone_plugged_in) {
            volumeZero();
            callVolumeLowAndMute();
            systemVolumeLow();
            audioManager.setSpeakerphoneOn(true);
            audioSystem.setSpeakerOn(false);
        }else {
            audioSystem.setSpeakerOn(true);
            audioManager.setMicrophoneMute(false);
        }*/
    }
}
