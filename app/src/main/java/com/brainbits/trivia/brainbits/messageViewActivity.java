package com.brainbits.trivia.brainbits;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class messageViewActivity extends AppCompatActivity {

    // Vars
    private ArrayList<String> messages;
    private SessionManager manager;
    private String description;

    // Widgets
    private TextView sender;
    private TextView descript;
    private ListView messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);

        Bundle itemIntent = getIntent().getExtras();
        String Name = itemIntent.getString("message Name");

        manager = new SessionManager(this);

        JSONObject json = manager.getMessages();

        try {

            JSONArray array =  json.getJSONArray(manager.CLUE_LIST);

            for (int i = 0; i < array.length(); i++) {

                JSONObject tmp = array.getJSONObject(i);
                String tmpNmae = tmp.getString(manager.MISSION_NAME_TAG);

                if (tmpNmae.equals(Name)) {
                    messages = (ArrayList<String>) tmp.get(manager.MISSION_CLUE_TAG);
                    description = tmp.getString(manager.MISSION_DESCRIPTION_TAG);
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }



        sender = (TextView) findViewById(R.id.sender_tv);
        descript = (TextView) findViewById(R.id.description_tv);

        messageList = (ListView) findViewById(R.id.messages_lv);

        sender.setText(Name);
        descript.setText(description);


        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,messages);
        messageList.setAdapter(adapter);

    }
}
