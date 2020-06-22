package com.example.homestay_kha.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Hotel_Address_Model implements Parcelable {
    private String address;
    private double longitude;
    private double latitude;
    private double distance_to;
    public Hotel_Address_Model(){}

    public Hotel_Address_Model(String address, double longitude, double latitude, double distance_to) {
        this.address = address;
        this.longitude = longitude;
        this.latitude = latitude;
        this.distance_to = distance_to;
    }


    protected Hotel_Address_Model(Parcel in) {
        address = in.readString();
        longitude = in.readDouble();
        latitude = in.readDouble();
        distance_to = in.readDouble();
    }

    public static final Creator<Hotel_Address_Model> CREATOR = new Creator<Hotel_Address_Model>() {
        @Override
        public Hotel_Address_Model createFromParcel(Parcel in) {
            return new Hotel_Address_Model(in);
        }

        @Override
        public Hotel_Address_Model[] newArray(int size) {
            return new Hotel_Address_Model[size];
        }
    };

    public double getDistance_to() {
        return distance_to;
    }

    public void setDistance_to(double distance_to) {
        this.distance_to = distance_to;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(address);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeDouble(distance_to);
    }
}
