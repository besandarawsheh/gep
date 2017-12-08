package com.example.hp.gep;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

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

public class BackgroundWorker extends AsyncTask<String,Void,String> {


    Context context;
    AlertDialog alertDialog;
    //constructor
    BackgroundWorker(Context ctx){

        context=ctx;
    }
    @Override
    protected String doInBackground(String... params) {
//extract epage from params
        String epage = params[0];
        String eventInfo_url = "http://192.168.1.108/addedEventInfo.php";
        if (epage.equals("addedEventInfo")) {
            //posting
            try {
                String name = params[1];
                String time = params[2];
                String date = params[3];
                String location = params[4];
                String type = params[5];
                String email=params[6];
                String password=params[7];
                String lat=params[8];
                String log=params[9];

                URL url = new URL(eventInfo_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+URLEncoder.encode("time","UTF-8")+"="+URLEncoder.encode(time,"UTF-8")+"&"+URLEncoder.encode("date","UTF-8")+"="+URLEncoder.encode(date,"UTF-8")+"&"+URLEncoder.encode("location","UTF-8")+"="+URLEncoder.encode(location,"UTF-8")+"&"+URLEncoder.encode("type","UTF-8")+"="+URLEncoder.encode(type,"UTF-8")+"&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")+"&"+URLEncoder.encode("lat","UTF-8")+"="+URLEncoder.encode(lat,"UTF-8")+"&"+URLEncoder.encode("log","UTF-8")+"="+URLEncoder.encode(log,"UTF-8");
                bufferedWriter.write(post_data);
                //+"&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+URLEncoder.encode("password","UTF-8")+"="+URLEncoder.encode(password,"UTF-8")
                //String post_data = URLEncoder.encode("name", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" + URLEncoder.encode("time", "UTF-8") + "=" + URLEncoder.encode(time, "UTF-8") + "&" + URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&" + URLEncoder.encode("location", "UTF-8") + "=" + URLEncoder.encode(location, "UTF-8") + "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type, "UTF-8") ;
                //bufferedWriter.write(post_data);
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


        }

        return null;

    }

    @Override
    protected void onPreExecute() {
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle("addedInfo status");

    }

    @Override
    protected void onPostExecute(String result) {
        alertDialog.setMessage(result);
        alertDialog.show();

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }


}