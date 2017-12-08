package com.example.hp.gep;


import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import com.example.hp.gep.services.LocationMonitoringService;


/**/

import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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
import java.util.Calendar;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;
import static com.example.hp.gep.R.id.date;
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
final String email=Email.getEmail();
    ProgressDialog pDialog;
    HttpService httpService = null;
    ListView eventListView;
    ProgressBar progressBar;
    String date_url="http://192.168.1.108/date.php";
    String allED_url = "http://192.168.1.108/allFE.php";
    String created_url = "http://192.168.1.108/se.php";
    String birthday_url = "http://192.168.1.108/birthday.php";
    String graduation_url = "http://192.168.1.108/graduation.php";
    String it_url = "http://192.168.1.108/it.php";
    String wedding_url = "http://192.168.1.108/wedding.php";
    String education_url = "http://192.168.1.108/education.php";
    String other_url = "http://192.168.1.108/other.php";
    String page = "createdEvents";

    List<String> eventsID = new ArrayList<>();
    private static final String TAG = homepage.class.getSimpleName();

    /**
     * Code used in requesting runtime permissions.
     */
    private static final int REQUEST_PERMISSIONS_REQUEST_CODE = 34;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent i = new Intent(getApplicationContext(),firsthome.class);
                    //ntent.putExtra("ListViewValue", eventsID.get(position).toString());

                    startActivity(i);
                    // mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.location_on:
                    //mTextMessage.setText(R.string.title_dashboard);
                    shareMyLoc.setAllowence(1);
enableTracker();
                    return true;
                case R.id.location_off:
                    shareMyLoc.setAllowence(0);
                    //onDestroy();
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
                case R.id.create_event:
                    Intent flip = new Intent(homepage.this, Main2Activity.class);
                    flip.putExtra("email", email);

                    startActivity(flip);

                    return  true;

            }
            return false;
        }

    };
    private boolean mAlreadyStartedService = false;
   // private TextView mMsgView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_homepage, contentFrameLayout);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setBackgroundColor(Color.parseColor("#B07D4A"));

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        //setContentView(R.layout.activity_homepage);
        //mMsgView = (TextView) findViewById(R.id.msgView);
        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {


                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String latitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LATITUDE);
                        String longitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LONGITUDE);

                        if (latitude != null && longitude != null) {
                            //mMsgView.setText(getString(R.string.msg_location_service_started) + "\n Latitude : " + latitude + "\n Longitude: " + longitude);
                            userLocBroadCast.setLatitude(latitude);
                            userLocBroadCast.setLongitude(longitude);
                            //shareMyLoc.setAllowence(0);
                            //String allow =Integer.toString(0);
//new Background(getApplicationContext()).execute(latitude,longitude,"besan",allow);

                            //enableTracker();

                        }


                    }



                }, new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST)
        );
       // shareMyLoc.setAllowence(0);
        //enableTracker();




        eventListView = (ListView) findViewById(R.id.listviewhome);
        final RequestQueue queue = Volley.newRequestQueue(this);
        //final RequestQueue queue = Volley.newRequestQueue(this);
