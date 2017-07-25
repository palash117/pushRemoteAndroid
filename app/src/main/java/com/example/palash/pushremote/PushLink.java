package com.example.palash.pushremote;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.palash.pushremote.event.impl.BasicEvent;
import com.example.palash.pushremote.event.type.EventType;
import com.example.palash.pushremote.restCallable.RestCallable;
import com.example.palash.pushremote.restTemplate.RestTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Set;
import java.util.logging.Logger;

public class PushLink extends AppCompatActivity implements View.OnClickListener{

    TextView tv;
    Spinner dropdown;

    String sharableLink = null;
    Button button ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push_link);

        initViews();
        retreiveLink();



        populateSlaveNames();
    }

    private void populateSlaveNames() {
        RestCallable callable = new RestCallable() {
            @Override
            public String call() throws Exception {

                Gson gson = new GsonBuilder().create();

                String[] itemList = gson.fromJson(this.getResposne(), String[].class);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, itemList);

                dropdown.setAdapter(adapter);

                return this.getResposne();

            }
        };
        RestTemplate rs = new RestTemplate();
        try {

            rs.setup("192.168.0.111", "/getConsumerNames", 8080, null, callable);
            rs.execute("", "GET");
        }
        catch (Exception e){
            tv.setText(e.toString());
        }
    }


    private void pushEvent() {
        RestCallable callable = new RestCallable() {
            @Override
            public String call() throws Exception {
                tv.setText(this.getResposne());

                return this.getResposne();

            }
        };
        RestTemplate rs = new RestTemplate();
        Gson gson = new GsonBuilder().create();

        try {
            BasicEvent event = new BasicEvent();
            event.setDestination((String)dropdown.getSelectedItem());
            event.setLink(sharableLink);
            event.setType(EventType.YOUTUBE);
            rs.setup("192.168.0.111", "/pushEvent", 8080, gson.toJson(event), callable);
            rs.execute("", "POST");
        }
        catch (Exception e){
            tv.setText(e.toString());
        }
    }


    private void initViews() {
        tv =(TextView) findViewById(R.id.pushLink);
        dropdown = (Spinner)findViewById(R.id.pushSpinner);
        button = (Button) findViewById(R.id.pushButton);
        button.setOnClickListener(this);
    }

    private void retreiveLink() {


        Intent intent = getIntent();

        Bundle bundle = intent.getExtras();

        Set<String> keySet = bundle.keySet();

        System.out.println(keySet);

        String link = bundle.getString(Intent.EXTRA_TEXT);

        sharableLink = link;

        System.out.println(link);

        tv.setText(link);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.pushButton:
                pushEvent();
                break;
        }
    }
}
