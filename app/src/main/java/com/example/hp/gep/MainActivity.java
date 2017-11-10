package com.example.hp.gep;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

public class MainActivity extends mainpage {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_main, contentFrameLayout);
       // setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.add_event);
        button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent flip =new Intent(MainActivity.this,Main2Activity.class);
                startActivity(flip);
            }
        });


        Button button1 = (Button) findViewById(R.id.view_all);
        button1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent flip1 =new Intent(MainActivity.this,Main3Activity.class);
                startActivity(flip1);
            }
        });


        Button button2 = (Button) findViewById(R.id.view_all);
        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                Intent flip1 =new Intent(MainActivity.this,Main3Activity.class);
                startActivity(flip1);
            }
        });


    }
}
