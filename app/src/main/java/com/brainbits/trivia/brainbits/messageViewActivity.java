package com.brainbits.trivia.brainbits;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class messageViewActivity extends AppCompatActivity {

    // Vars
    private ArrayList<String> messages;

    // Widgets
    TextView sender;
    TextView descript;
    ListView messageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_view);

        Bundle itemIntent = getIntent().getExtras();
        this.messages = itemIntent.getStringArrayList("Messages");

        sender = (TextView) findViewById(R.id.sender_tv);
        descript = (TextView) findViewById(R.id.description_tv);

        messageList = (ListView) findViewById(R.id.messages_lv);

        sender.setText(messages.get(0));
        messages.remove(0);

        descript.setText(messages.get(0));
        messages.remove(0);

        ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,messages);
        messageList.setAdapter(adapter);

        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(messageViewActivity.this,"Coming soon!", Toast.LENGTH_LONG).show();

            }
        });
    }
}