//final String email=Email.getEmail();
       final String email = getIntent().getStringExtra("email");
        final String password = getIntent().getStringExtra("password");
        progressBar = (ProgressBar) findViewById(R.id.progressBarhome);






        //Adding ListView Item click Listener.
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

                Intent intent = new Intent(homepage.this, willgo.class);

                //Sending ListView clicked value using intent.
                intent.putExtra("email", email);
                intent.putExtra("password",password);
                intent.putExtra("id",id);
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

                AlertDialog.Builder b = new AlertDialog.Builder(homepage.this,R.style.MyDialogTheme);

                String t;

                t = spin.getSelectedItem().toString();
                if(t.equals("Interested in events")){

                    //TODO




                }
                if (t.equals("Type")) {
                    // Toast.makeText(homepage.this, "TYPE " , Toast.LENGTH_SHORT).show();

                    b.setTitle(Html.fromHtml("<font color='#FFFACD'>Type</font>"));
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
                    Intent intent = new Intent(homepage.this,allEvents.class);

                    //Sending ListView clicked value using intent.
                    intent.putExtra("email",email);

                    startActivity(intent);
                }

                if (t.equals("All")) {

                  showEvent showEvent=new showEvent();
                    showEvent.show("all");



                }
                if(t.equals("Date")){

                    sortbyDate();
                }


            }


            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                final ArrayList array =new ArrayList<Event>();
                StringRequest postRequest = new StringRequest(Request.Method.POST, allED_url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(homepage.this, response, Toast.LENGTH_SHORT).show();
                                //eventsID.clear();
                                response+=",";
                                String[] data = response.split(",");
                                if (data.length>0) {
                                    for (int i = 0; i < data.length ; i+=2) {
                                        Event evento =new Event();
                                        Toast.makeText(homepage.this, data[i], Toast.LENGTH_SHORT).show();
                                        // evento.event_name
                                        evento.event_name = data[i];
                                        evento.event_date = data[i+1];

                                        //result.append(data[i])
                                        array.add(evento);

                                    }



                                    progressBar.setVisibility(View.GONE);
                                    eventListView.setVisibility(View.VISIBLE);
                                    // alertDialog.setMessage(result);
                                    //alertDialog.show();
                                    if (array != null) {
                                        ListAdapter adapter = new ListAdapter(array, getApplicationContext());
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
            }
        });


        // Spinner sp = (Spinner) findViewById(R.id.spinner3);


        // handleSpinner();

    }//onCreate


