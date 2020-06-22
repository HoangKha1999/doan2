package com.example.homestay_kha;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.homestay_kha.Adapers.AdapterBinhLuan;
import com.example.homestay_kha.Model.Hotel_model;
import com.example.homestay_kha.Model.Tien_nghi_model;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Hotel_detail_Activity extends AppCompatActivity implements OnMapReadyCallback, MapboxMap.OnMapClickListener {
    ImageView img_hotel_detail, imgPlayTrailer;
    TextView name_hotel_detail, address_hotel_detail;
    Hotel_model hotelModel;
    Button lay_map_hotel;
    double longitude, latitude;
    Toolbar toolbar_hotel_detail;
    MapView mapView;
    private MapboxMap mapboxMap;
    LinearLayout khungtienich;
    TextView tv_price, tv_time;
    TextView btn_binhluan;
    AdapterBinhLuan adapterBinhLuan;
    RecyclerView recyclerView_comment_detail;
    TextView tv_trangthai;
    NestedScrollView scrollView_detail;
    DatabaseReference databaseReference;
    VideoView videoView_detail;

    StorageReference storageVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Mapbox.getInstance(this, getString(R.string.access_token));
        setContentView(R.layout.activity_hotel_detail);

        videoView_detail = findViewById(R.id.video_view_detail);
        hotelModel = getIntent().getParcelableExtra("hotel");


        btn_binhluan = findViewById(R.id.btn_binhluan);
        recyclerView_comment_detail = findViewById(R.id.recyclerview_comment_detail);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView_comment_detail.setLayoutManager(layoutManager);

        toolbar_hotel_detail = findViewById(R.id.toolbar_detail_hotel);
        setSupportActionBar(toolbar_hotel_detail);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        latitude = hotelModel.getHotel_address_modelList().get(0).getLatitude();
        longitude = hotelModel.getHotel_address_modelList().get(0).getLongitude();

        tv_price = findViewById(R.id.tv_price_detail);
        tv_time = findViewById(R.id.tv_time);
        scrollView_detail = findViewById(R.id.scrollView_detail);
        img_hotel_detail = findViewById(R.id.img_hotel_detail);
        name_hotel_detail = findViewById(R.id.name_hotel_deail);
        address_hotel_detail = findViewById(R.id.address_hotel_detail);
        lay_map_hotel = findViewById(R.id.lay_map_hotel);
        mapView = findViewById(R.id.map_box_detail);
        khungtienich = findViewById(R.id.khungtenich);
        tv_trangthai = findViewById(R.id.tv_trangthai);
        imgPlayTrailer = findViewById(R.id.image_play_detail);


        mapView.getMapAsync(this);
        mapView.onCreate(savedInstanceState);
        lay_map_hotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hotel_detail_Activity.this, Maps_Hotel_Activity.class);
                intent.putExtra("latitude_hotel", latitude);
                intent.putExtra("longitude_hotel", longitude);
                startActivity(intent);
            }
        });
        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("daxem").child(hotelModel.getId_hotel()).setValue(hotelModel);
        Hienthichitiet();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mapView.onStart();


    }
    private  void Hienthichitiet(){
        name_hotel_detail.setText(hotelModel.getName_hotel());
        address_hotel_detail.setText(hotelModel.getHotel_address_modelList().get(0).getAddress());
        tv_price.setText("Giá phòng: " + hotelModel.getPrice_min() + " VNĐ " + " - " + hotelModel.getPrice_max() + "VNĐ");
        HienThiGioKhachSan();
        getTienich();

        //video_trailer
        if (hotelModel.getVideo_trailer_hotel() == null){
            videoView_detail.setVisibility(View.GONE);
            imgPlayTrailer.setVisibility(View.GONE);
            img_hotel_detail.setVisibility(View.VISIBLE);
            StorageReference storageReference =
                    FirebaseStorage.getInstance().getReference().child("img_hotel").child(hotelModel.getImg_hotel().get(0));
            final long ONE_MEGABYTE = 1024 * 1024;
            storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                @Override
                public void onSuccess(byte[] bytes) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    img_hotel_detail.setImageBitmap(bitmap);
                }
            });
            
        }
        else if (hotelModel.getVideo_trailer_hotel() != null) {
            img_hotel_detail.setVisibility(View.GONE);
            videoView_detail.setVisibility(View.VISIBLE);
            imgPlayTrailer.setVisibility(View.VISIBLE);
            storageVideo = FirebaseStorage.getInstance().getReference().child("video").child(hotelModel.getVideo_trailer_hotel());
            storageVideo.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    videoView_detail.setVideoURI(uri);
                    videoView_detail.seekTo(1);
                }
            });

            imgPlayTrailer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    videoView_detail.start();
                    MediaController mediaController = new MediaController(Hotel_detail_Activity.this);
                    videoView_detail.setMediaController(mediaController);
                    imgPlayTrailer.setVisibility(View.GONE);
                }
            });

        }

        btn_binhluan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Hotel_detail_Activity.this, Comment_activity.class);
                intent.putExtra("id_hotel", hotelModel.getId_hotel());
                intent.putExtra("name_hotel", hotelModel.getName_hotel());
                intent.putExtra("diachi", hotelModel.getHotel_address_modelList().get(0).getAddress());
                startActivity(intent);
            }
        });
        adapterBinhLuan = new AdapterBinhLuan(this, R.layout.custom_layout_binhluan, hotelModel.getBinhLuanModelList());
        recyclerView_comment_detail.setAdapter(adapterBinhLuan);
        adapterBinhLuan.notifyDataSetChanged();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
        khungtienich.removeAllViewsInLayout();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onMapReady(@NonNull MapboxMap mapboxMap) {
        this.mapboxMap = mapboxMap;
        mapboxMap.setStyle(Style.LIGHT, new Style.OnStyleLoaded() {
            @Override
            public void onStyleLoaded(@NonNull Style style) {
                mapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(latitude, longitude))
                        .title(hotelModel.getName_hotel()));
                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(latitude, longitude))
                        .zoom(18)
                        .build();
                mapboxMap.animateCamera(CameraUpdateFactory.newCameraPosition(position), 2000);

            }
        });

    }

    @Override
    public boolean onMapClick(@NonNull LatLng point) {
        return false;
    }

    public void getTienich() {
        for (String matienich : hotelModel.getTienich()) {
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("tienich").child(matienich);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Tien_nghi_model tienNghiModel = dataSnapshot.getValue(Tien_nghi_model.class);
                    StorageReference storageReference =
                            FirebaseStorage.getInstance().getReference().child("tiennich").child(tienNghiModel.getHinhtienich());
                    final long ONE_MEGABYTE = 1024 * 1024;
                    storageReference.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
                        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
                        @Override
                        public void onSuccess(byte[] bytes) {
                            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                            ImageView imageView = new ImageView(Hotel_detail_Activity.this);
                            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(50, 50);
                            imageView.setLayoutParams(layoutParams);
                            imageView.setImageBitmap(bitmap);
                            LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                            TextView tv_tienich = new TextView(Hotel_detail_Activity.this);
                            tv_tienich.setText(tienNghiModel.getTentienich());
                            tv_tienich.setLayoutParams(layoutParams1);
                            layoutParams1.setMarginEnd(5);
                            imageView.setPadding(5, 5, 5, 5);
                            khungtienich.addView(imageView);
                            khungtienich.addView(tv_tienich);
                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    }

    private void HienThiGioKhachSan() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

        String giohientai = dateFormat.format(calendar.getTime());
        String giomocua = hotelModel.getOpentime_hotel();
        String giodongcua = hotelModel.getClosetime_hotel();

        try {
            Date dateHienTai = dateFormat.parse(giohientai);
            Date dateMoCua = dateFormat.parse(giomocua);
            Date dateDongCua = dateFormat.parse(giodongcua);

            if (dateHienTai.after(dateMoCua) && dateHienTai.before(dateDongCua)) {
                //gio mo cua
                tv_time.setText("Giờ mở cửa: " + hotelModel.getOpentime_hotel() + " - " + hotelModel.getClosetime_hotel());
                tv_trangthai.setText("Đang mở cửa");
            } else if (dateMoCua.equals(dateDongCua)) {
                tv_time.setText("Giờ mở cửa: Luôn mở cửa");
                tv_trangthai.setVisibility(View.INVISIBLE);
            } else {
                //dong cua
                tv_time.setText("Giờ mở cửa: " + hotelModel.getOpentime_hotel() + " - " + hotelModel.getClosetime_hotel());
                tv_trangthai.setText("Đã đóng cửa");
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }


    }
}
