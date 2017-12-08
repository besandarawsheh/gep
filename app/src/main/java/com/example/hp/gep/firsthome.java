package com.example.hp.gep;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;

public class firsthome extends mainpage  implements BaseSliderView.OnSliderClickListener,
        ViewPagerEx.OnPageChangeListener {

    HttpParse httpParse = new HttpParse();
    HttpService httpService = null;
    ListView eventListView;
    ProgressBar progressBar;
    String birthday_url = "http://192.168.1.108/birthday.php";
    String graduation_url = "http://192.168.1.108/graduation.php";
    String it_url = "http://192.168.1.108/it.php";
    String wedding_url = "http://192.168.1.108/wedding.php";
    String education_url = "http://192.168.1.108/education.php";
    String other_url = "http://192.168.1.108/other.php";
    List<String> eventsID = new ArrayList<>();
    private static final String TAG = firsthome.class.getSimpleName();
String page ="";
    SliderLayout sliderLayout ;

    HashMap<String, String> HashMapForURL ;

    HashMap<String, Integer> HashMapForLocalRes ;
    //private TextView mTextMessage;
    private ImageSwitcher sw;
    private Button b1,b2;
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
                case R.id.navigation_dashboard:
                    //mTextMessage.setText(R.string.title_dashboard);
                    Intent in = new Intent(getApplicationContext(),homepage.class);
                    //ntent.putExtra("ListViewValue", eventsID.get(position).toString());

                    startActivity(in);
                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;

            }
            return false;
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout contentFrameLayout = (FrameLayout) findViewById(R.id.content_frame); //Remember this is the FrameLayout area within your activity_main.xml
        getLayoutInflater().inflate(R.layout.activity_firsthome, contentFrameLayout);


      //  mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
       // navigation.setBackgroundColor(Color.parseColor("#B07D4A"));

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        final String email = getIntent().getStringExtra("email");
        sliderLayout = (SliderLayout)findViewById(R.id.slider);
        eventListView = (ListView) findViewById(R.id.listviewhome);
        final RequestQueue queue = Volley.newRequestQueue(this);
        //final RequestQueue queue = Volley.newRequestQueue(this);
//final String email=Email.getEmail();
        progressBar = (ProgressBar) findViewById(R.id.progressBarhome);
        eventListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // TODO Auto-generated method stub

                Intent intent = new Intent(firsthome.this, willgo.class);

                //Sending ListView clicked value using intent.
                intent.putExtra("email", email);
                intent.putExtra("id",id);
                intent.putExtra("ListViewValue", eventsID.get(position).toString());
                startActivity(intent);

                //Finishing current activity after open next activity.
                finish();

            }
        });
        //Call this method if you want to add images from URL .
        AddImagesUrlOnline();

        //Call this method to add images from local drawable folder .
        // AddImageUrlFormLocalRes();

        //Call this method to stop automatic sliding.
        //sliderLayout.stopAutoCycle();

        for(String name : HashMapForURL.keySet()){

            TextSliderView textSliderView = new TextSliderView(firsthome.this);

            textSliderView
                    .description(name)
                    .image(HashMapForURL.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            textSliderView.bundle(new Bundle());

            textSliderView.getBundle()
                    .putString("extra",name);

            sliderLayout.addSlider(textSliderView);
        }
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.DepthPage);

        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);

        sliderLayout.setCustomAnimation(new DescriptionAnimation());

        sliderLayout.setDuration(3000);

        sliderLayout.addOnPageChangeListener(firsthome.this);


    }//onCreate


    @Override
    protected void onStop() {

        sliderLayout.stopAutoCycle();

        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

        Toast.makeText(this,slider.getBundle().get("extra") + "", Toast.LENGTH_SHORT).show();
        String type=slider.getBundle().get("extra").toString();

        switch (type) {
            case "IT":
                onITRequested();
                break;
            case "Education":
                onEducationRequested();
                break;
            case "Graduation":
                onGraduationRequested();
                break;
            case "Birthday":
                onBirthdayRequested();
                break;
            case "Wedding":
                onWeddingRequested();
                break;
            case "Other":
                onOtherRequested();
                break;
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

    @Override
    public void onPageSelected(int position) {

        Log.d("Slider Demo", "Page Changed: " + position);

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void AddImagesUrlOnline(){

        HashMapForURL = new HashMap<String, String>();

        HashMapForURL.put("IT", "http://netdomainhost.biz/i/wp-content/uploads/2015/01/live-stream.jpg");
        HashMapForURL.put("Education", "https://thumb1.shutterstock.com/display_pic_with_logo/702400/193539209/stock-photo-speaker-at-business-conference-and-presentation-audience-at-the-conference-hall-193539209.jpg");
        HashMapForURL.put("Graduation", "http://www.anu.edu.au/files/styles/anu_full_920_518/public/event/Graduation1image.jpg?itok=PyIwRH2B");
        HashMapForURL.put("Birthday", "https://thumb1.shutterstock.com/display_pic_with_logo/4256548/418556503/stock-photo-birthday-cake-with-candles-bright-lights-bokeh-418556503.jpg");
        HashMapForURL.put("Wedding", "https://thumb1.shutterstock.com/display_pic_with_logo/3668747/471093503/stock-photo-wedding-accessories-bouquet-and-accessories-of-bride-and-groom-wedding-details-471093503.jpg");
        HashMapForURL.put("Other", "https://thumb7.shutterstock.com/display_pic_with_logo/161489209/523706464/stock-photo-event-word-written-in-wooden-cube-523706464.jpg");
    }




   /* public void AddImageUrlFormLocalRes(){

        HashMapForLocalRes = new HashMap<String, Integer>();

       // HashMapForLocalRes.put("CupCake", R.drawable.cake);
        HashMapForLocalRes.put("Donut", R.drawable.birthday);
        HashMapForLocalRes.put("Eclair", R.drawable.unnamed);
        HashMapForLocalRes.put("Froyo", R.drawable.graduation);
       // HashMapForLocalRes.put("GingerBread", R.drawable.any);

    }*/



    public void onITRequested() {
        page = "it";
        new GetHttpResponse(firsthome.this).execute(page);
    }


    public void onEducationRequested() {

        page = "education";
        new GetHttpResponse(firsthome.this).execute(page);
    }


    public void onGraduationRequested() {
        page = "graduation";
        new GetHttpResponse(firsthome.this).execute(page);

    }

    public void onBirthdayRequested() {
        page = "birthday";
        new GetHttpResponse(firsthome.this).execute(page);

    }

    public void onWeddingRequested() {

        page = "wedding";
        new GetHttpResponse(firsthome.this).execute(page);

    }

    public void onOtherRequested() {

        page = "other";
        new GetHttpResponse(firsthome.this).execute(page);

    }
    private class GetHttpResponse extends AsyncTask<String, Void, String> {
        public Context context;
         AlertDialog alertDialog;
        String JSonresult;

        List<Event> eventList;

        public GetHttpResponse(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            alertDialog=new AlertDialog.Builder(context,R.style.MyDialogTheme).create();
            alertDialog.setTitle(Html.fromHtml("<font color='#FFFACD'>Sorry no matching results</font>"));
            Toast.makeText(firsthome.this, "onPreExecute!",
                    LENGTH_SHORT).show();

            super.onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            //passing http url to httpservice class


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
           // Toast.makeText(firsthome.this, "browse your events!",
             //       LENGTH_SHORT).show();
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
               else{

                alertDialog.setMessage(Html.fromHtml("<font color='#FFFACD'>OOPs:there is no events of this type,you could create your own</font>"));
               alertDialog.show();}
        }
    }





}
