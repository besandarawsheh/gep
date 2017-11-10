package com.example.hp.gep;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class SingleEvent extends mainpage {

    HttpParse httpParse = new HttpParse();
    ProgressDialog pDialog;

    // Http Url For Filter Student Data from Id Sent from previous activity.
    String filterED_url= "http://192.168.1.70/FilterEventData.php";

    // Http URL for delete Already Open Student Record.
    String delE_url = "http://192.168.1.70/DeleteEvent.php";

    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    String ParseResult ;
    HashMap<String,String> ResultHash = new HashMap<>();
    String FinalJSonObject ;
    TextView name,type,location,male,fmale;
    String nameHolder, typeHolder, locationHolder,mholder,fholder;
    Button EditButton, DeleteButton;
    String TempItem;
    ProgressDialog progressDialog2;
    PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_single_event, contentFrameLayout);
       // setContentView(R.layout.activity_single_event);
        pieChart = (PieChart) findViewById(R.id.piechart);
        pieChart.setUsePercentValues(true);
        name = (TextView)findViewById(R.id.textName);
        type = (TextView)findViewById(R.id.textType);
        location = (TextView)findViewById(R.id.textLocation);
        male = (TextView)findViewById(R.id.textmale);
        fmale = (TextView)findViewById(R.id.textfmale);

        EditButton = (Button)findViewById(R.id.buttonEdit);
        DeleteButton = (Button)findViewById(R.id.buttonDelete);

        //Receiving the ListView Clicked item value send by previous activity.
        TempItem = getIntent().getStringExtra("ListViewValue");

        //Calling method to filter Event Record and open selected record.
        HttpWebCall(TempItem);


        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(SingleEvent.this,editActivity.class);

                // Sending Event Id, Name, type and location to next EditActivity.
                intent.putExtra("event_id", TempItem);
                intent.putExtra("event_name", nameHolder);
                intent.putExtra("event_type", typeHolder);
                intent.putExtra("event_location", locationHolder);
                intent.putExtra("num_of_male", mholder);

                intent.putExtra("num_of_fmale", fholder);



                startActivity(intent);

                // Finishing current activity after opening next activity.
                finish();

            }
        });

        // Add Click listener on Delete button.
        DeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Calling Event delete method to delete current record using Event ID.
                EventDelete(TempItem);

            }
        });

    }

    // Method to Delete Event Record
    public void EventDelete(final String EventID) {

        class EventDeleteClass extends AsyncTask<String, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                progressDialog2 = ProgressDialog.show(SingleEvent.this, "Loading Data", null, true, true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                progressDialog2.dismiss();

                Toast.makeText(SingleEvent.this, httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                finish();

            }

            @Override
            protected String doInBackground(String... params) {

                // Sending Event id.
                hashMap.put("event_id", params[0]);

                finalResult = httpParse.postRequest(hashMap, delE_url);

                return finalResult;
            }
        }

        EventDeleteClass eventDeleteClass = new EventDeleteClass();

        eventDeleteClass.execute(EventID);
    }


    //Method to show current event Current Selected event
    public void HttpWebCall(final String PreviousListViewClickedItem){

        class HttpWebCallFunction extends AsyncTask<String,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                pDialog = ProgressDialog.show(SingleEvent.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {

                super.onPostExecute(httpResponseMsg);

                pDialog.dismiss();

                //Storing Complete JSon Object into String Variable.
                FinalJSonObject = httpResponseMsg ;

                //Parsing the Stored JSOn String to GetHttpResponse Method.
                new GetHttpResponse(SingleEvent.this).execute();

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
                            mholder = jsonObject.getString("num_of_male").toString() ;
                            fholder = jsonObject.getString("num_of_female").toString() ;

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
            ArrayList<Entry> yvalues = new ArrayList<Entry>();
            Float x=Float.valueOf(mholder);
            Float f=Float.valueOf(fholder);

            yvalues.add(new Entry(x, 0));
            yvalues.add(new Entry(f, 1));
            PieDataSet dataSet = new PieDataSet(yvalues, "");

            ArrayList<String> xVals = new ArrayList<String>();

            xVals.add("Male");
            xVals.add("Fmale");
            PieData data = new PieData(xVals, dataSet);
            data.setValueFormatter(new PercentFormatter());
            pieChart.setData(data);
            pieChart.setDescription("This is Pie Chart");

            pieChart.setDrawHoleEnabled(true);
            pieChart.setTransparentCircleRadius(25f);
            pieChart.setHoleRadius(25f);

            dataSet.setColors(ColorTemplate.VORDIPLOM_COLORS);
            data.setValueTextSize(13f);
            data.setValueTextColor(Color.DKGRAY);
            //pieChart.setOnChartValueSelectedListener(this);

            pieChart.animateXY(1400, 1400);
            // Setting event_name,type,location into TextView after done all process .
            name.setText(nameHolder);
            type.setText(typeHolder);
            location.setText(locationHolder);
            male.setText(mholder);
            fmale.setText(fholder);





        }
    }

}
