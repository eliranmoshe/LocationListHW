package com.defult.eliran.locationlisthw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;


import com.orm.SugarContext;

import java.util.ArrayList;
import java.util.List;

public class LocationListAct extends AppCompatActivity {
ListView listView;
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SugarContext.init(getApplicationContext());
        setContentView(R.layout.activity_location_list);
        listView= (ListView) findViewById(R.id.LocationLV);
        List<LocationObj> allLocation=LocationObj.listAll(LocationObj.class);
        adapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,allLocation);
        listView.setAdapter(adapter);



    }

}