public void sortbyDate(){
    final Calendar myCalendar = Calendar.getInstance();
    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            int y=myCalendar.get(Calendar.YEAR);
int m=myCalendar.get(Calendar.MONTH)+1;
            int d=myCalendar.get(Calendar.DAY_OF_MONTH);
String event_date =y+"-"+m+"-"+d;
            Toast.makeText(homepage.this,event_date,Toast.LENGTH_SHORT).show();
showeventsbyDate(event_date);
        }

    };

    new DatePickerDialog(homepage.this, date, myCalendar
            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    //int m =myCalendar.get(Calendar.MONTH)+1;

    //Toast.makeText(homepage.this,myCalendar.get(Calendar.YEAR)+"-"+m+"-"+myCalendar.get(Calendar.DAY_OF_MONTH),Toast.LENGTH_SHORT).show();




}



    public void onITRequested() {
        page = "it";
        new GetHttpResponse(homepage.this).execute(page);
    }


    public void onEducationRequested() {

        page = "education";
        new GetHttpResponse(homepage.this).execute(page);
    }


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


    public void showeventsbyDate(final String date){
        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        final String email=getIntent().getStringExtra("email");
        final ArrayList array =new ArrayList<Event>();
        StringRequest postRequest = new StringRequest(Request.Method.POST,date_url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(homepage.this, response, Toast.LENGTH_SHORT).show();

                        eventsID.clear();

                        response+=",";
                        //log.d("besan",response);
                        Toast.makeText(homepage.this,response,Toast.LENGTH_SHORT).show();

                        String[] data = response.split(",");
                        if (data.length>0) {
                            for (int i = 0; i < data.length ; i+=3) {
                                Event evento =new Event();
                                Toast.makeText(homepage.this, data[i], Toast.LENGTH_SHORT).show();
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
                                // Toast.makeText(homepage.this,"fdfghnbvc",Toast.LENGTH_LONG).show();
                                ListAdapter adapter = new ListAdapter(array, getApplicationContext());
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
                if(Email.getANum()==0){
                    String email=Email.getEmail();
                   // params.put("email",email.toString());
                    params.put("event_date",date);
                    return params;
                }
                else
                {
                    String email=getIntent().getStringExtra("email");
                   // params.put("email",email.toString());
                    params.put("event_date",date);
                    return params;}
            }
        };
        queue.add(postRequest);



    }


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


            if (page.equals("createdEvents")) {


                httpService = new HttpService(created_url);


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




    public class showEvent{

        final RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url;

        public void show(String selection){
            if(selection=="created"){
                url=created_url;
            }
            if(selection=="all"){
                url=allED_url;
            }

            final String email=Email.getEmail();
            final ArrayList array =new ArrayList<Event>();
            StringRequest postRequest = new StringRequest(Request.Method.POST,url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(homepage.this, response, Toast.LENGTH_SHORT).show();

                            eventsID.clear();

                            response+=",";
                            String[] data = response.split(",");
                            if (data.length>0) {
                                for (int i = 0; i < data.length ; i+=3) {
                                    Event evento =new Event();
                                    Toast.makeText(homepage.this, data[i], Toast.LENGTH_SHORT).show();
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
                                   // Toast.makeText(homepage.this,"fdfghnbvc",Toast.LENGTH_LONG).show();
                                    ListAdapter adapter = new ListAdapter(array, getApplicationContext());
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
                    String email=Email.getEmail();
                    params.put("email",email.toString());

                    if(Email.getANum()==0||Email.getANum()!=0){
                         email=Email.getEmail();
                        params.put("email",email.toString());
                        return params;
                    }
                    else
                    {
                         email=Email.getEmail();
                    params.put("email",email.toString());
                    return params;}
                }
            };
            queue.add(postRequest);



        }



    }

public void enableTracker(){

    String shareLoc =Integer.toString(shareMyLoc.getAllowence());
    Background background =new Background(this);
    background.execute(userLocBroadCast.getLatitude(),userLocBroadCast.getLongitude(),Email.getEmail(),shareLoc);

}

    @Override
    public void onResume() {
        super.onResume();

        startStep1();
    }


    /**
     * Step 1: Check Google Play services
     */
    private void startStep1() {

        //Check whether this user has installed Google play service which is being used by Location updates.
        if (isGooglePlayServicesAvailable()) {

            //Passing null to indicate that it is executing for the first time.
            startStep2(null);

        } else {
            Toast.makeText(getApplicationContext(), R.string.no_google_playservice_available, Toast.LENGTH_LONG).show();
        }
    }


    /**
     * Step 2: Check & Prompt Internet connection
     */
    private Boolean startStep2(DialogInterface dialog) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            promptInternetConnect();
            return false;
        }


        if (dialog != null) {
            dialog.dismiss();
        }

        //Yes there is active internet connection. Next check Location is granted by user or not.

        if (checkPermissions()) { //Yes permissions are granted by the user. Go to the next step.
            startStep3();
        } else {  //No user has not granted the permissions yet. Request now.
            requestPermissions();
        }
        return true;
    }

    /**
     * Show A Dialog with button to refresh the internet state.
     */
    private void promptInternetConnect() {
        AlertDialog.Builder builder = new AlertDialog.Builder(homepage.this);
        builder.setTitle(R.string.title_alert_no_intenet);
        builder.setMessage(R.string.msg_alert_no_internet);

        String positiveText = getString(R.string.btn_label_refresh);
        builder.setPositiveButton(positiveText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //Block the Application Execution until user grants the permissions
                        if (startStep2(dialog)) {

                            //Now make sure about location permission.
                            if (checkPermissions()) {

                                //Step 2: Start the Location Monitor Service
                                //Everything is there to start the service.
                                startStep3();
                            } else if (!checkPermissions()) {
                                requestPermissions();
                            }

                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    /**
     * Step 3: Start the Location Monitor Service
     */
    private void startStep3() {

        //And it will be keep running until you close the entire application from task manager.
        //This method will executed only once.

        if (!mAlreadyStartedService ) {

            //mMsgView.setText(R.string.msg_location_service_started);

            //Start location sharing service to app server.........
            Intent intent = new Intent(this, LocationMonitoringService.class);
            startService(intent);
            mAlreadyStartedService = true;

            // callBackground(userLocBroadCast.getLatitude(),userLocBroadCast.getLongitude());
            //Ends................................................
        }
    }

   /* private void callBackground(String latitude,String longitude) {
        Background background=new Background(getApplicationContext());

        background.execute(latitude,longitude, Email.getEmail());

    }*?

    /**
     * Return the availability of GooglePlayServices
     */
    public boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(this);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(this, status, 2404).show();
            }
            return false;
        }
        return true;
    }


    /**
     * Return the current state of the permissions needed.
     */
    private boolean checkPermissions() {
        int permissionState1 = ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION);

        int permissionState2 = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        return permissionState1 == PackageManager.PERMISSION_GRANTED && permissionState2 == PackageManager.PERMISSION_GRANTED;

    }

    /**
     * Start permissions requests.
     */
    private void requestPermissions() {

        boolean shouldProvideRationale =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION);

        boolean shouldProvideRationale2 =
                ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.ACCESS_COARSE_LOCATION);


        // Provide an additional rationale to the img_user. This would happen if the img_user denied the
        // request previously, but didn't check the "Don't ask again" checkbox.
        if (shouldProvideRationale || shouldProvideRationale2) {
            Log.i(TAG, "Displaying permission rationale to provide additional context.");
            showSnackbar(R.string.permission_rationale,
                    android.R.string.ok, new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Request permission
                            ActivityCompat.requestPermissions(homepage.this,
                                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                                    REQUEST_PERMISSIONS_REQUEST_CODE);
                        }
                    });
        } else {
            Log.i(TAG, "Requesting permission");
            // Request permission. It's possible this can be auto answered if device policy
            // sets the permission in a given state or the img_user denied the permission
            // previously and checked "Never ask again".
            ActivityCompat.requestPermissions(homepage.this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


    /**
     * Shows a {@link Snackbar}.
     *
     * @param mainTextStringId The id for the string resource for the Snackbar text.
     * @param actionStringId   The text of the action item.
     * @param listener         The listener associated with the Snackbar action.
     */
    private void showSnackbar(final int mainTextStringId, final int actionStringId,
                              View.OnClickListener listener) {
        Snackbar.make(
                findViewById(android.R.id.content),
                getString(mainTextStringId),
                Snackbar.LENGTH_INDEFINITE)
                .setAction(getString(actionStringId), listener).show();
    }

    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        Log.i(TAG, "onRequestPermissionResult");
        if (requestCode == REQUEST_PERMISSIONS_REQUEST_CODE) {
            if (grantResults.length <= 0) {
                // If img_user interaction was interrupted, the permission request is cancelled and you
                // receive empty arrays.
                Log.i(TAG, "User interaction was cancelled.");
            } else if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.i(TAG, "Permission granted, updates requested, starting location updates");
                startStep3();

            } else {
                // Permission denied.

                // Notify the img_user via a SnackBar that they have rejected a core permission for the
                // app, which makes the Activity useless. In a real app, core permissions would
                // typically be best requested during a welcome-screen flow.

                // Additionally, it is important to remember that a permission might have been
                // rejected without asking the img_user for permission (device policy or "Never ask
                // again" prompts). Therefore, a img_user interface affordance is typically implemented
                // when permissions are denied. Otherwise, your app could appear unresponsive to
                // touches or interactions which have required permissions.
                showSnackbar(R.string.permission_denied_explanation,
                        R.string.settings, new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                // Build intent that displays the App settings screen.
                                Intent intent = new Intent();
                                intent.setAction(
                                        Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package",
                                        BuildConfig.APPLICATION_ID, null);
                                intent.setData(uri);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        });
            }
        }
    }


    @Override
    public void onDestroy() {


        //Stop location sharing service to app server.........

        stopService(new Intent(this, LocationMonitoringService.class));
        mAlreadyStartedService = false;
        //Ends................................................


        super.onDestroy();
    }





}
