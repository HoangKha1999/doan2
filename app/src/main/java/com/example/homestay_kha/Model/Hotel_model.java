package com.example.homestay_kha.Model;

import android.location.Location;
import android.location.OnNmeaMessageListener;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;


import androidx.annotation.NonNull;

import com.example.homestay_kha.Controller.Interface.Hotel_Interface;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Hotel_model implements Parcelable {
    private DatabaseReference databaseReference_hotel;
    private String id_hotel, name_hotel,
            opentime_hotel, closetime_hotel,
            video_trailer_hotel, price_min, price_max;
    private List<String> img_hotel, tienich;
    private long like_hotel;
    private List<Hotel_Address_Model> hotel_address_modelList;
    private List<BinhLuanModel> binhLuanModelList;


    public Hotel_model() {
        databaseReference_hotel = FirebaseDatabase.getInstance().getReference();
    }

    public Hotel_model(String id_hotel, String name_hotel, String opentime_hotel, String closetime_hotel, String video_trailer_hotel, String price_min, String price_max, List<String> img_hotel, List<String> tienich,
                       long like_hotel, List<Hotel_Address_Model> hotel_address_modelList,
                       List<BinhLuanModel> binhLuanModelList) {
        this.id_hotel = id_hotel;
        this.name_hotel = name_hotel;
        this.opentime_hotel = opentime_hotel;
        this.closetime_hotel = closetime_hotel;
        this.price_min = price_min;
        this.price_max = price_max;
        this.img_hotel = img_hotel;
        this.tienich = tienich;
        this.video_trailer_hotel = video_trailer_hotel;
        this.like_hotel = like_hotel;
        this.hotel_address_modelList = hotel_address_modelList;
        this.binhLuanModelList = binhLuanModelList;
    }

    public static final Creator<Hotel_model> CREATOR = new Creator<Hotel_model>() {
        @Override
        public Hotel_model createFromParcel(Parcel in) {
            return new Hotel_model(in);
        }

        @Override
        public Hotel_model[] newArray(int size) {
            return new Hotel_model[size];
        }
    };

    public String getPrice_min() {
        return price_min;
    }

    public void setPrice_min(String price_min) {
        this.price_min = price_min;
    }

    public String getPrice_max() {
        return price_max;
    }

    public void setPrice_max(String price_max) {
        this.price_max = price_max;
    }


    protected Hotel_model(Parcel in) {
        id_hotel = in.readString();
        name_hotel = in.readString();
        opentime_hotel = in.readString();
        closetime_hotel = in.readString();
        video_trailer_hotel = in.readString();
        price_min = in.readString();
        price_max = in.readString();
        img_hotel = in.createStringArrayList();
        tienich = in.createStringArrayList();
        like_hotel = in.readLong();
        hotel_address_modelList = in.createTypedArrayList(Hotel_Address_Model.CREATOR);
        binhLuanModelList = in.createTypedArrayList(BinhLuanModel.CREATOR);


    }

    public List<String> getImg_hotel() {
        return img_hotel;
    }

    public void setImg_hotel(List<String> img_hotel) {
        this.img_hotel = img_hotel;
    }

    public List<String> getTienich() {
        return tienich;
    }

    public void setTienich(List<String> tienich) {
        this.tienich = tienich;
    }

    public String getId_hotel() {
        return id_hotel;
    }

    public void setId_hotel(String id_hotel) {
        this.id_hotel = id_hotel;
    }

    public String getName_hotel() {
        return name_hotel;
    }

    public void setName_hotel(String name_hotel) {
        this.name_hotel = name_hotel;
    }

    public String getOpentime_hotel() {
        return opentime_hotel;
    }

    public void setOpentime_hotel(String opentime_hotel) {
        this.opentime_hotel = opentime_hotel;
    }

    public String getClosetime_hotel() {
        return closetime_hotel;
    }

    public void setClosetime_hotel(String closetime_hotel) {
        this.closetime_hotel = closetime_hotel;
    }

    public String getVideo_trailer_hotel() {
        return video_trailer_hotel;
    }

    public void setVideo_trailer_hotel(String video_trailer_hotel) {
        this.video_trailer_hotel = video_trailer_hotel;
    }

    public long getLike_hotel() {
        return like_hotel;
    }

    public void setLike_hotel(long like_hotel) {
        this.like_hotel = like_hotel;
    }

    public List<Hotel_Address_Model> getHotel_address_modelList() {
        return hotel_address_modelList;
    }

    public void setHotel_address_modelList(List<Hotel_Address_Model> hotel_address_modelList) {
        this.hotel_address_modelList = hotel_address_modelList;
    }

    public List<BinhLuanModel> getBinhLuanModelList() {
        return binhLuanModelList;
    }

    public void setBinhLuanModelList(List<BinhLuanModel> binhLuanModelList) {
        this.binhLuanModelList = binhLuanModelList;
    }


    public void getHotel_List(final Hotel_Interface hotel_interface, final Location location_current) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //lấy hinh các khách sạn
                DataSnapshot dataSnapshot_hotel = dataSnapshot.child("hotel");
                for (DataSnapshot value_hotel : dataSnapshot_hotel.getChildren()) {
                    Hotel_model hotel_Model = value_hotel.getValue(Hotel_model.class);
                    DataSnapshot dataSnapshot_img = dataSnapshot.child("img_hotel").child(value_hotel.getKey());
                    hotel_Model.setId_hotel(value_hotel.getKey());
                    List<String> list_img_hotel = new ArrayList<>();
                    for (DataSnapshot value_hotel_img : dataSnapshot_img.getChildren()) {
                        list_img_hotel.add(value_hotel_img.getValue(String.class));
                    }
                    hotel_Model.setImg_hotel(list_img_hotel);
                    // lấy địa chỉ khách sạn
                    DataSnapshot dataSnapshot_address_hotel = dataSnapshot.child("hotel_branch")
                            .child(hotel_Model.getId_hotel());
                    List<Hotel_Address_Model> hotelAdressModelList = new ArrayList<>();
                    for (DataSnapshot value_address_hotel : dataSnapshot_address_hotel.getChildren()) {
                        Hotel_Address_Model adressModel = value_address_hotel.getValue(Hotel_Address_Model.class);
                        Location location_hotel = new Location("");

                        location_hotel.setLongitude(adressModel.getLongitude());
                        location_hotel.setLatitude(adressModel.getLatitude());

                        double distance_to = location_current.distanceTo(location_hotel) / 1000;
                        hotelAdressModelList.add(adressModel);
                        adressModel.setDistance_to(distance_to);
                    }
                    hotel_Model.setHotel_address_modelList(hotelAdressModelList);

                    // lay binh luan
                    List<BinhLuanModel> binhLuanModels = new ArrayList<>();
                    DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(hotel_Model.getId_hotel());

                    for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()) {
                        BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                        binhLuanModel.setManbinhluan(valueBinhLuan.getKey());


                        ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                        binhLuanModel.setThanhVienModel(thanhVienModel);
                        List<String> hinhanhBinhLuanList = new ArrayList<>();
                        DataSnapshot snapshotNodeHinhAnhBL = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getManbinhluan());
                        for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnhBL.getChildren()) {
                            hinhanhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
                        }
                        binhLuanModel.setHinhanhBinhLuanList(hinhanhBinhLuanList);
                        binhLuanModels.add(binhLuanModel);

                    }
                    hotel_Model.setBinhLuanModelList(binhLuanModels);
                    hotel_interface.getHotel_List_Model(hotel_Model);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        databaseReference_hotel.addValueEventListener(valueEventListener);
    }
    public void getHotel_List_daxem(final Hotel_Interface hotel_interface, final Location location_current) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //lấy hinh các khách sạn
                DataSnapshot dataSnapshot_hotel = dataSnapshot.child("daxem");
                for (DataSnapshot value_hotel : dataSnapshot_hotel.getChildren()) {
                    Hotel_model hotel_Model = value_hotel.getValue(Hotel_model.class);
                    DataSnapshot dataSnapshot_img = dataSnapshot.child("img_hotel").child(value_hotel.getKey());
                    hotel_Model.setId_hotel(value_hotel.getKey());
                    List<String> list_img_hotel = new ArrayList<>();
                    for (DataSnapshot value_hotel_img : dataSnapshot_img.getChildren()) {
                        list_img_hotel.add(value_hotel_img.getValue(String.class));
                    }
                    hotel_Model.setImg_hotel(list_img_hotel);
                    // lấy địa chỉ khách sạn
                    DataSnapshot dataSnapshot_address_hotel = dataSnapshot.child("hotel_branch")
                            .child(hotel_Model.getId_hotel());
                    List<Hotel_Address_Model> hotelAdressModelList = new ArrayList<>();
                    for (DataSnapshot value_address_hotel : dataSnapshot_address_hotel.getChildren()) {
                        Hotel_Address_Model adressModel = value_address_hotel.getValue(Hotel_Address_Model.class);
                        Location location_hotel = new Location("");

                        location_hotel.setLongitude(adressModel.getLongitude());
                        location_hotel.setLatitude(adressModel.getLatitude());

                        double distance_to = location_current.distanceTo(location_hotel) / 1000;
                        hotelAdressModelList.add(adressModel);
                        adressModel.setDistance_to(distance_to);
                    }
                    hotel_Model.setHotel_address_modelList(hotelAdressModelList);

                    // lay binh luan
                    List<BinhLuanModel> binhLuanModels = new ArrayList<>();
                    DataSnapshot snapshotBinhLuan = dataSnapshot.child("binhluans").child(hotel_Model.getId_hotel());

                    for (DataSnapshot valueBinhLuan : snapshotBinhLuan.getChildren()) {
                        BinhLuanModel binhLuanModel = valueBinhLuan.getValue(BinhLuanModel.class);
                        binhLuanModel.setManbinhluan(valueBinhLuan.getKey());
                        ThanhVienModel thanhVienModel = dataSnapshot.child("thanhviens").child(binhLuanModel.getMauser()).getValue(ThanhVienModel.class);
                        binhLuanModel.setThanhVienModel(thanhVienModel);
                        List<String> hinhanhBinhLuanList = new ArrayList<>();
                        DataSnapshot snapshotNodeHinhAnhBL = dataSnapshot.child("hinhanhbinhluans").child(binhLuanModel.getManbinhluan());
                        for (DataSnapshot valueHinhBinhLuan : snapshotNodeHinhAnhBL.getChildren()) {
                            hinhanhBinhLuanList.add(valueHinhBinhLuan.getValue(String.class));
                        }
                        binhLuanModel.setHinhanhBinhLuanList(hinhanhBinhLuanList);
                        binhLuanModels.add(binhLuanModel);

                    }
                    hotel_Model.setBinhLuanModelList(binhLuanModels);
                    hotel_interface.getHotel_List_Model(hotel_Model);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        databaseReference_hotel.addValueEventListener(valueEventListener);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id_hotel);
        dest.writeString(name_hotel);
        dest.writeString(opentime_hotel);
        dest.writeString(closetime_hotel);
        dest.writeString(video_trailer_hotel);
        dest.writeString(price_min);
        dest.writeString(price_max);
        dest.writeStringList(img_hotel);
        dest.writeStringList(tienich);
        dest.writeLong(like_hotel);
        dest.writeTypedList(hotel_address_modelList);
        dest.writeTypedList(binhLuanModelList);
    }
}
