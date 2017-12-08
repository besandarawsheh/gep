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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.simple.parser.JSONParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.*;

public class allEvents extends mainpage {
    ///////all my created events
    HashMap<String,String> hashMap = new HashMap<>();
    ListView eventListView;
    ProgressBar progressBar;
    String created_url = "http://192.168.1.108/se.php";
    List<String> eventsID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_all_events);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_all_events, contentFrameLayout);
        //setContentView(R.layout.activity_homepage);

        eventListView = (ListView) findViewById(R.id.listview1);
        final RequestQueue queue = Volley.newRequestQueue(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final String email =getIntent().getStringExtra("email");

        final ArrayList array =new ArrayList<Event>();
        StringRequest postRequest = new StringRequest(Request.Method.POST,created_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(allEvents.this, response, Toast.LENGTH_SHORT).show();
                        eventsID.clear();
                        response+=",";
                        String[] data = response.split(",");
                        //eventsID.clear();
                        if (data.length>0) {
                            for (int i = 0; i < data.length ; i+=3) {
                                Event evento =new Event();
                                Toast.makeText(allEvents.this, data[i], Toast.LENGTH_SHORT).show();
                                // evento.event_name

                                evento.event_name = data[i];
                                evento.event_date = data[i+1];

                                //result.append(data[i])
                                array.add(evento);
                                eventsID.add(data[i+2]);
                            }



                            progressBar.setVisibility(View.GONE);
                            eventListView.setVisibility(View.VISIBLE);
                            // alertDialog.setMessage(result);
                            //alertDialog.show();
                            if (array != null) {
                                ListAdapter adapter = new ListAdapter(array, allEvents.this);
                                eventListView.setAdapter(adapter);
                            }

                        }
                        else {
                            Toast.makeText(allEvents.this, "can't insert into array", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        // Log.d("Error.Response", String.valueOf(error));

                        Toast.makeText(allEvents.this, "hhhhh ", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
              if(Email.getANum()!=0){

                }
                else if(Email.getANum()!=0)
                {
                    String email=Email.getEmail();
                    params.put("email",email.toString());
                    return params;}

               else if(Email.getANum()==0){ String email=Email.getEmail();
                params.put("email",email.toString());
                return params;}
                else{
                String email=Email.getEmail();
                params.put("email",email.toString());
                return params;}
                //params.put("email",email.toString());
                return params;
            }
        };
        queue.add(postRequest);


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


}





