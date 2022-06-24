package com.dk.youkol.adapters;

import static com.dk.youkol.utils.Const.Admin;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dk.youkol.Activitys.DrivingActivity;
import com.dk.youkol.Activitys.KidsActivity;
import com.dk.youkol.R;
import com.dk.youkol.databinding.RowItemDashboardBinding;
import com.dk.youkol.models.DataModel;
import com.dk.youkol.utils.Const;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewholder> {

    public ArrayList<DataModel> dataModelArrayList;
    Activity activity;

    public HomeAdapter(ArrayList<DataModel> dataModelArrayList, Activity activity) {
        this.dataModelArrayList = dataModelArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowItemDashboardBinding binding = DataBindingUtil.inflate(LayoutInflater.from(activity), R.layout.row_item_dashboard,parent,false);
        MyViewholder viewHolder = new MyViewholder(binding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        DataModel dataModel = dataModelArrayList.get(position);
        Glide.with(activity).load(activity.getDrawable(dataModel.getImageId())).into(holder.binding.mainImage);
        holder.binding.textView3.setText(dataModel.getName());

        if (dataModel.isSelected()){
            holder.binding.mainbg.setSelected(true);
        }else {
            holder.binding.mainbg.setSelected(false);
        }

        holder.binding.maincard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent;
                if (Const.Kids.equals(dataModel.getName())){
                    intent = new Intent(activity, KidsActivity.class);
                    activity.startActivity(intent);
                }else if (Const.Driving.equals(dataModel.getName())){
                    intent = new Intent(activity, DrivingActivity.class);
                    intent.putExtra("type",dataModel.getName());
                    activity.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return dataModelArrayList.size();
    }

    public void update(ArrayList<DataModel> dataModelArrayList1) {
        dataModelArrayList.clear();
        dataModelArrayList.addAll(dataModelArrayList1);
        notifyDataSetChanged();
    }

    public class MyViewholder extends RecyclerView.ViewHolder {
        RowItemDashboardBinding binding;
        public MyViewholder(@NonNull RowItemDashboardBinding itemView) {
            super(itemView.getRoot());
            this.binding = itemView;
        }
    }
}
