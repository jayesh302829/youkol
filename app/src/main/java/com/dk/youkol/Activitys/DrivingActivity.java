package com.dk.youkol.Activitys;

import static com.dk.youkol.utils.Const.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.dk.youkol.Interfaces.DayInterface;
import com.dk.youkol.R;
import com.dk.youkol.adapters.DayAdapter;
import com.dk.youkol.databinding.ActivityDrivingBinding;
import com.dk.youkol.models.DayModel;
import com.dk.youkol.utils.Const;
import com.dk.youkol.utils.Const.*;
import com.suke.widget.SwitchButton;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DrivingActivity extends BaseActivity {

    ActivityDrivingBinding binding;
    Activity activity = this;
    DayAdapter dayAdapter;
    ArrayList<DayModel> dayModelArrayList = new ArrayList<>();

    boolean isTimebase = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_driving);

        init();

        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(activity,DrivingActivity.class));
                finish();
            }
        });

        binding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        binding.card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.mainbg1.isSelected()) {
                    binding.textView1.setTextColor(getResources().getColor(R.color.txtcolor));
                    binding.mainbg1.setSelected(false);
                }else {
                    binding.textView1.setTextColor(getResources().getColor(R.color.white));
                    binding.mainbg1.setSelected(true);
                }
            }
        });

        binding.card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.mainbg2.isSelected()) {
                    binding.textView2.setTextColor(getResources().getColor(R.color.txtcolor));
                    binding.mainbg2.setSelected(false);
                }else {
                    binding.textView2.setTextColor(getResources().getColor(R.color.white));
                    binding.mainbg2.setSelected(true);
                }
            }
        });

        binding.card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.mainbg3.isSelected()) {
                    binding.textView3.setTextColor(getResources().getColor(R.color.txtcolor));
                    binding.mainbg3.setSelected(false);
                }else {
                    binding.textView3.setTextColor(getResources().getColor(R.color.white));
                    binding.mainbg3.setSelected(true);
                }
            }
        });

        binding.card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.mainbg4.isSelected()) {
                    binding.textView4.setTextColor(getResources().getColor(R.color.txtcolor));
                    binding.mainbg4.setSelected(false);
                }else {
                    binding.textView4.setTextColor(getResources().getColor(R.color.white));
                    binding.mainbg4.setSelected(true);
                }
            }
        });

        binding.card5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.mainbg5.isSelected()) {
                    binding.textView5.setTextColor(getResources().getColor(R.color.txtcolor));
                    binding.mainbg5.setSelected(false);
                }else {
                    binding.textView5.setTextColor(getResources().getColor(R.color.white));
                    binding.mainbg5.setSelected(true);
                }
            }
        });

        binding.card6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.mainbg6.isSelected()) {
                    binding.textView6.setTextColor(getResources().getColor(R.color.txtcolor));
                    binding.mainbg6.setSelected(false);
                }else {
                    binding.textView6.setTextColor(getResources().getColor(R.color.white));
                    binding.mainbg6.setSelected(true);
                }
            }
        });

        binding.card7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.mainbg7.isSelected()) {
                    binding.textView7.setTextColor(getResources().getColor(R.color.txtcolor));
                    binding.mainbg7.setSelected(false);
                }else {
                    binding.textView7.setTextColor(getResources().getColor(R.color.white));
                    binding.mainbg7.setSelected(true);
                }
            }
        });
    }

    public void init(){
        dayModelArrayList.add(new DayModel("mon", "m", getResources().getDrawable(R.drawable.day_blue_border), false, true));
        dayModelArrayList.add(new DayModel("tue", "t", getResources().getDrawable(R.drawable.day_blue_border), false, true));
        dayModelArrayList.add(new DayModel("wed", "w", getResources().getDrawable(R.drawable.day_blue_border), false, true));
        dayModelArrayList.add(new DayModel("thu", "t", getResources().getDrawable(R.drawable.day_blue_border), false, true));
        dayModelArrayList.add(new DayModel("fri", "f", getResources().getDrawable(R.drawable.day_blue_border), false, true));
        dayModelArrayList.add(new DayModel("sat", "s", getResources().getDrawable(R.drawable.day_blue_border), false, true));
        dayModelArrayList.add(new DayModel("sun", "S", getResources().getDrawable(R.drawable.day_blue_border), false, true));


        binding.rvdays.setHasFixedSize(true);
//        binding.rvdays.addItemDecoration(new DividerItemDecoration(activity, LinearLayout.HORIZONTAL));
        binding.rvdays.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));

        ViewTreeObserver vto = binding.rvdays.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                binding.rvdays.getViewTreeObserver().removeOnPreDrawListener(this);
                int finalHeight = binding.rvdays.getMeasuredHeight();
                int finalWidth = binding.rvdays.getMeasuredWidth();
                dayAdapter = new DayAdapter(dayModelArrayList, isTimebase, activity, finalWidth, new DayInterface() {
                    @Override
                    public void passPosition(int position, DayModel dayModel) {
                        DayModel selectedday = dayAdapter.dayModelArrayList.get(position);
                        if (selectedday.isSelected()) {
                            selectedday.setSelected(false);
                        } else {
                            selectedday.setSelected(true);
                        }
                        dayAdapter.notifyDataSetChanged();
                    }
                });
                binding.timeZone.setVisibility(View.GONE);
                binding.rvdays.setAdapter(dayAdapter);
                return true;
            }
        });
        binding.seekBar.setMax(10);
        binding.seekBar1.setMax(1439);
        binding.seekBar2.setMax(1439);

        binding.seekBar1.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int hours = progress / 60;
                int minutes = progress % 60;
                binding.startTime.setText(String.format("%02d", hours) + ":" + String.format("%02d", minutes));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.seekBar2.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                int hours = progress / 60;
                int minutes = progress % 60;
                binding.endTime.setText(String.format("%02d", hours) + ":" + String.format("%02d", minutes));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.tvMeter.setText("Enable from ~" + progress + " meters away");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.seekBar1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (binding.switchButton2.isChecked()) {
                    return false;
                } else {
                    return true;
                }
            }
        });

        binding.seekBar2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (binding.switchButton2.isChecked()) {
                    return false;
                } else {
                    return true;
                }
            }
        });

        binding.seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (binding.switchnearby.isChecked()) {
                    return false;
                } else {
                    return true;
                }
            }
        });


        binding.switchButton2.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (!isChecked) {
                    binding.timeZone.setVisibility(View.GONE);
                    isTimebase = false;
                    binding.seekBar1.setProgress(0);
                    binding.seekBar2.setProgress(0);

                } else {
                    binding.timeZone.setVisibility(View.VISIBLE);
                    isTimebase = true;
                }
                dayAdapter.setupdate(isTimebase);
            }
        });

        binding.switchnearby.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    binding.clNearby.setVisibility(View.VISIBLE);
                    binding.seekBar.setThumb(getResources().getDrawable(R.drawable.seek_bar_slider_active));
                } else {
                    binding.clNearby.setVisibility(View.GONE);
                    binding.seekBar.setProgress(0);
                    binding.seekBar.setThumb(getResources().getDrawable(R.drawable.seek_bar_slider_deactive));
                }
            }
        });

        /*binding.appCompatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.seekBar.setThumb(getResources().getDrawable(R.drawable.seek_bar_slider_active));
                } else {
                    binding.seekBar.setProgress(0);
                    binding.seekBar.setThumb(getResources().getDrawable(R.drawable.seek_bar_slider_deactive));
                }
            }
        });*/


        setAdmin();


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