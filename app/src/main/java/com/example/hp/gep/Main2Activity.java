package com.example.hp.gep;
//add event info

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;

//import static android.app.AlertDialog.*;

public class Main2Activity extends mainpage {
    // AlertDialog alertDialog;
    EditText nametxt,timetxt,datetxt;
    String typetxt;
    int PLACE_PICKER_REQUEST =1;
    private EditText locationtxt ;
    public double srcxdb;
    public double srcydb;
    public String srcdb;
    final Calendar myCalendar = Calendar.getInstance();

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
                    //mTextMessage.setText(R.string.title_dashboard);
                    Intent in = new Intent(getApplicationContext(),homepage.class);
                    //ntent.putExtra("ListViewValue", eventsID.get(position).toString());

                    startActivity(in);
                    return true;
                case R.id.more:
                    more();
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.ok:
                    onSave();
                    return true;

            }
            return false;
        }

    };




    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_main2, contentFrameLayout);
        timetxt = (EditText) findViewById(R.id.time);

        datetxt= (EditText) findViewById(R.id.date);
       BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.bottomnavmain2);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

       final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                int m=myCalendar.get(Calendar.MONTH)+1;
                int d=myCalendar.get(Calendar.DAY_OF_MONTH);
                int y=myCalendar.get(Calendar.YEAR);
                String event_date =y+"-"+m+"-"+d;
                   // String myFormat = "yyyy-mm-dd"; //In which you need put here
                datetxt.setText(event_date);
                   // SimpleDateFormat sdf = new SimpleDateFormat(event_date, Locale.US);

                  //  datetxt.setText(sdf.format(myCalendar.getTime()));
                }


        };
        timetxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Main2Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        timetxt.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();

            }
        });


        datetxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(Main2Activity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });



        //datetxt = (EditText) findViewById(R.id.date);
        nametxt = (EditText) findViewById(R.id.name);
        locationtxt= (EditText) findViewById(R.id.location);


        locationtxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();

                Intent intent = null;
                try {
                    intent = builder.build(Main2Activity.this);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
                startActivityForResult(intent,PLACE_PICKER_REQUEST);

            }
        });
        //type spinner
        Spinner spin = (Spinner) findViewById(R.id.spinner3);
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


      /* ImageButton button3 = (ImageButton) findViewById(R.id.cancel);
        button3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Spinner sp = (Spinner) findViewById(R.id.spinner);
                String t;
                t= sp.getSelectedItem().toString();
                Toast.makeText(Main2Activity.this, t, Toast.LENGTH_LONG).show();
                if(t.equals("Birthday")||t.equals("Graduation")||(t.equals("Wedding"))){
                    Intent flip4 = new Intent(Main2Activity.this, birthday.class);
                    startActivity(flip4);

                }*/
              /*  else if(t.equals("IT")||t.equals("Education")){

                    Intent f = new Intent(Main2Activity.this, edu.class);
                    startActivity(f);
                }*/



          /// }//onClick
       /// });




    }//onCREAte

    private void more() {

        Spinner sp = (Spinner) findViewById(R.id.spinner3);
        String t;
        t= sp.getSelectedItem().toString();
        Toast.makeText(Main2Activity.this, t, Toast.LENGTH_LONG).show();
        if(t.equals("Birthday")||t.equals("Graduation")||(t.equals("Wedding"))){
            Intent flip4 = new Intent(Main2Activity.this, birthday.class);
            flip4.putExtra("t",t);
            startActivity(flip4);

        }
              else if(t.equals("IT")||t.equals("Education")){

                    Intent f = new Intent(Main2Activity.this, edu.class);
            f.putExtra("t",t);
                    startActivity(f);


    }}
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(getApplicationContext(), data);
                String toastMsg = String.format("%s", place.getAddress());
                locationtxt.setText(toastMsg);
                final LatLng location = place.getLatLng();
                Toast.makeText(this,locationtxt.getText(),Toast.LENGTH_LONG).show();
                Toast.makeText(this,toastMsg,Toast.LENGTH_LONG).show();

                srcxdb = location.latitude;
                srcydb =location.longitude;
                srcdb=toastMsg;
                Toast.makeText(this,"lat"+location.latitude+"long"+location.longitude,Toast.LENGTH_LONG).show();
            }
        }
    }


    public void onSave(){
        Spinner sp = (Spinner) findViewById(R.id.spinner3);
        String email =Email.getEmail();
        String password=getIntent().getStringExtra("password");
        typetxt=sp.getSelectedItem().toString();
        Toast.makeText(Main2Activity.this, typetxt, Toast.LENGTH_LONG).show();
        String etype=typetxt;
        String ename= nametxt.getText().toString();
        String etime= timetxt.getText().toString();
        String edate= datetxt.getText().toString();
        String elocation=locationtxt.getText().toString();
        String lat= ""+srcxdb;
        String log= ""+srcydb;
        String epage="addedEventInfo";
        BackgroundWorker backGroundWorker = new BackgroundWorker(this);
        //Toast.makeText(Main2Activity.this,email,LENGTH_SHORT).show();
        //Toast.makeText(Main2Activity.this,password,LENGTH_SHORT).show();
        backGroundWorker.execute(epage,ename,etime,edate,elocation,etype,email,password,lat,log);


    }

    ///clearing time and date edit texts
    public void click(View view){
        timetxt.setText(" ");


    }
   /* public void click1 (View view){

        datetxt.setText(" ");
    }*/


}

