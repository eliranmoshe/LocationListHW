package com.defult.eliran.locationlisthw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

public class LocationListAct extends AppCompatActivity {
ArrayList<LocationObj>allLoc;
    RecyclerView recyclerView;
    MapFragment mapFragment;
    LatLng latLng;
    CameraUpdate update;
    GoogleMap CurrentGoogleMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SugarContext.init(getApplicationContext());
        setContentView(R.layout.activity_location_list);
        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.RecyclerLayout);
        allLoc= (ArrayList<LocationObj>) LocationObj.listAll(LocationObj.class);
        mapFragment= new MapFragment();
        getFragmentManager().beginTransaction().add(R.id.MapLL , mapFragment ).commit();
        RecyclerManager adapter=new RecyclerManager(allLoc,this,linearLayout,mapFragment);
        recyclerView= (RecyclerView) findViewById(R.id.LocationRV);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);




        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                latLng= new LatLng(34.2,35.5 );
                update= CameraUpdateFactory.newLatLngZoom(latLng, 17);

                CurrentGoogleMap=googleMap;
                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                googleMap.moveCamera(update);




            }
        });



    }

}
