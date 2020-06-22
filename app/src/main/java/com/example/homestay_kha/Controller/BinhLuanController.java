package com.example.homestay_kha.Controller;

import com.example.homestay_kha.Model.BinhLuanModel;


import java.util.List;

/**
 * Created by Binh on 6/14/17.
 */

public class BinhLuanController {
    BinhLuanModel binhLuanModel;

    public  BinhLuanController(){
        binhLuanModel = new BinhLuanModel();
    }

    public void ThemBinhLuan(String maQuanAn, BinhLuanModel binhLuanModel, List<String> listHinh){
        binhLuanModel.ThemBinhLuan(maQuanAn,binhLuanModel,listHinh);
    }
}
