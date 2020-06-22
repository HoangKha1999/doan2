package com.example.homestay_kha.Controller;

import com.example.homestay_kha.Model.DownLoad_Polyline_Maps;
import com.example.homestay_kha.R;
import com.example.homestay_kha.Model.Parse_Polyline_Maps;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MapsHotel_Cotroller {
    DownLoad_Polyline_Maps downLoad_polyline_maps;
    Parse_Polyline_Maps parse_polyline_maps;

    public MapsHotel_Cotroller() {
    }
    public void Show_Route(GoogleMap googleMap, String link){
    downLoad_polyline_maps = new DownLoad_Polyline_Maps() ;
    parse_polyline_maps = new Parse_Polyline_Maps();
    downLoad_polyline_maps.execute(link);
        try {
            String dataJson = downLoad_polyline_maps.get();
            List<LatLng> latLngList  = parse_polyline_maps.Get_Coordinates_List(dataJson);
            PolylineOptions polylineOptions = new  PolylineOptions();
            for (LatLng coordinates : latLngList){
                polylineOptions.add(coordinates);
                polylineOptions.color(R.color.colorAccent);
                polylineOptions.width(3);
            }
            Polyline polyline  = googleMap.addPolyline(polylineOptions);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
