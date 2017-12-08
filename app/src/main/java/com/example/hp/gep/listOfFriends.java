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
import com.zopim.android.sdk.api.ZopimChat;
import com.zopim.android.sdk.prechat.ZopimChatActivity;

import org.json.simple.parser.JSONParser;

        import org.json.JSONArray;
        import org.json.JSONException;
        import org.json.JSONObject;

        import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.*;

public class  listOfFriends extends mainpage {

    ListView eventListView;
    ProgressBar progressBar;
    String allF_url = "http://192.168.1.108/myFriends.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ZopimChat.init("5JkxRtWyisDmbSD6J1SeMfwsZ4htowIz");
        //setContentView(R.layout.activity_all_events);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_list_of_friends, contentFrameLayout);
        //setContentView(R.layout.activity_homepage);

        eventListView = (ListView) findViewById(R.id.listview1);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        //String email =getIntent().getStringExtra("email");

       // new GetHttpResponse(listOfFriends.this).execute();


        final RequestQueue queue = Volley.newRequestQueue(this);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        final String email =getIntent().getStringExtra("email");

        final ArrayList array =new ArrayList<friendData>();
        StringRequest postRequest = new StringRequest(Request.Method.POST,allF_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(listOfFriends.this, response, Toast.LENGTH_SHORT).show();
                        //eventsID.clear();
                        response+=",";
                        String[] data = response.split(",");
                        //eventsID.clear();
                        if (data.length>0) {
                            for (int i = 0; i < data.length ; i+=3) {
                                friendData myRfriend =new friendData();
                                Toast.makeText(listOfFriends.this, data[i], Toast.LENGTH_SHORT).show();
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
                                fRequestAdapter adapter = new fRequestAdapter(array, listOfFriends.this);
                                eventListView.setAdapter(adapter);
                            }

                        }
                        else {
                            Toast.makeText(listOfFriends.this, "can't insert into array", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        // Log.d("Error.Response", String.valueOf(error));

                        Toast.makeText(listOfFriends.this, "hhhhh ", Toast.LENGTH_SHORT).show();
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

                AlertDialog.Builder builder = new AlertDialog.Builder(listOfFriends.this);
                builder.setTitle("Friend Action");
                builder.setMessage("What do you want from him/her?");

                // add the buttons
                // builder.setPositiveButton("Send Request", null);
                builder.setPositiveButton("Invite", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String action="Invite";
                        // do something like...
                        Action(action);
                    }
                });
                builder.setNeutralButton("StartChatting",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Intent i = new Intent(getApplicationContext(),chat.class);
                        //ntent.putExtra("ListViewValue", eventsID.get(position).toString());

                        startActivity(i);*/
                        startActivity(new Intent(getApplicationContext(), ZopimChatActivity.class));

                    }
                });

                builder.setNegativeButton("DeleteFriend",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        // do something like...
                        String delete="delete";
                       Action(delete);
                    }
                });

                // create and show the alert dialog
                AlertDialog dialog = builder.create();
                dialog.show();

            }
            public void Action(String action){

                TextView fre = (TextView)findViewById(R.id.txtRemail);
                TextView frn= (TextView)findViewById(R.id.txtRname);
                TextView frg = (TextView)findViewById(R.id.txtRgender);
                String email =Email.getEmail();
                String Femail =fre.getText().toString();
                String Fname =frn.getText().toString();
                String Fgender =frg.getText().toString();
               interactwithMyFriend interactwithMyFriend=new interactwithMyFriend(listOfFriends.this);
                interactwithMyFriend.execute(Femail,email,action,Fname,Fgender);

            }




        });
    }



}







