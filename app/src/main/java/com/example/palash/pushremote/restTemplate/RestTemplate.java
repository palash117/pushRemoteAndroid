package com.example.palash.pushremote.restTemplate;

import android.net.Uri;
import android.os.AsyncTask;
import android.telecom.Call;

import com.example.palash.pushremote.restCallable.RestCallable;
import com.google.gson.Gson;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.Callable;

/**
 * Created by palash on 22/7/17.
 */

public class RestTemplate <E> extends AsyncTask implements Restable {

    String base =null;

    String path = null;

    URI uri =null;

    int port;

    RestCallable callable;

    E entity;

    @Override
    public void setup(String base, String apiPath, int apiPort, Object entity, RestCallable callable) throws URISyntaxException {
        this.entity = (E)entity;
        this.base = base;
        this.path = path;
        this.port = port = port;
        this.callable = callable;
        try {
            String protocol = "http";
            String host = base;
            int port = apiPort;
            String path = apiPath;
            String auth = null;
            String fragment = null;
            uri = new URI(protocol, auth, host, port, path, null, fragment);
        }
        catch (Exception e){
            throw e;
        }
    }



    @Override
    public String postForObject(Object obj, URI uri) {
        String response = null;
        try{
        URL url = uri.toURL();
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json");
        //if(conn.)
        String input = (String) obj;
        OutputStream os = conn.getOutputStream();
        os.write(input.getBytes());
        os.flush();

        if(conn.getResponseCode() != HttpURLConnection.HTTP_CREATED&&conn.getResponseCode()!= HttpURLConnection.HTTP_OK){
            throw new RuntimeException("Failed : HTTP error code : "
                    + conn.getResponseCode());
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream())));

        String output;
        System.out.println("Output from Server .... \n");
        StringBuilder data = new StringBuilder();
        while ((output = br.readLine()) != null) {
            System.out.println(output);
            data.append(output);
        }

        conn.disconnect();
            response = (data.toString());




    }
            catch(Exception e){

    }
            return response;
    }

    @Override
    public String get(URI uri) {
        String response = null;
        try {
            URL url = uri.toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            if(conn.getResponseCode() != HttpURLConnection.HTTP_CREATED&&conn.getResponseCode()!= HttpURLConnection.HTTP_OK){
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream())));

            String output;
            System.out.println("Output from Server .... \n");
            StringBuilder data = new StringBuilder();
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                data.append(output);
            }

            conn.disconnect();
            response = (data.toString());
            if(response == null){
                throw new NullPointerException();
            }

        }
        catch(Exception e){
            response = e.toString();
        }
        return response;
    }

    @Override
    protected String doInBackground(Object[] objects) {
        String response = null;

        RestType type = RestType.getRestTypeByName((String)objects[1]);

        switch (type){
            case GET:
                response = get(uri);
                break;
            case POST:
                response = postForObject( entity, uri);
                break;
        }

        return response;
    }

    @Override
    protected void onPostExecute(Object s) {

        String str = (String)s;
        try {
            callable.setResposne(str);
            callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
