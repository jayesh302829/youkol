package com.dk.youkol.Activitys;

import static com.dk.youkol.utils.Const.*;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.dk.youkol.Interfaces.DayInterface;
import com.dk.youkol.R;
import com.dk.youkol.adapters.DayAdapter;
import com.dk.youkol.databinding.ActivityDrivingBinding;
import com.dk.youkol.models.DayModel;
import com.dk.youkol.roomdb.RepositoryData;
import com.dk.youkol.roomdb.RoomDataModel;
import com.suke.widget.SwitchButton;

import java.util.ArrayList;
import java.util.List;

public class DrivingActivity extends BaseActivity {

    ActivityDrivingBinding binding;
    Activity activity = this;
    DayAdapter dayAdapter;
    ArrayList<DayModel> dayModelArrayList = new ArrayList<>();
    String type;
    boolean isTimebase = false;
    RoomDataModel roomDataModel;
    RepositoryData repositoryData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_driving);
        repositoryData = new RepositoryData(activity);
        type = getIntent().getStringExtra("type");
        roomDataModel = new RoomDataModel();
        init();

        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RoomDataModel> dataAllList = repositoryData.getAllList();

                for (int position = 0; position < dataAllList.size(); position++) {
                    RoomDataModel roomDataModel = dataAllList.get(position);
                    if (roomDataModel.isReset() && roomDataModel.getType().equals(Driving)) {
                        setData(roomDataModel);
                    }
                }
            }
        }).start();

        binding.btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetData();
            }
        });

        binding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validation();
            }
        });

        binding.card1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (binding.mainbg1.isSelected()) {
                    binding.textView1.setTextColor(getResources().getColor(R.color.txtcolor));
                    binding.mainbg1.setSelected(false);
                } else {
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
                } else {
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
                } else {
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
                } else {
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
                } else {
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
                } else {
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
                } else {
                    binding.textView7.setTextColor(getResources().getColor(R.color.white));
                    binding.mainbg7.setSelected(true);
                }
            }
        });
    }

    private void setData(RoomDataModel roomDataModel) {
        String[] devices = roomDataModel.getDeviceAllow().split(",");
        binding.mainbg1.setSelected(Boolean.parseBoolean(devices[0]));
        binding.mainbg2.setSelected(Boolean.parseBoolean(devices[1]));
        binding.mainbg3.setSelected(Boolean.parseBoolean(devices[2]));
        binding.mainbg4.setSelected(Boolean.parseBoolean(devices[3]));
        binding.mainbg5.setSelected(Boolean.parseBoolean(devices[4]));
        binding.mainbg6.setSelected(Boolean.parseBoolean(devices[5]));
        binding.mainbg7.setSelected(Boolean.parseBoolean(devices[6]));
        binding.switchnearby.setChecked(Boolean.parseBoolean(roomDataModel.getIsNewarby()));
        binding.switchButton.setChecked(Boolean.parseBoolean(roomDataModel.getLocationBase()));
        binding.switchButton1.setChecked(Boolean.parseBoolean(roomDataModel.getIsSpeedBase()));
        binding.switchButton2.setChecked(Boolean.parseBoolean(roomDataModel.getIsTimeBase()));

    }

    private void resetData() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                List<RoomDataModel> dataAllList = repositoryData.getAllList();
                if (dataAllList.size() > 0) {
                    for (int i = 0; i < dataAllList.size(); i++) {
                        RoomDataModel roomDataModel1 = dataAllList.get(i);
                        if (roomDataModel1.getType().trim().equals("Driving")) {
                            repositoryData.updateTask(ResetModel(roomDataModel1));
                            finish();
                            startActivity(new Intent(activity, DrivingActivity.class));
                        }
                    }
                } else {
                    finish();
                    startActivity(new Intent(activity, DrivingActivity.class));
                }
            }
        }).start();
    }

    private void validation() {
        String card1 = String.valueOf(binding.mainbg1.isSelected());
        String card2 = String.valueOf(binding.mainbg2.isSelected());
        String card3 = String.valueOf(binding.mainbg3.isSelected());
        String card4 = String.valueOf(binding.mainbg4.isSelected());
        String card5 = String.valueOf(binding.mainbg5.isSelected());
        String card6 = String.valueOf(binding.mainbg6.isSelected());
        String card7 = String.valueOf(binding.mainbg7.isSelected());
        String devices = card1 + "," + card2 + "," + card3 + "," + card4 + "," + card5 + "," + card6 + "," + card7;
        roomDataModel.setDeviceAllow(devices);
        roomDataModel.setIsNewarby(String.valueOf(binding.switchnearby.isChecked()));
        roomDataModel.setLocationBase(String.valueOf(binding.switchButton.isChecked()));
        roomDataModel.setIsSpeedBase(String.valueOf(binding.switchButton1.isChecked()));
        roomDataModel.setIsTimeBase(String.valueOf(binding.switchButton2.isChecked()));
        roomDataModel.setNearbyDistance(String.valueOf(binding.seekBar.getProgress()));
        roomDataModel.setSpeed(String.valueOf(binding.seekBarSpeed.getProgress()));
        roomDataModel.setStartTime(String.valueOf(binding.seekBar1.getProgress()));
        roomDataModel.setEndTime(String.valueOf(binding.seekBar2.getProgress()));
        if (binding.rballuser.isChecked()) {
            roomDataModel.setPolicyApply(binding.rballuser.getText().toString());
        } else if (binding.rbspecificGroup.isChecked()) {
            roomDataModel.setPolicyApply(binding.rbspecificGroup.getText().toString());
        } else if (binding.rbspecificUser.isChecked()) {
            roomDataModel.setPolicyApply(binding.rbspecificUser.getText().toString());
        }
        String check1 = String.valueOf(binding.appCompatCheckBox1.isChecked());
        String check2 = String.valueOf(binding.appCompatCheckBox2.isChecked());
        String check3 = String.valueOf(binding.appCompatCheckBox3.isChecked());
        String check4 = String.valueOf(binding.appCompatCheckBox4.isChecked());
        String check5 = String.valueOf(binding.appCompatCheckBox5.isChecked());
        String notification = check1 + "," + check2 + "," + check3 + "," + check4 + "," + check5;
        roomDataModel.setNotification(notification);
        roomDataModel.setType("Driving");
        ArrayList<DayModel> list = dayAdapter.dayModelArrayList;
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DayModel model = list.get(i);
            if (model.isSelected()) {
                strings.add(model.getDayName());
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            sb.append(s);
            sb.append(" ");
        }
        roomDataModel.setSelectedDays(sb.toString());
        roomDataModel.setReset(true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<RoomDataModel> dataAllList = repositoryData.getAllList();
                if (dataAllList.size() > 0) {
                    for (int i = 0; i < dataAllList.size(); i++) {
                        RoomDataModel roomDataModel1 = dataAllList.get(i);
                        if (roomDataModel1.getType().trim().equals("Driving")) {
                            repositoryData.updateTask(Updatemodel(roomDataModel1));
                            finish();
                        } else {
                            repositoryData.insertTask(roomDataModel);
                            finish();
                        }
                    }
                } else {
                    repositoryData.insertTask(roomDataModel);
                    finish();
                }
            }
        }).start();
    }

    public RoomDataModel Updatemodel(RoomDataModel roomDataModel) {
        String card1 = String.valueOf(binding.mainbg1.isSelected());
        String card2 = String.valueOf(binding.mainbg2.isSelected());
        String card3 = String.valueOf(binding.mainbg3.isSelected());
        String card4 = String.valueOf(binding.mainbg4.isSelected());
        String card5 = String.valueOf(binding.mainbg5.isSelected());
        String card6 = String.valueOf(binding.mainbg6.isSelected());
        String card7 = String.valueOf(binding.mainbg7.isSelected());
        String devices = card1 + "," + card2 + "," + card3 + "," + card4 + "," + card5 + "," + card6 + "," + card7;
        roomDataModel.setDeviceAllow(devices);
        roomDataModel.setIsNewarby(String.valueOf(binding.switchnearby.isChecked()));
        roomDataModel.setLocationBase(String.valueOf(binding.switchButton.isChecked()));
        roomDataModel.setIsSpeedBase(String.valueOf(binding.switchButton1.isChecked()));
        roomDataModel.setIsTimeBase(String.valueOf(binding.switchButton2.isChecked()));
        roomDataModel.setNearbyDistance(String.valueOf(binding.seekBar.getProgress()));
        roomDataModel.setSpeed(String.valueOf(binding.seekBarSpeed.getProgress()));
        roomDataModel.setStartTime(String.valueOf(binding.seekBar1.getProgress()));
        roomDataModel.setEndTime(String.valueOf(binding.seekBar2.getProgress()));
        if (binding.rballuser.isChecked()) {
            roomDataModel.setPolicyApply(binding.rballuser.getText().toString());
        } else if (binding.rbspecificGroup.isChecked()) {
            roomDataModel.setPolicyApply(binding.rbspecificGroup.getText().toString());
        } else if (binding.rbspecificUser.isChecked()) {
            roomDataModel.setPolicyApply(binding.rbspecificUser.getText().toString());
        }
        String check1 = String.valueOf(binding.appCompatCheckBox1.isChecked());
        String check2 = String.valueOf(binding.appCompatCheckBox2.isChecked());
        String check3 = String.valueOf(binding.appCompatCheckBox3.isChecked());
        String check4 = String.valueOf(binding.appCompatCheckBox4.isChecked());
        String check5 = String.valueOf(binding.appCompatCheckBox5.isChecked());
        String notification = check1 + "," + check2 + "," + check3 + "," + check4 + "," + check5;
        roomDataModel.setNotification(notification);
        roomDataModel.setType("Driving");
        roomDataModel.setReset(true);
        ArrayList<DayModel> list = dayAdapter.dayModelArrayList;
        ArrayList<String> strings = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            DayModel model = list.get(i);
            if (model.isSelected()) {
                strings.add(model.getDayName());
            }
        }
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            sb.append(s);
            sb.append(" ");
        }
        roomDataModel.setSelectedDays(sb.toString());
        return roomDataModel;
    }

    public RoomDataModel ResetModel(RoomDataModel roomDataModel) {
        roomDataModel.setDeviceAllow("");
        roomDataModel.setIsNewarby("");
        roomDataModel.setLocationBase("");
        roomDataModel.setIsSpeedBase("");
        roomDataModel.setIsTimeBase("");
        roomDataModel.setNearbyDistance("");
        roomDataModel.setSpeed("");
        roomDataModel.setStartTime("");
        roomDataModel.setEndTime("");
        roomDataModel.setPolicyApply("");
        roomDataModel.setNotification("");
        roomDataModel.setSelectedDays("");
        roomDataModel.setReset(false);
        return roomDataModel;
    }

    public void init() {
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
        binding.seekBarSpeed.setProgress(0);
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
                binding.tvMeter.setText("Enable from ~" + progress + " meters away");
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


        binding.switchButton1.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                if (isChecked) {
                    binding.speedbased.setVisibility(View.VISIBLE);
                    binding.seekBarSpeed.setProgress(0);
                } else {
                    binding.speedbased.setVisibility(View.GONE);
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