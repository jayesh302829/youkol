package com.dk.youkol.Activitys;

import static android.media.VolumeProvider.VOLUME_CONTROL_ABSOLUTE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioDeviceInfo;
import android.media.AudioManager;
import android.media.VolumeProvider;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.dk.youkol.R;
import com.dk.youkol.databinding.ActivityMainBinding;
import com.dk.youkol.receivers.HeadsetReceiver;
import com.dk.youkol.receivers.VolumeChangeReceiver;
import com.dk.youkol.services.BackgroundService;
import com.dk.youkol.utils.Const;
import com.dk.youkol.utils.Util;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.http.impl.client.AbstractResponseHandler;

public class MainActivity extends BaseActivity {

    ActivityMainBinding binding;
    Activity activity = this;
    private boolean isEmailValid;
    private boolean isPasswordValid;
    HeadsetReceiver headsetReceiver;
    VolumeChangeReceiver volumeChangeReceiver;
    String VOLUME_CHANGE_ACTION = "android.media.VOLUME_CHANGED_ACTION";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_main);
        headsetReceiver = new HeadsetReceiver();
        volumeChangeReceiver = new VolumeChangeReceiver();
        IntentFilter receiverFilter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        IntentFilter volFilter = new IntentFilter(VOLUME_CHANGE_ACTION);
        registerReceiver(headsetReceiver, receiverFilter);
        registerReceiver(volumeChangeReceiver, volFilter);
        onclick();

        if (BackgroundService.getInstance() == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(new Intent(this, BackgroundService.class));
            } else {
                startService(new Intent(this, BackgroundService.class));
            }
        }
    }

    private void onclick() {
        binding.clLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.edtEmail.getText().toString();
                String password = binding.edtPassword.getText().toString();

                /*if (email.isEmpty()) {
                    binding.edtEmail.setError(getString(R.string.email_not_empty));
                    isEmailValid = false;
                } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    binding.edtEmail.setError(getString(R.string.validemail));
                    isEmailValid = false;
                } else  {
                    binding.edtEmail.setError(null);
                    isEmailValid = true;
                }

                if (password.isEmpty()) {
                    binding.edtPassword.setError(getString(R.string.password_not_empty));
                    isPasswordValid = false;
                } else if (password.length() <= 5) {
                    binding.edtPassword.setError(getString(R.string.valid_password));
                    isPasswordValid = false;
                } else  {
                    binding.edtPassword.setError(null);
                    isPasswordValid = true;
                }

                if (isEmailValid && isPasswordValid){

                    if (email.equalsIgnoreCase("johndoe@comminsur.com") && password.equals("123456")){*/
                Intent intent = new Intent(activity, DashboardActivity.class);
                intent.putExtra("type", Const.Admin);
                startActivity(intent);
                finish();
                   /* }else {
                        Toast.makeText(activity, "Email and Password Wrong\n PLease Try Again!", Toast.LENGTH_SHORT).show();
                    }
                }*/
            }
        });
        binding.clContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(activity, DrivingViewActivity.class);
                intent.putExtra("type", Const.Guest);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        getApplicationContext().registerReceiver(headsetReceiver, filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        getApplicationContext().unregisterReceiver(headsetReceiver);
//        getApplicationContext().unregisterReceiver(volumeChangeReceiver);
    }
}
