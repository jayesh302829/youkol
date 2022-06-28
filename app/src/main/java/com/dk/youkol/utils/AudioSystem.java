package com.dk.youkol.utils;

public class AudioSystem {

    private static final int REFLECTION_ERROR = -999;

    private static final String DEVICE_OUT_SPEAKER = "DEVICE_OUT_SPEAKER";
    private static final String DEVICE_OUT_EARPIECE = "DEVICE_OUT_EARPIECE";
    private static final String DEVICE_OUT_WIRED_HEADPHONE = "DEVICE_OUT_WIRED_HEADPHONE";


    Class<?> mAudioSystem;

    public AudioSystem() {

        try {
            mAudioSystem = Class.forName("android.media.AudioSystem");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private int getConstantValue(String s) {

        try {
            return ((Integer) mAudioSystem.getDeclaredField(s).get(int.class)).intValue();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return REFLECTION_ERROR;

    }


    private int setDeviceConnectionState(int i, int j, String s) {

        try {
            return (Integer) mAudioSystem.getMethod("setDeviceConnectionState", int.class, int.class, String.class).invoke(mAudioSystem, i, j, s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return REFLECTION_ERROR;
    }

    private int setDeviceConnectionState(String deviceName, Boolean state) {
        return setDeviceConnectionState(getConstantValue(deviceName), (state ? 1 : 0), "");
    }

    private void forceWiredHeadphonesMedia() {
        setDeviceConnectionState(DEVICE_OUT_WIRED_HEADPHONE, true);
        setDeviceConnectionState(DEVICE_OUT_SPEAKER, false);
    }


    private void forceSpeakerMedia() {
        setDeviceConnectionState(DEVICE_OUT_SPEAKER, true);
        setDeviceConnectionState(DEVICE_OUT_EARPIECE, true);
        setDeviceConnectionState(DEVICE_OUT_WIRED_HEADPHONE, false);
        setDeviceConnectionState(DEVICE_OUT_SPEAKER, true);
    }

    public void setSpeakerOn(Boolean state) {
        if (state) {
            forceSpeakerMedia();
        } else {
            forceWiredHeadphonesMedia();
        }
    }
}