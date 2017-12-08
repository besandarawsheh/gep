package com.example.hp.gep;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.AlertDialog;
        import android.content.Context;
        import android.content.Intent;
        import android.nfc.Tag;
        import android.os.AsyncTask;
        import android.util.Log;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.FrameLayout;
        import android.widget.ListView;
        import android.widget.ProgressBar;
import android.widget.TextView;
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

public class friendsRequests extends mainpage {

    ListView eventListView;
    ProgressBar progressBar;
    String allF_url = "http://192.168.1.108/fRequests.php";
    List<String> FRID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //setContentView(R.layout.activity_all_events);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_friends_requests, contentFrameLayout);
        //setContentView(R.layout.activity_homepage);

        eventListView = (ListView) findViewById(R.id.listview1);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        eventListView = (ListView) findViewById(R.id.listview1);
        final RequestQueue queue = Volley.newRequestQueue(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final String email =getIntent().getStringExtra("email");

        final ArrayList array =new ArrayList<friendData>();
        StringRequest postRequest = new StringRequest(Request.Method.POST,allF_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(friendsRequests.this, response, Toast.LENGTH_SHORT).show();
                        //eventsID.clear();
                        response+=",";
                        String[] data = response.split(",");
                        //eventsID.clear();
                        if (data.length>0) {
                            for (int i = 0; i < data.length ; i+=3) {
                                friendData myRfriend =new friendData();
                                Toast.makeText(friendsRequests.this, data[i], Toast.LENGTH_SHORT).show();
                                // evento.event_name

                               myRfriend.Name = data[i];
                                myRfriend.email = data[i+1];
                                myRfriend.gender = data[i+2];
                                //result.append(data[i])
                                array.add(myRfriend);
                               // eventsID.add(data[i+2]);
                            }



                            progressBar.setVisibility(View.GONE);
                            eventListView.setVisibility(View.VISIBLE);
                            // alertDialog.setMessage(result);
                            //alertDialog.show();
                            if (array != null) {
                                fRequestAdapter adapter = new fRequestAdapter(array, friendsRequests.this);
                                eventListView.setAdapter(adapter);
                            }

                        }
                        else {
                            Toast.makeText(friendsRequests.this, "can't insert into array", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        // Log.d("Error.Response", String.valueOf(error));

                        Toast.makeText(friendsRequests.this, "hhhhh ", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                if(Email.getANum()!=0){
                    String email=Email.getEmail();
                    params.put("email",email.toString());
                    return params;
                }
                else if(Email.getANum()!=0)
                {
                    String email=getIntent().getStringExtra("email");
                    params.put("email",email.toString());
                    return params;}

                else{ String email=Email.getEmail();
                    params.put("email",email.toString());
                    return params;}
                //params.put("email",email.toString());
                //return params;
            }
        };
        queue.add(postRequest);







        //Adding ListView Item click Listener.

        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

               AlertDialog.Builder builder = new AlertDialog.Builder(friendsRequests.this);
                builder.setTitle("Friend Request");
                builder.setMessage("Would you like to Accept him/her As a Friend?");

                // add the buttons
                // builder.setPositiveButton("Send Request", null);
                builder.setPositiveButton("ACCEPT", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                      String accept="accept";
                        // do something like...
                        acceptRequest(accept);
                    }
                });
                builder.setNeutralButton("Ignore",null);

                builder.setNegativeButton("DELETE",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // do something like...
                       String delete="delete";
                        acceptRequest(delete);
                    }
                });

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
            public void acceptRequest(String action){

                 TextView fre = (TextView)findViewById(R.id.txtRemail);
                TextView frn= (TextView)findViewById(R.id.txtRname);
                TextView frg = (TextView)findViewById(R.id.txtRgender);
                String email =Email.getEmail();
                String Femail =fre.getText().toString();
                String Fname =frn.getText().toString();
                String Fgender =frg.getText().toString();
                acceptFriend acceptFriend=new acceptFriend(friendsRequests.this);
                acceptFriend.execute(Femail,email,action,Fname,Fgender);

            }




        });
    }

    // JSON parse class started from here.
    /*private class GetHttpResponse extends AsyncTask<Void,Void,Void> {
        public Context context;
        // AlertDialog alertDialog;
        String JSonresult;
        String email =getIntent().getStringExtra("email");
        List<friendData> requestList;

        public GetHttpResponse(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            //alertDialog=new AlertDialog.Builder(context).create();
            //alertDialog.setTitle("status");
            Toast.makeText(friendsRequests.this, "onPreExecute!",
                    LENGTH_SHORT).show();

            super.onPreExecute();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            //passing http url to httpservice class
            HttpService httpService = new HttpService(allED_url);

            try {
//TODO
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
                            FRID.clear();
                            //JSONArray array;
                            friendData adder;
                           requestList = new ArrayList<friendData>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                adder = new friendData();
                                object = jsonArray.getJSONObject(i);
                                //adding event id to eventsID
                                // if(email.equals(object.getString("").toString()))
                                FRID.add(object.getString("Fid").toString());

                                //adding event location
                                adder.Name = object.getString("Uname").toString();
                                adder.email = object.getString("Uemail").toString();
                                adder.gender= object.getString("UGender").toString();

                               requestList.add(adder);
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
            Toast.makeText(friendsRequests.this, "Friend Requests!",
                    LENGTH_SHORT).show();
            // alertDialog.setMessage("ggtgtg");
            // alertDialog.show();
            progressBar.setVisibility(View.GONE);
            eventListView.setVisibility(View.VISIBLE);
            // alertDialog.setMessage(result);
            //alertDialog.show();
            if(requestList != null)
            {
                fRequestAdapter adapter = new fRequestAdapter(requestList, context);
                eventListView.setAdapter(adapter);
            }
            //      alertDialog.setMessage(result);
            //    alertDialog.show();
        }

    }*/

}






