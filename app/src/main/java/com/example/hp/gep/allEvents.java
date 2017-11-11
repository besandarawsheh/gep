package com.example.hp.gep;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;
import org.json.simple.parser.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.widget.Toast.*;

public class allEvents extends mainpage {
    HashMap<String,String> hashMap = new HashMap<>();
    ListView eventListView;
    ProgressBar progressBar;
    String allED_url = "http://192.168.1.108/createdEvents.php";
    List<String> eventsID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_all_events);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_all_events, contentFrameLayout);
        //setContentView(R.layout.activity_homepage);

        eventListView = (ListView) findViewById(R.id.listview1);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        String email =getIntent().getStringExtra("email");

        new GetHttpResponse(allEvents.this).execute();


        //Adding ListView Item click Listener.

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           // @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

                Intent intent = new Intent(allEvents.this,SingleEvent.class);

                //Sending ListView clicked value using intent.
                intent.putExtra("ListViewValue", eventsID.get(position).toString());

                startActivity(intent);

                //Finishing current activity after open next activity.
                finish();

            }
        });
    }

    // JSON parse class started from here.
    private class GetHttpResponse extends AsyncTask<Void,Void,Void> {
        public Context context;
       // AlertDialog alertDialog;
        String JSonresult;
        String email =getIntent().getStringExtra("email");
        List<Event> eventList;

        public GetHttpResponse(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            //alertDialog=new AlertDialog.Builder(context).create();
            //alertDialog.setTitle("status");
            Toast.makeText(allEvents.this, "onPreExecute!",
                    LENGTH_SHORT).show();

            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //passing http url to httpservice class

            HttpService httpService = new HttpService(allED_url);

            try {

               httpService.ExecutePostRequest();

                if (httpService.getResponseCode() == 200) {
                    JSonresult = httpService.getResponse();
                    //FERGERGTRTRG  UNDER
                    Log.d("{Result}", JSonresult);
                    if (JSonresult != null) {
                        JSONArray jsonArray = null;
                        try {

                            jsonArray= new JSONArray(JSonresult);
                            JSONObject object;
                            //YUKUJTYJTYJTYJ UNDER
                           eventsID.clear();
                            //JSONArray array;
                            Event evento;
                            eventList = new ArrayList<Event>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                evento = new Event();
                                object = jsonArray.getJSONObject(i);
                                //adding event id to eventsID
                               // if(email.equals(object.getString("").toString()))
                                eventsID.add(object.getString("event_id").toString());

                               //adding event location
                                evento.event_name = object.getString("event_name").toString();
                                evento.event_date = object.getString("event_date").toString();


                                eventList.add(evento);
                            }
                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {
                    makeText(context, httpService.getErrorMessage(), LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(Void result) {
            Toast.makeText(allEvents.this, "browse your events!",
                    LENGTH_SHORT).show();
           // alertDialog.setMessage("ggtgtg");
           // alertDialog.show();
            progressBar.setVisibility(View.GONE);
            eventListView.setVisibility(View.VISIBLE);
           // alertDialog.setMessage(result);
            //alertDialog.show();
          if(eventList != null)
            {
                ListAdapter adapter = new ListAdapter(eventList, context);
                eventListView.setAdapter(adapter);
            }
            //      alertDialog.setMessage(result);
            //    alertDialog.show();
        }

    }

}





