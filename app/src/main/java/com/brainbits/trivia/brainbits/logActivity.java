package com.brainbits.trivia.brainbits;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class logActivity extends AppCompatActivity {

    // Vars
    private SessionManager manager;
    private JSONObject info;
    ArrayList<String> logInfo;

    // Widgets
    private ListView log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log);

        logInfo = new ArrayList<>();

        manager = new SessionManager(this);
        info = manager.getLog();

        try {

            logInfo = (ArrayList<String>) info.get(manager.LOG_TAG);

        } catch (JSONException e) {

            e.printStackTrace();
        }

        log = (ListView) findViewById(R.id.log_list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,logInfo);
        log.setAdapter(adapter);

    }
}
