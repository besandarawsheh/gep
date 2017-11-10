package com.example.hp.gep;

/**
 * Created by HP on 08/11/2017.
 */
import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

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

public class acceptFriend extends AsyncTask<String,Void,String> {

        Context context;
        AlertDialog alertDialog;
        //constructor
        acceptFriend(Context ctx){

        context=ctx;
        }


@Override
protected String doInBackground(String...params){
//extract epage from params

        String friendInfo_url="http://192.168.1.70/actionFRequest.php";

        //posting
        try{

        String Femail=params[0];
        String email=params[1];
            String action =params[2];
            String Fname =params[3];
            String Fgender =params[4];
        URL url=new URL(friendInfo_url);
        HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestMethod("POST");
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setDoInput(true);
        OutputStream outputStream=httpURLConnection.getOutputStream();
        BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
        String post_data=URLEncoder.encode("Femail","UTF-8")+"="+URLEncoder.encode(Femail,"UTF-8")+"&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+URLEncoder.encode("action","UTF-8")+"="+URLEncoder.encode(action,"UTF-8")+"&"+URLEncoder.encode("Fname","UTF-8")+"="+URLEncoder.encode(Fname,"UTF-8")+"&"+URLEncoder.encode("Fgender","UTF-8")+"="+URLEncoder.encode(Fgender,"UTF-8");
        bufferedWriter.write(post_data);
        bufferedWriter.flush();
        bufferedWriter.close();
        outputStream.close();
        InputStream inputStream=httpURLConnection.getInputStream();
        BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream,"iso_8859-1"));

        String line="";
        String result="";
        while((line=bufferedReader.readLine())!=null){
        result+=line;
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
protected void onPreExecute(){
        alertDialog=new AlertDialog.Builder(context).create();
        alertDialog.setTitle(" Request status");

        }

@Override
protected void onPostExecute(String result){
        alertDialog.setMessage(result);
        alertDialog.show();
        }

@Override
protected void onProgressUpdate(Void...values){
        super.onProgressUpdate(values);
        }

        }

