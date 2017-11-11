package com.example.hp.gep;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

//import android.net.http.RequestQueue;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

/*import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;*/

import org.apache.http.HttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.hp.gep.R.id.login_input_email;
import static com.example.hp.gep.R.id.parent;

public class homepage extends mainpage {
    // HttpService httpService;
    HttpParse httpParse = new HttpParse();
    // private int mLastSpinnerPosition = 0;
    String finalResult;
    HashMap<String, String> hashMap = new HashMap<>();
    String ParseResult;
    HashMap<String, String> ResultHash = new HashMap<>();
    String FinalJSonObject;
   // private RequestQueue requestQueue ;
    //StringRequest request ;

    ProgressDialog pDialog;
    HttpService httpService = null;
    ListView eventListView;
    ProgressBar progressBar;
    String allED_url = "http://192.168.1.108/AllEventsData.php";
    String created_url = "http://192.168.1.108/se.php";
    String birthday_url = "http://192.168.1.108/birthday.php";
    String graduation_url = "http://192.168.1.108/graduation.php";
    String it_url = "http://192.168.1.108/it.php";
    String wedding_url = "http://192.168.1.108/wedding.php";
    String education_url = "http://192.168.1.108/education.php";
    String other_url = "http://192.168.1.108/other.php";
    String page = "createdEvents";

