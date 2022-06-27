package com.dk.youkol.utils;

import android.content.Context;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.os.Build;

public class Util {

    public static boolean isHeadsetOn(Context context) {
        AudioManager am = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        if (am == null)
            return false;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return am.isWiredHeadsetOn() || am.isBluetoothScoOn() || am.isBluetoothA2dpOn();
        } else {
            AudioDeviceInfo[] devices = am.getDevices(AudioManager.GET_DEVICES_OUTPUTS);

            for (AudioDeviceInfo device : devices) {
                if (device.getType() == AudioDeviceInfo.TYPE_WIRED_HEADSET
                        || device.getType() == AudioDeviceInfo.TYPE_WIRED_HEADPHONES
                        || device.getType() == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP
                        || device.getType() == AudioDeviceInfo.TYPE_BLUETOOTH_SCO) {
                    return true;
                }
            }
        }
        return false;
    }
}
