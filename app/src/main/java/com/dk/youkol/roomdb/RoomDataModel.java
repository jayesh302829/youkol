package com.dk.youkol.roomdb;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class RoomDataModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    @ColumnInfo(name = "type")
    public String type;

    @ColumnInfo(name = "deviceAllow")
    public String deviceAllow;

    @ColumnInfo(name = "isNewarby")
    public String isNewarby;

    @ColumnInfo(name = "nearbyDistance")
    public String nearbyDistance;

    @ColumnInfo(name = "locationBase")
    public String locationBase;

    @ColumnInfo(name = "isSpeedBase")
    public String isSpeedBase;

    @ColumnInfo(name = "speed")
    public String speed;

    @ColumnInfo(name = "isTimeBase")
    public String isTimeBase;

    @ColumnInfo(name = "startTime")
    public String startTime;

    @ColumnInfo(name = "endTime")
    public String endTime;

    @ColumnInfo(name = "selectedDays")
    public String selectedDays;

    @ColumnInfo(name = "policyApply")
    public String policyApply;

    @ColumnInfo(name = "notification")
    public String notification;

    @ColumnInfo(name = "isReset")
    public boolean isReset;

    public boolean isReset() {
        return isReset;
    }

    public void setReset(boolean reset) {
        isReset = reset;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDeviceAllow() {
        return deviceAllow;
    }

    public void setDeviceAllow(String deviceAllow) {
        this.deviceAllow = deviceAllow;
    }

    public String getIsNewarby() {
        return isNewarby;
    }

    public void setIsNewarby(String isNewarby) {
        this.isNewarby = isNewarby;
    }

    public String getNearbyDistance() {
        return nearbyDistance;
    }

    public void setNearbyDistance(String nearbyDistance) {
        this.nearbyDistance = nearbyDistance;
    }

    public String getLocationBase() {
        return locationBase;
    }

    public void setLocationBase(String locationBase) {
        this.locationBase = locationBase;
    }

    public String getIsSpeedBase() {
        return isSpeedBase;
    }

    public void setIsSpeedBase(String isSpeedBase) {
        this.isSpeedBase = isSpeedBase;
    }

    public String getSpeed() {
        return speed;
    }

    public void setSpeed(String speed) {
        this.speed = speed;
    }

    public String getIsTimeBase() {
        return isTimeBase;
    }

    public void setIsTimeBase(String isTimeBase) {
        this.isTimeBase = isTimeBase;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSelectedDays() {
        return selectedDays;
    }

    public void setSelectedDays(String selectedDays) {
        this.selectedDays = selectedDays;
    }

    public String getPolicyApply() {
        return policyApply;
    }

    public void setPolicyApply(String policyApply) {
        this.policyApply = policyApply;
    }

    public String getNotification() {
        return notification;
    }

    public void setNotification(String notification) {
        this.notification = notification;
    }
}
