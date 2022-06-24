package com.dk.youkol.roomdb;


import android.content.Context;

import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.dk.youkol.roomdb.dbInterface.DataDaoAccess;

@androidx.room.Database(entities = {RoomDataModel.class}, version = 2, exportSchema = false)
public abstract class DatabaseFile extends RoomDatabase {

    public static String DB_NAME = "YoukolDB";

    public abstract DataDaoAccess dataDaoAccess();

    private static DatabaseFile databaseFile;

    public static DatabaseFile getInstance(Context context){
        if (databaseFile == null){
            databaseFile = buildDatabaseInstance(context);
        }
        return databaseFile;
    }

    private static DatabaseFile buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,DatabaseFile.class,DB_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }

}

