package com.dk.youkol.roomdb;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.List;

public class RepositoryData {
    Context context;
    List<RoomDataModel> roomDataModels = new ArrayList<>();

    public RepositoryData(Context context) {
        this.context = context;
    }

    public void insertTask(RoomDataModel roomDataModel) {
        Thread t = new Thread() {
            public void run() {
                DatabaseFile.getInstance(context).dataDaoAccess().insertTask(roomDataModel);
            }
        };
        t.start();
    }

    public List<RoomDataModel> getAllList() {
        return DatabaseFile.getInstance(context).dataDaoAccess().AllList();
    }

    public LiveData<List<RoomDataModel>> getLiveList() {
        return DatabaseFile.getInstance(context).dataDaoAccess().LiveList();
    }

    public void updateTask(final RoomDataModel roomDataModel) {
        Thread t = new Thread() {
            public void run() {
                try {
                    DatabaseFile.getInstance(context).dataDaoAccess().updateData(roomDataModel);
                } catch (Exception e) {
                    Log.e("TAG", "run: "+e.getMessage() );
                }
            }
        };
        t.start();
    }

}
