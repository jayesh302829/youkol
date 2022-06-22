package com.dk.youkol.Activitys;

import static com.dk.youkol.utils.Const.Atwork;
import static com.dk.youkol.utils.Const.Biking;
import static com.dk.youkol.utils.Const.Driving;
import static com.dk.youkol.utils.Const.Kids;
import static com.dk.youkol.utils.Const.Outsidevehicle;
import static com.dk.youkol.utils.Const.Walking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.dk.youkol.R;
import com.dk.youkol.adapters.HomeAdapter;
import com.dk.youkol.databinding.ActivityDashboardBinding;
import com.dk.youkol.models.DataModel;
import com.dk.youkol.utils.GridSpacingItemDecoration;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class DashboardActivity extends BaseActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {

    ActivityDashboardBinding binding;
    Activity activity = this;

    ArrayList<DataModel> dataModelArrayList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity, R.layout.activity_dashboard);

        binding.rvMenu.setLayoutManager(new GridLayoutManager(activity,2));
        dataModelArrayList.add(new DataModel(0,R.drawable.ic_driving, Driving));
        dataModelArrayList.add(new DataModel(0,R.drawable.iv_outside_vehicle,Outsidevehicle));
        dataModelArrayList.add(new DataModel(0,R.drawable.iv_walking, Walking));
        dataModelArrayList.add(new DataModel(0,R.drawable.iv_biking,Biking));
        dataModelArrayList.add(new DataModel(0,R.drawable.iv_at_work,Atwork));
        dataModelArrayList.add(new DataModel(0,R.drawable.iv_kids,Kids));
        int spanCount = 2; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = true;

        binding.bottomNavigationView.setOnNavigationItemSelectedListener(this);

        binding.rvMenu.setLayoutFrozen(false);
        binding.rvMenu.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
//        binding.rvMenu.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        HomeAdapter homeAdapter = new HomeAdapter(dataModelArrayList,activity);
        binding.rvMenu.setAdapter(homeAdapter);
        binding.rvMenu.setHasFixedSize(true);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_home) {
            Intent intent = new Intent(activity,MainActivity.class);
            startActivity(intent);
            finish();
            return true;
        }
        return false;
    }
}