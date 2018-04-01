package com.example.palash.pushremote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.palash.pushremote.event.impl.VolumeEvent;
import com.example.palash.pushremote.event.type.EventType;
import com.example.palash.pushremote.restCallable.RestCallable;
import com.example.palash.pushremote.restTemplate.RestTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Set;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView tv;
    Spinner dropdown;
    Button volPlus5;
    Button volMinus5;
    TextView volume;

    String sharableLink = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();


        populateSlaveNames();

        RestCallable callable = new RestCallable() {
            @Override
            public String call() throws Exception {
                tv.setText(this.getResposne());

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

    private void initViews() {
        tv =(TextView) findViewById(R.id.baseTV1);
        dropdown = (Spinner)findViewById(R.id.spinner1);
        volume = (TextView)findViewById(R.id.volumeTv);
        volMinus5 = (Button)findViewById(R.id.volMinus5);
        volPlus5 = (Button)findViewById(R.id.volPlus5);
        volMinus5.setOnClickListener(this);
        volPlus5.setOnClickListener(this);
        tv.setText("0");
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
            case R.id.volMinus5:
                processVolume(-5);
                break;
            case R.id.volPlus5:
                processVolume(5);
                break;
            default:break;
        }
    }

    private void processVolume(int change) {
        int value = Integer.parseInt(volume.getText().toString());
        int newValue = value+change;
        if(newValue>=0 && newValue<=100){
            volume.setText(String.valueOf(newValue));
            changeRemoteVolume();
        }
    }
    private void changeRemoteVolume() {
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
            VolumeEvent event = new VolumeEvent();
            event.setDestination((String)dropdown.getSelectedItem());
            event.setVolumePercentage(Integer.parseInt(volume.getText().toString()));
            event.setType(EventType.YOUTUBE);
            rs.setup("192.168.0.111", "/changeVolume", 8080, gson.toJson(event), callable);
            rs.execute("", "POST");
        }
        catch (Exception e){
            tv.setText(e.toString());
        }
    }
}


