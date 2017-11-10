package com.example.hp.gep;

import android.app.Application;

/**
 * Created by HP on 06/11/2017.
 */

public  class Email {
    private static String email;

    public static String getEmail() {
        return email;
    }

    public  static  void setEmail(String someVariable) {
       email = someVariable;
    }


}
