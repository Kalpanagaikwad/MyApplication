package com.myapplication.map.Orders;

public class Orders {
    private String name;

    private Location location;

    private int phone;

    private String address;

    public void setName(String name){
        this.name = name;
    }
    public String getName(){
        return this.name;
    }
    public void setLocation(Location location){
        this.location = location;
    }
    public Location getLocation(){
        return this.location;
    }
    public void setPhone(int phone){
        this.phone = phone;
    }
    public int getPhone(){
        return this.phone;
    }
    public void setAddress(String address){
        this.address = address;
    }
    public String getAddress(){
        return this.address;
    }
}