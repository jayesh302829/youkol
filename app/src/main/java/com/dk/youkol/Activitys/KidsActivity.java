package com.dk.youkol.Activitys;

import static com.dk.youkol.utils.Const.Atwork;
import static com.dk.youkol.utils.Const.Biking;
import static com.dk.youkol.utils.Const.Driving;
import static com.dk.youkol.utils.Const.Kids;
import static com.dk.youkol.utils.Const.Outsidevehicle;
import static com.dk.youkol.utils.Const.*;
import static com.dk.youkol.utils.Const.Walking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Activity;
import android.os.Bundle;

import com.dk.youkol.R;
import com.dk.youkol.adapters.HomeAdapter;
import com.dk.youkol.databinding.ActivityKidsBinding;
import com.dk.youkol.models.DataModel;
import com.dk.youkol.utils.GridSpacingItemDecoration;

import java.util.ArrayList;

public class KidsActivity extends BaseActivity {

    ActivityKidsBinding binding;
    Activity activity = this;
    ArrayList<DataModel> dataModelArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(activity,R.layout.activity_kids);


        binding.rvKidsmenu.setLayoutManager(new GridLayoutManager(activity,2));
        dataModelArrayList.add(new DataModel(0,R.drawable.iv_kids, Skateboarding));
        dataModelArrayList.add(new DataModel(1,R.drawable.iv_rollerblading,Rollerblading));
        dataModelArrayList.add(new DataModel(2,R.drawable.iv_outdoor_playing, OutdoorPlaying));
        dataModelArrayList.add(new DataModel(3,R.drawable.iv_biking_2,Biking));
        dataModelArrayList.add(new DataModel(4,R.drawable.iv_at_school,AtSchool));
        dataModelArrayList.add(new DataModel(5,R.drawable.iv_other_activities,OtherActivities));
        int spanCount = 2; // 3 columns
        int spacing = 20; // 50px
        boolean includeEdge = true;

        binding.rvKidsmenu.setLayoutFrozen(false);
        binding.rvKidsmenu.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, includeEdge));
//        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.spacing);
//        binding.rvMenu.addItemDecoration(new SpacesItemDecoration(spacingInPixels));

        HomeAdapter homeAdapter = new HomeAdapter(dataModelArrayList,activity);
        binding.rvKidsmenu.setAdapter(homeAdapter);
        binding.rvKidsmenu.setHasFixedSize(true);

    }
}