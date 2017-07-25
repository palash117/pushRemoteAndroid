package com.example.palash.pushremote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.palash.pushremote.restCallable.RestCallable;
import com.example.palash.pushremote.restTemplate.RestTemplate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    TextView tv;
    Spinner dropdown;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

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
    }
}


