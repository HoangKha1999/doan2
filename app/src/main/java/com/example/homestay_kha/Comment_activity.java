package com.example.homestay_kha;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.homestay_kha.Adapers.AdapterHienThiHinhBinhLuanDuocChon;
import com.example.homestay_kha.Controller.BinhLuanController;
import com.example.homestay_kha.Model.BinhLuanModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Comment_activity extends AppCompatActivity {
    RatingBar ratingBar_comment;
    TextView tv_ten, tv_diachi, tv_dangBl;
    BinhLuanController binhLuanController;
    ImageView btnCHonhinh, btn_chupHinh;
    Toolbar toolbar_commment;
    RecyclerView recyclerView_comment;
    AdapterHienThiHinhBinhLuanDuocChon adapterHienThiHinhBinhLuanDuocChon;
    EditText edTieuDeBinhLuan, edNoiDungBinhLuan;
    List<String> listHinhDuocChon;
    final int REQUEST_CHONHINHBINHLUAN = 11;
    final int REQUEST_CHUPHINH = 7;
    String id_hotel, SaveCurrentDate, SaveCurrentTime, date_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        recyclerView_comment = findViewById(R.id.recyclerHinhBinhLuan_comment);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView_comment.setLayoutManager(layoutManager);
        listHinhDuocChon = new ArrayList<>();

        toolbar_commment = findViewById(R.id.toolbar_comment);
        setSupportActionBar(toolbar_commment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy ");
        SaveCurrentDate = currentDate.format(calendar.getTime());
        SimpleDateFormat currentTime = new SimpleDateFormat("h:mm a");
        SaveCurrentTime = currentTime.format(calendar.getTime());
        date_time = " " + SaveCurrentDate;

        ratingBar_comment = findViewById(R.id.rating_bar_commnet);
        edNoiDungBinhLuan = findViewById(R.id.edtNoiDungBinhLuan);
        edTieuDeBinhLuan = findViewById(R.id.edtTieuDeBinhLuan);
        binhLuanController = new BinhLuanController();
        tv_ten = findViewById(R.id.txtTenQuanAn);
        tv_diachi = findViewById(R.id.txtDiaChiQuanAn);
        tv_dangBl = findViewById(R.id.txtDangBinhLuan);
        btnCHonhinh = findViewById(R.id.btnChonHinh);
        btn_chupHinh = findViewById(R.id.btn_chupHinh);
        id_hotel = getIntent().getStringExtra("id_hotel");
        String ten = getIntent().getStringExtra("name_hotel");
        String diachi = getIntent().getStringExtra("diachi");
        tv_ten.setText(ten);
        tv_diachi.setText(diachi);

        btnCHonhinh.setOnClickListener(v -> {
            Intent intent = new Intent(Comment_activity.this, ChonHinhActivity.class);
            startActivityForResult(intent, REQUEST_CHONHINHBINHLUAN);

        });
        btn_chupHinh.setOnClickListener(v -> {
            Intent intent = new Intent(Comment_activity.this, ChupHinhActivity.class);
            startActivity(intent);

        });
        tv_dangBl.setOnClickListener(v -> {
            if (edTieuDeBinhLuan.getText().toString().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập tiêu đề", Toast.LENGTH_SHORT).show();

            } else if (edNoiDungBinhLuan.getText().toString().isEmpty()) {
                Toast.makeText(this, "Vui lòng nhập nội dung ", Toast.LENGTH_SHORT).show();

            } else if (edNoiDungBinhLuan.getText().toString().length() > 500) {
                Toast.makeText(this, "Nhập nội dung ít hơn 500 kí tự", Toast.LENGTH_SHORT).show();


            } else if (ratingBar_comment.getRating() == 0) {
                Toast.makeText(this, "Vui lòng đánh giá chất lượng", Toast.LENGTH_SHORT).show();
            } else {
                BinhLuanModel binhLuanModel = new BinhLuanModel();
                String tieude = edTieuDeBinhLuan.getText().toString();
                String noidung = edNoiDungBinhLuan.getText().toString();
                String mauser = "Dg4e9Phl0fSlChlvkOaEiijwc612";
                binhLuanModel.setTieude(tieude);
                binhLuanModel.setNoidung(noidung);
                binhLuanModel.setChamdiem(ratingBar_comment.getRating());
                binhLuanModel.setDate_time(date_time);
                binhLuanModel.setLuotthich(1);
                binhLuanModel.setMauser(mauser);
                binhLuanController.ThemBinhLuan(id_hotel, binhLuanModel, listHinhDuocChon);
                Toast.makeText(Comment_activity.this, "Đăng bình luận thành công", Toast.LENGTH_SHORT).show();
                finish();
            }


 });
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CHONHINHBINHLUAN) {
            if (resultCode == RESULT_OK) {
                listHinhDuocChon = data.getStringArrayListExtra("listHinhDuocChon");
                adapterHienThiHinhBinhLuanDuocChon = new AdapterHienThiHinhBinhLuanDuocChon(this, R.layout.custom_layout_hienthihinhbinhluan, listHinhDuocChon);
                recyclerView_comment.setAdapter(adapterHienThiHinhBinhLuanDuocChon);
                adapterHienThiHinhBinhLuanDuocChon.notifyDataSetChanged();
            }
        }
    }
}
