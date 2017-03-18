package com.defult.eliran.locationlisthw;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.textservice.TextInfo;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.aakira.expandablelayout.ExpandableRelativeLayout;
import com.levelupstudio.recyclerview.ExpandableRecyclerView;

import java.util.List;


public class RecyclerManager extends RecyclerView.Adapter<RecyclerManager.ViewHolder> {
List<LocationObj> allLoc;
    Context context;
    LinearLayout linearLayout;

    public RecyclerManager(List<LocationObj> allLoc, Context context, LinearLayout linearLayout) {
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
        ExpandableRelativeLayout expandableRelativeLayout;
        boolean isexpand=false;


        public ViewHolder(View itemView) {
            super(itemView);
            expandableRelativeLayout= (ExpandableRelativeLayout) itemView.findViewById(R.id.ExpandableLatLngLayout);
            expandableRelativeLayout.toggle();
            CityNameTV= (TextView) itemView.findViewById(R.id.CItyNameTV);
            LatItemTV= (TextView) itemView.findViewById(R.id.LatItemTV);
            LngItemTV= (TextView) itemView.findViewById(R.id.LngItemTV);


        }

        public  void bindData(final LocationObj locationObj)
        {
            CityNameTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (isexpand==false) {
                        expandableRelativeLayout.expand();
                        LatItemTV.setText("lat: " + locationObj.lat);
                        LngItemTV.setText(" lng: " + locationObj.lng);
                        isexpand=true;
                    }
                    else if (isexpand==true)
                    {
                        expandableRelativeLayout.collapse();
                        isexpand=false;
                    }


                   //;; Toast.makeText(context, , Toast.LENGTH_SHORT).show();
                }
            });
            CityNameTV.setText(locationObj.Cityname);
        }
    }
    {

    }
}
