package com.dk.youkol.utils;

import static android.media.AudioManager.USE_DEFAULT_STREAM_TYPE;
import static com.dk.youkol.receivers.HeadsetReceiver.Microphone_Plugged_in;

import android.content.Context;
import android.database.ContentObserver;
import android.media.AudioManager;
import android.os.Handler;
import android.util.Log;

import com.dk.youkol.Activitys.MainActivity;
import com.dk.youkol.roomdb.RepositoryData;
import com.dk.youkol.roomdb.RoomDataModel;

import java.util.List;


public class SettingsContentObserver extends ContentObserver {
    private final RepositoryData repositoryData;
    int previousVolume;
    Context context;
    AudioManager audioManager;
    boolean isSWSpeaker,isSWEarphone,isSWBleConnected;

    public SettingsContentObserver(Context c, Handler handler) {
        super(handler);
        context = c;

        Log.e("TAG", "SettingsContentObserver: ");
        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        previousVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        repositoryData = new RepositoryData(context);
    }

    @Override
    public boolean deliverSelfNotifications() {
        Log.e("TAG", "deliverSelfNotifications: ");
        return super.deliverSelfNotifications();
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        AudioManager audio = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int currentVolume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);

        int delta = previousVolume - currentVolume;

       /* new Thread(new Runnable() {
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
    }

    public void manage(int i) {
        if (i == 1) {
            if (isSWSpeaker) {
                Log.e("TAG", "manage: "+Microphone_Plugged_in );
                Log.e("TAG", "manage: "+audioManager.isBluetoothScoOn() );
                Log.e("TAG", "manage: "+audioManager.isBluetoothA2dpOn() );
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
            } else {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                audioManager.startBluetoothSco();
                audioManager.setBluetoothScoOn(true);
            }

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
                if (audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn()) {
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
            }
        } else if (i == 2) {
            if (isSWSpeaker && isSWEarphone) {
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
                }/*else {
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, true);
                }*/

            } else {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                audioManager.startBluetoothSco();
                audioManager.setBluetoothScoOn(true);
            }

            if (isSWSpeaker && isSWBleConnected) {
               /* if (audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn()){
                    Log.e("TAG", "onReceive HeadsetReceiver: BleConnected Mute" );
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                    audioManager.setStreamMute(AudioManager.STREAM_MUSIC, true);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, true);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
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
                }*/

                if (audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn() ) {
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
                    audioManager.setStreamMute(AudioManager.STREAM_DTMF, true);
                    audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                    audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                    audioManager.stopBluetoothSco();
                    audioManager.setBluetoothScoOn(false);
                }

            }/* else {
                audioManager.setStreamMute(AudioManager.STREAM_MUSIC, false);
                audioManager.setStreamMute(AudioManager.STREAM_DTMF, false);
                audioManager.setStreamMute(AudioManager.STREAM_VOICE_CALL, false);
                audioManager.setStreamMute(USE_DEFAULT_STREAM_TYPE, false);
                audioManager.startBluetoothSco();
                audioManager.setBluetoothScoOn(true);
            }*/

            if (isSWEarphone && isSWBleConnected) {

                if (Microphone_Plugged_in || audioManager.isBluetoothScoOn() || audioManager.isBluetoothA2dpOn()) {
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

            } /*else {
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

}