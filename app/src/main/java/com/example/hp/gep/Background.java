package com.example.hp.gep;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.hp.gep.services.LocationMonitoringService;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by HP on 20/11/2017.
 */

public class Background  extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    //constructor
    public Background(Context ctx){

        context=ctx;
    }


    @Override
    protected String doInBackground(String... params) {
//extract epage from params

        String friendInfo_url = "http://192.168.1.108/userLoc.php";

        //posting
        try {
            String loc_lat= params[0];
            String loc_long= params[1];
String email =params[2];
String shareLoc =params[3];
            //Toast.makeText(context,email,Toast.LENGTH_SHORT).show();
            URL url = new URL(friendInfo_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data = URLEncoder.encode("loc_lat","UTF-8")+"="+URLEncoder.encode(loc_lat,"UTF-8")+"&"+URLEncoder.encode("loc_long","UTF-8")+"="+URLEncoder.encode(loc_long,"UTF-8")+"&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+URLEncoder.encode("shareLoc","UTF-8")+"="+URLEncoder.encode(shareLoc,"UTF-8");
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
            return result;


        }

        catch(MalformedURLException e){
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }




        return null;

    }

    @Override
    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("userLoc status");

    }

    @Override
    protected void onPostExecute(String result) {
        Toast.makeText(context,result,Toast.LENGTH_SHORT).show();
        //alertDialog.setMessage(result);
        //alertDialog.show();
        String email=Email.getEmail();
        Intent i = new Intent(context,homepage.class);
        //ntent.putExtra("ListViewValue", eventsID.get(position).toString());
        i.putExtra("email",email);
        context.startActivity(i);

        Toast.makeText(context,email,Toast.LENGTH_LONG).show();
       // new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST);
        //context.startActivity(new Intent(context, homepage.class).putExtra(email,"email"));

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }



}