    List<String> eventsID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_homepage, contentFrameLayout);
        //setContentView(R.layout.activity_homepage);

        eventListView = (ListView) findViewById(R.id.listviewhome);
        final RequestQueue queue = Volley.newRequestQueue(this);
        //final RequestQueue queue = Volley.newRequestQueue(this);

        final String email = getIntent().getStringExtra("email");
        final String password = getIntent().getStringExtra("password");
        final String id = getIntent().getStringExtra("id");
        //Toast.makeText(homepage.this,email,LENGTH_SHORT).show();
        // Toast.makeText(homepage.this,password,LENGTH_SHORT).show();
        // Toast.makeText(homepage.this,id,LENGTH_SHORT).show();
        progressBar = (ProgressBar) findViewById(R.id.progressBarhome);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.adfab);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent flip = new Intent(homepage.this, Main2Activity.class);
                flip.putExtra("email", email);
                flip.putExtra("password", password);
                startActivity(flip);

            }//onClick
        });


        //new GetHttpResponse(homepage.this).execute(page,email);

        //Adding ListView Item click Listener.
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

                Intent intent = new Intent(homepage.this, SingleEvent.class);

                //Sending ListView clicked value using intent.
                intent.putExtra("ListViewValue", eventsID.get(position).toString());

                startActivity(intent);

                //Finishing current activity after open next activity.
                finish();

            }
        });

        final Spinner spin = (Spinner) findViewById(R.id.spinner3);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adap = ArrayAdapter.createFromResource(this,
                R.array.event_filter, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adap.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spin.setAdapter(adap);


        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // if (mLastSpinnerPosition == i) {
                //   return; //do nothing
                //}

                //  mLastSpinnerPosition = i;
                //do the rest of your code now


                AlertDialog.Builder b = new AlertDialog.Builder(homepage.this);

                String t;

                t = spin.getSelectedItem().toString();
                if (t.equals("Type")) {
                    // Toast.makeText(homepage.this, "TYPE " , Toast.LENGTH_SHORT).show();

                    b.setTitle("TYPE");
                    String[] types = {"IT", "Education", "Graduation", "Birthday", "Wedding", "Other"};
                    b.setItems(types, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            switch (which) {
                                case 0:
                                    onITRequested();
                                    break;
                                case 1:
                                    onEducationRequested();
                                    break;
                                case 2:
                                    onGraduationRequested();
                                    break;
                                case 3:
                                    onBirthdayRequested();
                                    break;
                                case 4:
                                    onWeddingRequested();
                                    break;
                                case 5:
                                    onOtherRequested();
                                    break;
                            }
                        }

                    });

                    b.show();

                }
                if (t.equals("Created events")) {

                    Toast.makeText(homepage.this, "created ", Toast.LENGTH_SHORT).show();

                    page = "createdEvents";
                    final ArrayList array =new ArrayList<Event>();
                    StringRequest postRequest = new StringRequest(Request.Method.POST, created_url,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response) {
                                    Event evento =new Event();
                                    Toast.makeText(homepage.this, response, Toast.LENGTH_SHORT).show();
                                    String[] data = response.split(",");
                                    Log.d("raslandata", data.toString());
                                    if (data.length>0) {
                                        for (int i = 0; i < data.length; i++) {

                                            Toast.makeText(homepage.this, data[i], Toast.LENGTH_SHORT).show();
                                           // evento.event_name
                                            evento.event_name = data[0];
                                          evento.event_date = data[1];

/*for(int j=0;j<response.length();j++){


    array.add(evento);


}*/

                                            //result.append(data[i]);
                                        }
                                        array.add(evento);



                                        progressBar.setVisibility(View.GONE);
                                        eventListView.setVisibility(View.VISIBLE);
                                        // alertDialog.setMessage(result);
                                        //alertDialog.show();
                                        if (array != null) {
                                            ListAdapter adapter = new ListAdapter(array, homepage.this);
                                            eventListView.setAdapter(adapter);
                                        }

                                    }
                                    else {
                                        Toast.makeText(homepage.this, "can't insert into array", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // error
                                    // Log.d("Error.Response", String.valueOf(error));

                                    Toast.makeText(homepage.this, "hhhhh ", Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams()
                        {
                            Map<String, String>  params = new HashMap<String, String>();
                            params.put("email",email.toString());
                            return params;
                        }
                    };
                    queue.add(postRequest);


                   // new GetResponse(homepage.this).execute(page);
                    // httpService.AddParam("email",email);
                    //onCretedCall("email");//email.trim());
                   /* Intent intent = new Intent(homepage.this,allEvents.class);

                    //Sending ListView clicked value using intent.
                    intent.putExtra("email",email);

                    startActivity(intent);*/

                    //Finishing current activity after open next activity.
                    //HttpWebCallFunction httpWebCallFunction=new HttpWebCallFunction(homepage.this);
                    //httpWebCallFunction.execute(email);
                    //new GetHttpResponse(homepage.this).execute(page);
                    // httpService = new HttpService(created_url);
                }

                if (t.equals("All")) {
                    page = "AllEventsData";
                    new GetHttpResponse(homepage.this).execute(page, email);
                    //httpService = new HttpService(allED_url);
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //httpService = new HttpService(allED_url);
                // finish();
                //startActivity(getIntent());
            }
        });


        // Spinner sp = (Spinner) findViewById(R.id.spinner3);


        // handleSpinner();

    }//onCreate



    /* public class HttpWebCallFunction extends AsyncTask<String, Void, String> {
         // String pge="createdEvents";
          Context context;
          android.app.AlertDialog alertDialog;
          //constructor
          HttpWebCallFunction(Context ctx){

              context=ctx;
          }
          @Override
          protected String doInBackground(String... params) {
//extract epage from params

              String eventInfo_url = "http://192.168.1.35/createdEvents.php";

              //posting
              try {
                 ArrayList eventls ;//=new ArrayList<Event>();
                  String email = params[0];
                  URL url = new URL(eventInfo_url);
                  HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                  httpURLConnection.setRequestMethod("POST");
                  httpURLConnection.setDoOutput(true);
                  httpURLConnection.setDoInput(true);
                  OutputStream outputStream = httpURLConnection.getOutputStream();
                  BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                  String post_data = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
                  bufferedWriter.write(post_data);
                  bufferedWriter.flush();
                  bufferedWriter.close();
                  outputStream.close();
                  InputStream inputStream = httpURLConnection.getInputStream();
                  BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso_8859-1"));

                  String line = "";
                  String result = "";
                  while ((line = bufferedReader.readLine()) != null) {
                      result += line;
                  }

                  bufferedReader.close();
                  inputStream.close();
                  httpURLConnection.disconnect();
                  String JSonresult = httpService.getResponse();
                  try {
                      JSONObject jsonObject = new JSONObject(JSonresult);
                      Log.d("raslanid", jsonObject.getString("id"));
                      Log.d("{Result}", JSonresult);
                      if (JSonresult != null) {
                          JSONArray jsonArray = null;
                          try {



                              jsonArray = new JSONArray(JSonresult);

                              JSONObject object;

                              //YUKUJTYJTYJTYJ UNDER
                              eventsID.clear();
                              //JSONArray array;
                              Event evento;
                              eventls = new ArrayList<Event>();
                              for (int i = 0; i < jsonArray.length(); i++) {
                                  evento = new Event();
                                  object = jsonArray.getJSONObject(i);
                                  //adding event id to eventsID
                                  eventsID.add(object.getString("event_id").toString());

                                  //adding event location
                                  evento.event_name = object.getString("event_name").toString();
                                  evento.event_date = object.getString("event_date").toString();


                                  eventls.add(evento);
                              }
                          } catch (JSONException e) {
                              // TODO Auto-generated catch block
                              e.printStackTrace();
                          }
                      }
                  //} //else {
                      makeText(context, httpService.getErrorMessage(), LENGTH_SHORT).show();
                  //}
              } catch (Exception e) {
                  // TODO Auto-generated catch block
                  e.printStackTrace();
              }
              return null;


                  } catch (UnsupportedEncodingException e) {
                  e.printStackTrace();
              } catch (ProtocolException e) {
                  e.printStackTrace();
              } catch (MalformedURLException e) {
                  e.printStackTrace();
              } catch (IOException e) {
                  e.printStackTrace();
              }

              return null;

              }

              //catch(MalformedURLException e){
                //  e.printStackTrace();
              //}catch(IOException e){
                //  e.printStackTrace();
              //}





             // return null;

          //}

          @Override
          protected void onPreExecute() {
              alertDialog=new android.app.AlertDialog.Builder(context).create();
              alertDialog.setTitle("posting email status");

          }

          @Override
          protected void onPostExecute(String result) {
             alertDialog.setMessage(result);
             alertDialog.show();
              progressBar.setVisibility(View.GONE);
              eventListView.setVisibility(View.VISIBLE);
              // alertDialog.setMessage(result);
              //alertDialog.show();
             /*if (e != null) {
                  ListAdapter adapter = new ListAdapter(e, context);
                  eventListView.setAdapter(adapter);

              //new GetResponse(homepage.this).execute("createdEvents");
              // homepage hp= new homepage();
              // hp.ANY();

          }}

          @Override
          protected void onProgressUpdate(Void... values) {
              super.onProgressUpdate(values);
          }







      }*/


    public void onITRequested() {
        page = "it";
        new GetHttpResponse(homepage.this).execute(page);
    }


    public void onEducationRequested() {

        page = "education";
        new GetHttpResponse(homepage.this).execute(page);
    }
    /*public void onCretedCall(String email){
        showCreatedEvents showCreatedEvents= new showCreatedEvents(homepage.this);
        showCreatedEvents.execute(email);

    }*/

    public void onGraduationRequested() {
        page = "graduation";
        new GetHttpResponse(homepage.this).execute(page);

    }

    public void onBirthdayRequested() {
        page = "birthday";
        new GetHttpResponse(homepage.this).execute(page);

    }

    public void onWeddingRequested() {

        page = "wedding";
        new GetHttpResponse(homepage.this).execute(page);

    }

    public void onOtherRequested() {

        page = "other";
        new GetHttpResponse(homepage.this).execute(page);

    }
  /*  public void ANY(){
        page = "createdEvents";
        new GetHttpResponse(homepage.this).execute(page);

    }*/

    // JSON parse class started from here.
    private class GetHttpResponse extends AsyncTask<String, Void, String> {
        public Context context;
        // AlertDialog alertDialog;
        String JSonresult;

        List<Event> eventList;

        public GetHttpResponse(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            //alertDialog=new AlertDialog.Builder(context).create();
            //alertDialog.setTitle("status");
            Toast.makeText(homepage.this, "onPreExecute!",
                    LENGTH_SHORT).show();

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            //passing http url to httpservice class


            //String page=params[0];
            // HttpService httpService = null;
            //if (page.equals("createdEvents")) {
            // httpService = new HttpService(created_url);
            //}
            //String email=params[1];

            if (page.equals("createdEvents")) {

//JSonresult=FinalJSonObject;
                //httpService.responseCode = 200;
                httpService = new HttpService(created_url);

                //JSonresult=FinalJSonObject;

                //Toast.makeText(homepage.this,email , Toast.LENGTH_SHORT).show();
            }


            if (page.equals("AllEventsData")) {


                httpService = new HttpService(allED_url);
            }

            if (page.equals("birthday")) {

                httpService = new HttpService(birthday_url);

            }
            if (page.equals("graduation")) {

                httpService = new HttpService(graduation_url);

            }
            if (page.equals("education")) {

                httpService = new HttpService(education_url);

            }
            if (page.equals("wedding")) {

                httpService = new HttpService(wedding_url);

            }
            if (page.equals("it")) {

                httpService = new HttpService(it_url);

            }
            if (page.equals("other")) {

                httpService = new HttpService(other_url);

            }


            try {
                if(page.equals("createdEvents")){
                   // final String email = getIntent().getStringExtra("email");
                    //httpService.AddParam("email",email);
                    //httpService.ExecuteGetRequest();
                }
               httpService.ExecutePostRequest();

                //String email=params[1];
                //Toast.makeText(homepage.this,email , Toast.LENGTH_SHORT).show();

                if (httpService.getResponseCode() == 200) {
                    JSonresult = httpService.getResponse();
                    //FERGERGTRTRG  UNDER
                    Log.d("{Result}", JSonresult);
                    if (JSonresult != null) {
                        JSONArray jsonArray = null;
                        try {



                            jsonArray = new JSONArray(JSonresult);

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
                                eventsID.add(object.getString("event_id").toString());

                                //adding event location
                                evento.event_name = object.getString("event_name").toString();
                                evento.event_date = object.getString("event_date").toString();


                                eventList.add(evento);
                            }
                        } catch (JSONException e) {
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
        protected void onPostExecute(String result) {
            Toast.makeText(homepage.this, "browse your events!",
                    LENGTH_SHORT).show();
            // alertDialog.setMessage("ggtgtg");
            // alertDialog.show();
            progressBar.setVisibility(View.GONE);
            eventListView.setVisibility(View.VISIBLE);
            // alertDialog.setMessage(result);
            //alertDialog.show();
            if (eventList != null) {
                ListAdapter adapter = new ListAdapter(eventList, context);
                eventListView.setAdapter(adapter);
            }
            //      alertDialog.setMessage(result);
            //    alertDialog.show();
        }
    }

/*private class GetResponse extends AsyncTask<String, Void, String> {
    public Context context;
    // AlertDialog alertDialog;
    String JSonresult;

    List<Event> eventList;
    public GetResponse(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {

        //alertDialog=new AlertDialog.Builder(context).create();
        //alertDialog.setTitle("status");
        Toast.makeText(homepage.this, "onPreExecute!",
                LENGTH_SHORT).show();

        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {

            //httpService.responseCode = 200;
            httpService = new HttpService(created_url);

        try {
            httpService.ExecutePostRequest();

            if (httpService.getResponseCode() == 200) {
                JSonresult = httpService.getResponse();
                //FERGERG4TRTRG  UNDER
                Log.d("{Result}", JSonresult);
                Toast.makeText(getApplicationContext()
                        , "doInBackGround!",
                        LENGTH_SHORT).show();
                if (JSonresult != null) {
                    Toast.makeText(getApplicationContext(), JSonresult, Toast.LENGTH_LONG).show();
                    JSONArray jsonArray = null;
                    try {
                        jsonArray = new JSONArray();
                        JSONParser parser = new JSONParser();
                        //JSONObject json =
                        JSONObject object =new JSONObject();
                        //JSonresult, Event.class);
                                //(JSONObject) parser.parse(FinalJSonObject);
                        String eventsForId = object.getString("id");
                        Log.d("eventsForId", eventsForId);
                        eventsID.clear();
                        //JSONArray array;
                        Event evento;
                        eventList = new ArrayList<Event>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            evento = new Event();
                            object = jsonArray.getJSONObject(i);
                            //adding event id to eventsID
                            eventsID.add(object.getString("event_id").toString());

                            //adding event location
                            evento.event_name = object.getString("event_name").toString();
                            evento.event_date = object.getString("event_date").toString();


                            eventList.add(evento);
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        Log.d("raslan", "error");
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
    protected void onPostExecute(String result) {
        Toast.makeText(homepage.this, "browse your events!",
                LENGTH_SHORT).show();
        Toast.makeText(homepage.this, JSonresult, Toast.LENGTH_LONG).show();
        try {
            JSONObject object =new JSONObject(JSonresult);
        //JSonresult, Event.class);
        //(JSONObject) parser.parse(FinalJSonObject);
                JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(JSonresult);
            String eventsForId = null;
            eventsForId = object.getString("id");
            Log.d("eventsForId", eventsForId);
            Log.d("JSONOBJECT", jsonObject.toString());
            JSONArray jsonArray = object.getJSONArray(JSonresult);
            Event evento;
            eventList = new ArrayList<Event>();
            for (int i = 0; i < jsonArray.length(); i++) {
                evento = new Event();
                object = jsonArray.getJSONObject(i);
                //adding event id to eventsID
                eventsID.add(object.getString("event_id").toString());

                //adding event location
                evento.event_name = object.getString("event_name").toString();
                evento.event_date = object.getString("event_date").toString();


                eventList.add(evento);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // alertDialog.setMessage("ggtgtg");
        // alertDialog.show();
        progressBar.setVisibility(View.GONE);
        eventListView.setVisibility(View.VISIBLE);
        // alertDialog.setMessage(result);
        //alertDialog.show();
        if (eventList != null) {
            ListAdapter adapter = new ListAdapter(eventList, context);
            eventListView.setAdapter(adapter);
        }
        //      alertDialog.setMessage(result);
        //    alertDialog.show();
    }

}*/



}
