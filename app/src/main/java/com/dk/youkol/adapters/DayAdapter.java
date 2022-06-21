package com.dk.youkol.adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.dk.youkol.Interfaces.DayInterface;
import com.dk.youkol.R;
import com.dk.youkol.databinding.RowItemDayBinding;
import com.dk.youkol.models.DayModel;

import java.util.ArrayList;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.MyViewHolder> {

    public ArrayList<DayModel> dayModelArrayList;
    Activity activity;
    DayInterface dayInterface;
    int width;
    boolean isTimebase;

    public DayAdapter(ArrayList<DayModel> dayModelArrayList, boolean isTimebase, Activity activity, int width, DayInterface dayInterface) {
        this.activity = activity;
        this.dayModelArrayList = dayModelArrayList;
        this.dayInterface = dayInterface;
        this.width = width;
        this.isTimebase = isTimebase;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowItemDayBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.row_item_day, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DayModel dayModel = dayModelArrayList.get(position);
        bind(holder.binding, dayModel, position);
    }

    private void bind(RowItemDayBinding binding, DayModel dayModel, int position) {
        binding.maincontainer.getLayoutParams().width = (int) (width / 8.5);

        if (dayModel.isEnable()) {
            if (dayModel.isSelected()) {
                binding.day.setText(dayModel.getDaySortName());
                binding.corner.setBackground(activity.getResources().getDrawable(R.drawable.day_active_border));
                binding.day.setTextColor(activity.getResources().getColor(R.color.white));
            } else {
                binding.day.setText(dayModel.getDaySortName());
                binding.corner.setBackground(activity.getResources().getDrawable(R.drawable.day_blue_border));
                binding.day.setTextColor(activity.getResources().getColor(R.color.daybodercolor));
            }
            if (isTimebase) {
                binding.maincontainer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dayInterface.passPosition(position, dayModel);
                    }
                });
            }else {
                binding.maincontainer.setOnClickListener(null);
            }
        } else {
            if (dayModel.isSelected()) {
                binding.day.setText(dayModel.getDaySortName());
                binding.corner.setBackground(activity.getResources().getDrawable(R.drawable.day_active_border_disable));
            } else {
                binding.day.setText(dayModel.getDaySortName());
                binding.corner.setBackground(activity.getResources().getDrawable(R.drawable.day_deactive_border_disable));
            }
            binding.day.setTextColor(activity.getResources().getColor(R.color.white));
        }
    }

    @Override
    public int getItemCount() {
        return dayModelArrayList.size();
    }

    public void setupdate(boolean isTime) {
        isTimebase = isTime;
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        RowItemDayBinding binding;

        public MyViewHolder(@NonNull RowItemDayBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
