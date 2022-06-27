package com.dk.youkol.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

public class HeadsetReceiver extends BroadcastReceiver {
    public static boolean Microphone_Plugged_in = false;
    AudioManager audioManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        int iii = 2;
        if (Intent.ACTION_HEADSET_PLUG.equals(action)) {
            iii = intent.getIntExtra("state", -1);
            if (iii == 0) {
                Microphone_Plugged_in = false;
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,0,0);
                audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 0, 0);
                audioManager.setMicrophoneMute(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    audioManager.adjustVolume(AudioManager.ADJUST_MUTE, AudioManager.STREAM_VOICE_CALL);
                }else {
                    audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.STREAM_VOICE_CALL);
                }
            } else if (iii == 1) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,50,0);
                audioManager.setStreamVolume(AudioManager.STREAM_VOICE_CALL, 50, 0);
                audioManager.setMicrophoneMute(true);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    audioManager.adjustVolume(AudioManager.ADJUST_UNMUTE, AudioManager.STREAM_VOICE_CALL);
                }else {
                    audioManager.adjustVolume(AudioManager.ADJUST_LOWER, AudioManager.STREAM_VOICE_CALL);
                }
                Microphone_Plugged_in = true;
                Log.e("TAG", "onReceive: microphone plugged in");
            }
        }
    }
}
