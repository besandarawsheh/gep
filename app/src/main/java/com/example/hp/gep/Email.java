package com.example.hp.gep;

import android.app.Application;

/**
 * Created by HP on 06/11/2017.
 */

public  class Email {
    private static String email;
private static  int activitynum ;
    public static int getANum() {
        return activitynum;
    }

    public  static  void setANum(int someVariable) {
        activitynum = someVariable;
    }


    public static String getEmail() {
        return email;
    }

    public  static  void setEmail(String someVariable) {
       email = someVariable;
    }


}
