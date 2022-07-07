package com.dk.youkol.Activitys;

import static com.dk.youkol.utils.Const.Caraudio;
import static com.dk.youkol.utils.Const.Carkits;
import static com.dk.youkol.utils.Const.Driving;
import static com.dk.youkol.utils.Const.Headphones;
import static com.dk.youkol.utils.Const.Headset;
import static com.dk.youkol.utils.Const.Otherdevices;
import static com.dk.youkol.utils.Const.Phonescreen;
import static com.dk.youkol.utils.Const.Speaker;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.dk.youkol.Interfaces.DayInterface;
import com.dk.youkol.R;
import com.dk.youkol.adapters.DayAdapter;
import com.dk.youkol.databinding.ActivityDrivingViewBinding;
import com.dk.youkol.models.DayModel;
import com.dk.youkol.roomdb.Converters;
import com.dk.youkol.roomdb.RepositoryData;
import com.dk.youkol.roomdb.RoomDataModel;

import java.util.ArrayList;
import java.util.List;

public class DrivingViewActivity extends BaseActivity {

    ActivityDrivingViewBinding binding;
    Activity activity = this;
    DayAdapter dayAdapter;
    ArrayList<DayModel> dayModelArrayList = new ArrayList<>();
    private RepositoryData repositoryData;
    boolean card1, card2, card3, card4, card5, card6, card7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_driving_view);
        repositoryData = new RepositoryData(activity);

        binding.seekBar.setMax(10);
        binding.seekBar1.setMax(1439);
        binding.seekBar2.setMax(1439);
//        binding.seekBarSpeed.setProgress(0);
        binding.seekBarSpeed.setMin(20);
        binding.seekBarSpeed.setMax(80);

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
                binding.tvmeters.setText("Enable from ~" + progress + " meters away");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.seekBarSpeed.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                binding.tvSpeed.setText(progress + " km/h");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

//        binding.circleImageView1.setCircleBackgroundColor(getResources().getColor(R.color.white));
//        binding.mainbg.setBackgroundColor(getResources().getColor(R.color.disablecolor));
//        binding.textView1.setTextColor(getResources().getColor(R.color.white));
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

        dayModelArrayList.add(new DayModel("mon", "m", R.drawable.day_blue_border, false));
        dayModelArrayList.add(new DayModel("tue", "t", R.drawable.day_blue_border, false));
        dayModelArrayList.add(new DayModel("wed", "w", R.drawable.day_blue_border, false));
        dayModelArrayList.add(new DayModel("thu", "t", R.drawable.day_blue_border, false));
        dayModelArrayList.add(new DayModel("fri", "f", R.drawable.day_blue_border, false));
        dayModelArrayList.add(new DayModel("sat", "s", R.drawable.day_blue_border, false));
        dayModelArrayList.add(new DayModel("sun", "S", R.drawable.day_blue_border, false));

        binding.seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        binding.seekBarSpeed.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        binding.seekBar1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        binding.seekBar2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });


        binding.rvdays.setHasFixedSize(true);
