package com.defult.eliran.locationlisthw;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.github.aakira.expandablelayout.ExpandableLinearLayout;
import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class RecyclerManager extends RecyclerView.Adapter<RecyclerManager.ViewHolder> {
    List<LocationObj> allLoc;
    Context context;
    LinearLayout linearLayout;
    MapFragment mapFragment;

    public RecyclerManager(List<LocationObj> allLoc, Context context, LinearLayout linearLayout, MapFragment mapFragment) {
        this.mapFragment=mapFragment;
        this.allLoc = allLoc;
        this.context = context;
        this.linearLayout = linearLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.list_item,parent,false );

        ViewHolder viewHolder= new ViewHolder(v);

        return  viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        LocationObj locationObj= allLoc.get(position);

        holder.bindData(locationObj);
    }

    @Override
    public int getItemCount() {
        return allLoc.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView CityNameTV;
        TextView LatItemTV;
        TextView LngItemTV;
        ExpandableLinearLayout expandableLinearLayout;
        LatLng latLng;
        CameraUpdate update;
        GoogleMap CurrentGoogleMap;


        public ViewHolder(View itemView) {
            super(itemView);
            expandableLinearLayout= (ExpandableLinearLayout) itemView.findViewById(R.id.ExpandableLatLngLayout);
            expandableLinearLayout.toggle();
            CityNameTV= (TextView) itemView.findViewById(R.id.CItyNameTV);
            LatItemTV= (TextView) itemView.findViewById(R.id.LatItemTV);
            LngItemTV= (TextView) itemView.findViewById(R.id.LngItemTV);


        }

        public  void bindData(final LocationObj locationObj)
        {
            CityNameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!expandableLinearLayout.isExpanded()) {
                   mapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                            latLng = new LatLng(locationObj.lat, locationObj.lng);
                            update = CameraUpdateFactory.newLatLngZoom(latLng, 17);

                            CurrentGoogleMap = googleMap;
                            googleMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                            googleMap.moveCamera(update);

                        }
                    });

                        expandableLinearLayout.expand();

                                LatItemTV.setText("lat: " + locationObj.lat);
                                LngItemTV.setText(" lng: " + locationObj.lng);
                            } else if (expandableLinearLayout.isExpanded()) {
                        expandableLinearLayout.collapse();
                            }
                        }
                    });
                    CityNameTV.setText(locationObj.Cityname);
                }


            }


}
