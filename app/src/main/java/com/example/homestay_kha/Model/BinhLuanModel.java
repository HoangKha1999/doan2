package com.example.homestay_kha.Model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.util.List;


public class BinhLuanModel implements Parcelable{

    ThanhVienModel thanhVienModel;
    String tieude;
    String manbinhluan;
    String mauser;
    String noidung;
    double chamdiem;
    long luotthich;
    String date_time;
    List<String> hinhanhBinhLuanList;


    public BinhLuanModel(){
    }

    protected BinhLuanModel(Parcel in) {
        thanhVienModel = in.readParcelable(ThanhVienModel.class.getClassLoader());
        tieude = in.readString();
        manbinhluan = in.readString();
        mauser = in.readString();
        noidung = in.readString();
        chamdiem = in.readDouble();
        luotthich = in.readLong();
        date_time = in.readString();
        hinhanhBinhLuanList = in.createStringArrayList();
    }

    public static final Creator<BinhLuanModel> CREATOR = new Creator<BinhLuanModel>() {
        @Override
        public BinhLuanModel createFromParcel(Parcel in) {
            return new BinhLuanModel(in);
        }

        @Override
        public BinhLuanModel[] newArray(int size) {
            return new BinhLuanModel[size];
        }
    };

    public String getManbinhluan() {
        return manbinhluan;
    }

    public void setManbinhluan(String manbinhluan) {
        this.manbinhluan = manbinhluan;
    }



    public List<String> getHinhanhBinhLuanList() {
        return hinhanhBinhLuanList;
    }

    public void setHinhanhBinhLuanList(List<String> hinhanhList) {
        this.hinhanhBinhLuanList = hinhanhList;
    }


    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getMauser() {
        return mauser;
    }

    public void setMauser(String mauser) {
        this.mauser = mauser;
    }

    public double getChamdiem() {
        return chamdiem;
    }

    public void setChamdiem(double chamdiem) {
        this.chamdiem = chamdiem;
    }

    public long getLuotthich() {
        return luotthich;
    }

    public void setLuotthich(long luotthich) {
        this.luotthich = luotthich;
    }

    public ThanhVienModel getThanhVienModel() {
        return thanhVienModel;
    }

    public void setThanhVienModel(ThanhVienModel thanhVienModel) {
        this.thanhVienModel = thanhVienModel;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getTieude() {
        return tieude;
    }

    public void setTieude(String tieude) {
        this.tieude = tieude;
    }


    public void ThemBinhLuan(String maQuanAn, BinhLuanModel binhLuanModel, final List<String> listHinh){
        DatabaseReference nodeBinhLuan = FirebaseDatabase.getInstance().getReference().child("binhluans");
        String mabinhluan =  nodeBinhLuan.child(maQuanAn).push().getKey();
        nodeBinhLuan.child(maQuanAn).child(mabinhluan).setValue(binhLuanModel).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    if(listHinh.size() >= 0){
                        for(String valueHinh : listHinh){
                            Uri uri = Uri.fromFile(new File(valueHinh));
                            StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("hinhanh/"+uri.getLastPathSegment());
                            storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                                }
                            });
                        }
                    }

                }
            }
        });


        if(listHinh.size() > 0){
            for(String valueHinh : listHinh){
                Uri uri = Uri.fromFile(new File(valueHinh));
                FirebaseDatabase.getInstance().getReference().child("hinhanhbinhluans").child(mabinhluan).push().setValue(uri.getLastPathSegment());
            }
        }


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(thanhVienModel, flags);
        dest.writeString(tieude);
        dest.writeString(manbinhluan);
        dest.writeString(mauser);
        dest.writeString(noidung);
        dest.writeDouble(chamdiem);
        dest.writeLong(luotthich);
        dest.writeString(date_time);
        dest.writeStringList(hinhanhBinhLuanList);
    }
}
