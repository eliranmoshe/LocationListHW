package com.defult.eliran.locationlisthw;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.orm.SugarContext;
import com.orm.SugarDb;

public class MainActivity extends AppCompatActivity implements LocationListener {
    Location currentLocation;
    LocationManager locationManager;
    TextView currentLocationTV;
    EditText CityET;
    double lat=34.5;
    double lng=32.5;
    MapFragment mapFragment;
    CameraUpdate update;
    GoogleMap CurrentGoogleMap;
    LatLng latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SugarContext.init(getApplicationContext());
        setContentView(R.layout.activity_main);
        currentLocationTV= (TextView) findViewById(R.id.LocationTV);
        CityET= (EditText) findViewById(R.id.CityET);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        ((Button)findViewById(R.id.SaveBtn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CityName=CityET.getText().toString();
                CityName.trim();
                LocationObj currentobj=new LocationObj(CityET.getText().toString(),lat,lng);
                if (CityName.equals(""))
                {
                    LinearLayout mainLayout= (LinearLayout) findViewById(R.id.activity_main);
                    //TODO SNACKBAR
                    Snackbar snackbar = Snackbar
                            .make(mainLayout, "No City Name", Snackbar.LENGTH_LONG)
                            .setDuration(5000);

                    snackbar.show();

                }else {
                    currentobj.save();
                }

            }
        });
        mapFragment= new MapFragment();

        getFragmentManager().beginTransaction().add(R.id.MapContainer , mapFragment ).commit();

        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

                latLng= new LatLng(lat,lng );
                update= CameraUpdateFactory.newLatLngZoom(latLng, 17);

                CurrentGoogleMap=googleMap;
                googleMap.addMarker(new MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                googleMap.moveCamera(update);




            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        //check if has permission
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION);

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            //requestLocationUpdates(the provider used to get location - gps/network , refresh time milliseconds ,minimum refresh distance,
            //location listener)
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, MainActivity.this);
        } else {
            //request permission 12 is the request number
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 12);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        locationManager.removeUpdates(this);
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation=location;
        lat=currentLocation.getLatitude();
        lng=currentLocation.getLongitude();
        latLng= new LatLng(lat,lng );
        update= CameraUpdateFactory.newLatLngZoom(latLng, 17);
        CurrentGoogleMap.addMarker(new MarkerOptions()
                .position(latLng)
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        CurrentGoogleMap.moveCamera(update);
        currentLocationTV.setText("yout current location is :  \nlat: "+lat+"\nlng: "+lng);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.GoToListBtn)
        {
            Intent intent=new Intent(MainActivity.this,LocationListAct.class);
            startActivity(intent);
        }
        return true;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 12) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                LocationManager locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                }
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 1, MainActivity.this);
            } else {
                Toast.makeText(MainActivity.this, "you must open the gps permission!", Toast.LENGTH_SHORT).show();
            }
        }


    }

}
