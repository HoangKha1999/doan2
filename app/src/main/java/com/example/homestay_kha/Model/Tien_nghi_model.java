package com.example.homestay_kha.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.homestay_kha.Controller.Interface.Tien_nghi_Interface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Tien_nghi_model implements Parcelable {
    private String id_tien_nghi, tentienich, hinhtienich;
    DatabaseReference databaseReference;
    public Tien_nghi_model(){
        databaseReference = FirebaseDatabase.getInstance().getReference();}

    public Tien_nghi_model(String id_tien_nghi, String tentienich, String hinhtienich) {
        this.id_tien_nghi = id_tien_nghi;
        this.tentienich = tentienich;
        this.hinhtienich = hinhtienich;
    }

    protected Tien_nghi_model(Parcel in) {
        id_tien_nghi = in.readString();
        tentienich = in.readString();
        hinhtienich = in.readString();
    }

    public static final Creator<Tien_nghi_model> CREATOR = new Creator<Tien_nghi_model>() {
        @Override
        public Tien_nghi_model createFromParcel(Parcel in) {
            return new Tien_nghi_model(in);
        }

        @Override
        public Tien_nghi_model[] newArray(int size) {
            return new Tien_nghi_model[size];
        }
    };

    public String getId_tien_nghi() {
        return id_tien_nghi;
    }

    public void setId_tien_nghi(String id_tien_nghi) {
        this.id_tien_nghi = id_tien_nghi;
    }

    public String getTentienich() {
        return tentienich;
    }

    public void setTentienich(String tentienich) {
        this.tentienich = tentienich;
    }

    public String getHinhtienich() {
        return hinhtienich;
    }

    public void setHinhtienich(String hinhtienich) {
        this.hinhtienich = hinhtienich;
    }
    public void getTienNghiModel(Tien_nghi_Interface tien_nghi_interface)
    {

        ValueEventListener valueEventListener =  new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                DataSnapshot dataSnapshot_tiennghi_hotel =  dataSnapshot.child("hotel").child("tienich");


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_tien_nghi);
        dest.writeString(tentienich);
        dest.writeString(hinhtienich);
    }
}
