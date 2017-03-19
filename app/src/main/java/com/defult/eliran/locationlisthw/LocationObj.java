package com.defult.eliran.locationlisthw;

import com.orm.SugarRecord;

/**
 * Created by eliran on 3/14/2017.
 */

public class LocationObj extends SugarRecord {
    String Cityname;
    double lat;
    double lng;

    public LocationObj() {
    }

    public LocationObj(String cityname, double lat, double lng) {
        Cityname = cityname;
        this.lat = lat;
        this.lng = lng;
    }

    @Override
    public String toString() {
        return Cityname+"\nlat: "+lat+"\nlng: "+lng;
    }
}
