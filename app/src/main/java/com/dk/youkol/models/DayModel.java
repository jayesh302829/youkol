package com.dk.youkol.models;

import android.graphics.drawable.Drawable;

public class DayModel {

    String dayName,daySortName;
    Drawable drawable;
    boolean isSelected,isEnable;

    public DayModel(String dayName, String daySortName, Drawable drawable, boolean isSelected, boolean isEnable) {
        this.dayName = dayName;
        this.daySortName = daySortName;
        this.drawable = drawable;
        this.isSelected = isSelected;
        this.isEnable = isEnable;
    }

    public String getDayName() {
        return dayName;
    }

    public void setDayName(String dayName) {
        this.dayName = dayName;
    }

    public String getDaySortName() {
        return daySortName;
    }

    public void setDaySortName(String daySortName) {
        this.daySortName = daySortName;
    }

    public Drawable getDrawable() {
        return drawable;
    }

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }
}