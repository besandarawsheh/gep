package com.example.hp.gep;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;

public class editActivity extends AppCompatActivity {

    String HttpURL = "http://192.168.1.70/UpdateEvent.php";
    ProgressDialog progressDialog;
    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    EditText eventName, eventType, eventLocation;
    Button EditEvent;
    String IdHolder, nameHolder, typeHolder, locationHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        eventName = (EditText)findViewById(R.id.editName);
        eventType = (EditText)findViewById(R.id.editEventType);
        eventLocation = (EditText)findViewById(R.id.editEventLocation);

        EditEvent = (Button)findViewById(R.id.UpdateButton);

        // Receive Event ID, Name , TYPE, LOCATION Send by previous SingleEvent  Activity.
        IdHolder = getIntent().getStringExtra("event_id");
        nameHolder = getIntent().getStringExtra("event_name");
        typeHolder = getIntent().getStringExtra("event_type");
        locationHolder = getIntent().getStringExtra("event_location");

        // Setting Received eventName, type, location into EditText.
        eventName.setText(nameHolder);
        eventType.setText(typeHolder);
        eventLocation.setText(locationHolder);

        // Adding click listener to update button .
        EditEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Getting data from EditText after button click.
                GetDataFromEditText();

                // Sending Event Name, type, location to method to update on server.
                EventRecordUpdate(IdHolder,nameHolder,typeHolder, locationHolder);

            }
        });


    }

    // Method to get existing data from EditText.
    public void GetDataFromEditText(){

        nameHolder = eventName.getText().toString();
        typeHolder = eventType.getText().toString();
        locationHolder = eventLocation.getText().toString();

    }

    // Method to Update Event Record.
    public void EventRecordUpdate(final String ID, final String E_Name, final String E_Type, final String E_location){

        class EventRecordUpdateClass extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog = ProgressDialog.show(editActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog.dismiss();

                Toast.makeText(editActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hashMap.put("event_id",params[0]);

                hashMap.put("event_name",params[1]);

                hashMap.put("event_type",params[2]);

                hashMap.put("event_location",params[3]);

                finalResult = httpParse.postRequest(hashMap, HttpURL);

                return finalResult;
            }
        }

        EventRecordUpdateClass eventRecordUpdateClass = new EventRecordUpdateClass();

        eventRecordUpdateClass.execute(ID,E_Name,E_Type,E_location);
    }
}