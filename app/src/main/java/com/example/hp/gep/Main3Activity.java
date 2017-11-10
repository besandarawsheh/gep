package com.example.hp.gep;
//view all events

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

     Button button1 = (Button) findViewById(R.id.allb);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent flip3 = new Intent(Main3Activity.this, allEvents.class);
                startActivity(flip3);


            }//onClick
        });






    }















}
