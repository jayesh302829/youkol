package com.dk.youkol.Activitys;

import static com.dk.youkol.utils.Const.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.dk.youkol.R;
import com.dk.youkol.databinding.ActivityDrivingBinding;
import com.dk.youkol.utils.Const;
import com.dk.youkol.utils.Const.*;

public class DrivingActivity extends AppCompatActivity {

    ActivityDrivingBinding binding;
    Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_driving);

        String type = getIntent().getStringExtra("type");
        if (type.equals(Admin)){
         setAdmin();
        }else {
            setUser();
        }

    }

    private void setUser() {

        binding.circleImageView1.setCircleBackgroundColor(getResources().getColor(R.color.white));
        binding.mainbg.setBackgroundColor(getResources().getColor(R.color.disablecolor));
        binding.textView1.setTextColor(getResources().getColor(R.color.white));
        Glide.with(activity).load(getDrawable(R.drawable.car)).into(binding.mainImage1);
        Glide.with(activity).load(getDrawable(R.drawable.iv_speaker)).into(binding.mainImage);
        Glide.with(activity).load(getDrawable(R.drawable.iv_headphones)).into(binding.mainImage3);
        Glide.with(activity).load(getDrawable(R.drawable.iv_headset)).into(binding.mainImage4);
        Glide.with(activity).load(getDrawable(R.drawable.iv_carkits)).into(binding.mainImage5);
        Glide.with(activity).load(getDrawable(R.drawable.iv_bluetooth)).into(binding.mainImage6);
        Glide.with(activity).load(getDrawable(R.drawable.iv_phonescreen)).into(binding.mainImage7);

        binding.textView1.setText(Caraudio);
        binding.textView2.setText(Speaker);
        binding.textView3.setText(Headphones);
        binding.textView4.setText(Headset);
        binding.textView5.setText(Carkits);
        binding.textView6.setText(Otherdevices);
        binding.textView7.setText(Phonescreen);

    }

    private void setAdmin() {

        Glide.with(activity).load(getDrawable(R.drawable.iv_caraudio)).into(binding.mainImage1);
        Glide.with(activity).load(getDrawable(R.drawable.iv_speaker)).into(binding.mainImage);
        Glide.with(activity).load(getDrawable(R.drawable.iv_headphones)).into(binding.mainImage3);
        Glide.with(activity).load(getDrawable(R.drawable.iv_headset)).into(binding.mainImage4);
        Glide.with(activity).load(getDrawable(R.drawable.iv_carkits)).into(binding.mainImage5);
        Glide.with(activity).load(getDrawable(R.drawable.iv_bluetooth)).into(binding.mainImage6);
        Glide.with(activity).load(getDrawable(R.drawable.iv_phonescreen)).into(binding.mainImage7);

        binding.textView1.setText(Caraudio);
        binding.textView2.setText(Speaker);
        binding.textView3.setText(Headphones);
        binding.textView4.setText(Headset);
        binding.textView5.setText(Carkits);
        binding.textView6.setText(Otherdevices);
        binding.textView7.setText(Phonescreen);

    }
}