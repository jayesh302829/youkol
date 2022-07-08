package com.dk.youkol.receivers;

import static android.media.AudioManager.USE_DEFAULT_STREAM_TYPE;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.dk.youkol.Activitys.MainActivity;
import com.dk.youkol.roomdb.RepositoryData;
import com.dk.youkol.roomdb.RoomDataModel;
import com.dk.youkol.utils.Const;

import java.util.List;

public class HeadsetReceiver extends BroadcastReceiver {
    public static boolean Microphone_Plugged_in = false;
    AudioManager audioManager;
    private RepositoryData repositoryData;
    private boolean firsttime = false;

    int condition = 0;
    private boolean isSWEarphone,isSWBleConnected,isSWSpeaker;

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        firsttime = true;
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        repositoryData = new RepositoryData(context);

        int iii = 2;
        if (Intent.ACTION_HEADSET_PLUG.equals(action)) {
            iii = intent.getIntExtra("state", -1);
            if (iii == 0) {
                Microphone_Plugged_in = false;
//                manage(i);

                /*new Thread(new Runnable() {
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
                                    }
                                    if (devices[1].equals("true")) {
                                        i++;
                                        isSWSpeaker = true;
                                    }
                                    if (devices[3].equals("true")) {
                                        i++;
                                        isSWBleConnected = true;
                                    }
                                    manage(i);

                                }
                            }
                        }
                    }
                }).start();*/

            } else if (iii == 1) {
                Microphone_Plugged_in = true;
//                manage(i);
                /*new Thread(new Runnable() {
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
                                    }
                                    if (devices[1].equals("true")) {
                                        i++;
                                        isSWSpeaker = true;
                                    }
                                    if (devices[3].equals("true") || devices[0].equals("true") || devices[4].equals("true") || devices[5].equals("true")) {
                                        i++;
                                        isSWBleConnected = true;
                                    }
                                    manage(i);

                                }
                            }
                        }
                    }
                }).start();*/
                Log.e("TAG", "onReceive: microphone plugged in");
            }

        }
    }

    public void manage(int i){
        if (i == 1){
            if (isSWSpeaker){
                if (Microphone_Plugged_in || audioManager.isBluetoothA2dpOn()){
                    Log.e("TAG", "onReceive:isSWSpeaker   Microphone_Plugged_in,  BluetoothScoOn " );
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                    audioManager.startBluetoothSco();
                    audioManager.setBluetoothScoOn(true);
                }else {
                    Log.e("TAG", "onReceive HeadsetReceiver: Speaker Mute" );
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                    audioManager.startBluetoothSco();
                    audioManager.setBluetoothScoOn(true);
                }
            }else {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                audioManager.startBluetoothSco();
                audioManager.setBluetoothScoOn(true);
            }

            if (isSWEarphone){
                if (Microphone_Plugged_in){
                    Log.e("TAG", "onReceive HeadsetReceiver: Earphone Mute" );
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                    audioManager.startBluetoothSco();
                    audioManager.setBluetoothScoOn(true);
                }else {
                    Log.e("TAG", "onReceive HeadsetReceiver: Earphone UnMute 0" );
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                    audioManager.startBluetoothSco();
                    audioManager.setBluetoothScoOn(true);
                }
            }

            if (isSWBleConnected){
                if (audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn()){
                    Log.e("TAG", "onReceive HeadsetReceiver: BleConnected Mute" );
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                    audioManager.stopBluetoothSco();
                    audioManager.setBluetoothScoOn(false);
                }else {
                    Log.e("TAG", "onReceive HeadsetReceiver: BleConnected UnMute" );
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                    audioManager.startBluetoothSco();
                    audioManager.setBluetoothScoOn(true);
                }
            }
        }else if (i == 2){
            if (isSWSpeaker && isSWEarphone){
                audioManager.startBluetoothSco();
                audioManager.setBluetoothScoOn(true);
                if (audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn() ){
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);

                    if (Microphone_Plugged_in){
                        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                        audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                        audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                        audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                    }else {
                        audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                        audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                        audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                        audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                    }

                }else {
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                }
               /* audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                audioManager.startBluetoothSco();
                audioManager.setBluetoothScoOn(true);
                audioManager.setSpeakerphoneOn(false);*/
            }else {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                audioManager.startBluetoothSco();
                audioManager.setBluetoothScoOn(true);
            }

            if (isSWSpeaker && isSWBleConnected){
                if (audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn()){
                    Log.e("TAG", "onReceive HeadsetReceiver: BleConnected Mute" );
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

                } else{
                    Log.e("TAG", "onReceive HeadsetReceiver: BleConnected UnMute" );
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                    audioManager.stopBluetoothSco();
                    audioManager.setBluetoothScoOn(false);
                }
            }/*else {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                audioManager.startBluetoothSco();
                audioManager.setBluetoothScoOn(true);
            }*/

            if (isSWEarphone && isSWBleConnected){

                if (Microphone_Plugged_in || audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn()){
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                    audioManager.stopBluetoothSco();
                    audioManager.setBluetoothScoOn(false);
                }else {
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                    audioManager.startBluetoothSco();
                    audioManager.setBluetoothScoOn(true);
                }

            }/*else {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                audioManager.startBluetoothSco();
                audioManager.setBluetoothScoOn(true);
            }*/

        }else  if (i >= 3){
            if (isSWSpeaker && isSWEarphone && isSWBleConnected){
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
            }else {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
            }
        }
    }

    private void getDataAndSetVolume(Context context) {

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        repositoryData = new RepositoryData(context);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RoomDataModel> roomDataModelList = repositoryData.getAllList();
                for (int roomModelPosition = 0; roomModelPosition < roomDataModelList.size(); roomModelPosition++) {
                    RoomDataModel roomDataModel = roomDataModelList.get(roomModelPosition);
                    if (roomDataModel.getType().equals(Const.Driving)) {
                        String[] devices = roomDataModel.getDeviceAllow().split(",");

                        for (String device : devices) {
                            if (device.equals("true")) {
                                condition++;
                            }
                        }

                        if (condition == 1) {
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
                            }
                        } else if (condition >= 2) {
                            if (devices.length > 1) {
                                if (devices[1].equals("true") && devices[3].equals("true")) {
                                    speakerAndBluetooth();
                                }
                                if (devices[2].equals("true") && devices[1].equals("true")) {
                                    headPhoneAndSpeaker();
                                }
                                if (devices[2].equals("true") && devices[3].equals("true")) {
                                    headPhoneAndBle();
                                }
                            }
                        }

                        /*if (devices.length > 1) {
                            if (devices[2].equals("true")) {
//                                headPhone();
                            }
                            if (devices[1].equals("true")) {
//                                speaker();
                            }
                            if (devices[3].equals("true") || devices[0].equals("true") || devices[4].equals("true") || devices[5].equals("true")) {
                                bluetooth();
                            }
                            if (devices[1].equals("true") && devices[3].equals("true")) {
//                                speakerAndBluetooth();
                            }
                            if (devices[2].equals("true") && devices[1].equals("true")) {
//                                headPhoneAndSpeaker();
                            }
                            if (devices[2].equals("true") && devices[3].equals("true")) {
//                                headPhoneAndBle();
                            }
                        }*/
                    } else {
                        audioManager.setMicrophoneMute(false);
                    }

                }

            }
        }).start();

    }

    private void speakerAndBluetooth() {
        if (audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn() || !Microphone_Plugged_in) {
            Log.e("TAG", "speakerAndBluetooth: unMute ");
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, false);
        } else {
            Log.e("TAG", "speakerAndBluetooth: Mute ");
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, true);
        }
    }

    private void headPhoneAndBle() {
        if (audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn() || Microphone_Plugged_in) {
            Log.e("TAG", "headPhoneAndBle: UnMute");
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, false);
        } else {
            Log.e("TAG", "headPhoneAndBle: Mute");
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, true);
        }
    }

    private void headPhoneAndSpeaker() {
        if (audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn()) {
            Log.e("TAG", "headPhoneAndSpeaker: Unmute ");
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, true);
        } else {
            Log.e("TAG", "headPhoneAndSpeaker: Mute ");
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, false);
        }
    }

    private void bluetooth() {

        if (audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn()) {
            if (firsttime) {
                // 1. case - bluetooth device
                if (!Microphone_Plugged_in) {
                    audioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
                    audioManager.startBluetoothSco();
                    audioManager.setBluetoothScoOn(true);
                    firsttime = false;
                } else {
                    audioManager.setMode(AudioManager.MODE_NORMAL);
                    audioManager.setStreamMute(AudioManager.STREAM_SYSTEM, false);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                }
            }
        } /*else {
            // 2. case - wired device
            audioManager.setMode(AudioManager.MODE_NORMAL);
            audioManager.stopBluetoothSco();
            audioManager.setBluetoothScoOn(false);
            audioManager.setSpeakerphoneOn(false);
        }*/

//        if (MainActivity.isBleConnected){
       /* if (audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn()) {
            audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
            audioManager.setWiredHeadsetOn(false);
            audioManager.setSpeakerphoneOn(false);
        }else {
            audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
        }*/
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


    private void headPhone() {

        if (!Microphone_Plugged_in) {
            /*volumeZero();
            callVolumeLowAndMute();
            systemVolumeLow();*/
            Log.e("TAG", "headPhone: Mute ");
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, true);
        } else {
            Log.e("TAG", "headPhone: unMute ");
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, false);
        }
    }

    private void speaker() {
//        Log.e("TAG", "isBluetoothScoOn ==>  "+ audioManager.isBluetoothScoOn());
//        Log.e("TAG", "isBluetoothA2dpOn ==>  "+ audioManager.isBluetoothA2dpOn());
//        Log.e("TAG", "speaker: isBluetoothHeadsetConnected ==> "+ Util.isBluetoothHeadsetConnected());
//        Log.e("TAG", "speaker: isWiredHeadsetConnected ==> "+ Util.isWiredHeadsetConnected(context));

        if (Microphone_Plugged_in) {
//            audioManager.setStreamVolume(AudioManager.STREAM_DTMF, 0, 0);
//            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 0, AudioManager.ADJUST_MUTE);
//            audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 0, AudioManager.ADJUST_MUTE);
//            audioManager.setStreamVolume(AudioManager.STREAM_DTMF, 0, AudioManager.ADJUST_MUTE);
//            audioManager.setMicrophoneMute(true);
//            audioManager.setStreamMute(AudioManager.STREAM_DTMF,true);
//            audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL,true);
            audioManager.setStreamMute(AudioManager.USE_DEFAULT_STREAM_TYPE, true);
//            audioManager.adjustVolume(0, AudioManager.STREAM_VOICE_CALL);
            Log.e("TAG", "speaker: Unmute ");
        } else {
            Log.e("TAG", "speaker: Mute ");
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
