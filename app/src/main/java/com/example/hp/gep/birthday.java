package com.example.hp.gep;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class birthday extends mainpage {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.home:
                    Intent i = new Intent(getApplicationContext(),firsthome.class);
                    //ntent.putExtra("ListViewValue", eventsID.get(position).toString());
                    startActivity(i);
                    // mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.ret:
                    Intent b = new Intent(getApplicationContext(),Main2Activity.class);
                    //ntent.putExtra("ListViewValue", eventsID.get(position).toString());
                    startActivity(b);
                    return true;
                case R.id.done:
                   // shareMyLoc.setAllowence(0);
                    //onDestroy();
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.create_event:

                 return  true;


            }
            return false;
        }

    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_birthday, contentFrameLayout);
        LinearLayout ll = (LinearLayout) findViewById(R.id.container);
         final String t = getIntent().getStringExtra("t");

        if( t.equals("Birthday")) {
            ll.setBackgroundResource(R.drawable.bebirth);
        }

        if( t.equals("Graduation")) {
            ll.setBackgroundResource(R.drawable.grad);

        }
        TextView tv = (TextView) this.findViewById(R.id.wish);
        tv.setSelected(true);


      /*  Typeface tf = Typeface.createFromAsset(getAssets(),
                "fonts");
        TextView tv = (TextView) findViewById(R.id.textView15);
        tv.setTypeface(tf);*/


    }
}
