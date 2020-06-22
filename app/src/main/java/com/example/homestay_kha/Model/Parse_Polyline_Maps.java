package com.example.homestay_kha.Model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.PolyUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Parse_Polyline_Maps {
    public static List<LatLng> Get_Coordinates_List(String DataJson)
    {
        List<LatLng> latLngs  =new ArrayList<>();
        try {
            JSONObject jsonObject  =new JSONObject(DataJson);

            JSONArray routes  = jsonObject.getJSONArray("routes");

            for (int i =  0 ; i < routes.length();i++){
                JSONObject object = routes.getJSONObject(i);
                JSONObject overview_polyline = object.getJSONObject("overview_polyline");
                String points = overview_polyline.getString("points");
                latLngs = PolyUtil.decode(points);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  latLngs;
    }
}
