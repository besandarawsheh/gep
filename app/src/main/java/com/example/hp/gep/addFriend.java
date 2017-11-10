package com.example.hp.gep;

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

/**
 * Created by HP on 06/11/2017.
 */

public class addFriend extends AsyncTask<String,Void,String> {

    Context context;
    AlertDialog alertDialog;
    //constructor
    addFriend(Context ctx){

        context=ctx;
    }


    @Override
    protected String doInBackground(String... params) {
//extract epage from params

        String friendInfo_url = "http://192.168.1.70/addFriend.php";

        //posting
        try {
            String Fname = params[0];
            String Femail = params[1];
            String email =params[2];

            //Toast.makeText(context,email,Toast.LENGTH_SHORT).show();
            URL url = new URL(friendInfo_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            String post_data =URLEncoder.encode("Fname","UTF-8")+"="+URLEncoder.encode(Fname,"UTF-8")+"&"+URLEncoder.encode("Femail","UTF-8")+"="+URLEncoder.encode(Femail,"UTF-8")+"&"+URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8");
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
        alertDialog.setTitle("AddFriend Request status");

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