//        binding.rvdays.addItemDecoration(new DividerItemDecoration(activity, LinearLayout.HORIZONTAL));
        binding.rvdays.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL, false));

        ViewTreeObserver vto = binding.rvdays.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                binding.rvdays.getViewTreeObserver().removeOnPreDrawListener(this);
                int finalHeight = binding.rvdays.getMeasuredHeight();
                int finalWidth = binding.rvdays.getMeasuredWidth();
                dayAdapter = new DayAdapter("View", dayModelArrayList, false, activity, finalWidth, new DayInterface() {
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
                binding.rvdays.setAdapter(dayAdapter);
                return true;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RoomDataModel> dataAllList = repositoryData.getAllList();

                for (int position = 0; position < dataAllList.size(); position++) {
                    RoomDataModel roomDataModel = dataAllList.get(position);
                    if (roomDataModel.isReset() && roomDataModel.getType().equals(Driving)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setData(roomDataModel);
                            }
                        });
                    }
                }
            }
        }).start();

       /* binding.appCompatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    binding.seekBar.setThumb(getResources().getDrawable(R.drawable.seek_bar_slider_active));
                } else {
                    binding.seekBar.setThumb(getResources().getDrawable(R.drawable.seek_bar_slider_deactive));
                }
            }
        });*/

    }

    private void setData(RoomDataModel roomDataModel) {
        boolean isNearby = Boolean.parseBoolean(roomDataModel.getIsNewarby());
        boolean isLocationBase = Boolean.parseBoolean(roomDataModel.getLocationBase());
        boolean isSpeedBase = Boolean.parseBoolean(roomDataModel.getIsSpeedBase());
        boolean isTimeBase = Boolean.parseBoolean(roomDataModel.getIsTimeBase());

        if (isTimeBase) {
            ArrayList<DayModel> dayModelArrayList = Converters.fromString(roomDataModel.getSelectedDays());
            dayAdapter.setUpdateList(dayModelArrayList);
        }

        String[] devices = roomDataModel.getDeviceAllow().split(",");
        card1 = Boolean.parseBoolean(devices[0]);
        card2 = Boolean.parseBoolean(devices[1]);
        card3 = Boolean.parseBoolean(devices[2]);
        card4 = Boolean.parseBoolean(devices[3]);
        card5 = Boolean.parseBoolean(devices[4]);
        card6 = Boolean.parseBoolean(devices[5]);
        card7 = Boolean.parseBoolean(devices[6]);

        binding.mainbg.setSelected(card1);
        binding.mainbg1.setSelected(card2);
        binding.mainbg2.setSelected(card3);
        binding.mainbg3.setSelected(card4);
        binding.mainbg4.setSelected(card5);
        binding.mainbg5.setSelected(card6);
        binding.mainbg6.setSelected(card7);

        if (card1) {
            binding.textView1.setTextColor(getResources().getColor(R.color.white));
        }
        if (card2) {
            binding.textView2.setTextColor(getResources().getColor(R.color.white));
            binding.tvblock1.setVisibility(View.VISIBLE);
        }
        if (card3) {
            binding.textView3.setTextColor(getResources().getColor(R.color.white));
            binding.tvblock2.setVisibility(View.VISIBLE);
        }
        if (card4) {
            binding.textView4.setTextColor(getResources().getColor(R.color.white));
            binding.tvblock3.setVisibility(View.VISIBLE);
        }
        if (card5) {
            binding.textView5.setTextColor(getResources().getColor(R.color.white));
        }
        if (card6) {
            binding.textView6.setTextColor(getResources().getColor(R.color.white));
        }
        if (card7) {
            binding.textView7.setTextColor(getResources().getColor(R.color.white));
        }

        if (isNearby) {
            Glide.with(activity).load(getDrawable(R.drawable.iv_switch_on)).into(binding.ivSwitch);
            binding.tvmeters.setVisibility(View.VISIBLE);
            binding.seekBar.setVisibility(View.VISIBLE);
            binding.seekBar.setProgress(Integer.parseInt(roomDataModel.nearbyDistance));
        } else {
            Glide.with(activity).load(getDrawable(R.drawable.iv_switch_off)).into(binding.ivSwitch);
            binding.tvmeters.setVisibility(View.GONE);
            binding.seekBar.setVisibility(View.GONE);
        }

        if (isLocationBase) {
            Glide.with(activity).load(getDrawable(R.drawable.iv_switch_on)).into(binding.ivSwitch1);
        } else {
            Glide.with(activity).load(getDrawable(R.drawable.iv_switch_off)).into(binding.ivSwitch1);
        }

        if (isSpeedBase) {
            binding.seekBarSpeed.setProgress(Integer.parseInt(roomDataModel.getSpeed()));
            binding.speedbased.setVisibility(View.VISIBLE);
            Glide.with(activity).load(getDrawable(R.drawable.iv_switch_on)).into(binding.ivSwitch2);

        } else {
            binding.speedbased.setVisibility(View.GONE);
            Glide.with(activity).load(getDrawable(R.drawable.iv_switch_off)).into(binding.ivSwitch2);
        }

        if (isTimeBase) {
            binding.timeZone.setVisibility(View.VISIBLE);
            binding.seekBar1.setProgress(Integer.parseInt(roomDataModel.startTime));
            binding.seekBar2.setProgress(Integer.parseInt(roomDataModel.endTime));
            Glide.with(activity).load(getDrawable(R.drawable.iv_switch_on)).into(binding.ivSwitch3);
        } else {
            binding.timeZone.setVisibility(View.GONE);
            Glide.with(activity).load(getDrawable(R.drawable.iv_switch_off)).into(binding.ivSwitch3);
        }

        try {
            if (roomDataModel.getPolicyApply().equals("All users")){
                binding.rballuser.setChecked(true);
            }else if (roomDataModel.getPolicyApply().equals("Specific groups")){
                binding.rbspecificGroup.setChecked(true);
            }else {
                binding.rbspecificUser.setChecked(true);
            }
        }catch (Exception exception){

        }

        String[] notifications = roomDataModel.getNotification().split(",");
        binding.appCompatCheckBox1.setChecked(Boolean.parseBoolean(notifications[0]));
        binding.appCompatCheckBox2.setChecked(Boolean.parseBoolean(notifications[1]));
        binding.appCompatCheckBox3.setChecked(Boolean.parseBoolean(notifications[2]));
        binding.appCompatCheckBox4.setChecked(Boolean.parseBoolean(notifications[3]));
        binding.appCompatCheckBox5.setChecked(Boolean.parseBoolean(notifications[4]));

    }
}