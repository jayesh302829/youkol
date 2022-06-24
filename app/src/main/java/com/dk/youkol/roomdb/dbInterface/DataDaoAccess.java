package com.dk.youkol.roomdb.dbInterface;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dk.youkol.roomdb.RoomDataModel;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface DataDaoAccess {

    @Insert(onConflict = IGNORE)
    Long insertTask(RoomDataModel widget);

    @Query("SELECT * FROM RoomDataModel")
    List<RoomDataModel> AllList();

    @Query("SELECT * FROM RoomDataModel")
    LiveData<List<RoomDataModel>> LiveList();

    @Update
    void updateData(RoomDataModel roomDataModel);
}
