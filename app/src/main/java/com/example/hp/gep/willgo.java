package com.example.hp.gep;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
public class willgo extends AppCompatActivity {

    HttpParse httpParse = new HttpParse();
    ProgressDialog pDialog;

    // Http Url For Filter Student Data from Id Sent from previous activity.
    String filterED_url= "http://192.168.1.108/FilterEventData.php";
    String willcomeURL = "http://192.168.1.108/willcome.php";
    // Http URL for delete Already Open Student Record.
    String delE_url = "http://192.168.1.108/DeleteEvent.php";
    String canturl = "http://192.168.1.108/cant.php";
    String interstedURL = "http://192.168.1.108/intersted.php";

    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    String ParseResult ;
    HashMap<String,String> ResultHash = new HashMap<>();
    String FinalJSonObject ;
    TextView name,type,location;
    String nameHolder, typeHolder, locationHolder;
    RadioButton willcomeButton, cantButton ,InterstedButton ;
    String TempItem;
    String email,password,id;
    ProgressDialog progressDialog2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_willgo);

        name = (TextView)findViewById(R.id.textName);
        type = (TextView)findViewById(R.id.textType);
        location = (TextView)findViewById(R.id.textLocation);

        willcomeButton = (RadioButton)findViewById(R.id.buttonEdit);
        cantButton = (RadioButton)findViewById(R.id.buttonDelete);
        InterstedButton=(RadioButton)findViewById(R.id.buttoninterst);

        //Receiving the ListView Clicked item value send by previous activity.
        TempItem = getIntent().getStringExtra("ListViewValue");
        email=getIntent().getStringExtra("email");
        password=getIntent().getStringExtra("password");
        id=getIntent().getStringExtra("id");

        //Calling method to filter Event Record and open selected record.
        HttpWebCall(TempItem);



        willcomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                willcome(TempItem);

           /*     Intent intent = new Intent(SingleEvent.this,editActivity.class);

                // Sending Event Id, Name, type and location to next EditActivity.
                intent.putExtra("event_id", TempItem);
                intent.putExtra("event_name", nameHolder);
                intent.putExtra("event_type", typeHolder);
                intent.putExtra("event_location", locationHolder);

                startActivity(intent);

                // Finishing current activity after opening next activity.
                finish();*/

            }
        });

        // Add Click listener on Delete button.
        cantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling Event delete method to delete current record using Event ID.
                Eventcant(TempItem);

            }
        });
        InterstedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling Event delete method to delete current record using Event ID.
                intersted(TempItem);

            }
        });

    }
    // Method to willcome Record
    public void willcome(final String EventID) {
        class willcomeClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog2 = ProgressDialog.show(willgo.this, "Loading Data", null, true, true);
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog2.dismiss();
                Toast.makeText(willgo.this, "You  sign successfuly", Toast.LENGTH_LONG).show();
                //  finish();
            }
            @Override
            protected String doInBackground(String... params) {
                // Sending Event id.
                hashMap.put("event_id", params[0]);
                hashMap.put("email", params[1]);
                hashMap.put("password", params[2]);
                // hashMap.put("id", params[3]);

                finalResult = httpParse.postRequest(hashMap, willcomeURL);
                return finalResult;
            }
        }
        willcomeClass willcomeclass = new willcomeClass();
        willcomeclass.execute(EventID,email,password);

        // willcomeclass.execute(id);

    }




    public void intersted(final String EventID) {
        class interstedClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog2 = ProgressDialog.show(willgo.this, "Loading Data", null, true, true);
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog2.dismiss();
                Toast.makeText(willgo.this, "Thanks..", Toast.LENGTH_LONG).show();
                //  finish();
            }
            @Override
            protected String doInBackground(String... params) {
                // Sending Event id.
                hashMap.put("event_id", params[0]);
                hashMap.put("email", params[1]);
                hashMap.put("password", params[2]);
                // hashMap.put("id", params[3]);

                finalResult = httpParse.postRequest(hashMap, interstedURL);
                return finalResult;
            }
        }
        interstedClass interstclass = new interstedClass();
        interstclass.execute(EventID,email,password);

        // willcomeclass.execute(id);

    }



    // Method to Delete Event Record
    public void Eventcant(final String EventID) {

        class EventcantClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog2 = ProgressDialog.show(willgo.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog2.dismiss();

                Toast.makeText(willgo.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                finish();

            }

            @Override
            protected String doInBackground(String... params) {

                // Sending Event id.
                hashMap.put("event_id", params[0]);

                finalResult = httpParse.postRequest(hashMap, canturl);

                return finalResult;
            }
        }

        EventcantClass eventcantClass = new EventcantClass();

        eventcantClass.execute(EventID);
    }


    //Method to show current event Current Selected event
    public void HttpWebCall(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(willgo.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                //Storing Complete JSon Object into String Variable.
                FinalJSonObject = httpResponseMsg ;

                //Parsing the Stored JSOn String to GetHttpResponse Method.
                new GetHttpResponse(willgo.this).execute();

            }

            @Override
            protected String doInBackground(String... params) {

                ResultHash.put("event_id",params[0]);


                ParseResult = httpParse.postRequest(ResultHash, filterED_url);

                return ParseResult;
            }
        }

        HttpWebCallFunction httpWebCallFunction = new HttpWebCallFunction();

        httpWebCallFunction.execute(PreviousListViewClickedItem);
    }


    // Parsing Complete JSON Object.
    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        public Context context;

        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            try
            {
                if(FinalJSonObject != null)
                {
                    JSONArray jsonArray = null;

                    try {
                        jsonArray = new JSONArray(FinalJSonObject);

                        JSONObject jsonObject;

                        for(int i=0; i<jsonArray.length(); i++)
                        {
                            jsonObject = jsonArray.getJSONObject(i);

                            // Storing event Name, type,location into Variables.
                            nameHolder = jsonObject.getString("event_name").toString() ;
                            typeHolder = jsonObject.getString("event_type").toString() ;
                            locationHolder = jsonObject.getString("event_location").toString() ;

                        }
                    }
                    catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {

            // Setting event_name,type,location into TextView after done all process .
            name.setText(nameHolder);
            type.setText(typeHolder);
            location.setText(locationHolder);

        }
    }

}