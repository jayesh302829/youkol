package com.dk.youkol.Activitys;

import static com.dk.youkol.utils.Const.Caraudio;
import static com.dk.youkol.utils.Const.Carkits;
import static com.dk.youkol.utils.Const.Headphones;
import static com.dk.youkol.utils.Const.Headset;
import static com.dk.youkol.utils.Const.Otherdevices;
import static com.dk.youkol.utils.Const.Phonescreen;
import static com.dk.youkol.utils.Const.Speaker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.CompoundButton;

import com.bumptech.glide.Glide;
import com.dk.youkol.Interfaces.DayInterface;
import com.dk.youkol.R;
import com.dk.youkol.adapters.DayAdapter;
import com.dk.youkol.databinding.ActivityDrivingViewBinding;
import com.dk.youkol.models.DayModel;

import java.util.ArrayList;

public class DrivingViewActivity extends AppCompatActivity {

    ActivityDrivingViewBinding binding;
    Activity activity = this;
    DayAdapter dayAdapter;
    ArrayList<DayModel> dayModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_driving_view);

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


        dayModelArrayList.add(new DayModel("mon","m",getResources().getDrawable(R.drawable.day_blue_border),true,false));
        dayModelArrayList.add(new DayModel("tue","t",getResources().getDrawable(R.drawable.day_blue_border),true,false));
        dayModelArrayList.add(new DayModel("wed","w",getResources().getDrawable(R.drawable.day_blue_border),false,false));
        dayModelArrayList.add(new DayModel("thu","t",getResources().getDrawable(R.drawable.day_blue_border),true,false));
        dayModelArrayList.add(new DayModel("fri","f",getResources().getDrawable(R.drawable.day_blue_border),false,false));
        dayModelArrayList.add(new DayModel("sat","s",getResources().getDrawable(R.drawable.day_blue_border),false,false));
        dayModelArrayList.add(new DayModel("sun","S",getResources().getDrawable(R.drawable.day_blue_border),true,false));

        binding.seekBar.setOnTouchListener(new View.OnTouchListener() {
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
        binding.rvdays.setLayoutManager(new LinearLayoutManager(activity, RecyclerView.HORIZONTAL,false));

        ViewTreeObserver vto = binding.rvdays.getViewTreeObserver();
        vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                binding.rvdays.getViewTreeObserver().removeOnPreDrawListener(this);
                int finalHeight = binding.rvdays.getMeasuredHeight();
                int finalWidth = binding.rvdays.getMeasuredWidth();
                dayAdapter = new DayAdapter(dayModelArrayList, false, activity,finalWidth,new DayInterface() {
                    @Override
                    public void passPosition(int position, DayModel dayModel) {
                        DayModel selectedday = dayAdapter.dayModelArrayList.get(position);
                        if (selectedday.isSelected()){
                            selectedday.setSelected(false);
                        }else {
                            selectedday.setSelected(true);
                        }
                        dayAdapter.notifyDataSetChanged();
                    }
                });
                binding.rvdays.setAdapter(dayAdapter);
                return true;
            }
        });

        binding.appCompatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    binding.seekBar.setThumb(getResources().getDrawable(R.drawable.seek_bar_slider_active));
                }else {
                    binding.seekBar.setThumb(getResources().getDrawable(R.drawable.seek_bar_slider_deactive));
                }
            }
        });

    }
}