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
        import org.json.simple.parser.JSONParser;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
        import java.util.List;

        import static android.widget.Toast.*;

public class friendsRequests extends mainpage {

    ListView eventListView;
    ProgressBar progressBar;
    String allED_url = "http://192.168.1.70/fRequests.php";
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
        //String email =getIntent().getStringExtra("email");

        new GetHttpResponse(friendsRequests.this).execute();


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
    private class GetHttpResponse extends AsyncTask<Void,Void,Void> {
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

    }

}






