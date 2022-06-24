package com.dk.youkol.Activitys;

import static com.dk.youkol.utils.Const.Atwork;
import static com.dk.youkol.utils.Const.Biking;
import static com.dk.youkol.utils.Const.Driving;
import static com.dk.youkol.utils.Const.Kids;
import static com.dk.youkol.utils.Const.Outsidevehicle;
import static com.dk.youkol.utils.Const.Walking;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.dk.youkol.R;
import com.dk.youkol.adapters.HomeAdapter;
import com.dk.youkol.databinding.ActivityDashboardBinding;
import com.dk.youkol.models.DataModel;
import com.dk.youkol.roomdb.RepositoryData;
import com.dk.youkol.roomdb.RoomDataModel;
import com.dk.youkol.utils.GridSpacingItemDecoration;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    ActivityDashboardBinding binding;
    Activity activity = this;
    RepositoryData repositoryData;
    ArrayList<DataModel> dataModelArrayList = new ArrayList<>();
    HomeAdapter homeAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_dashboard);
        repositoryData = new RepositoryData(activity);
        binding.rvMenu.setLayoutManager(new GridLayoutManager(activity, 2));
        dataModelArrayList.add(new DataModel(0, R.drawable.ic_driving, Driving, false));
        dataModelArrayList.add(new DataModel(0, R.drawable.iv_outside_vehicle, Outsidevehicle, false));
        dataModelArrayList.add(new DataModel(0, R.drawable.iv_walking, Walking, false));
        dataModelArrayList.add(new DataModel(0, R.drawable.iv_biking, Biking, false));
        dataModelArrayList.add(new DataModel(0, R.drawable.iv_at_work, Atwork, false));
        dataModelArrayList.add(new DataModel(0, R.drawable.iv_kids, Kids, false));
        int spanCount = 2; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = true;
        homeAdapter = new HomeAdapter(dataModelArrayList, activity);
        binding.rvMenu.setAdapter(homeAdapter);
        binding.rvMenu.setHasFixedSize(true);

        getData();


        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this);

        binding.rvMenu.setLayoutFrozen(false);
        binding.rvMenu.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
//        binding.rvMenu.addItemDecoration(new SpacesItemDecoration(spacingInPixels));



    }

    @Override
    protected void onResume() {
        getData();
        super.onResume();
    }

    private void getData() {
        repositoryData.getLiveList().observe(this, new Observer<List<RoomDataModel>>() {
            @Override
            public void onChanged(List<RoomDataModel> roomDataModels) {
                if (roomDataModels.size() > 0) {
                    for (int i = 0; i < roomDataModels.size(); i++) {
                        RoomDataModel roomDataModel = roomDataModels.get(i);
                        if (roomDataModel.getType().equals(Driving)) {
                            boolean b = roomDataModel.isReset();
                            ArrayList<DataModel> dataModelArrayList = new ArrayList<DataModel>();
                            dataModelArrayList.add(new DataModel(0, R.drawable.ic_driving, Driving, b));
                            dataModelArrayList.add(new DataModel(0, R.drawable.iv_outside_vehicle, Outsidevehicle, false));
                            dataModelArrayList.add(new DataModel(0, R.drawable.iv_walking, Walking, false));
                            dataModelArrayList.add(new DataModel(0, R.drawable.iv_biking, Biking, false));
                            dataModelArrayList.add(new DataModel(0, R.drawable.iv_at_work, Atwork, false));
                            dataModelArrayList.add(new DataModel(0, R.drawable.iv_kids, Kids, false));
                            homeAdapter.update(dataModelArrayList);
                        }
                    }
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_home) {
            Intent intent = new Intent(activity, MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }
}