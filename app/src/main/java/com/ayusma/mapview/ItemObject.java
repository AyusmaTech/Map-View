package com.ayusma.mapview;

public class ItemObject {

    private String name;
    private String locationName;
    private String position;

    public ItemObject(String name, String locationName,String position) {
        this.name = name;
        this.locationName = locationName;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        this.locationName = locationName;
    }

    public String getName2() {
        return locationName;
    }

    public String getPosition() {
        return position;
    }

    public void setName2(String locationName) {

    }

}
