package com.example.hp.gep;

/**
 * Created by HP on 28/11/2017.
 */

public class userLocBroadCast {
    private static String latitude;
    private static String longitude;



    public static String getLatitude() {
        return latitude;
    }

    public  static  void setLatitude(String someVariable) {
        latitude = someVariable;
    }

    public static String getLongitude() {
        return longitude;
    }

    public  static  void setLongitude(String someVariable) {
        longitude = someVariable;
    }


}
