package com.example.homestay_kha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.homestay_kha.Adapers.AdapterChonHinhBinhLuan;
import com.example.homestay_kha.Model.ChonHinhBinhLuanModel;

import java.util.ArrayList;
import java.util.List;

public class ChonHinhActivity extends AppCompatActivity {
    List<ChonHinhBinhLuanModel>listDuongDan;
    List<String> listHinhDuocChon;
    AdapterChonHinhBinhLuan adapterChonHinhBinhLuan;
    RecyclerView recyclerChonHinhBinhLuan;
    Toolbar toolbar_chonHinh;
    TextView tv_Xong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chon_hinh);
        toolbar_chonHinh = findViewById(R.id.toolbar_chon_hinh);
        setSupportActionBar(toolbar_chonHinh);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        listDuongDan = new ArrayList<>();
        listHinhDuocChon = new ArrayList<>();

        recyclerChonHinhBinhLuan = findViewById(R.id.recyclerChonHinhBinhLuan);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,2);
        adapterChonHinhBinhLuan = new AdapterChonHinhBinhLuan(this,R.layout.custom_layout_chonhinhbinhluan,listDuongDan);
        recyclerChonHinhBinhLuan.setLayoutManager(layoutManager);
        recyclerChonHinhBinhLuan.setAdapter(adapterChonHinhBinhLuan);

        int checkReadExStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (checkReadExStorage != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            getTatCaHinhAnhTrongTheNho();
        }
        tv_Xong = findViewById(R.id.tv_xong_chonhinh);
        tv_Xong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(ChonHinhBinhLuanModel value : listDuongDan){
                    if(value.isCheck()){
                        listHinhDuocChon.add(value.getDuongdan());
                    }
                }
                Intent data = getIntent();
                data.putStringArrayListExtra("listHinhDuocChon", (ArrayList<String>) listHinhDuocChon);
                setResult(RESULT_OK,data);
                finish();
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getTatCaHinhAnhTrongTheNho();
            }
        }
    }
    public void getTatCaHinhAnhTrongTheNho() {
        String [] projection = {MediaStore.Images.Media.DATA};
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            String duongdan = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            ChonHinhBinhLuanModel chonHinhBinhLuanModel = new ChonHinhBinhLuanModel(duongdan, false);
            listDuongDan.add(chonHinhBinhLuanModel);
            adapterChonHinhBinhLuan.notifyDataSetChanged();
            cursor.moveToNext();
        }
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

}
