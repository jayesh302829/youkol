package com.dk.youkol.roomdb;

import androidx.room.TypeConverter;

import com.dk.youkol.models.DayModel;
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Converters {
    @TypeConverter
    public static ArrayList<DayModel> fromString(String value) {
        Type listType = new TypeToken<ArrayList<DayModel>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromArrayList(ArrayList<DayModel> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}