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


import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

public class LocationListAct extends AppCompatActivity {
ArrayList<LocationObj>allLoc;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SugarContext.init(getApplicationContext());
        setContentView(R.layout.activity_location_list);
        LinearLayout linearLayout= (LinearLayout) findViewById(R.id.RecyclerLayout);
        allLoc= (ArrayList<LocationObj>) LocationObj.listAll(LocationObj.class);
        RecyclerManager adapter=new RecyclerManager(allLoc,this,linearLayout);
        recyclerView= (RecyclerView) findViewById(R.id.LocationRV);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);



    }

}
