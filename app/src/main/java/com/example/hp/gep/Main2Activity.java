package com.example.hp.gep;
//add event info

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

//import static android.app.AlertDialog.*;

public class Main2Activity extends AppCompatActivity {
   // AlertDialog alertDialog;
    EditText nametxt,timetxt,datetxt,locationtxt;
    String typetxt;


    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        timetxt = (EditText) findViewById(R.id.time);
        datetxt = (EditText) findViewById(R.id.date);
        nametxt = (EditText) findViewById(R.id.name);
        locationtxt= (EditText) findViewById(R.id.location);
        //type spinner
        Spinner spin = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(this,
                R.array.type_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spin.setAdapter(adap);



        //spinnerlistener
        spin.setOnItemSelectedListener(new spinner());
       // typetxt=spin.getSelectedItem().toString();


        ImageButton button3 = (ImageButton) findViewById(R.id.cancel);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Spinner sp = (Spinner) findViewById(R.id.spinner);
String t;
                t= sp.getSelectedItem().toString();
               Toast.makeText(Main2Activity.this, t, Toast.LENGTH_LONG).show();
                if(t.equals("Birthday")){
                   Intent flip4 = new Intent(Main2Activity.this, birthday.class);
                    startActivity(flip4);

                }



            }//onClick
            });
    }//onCREAte



    public void onSave(View view){
        Spinner sp = (Spinner) findViewById(R.id.spinner);
        String email =getIntent().getStringExtra("email");
        String password=getIntent().getStringExtra("password");
        typetxt=sp.getSelectedItem().toString();
        Toast.makeText(Main2Activity.this, typetxt, Toast.LENGTH_LONG).show();
        String etype=typetxt;
        String ename= nametxt.getText().toString();
        String etime= timetxt.getText().toString();
        String edate= datetxt.getText().toString();
        String elocation=locationtxt.getText().toString();
        String epage="addedEventInfo";
        BackgroundWorker backGroundWorker = new BackgroundWorker(this);
        //Toast.makeText(Main2Activity.this,email,LENGTH_SHORT).show();
        //Toast.makeText(Main2Activity.this,password,LENGTH_SHORT).show();
        backGroundWorker.execute(epage,ename,etime,edate,elocation,etype,email,password);


    }

///clearing time and date edit texts
    public void click(View view){
       timetxt.setText(" ");


    }
public void click1 (View view){

    datetxt.setText(" ");
}

    }

