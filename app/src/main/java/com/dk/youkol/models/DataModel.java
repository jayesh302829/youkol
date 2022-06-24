package com.dk.youkol.models;

public class DataModel {
    int id,imageId;
    String name;
    boolean isSelected;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public DataModel(int id, int imageId, String name, boolean isSelected) {
        this.id = id;
        this.imageId = imageId;
        this.name = name;
        this.isSelected = isSelected;
    }
}
