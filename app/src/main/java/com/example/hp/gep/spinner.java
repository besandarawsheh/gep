package com.example.hp.gep;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

/**
 * Created by HP on 23/10/2017.
 */

public class spinner extends Main2Activity implements AdapterView.OnItemSelectedListener {


    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
     parent.getItemAtPosition(pos);

    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }


    }
