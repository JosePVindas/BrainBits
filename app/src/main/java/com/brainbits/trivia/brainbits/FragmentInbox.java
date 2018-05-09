package com.brainbits.trivia.brainbits;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class FragmentInbox  extends Fragment {

    View view;


    public FragmentInbox() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.inbox_fragment,container,false);

       final  ArrayList<ArrayList<String>> data = getMessages();

        ArrayList<String> messages = new ArrayList<String>();

        for (int i = 0; i < data.size(); i++){
            ArrayList<String> tmp = data.get(i);
            messages.add(tmp.get(0));
        }
        ListView messageView = (ListView)view.findViewById(R.id.inbox_feed);

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,messages);
        messageView.setAdapter(adapter);

        messageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Intent i = new Intent(getActivity(),messageViewActivity.class);
                Bundle bundle = new Bundle();
                ArrayList<String> dta = data.get(position);
                bundle.putStringArrayList("Messages",dta);
                i.putExtras(bundle);
                startActivity(i);
            }
        });





        return view;
    }


    private ArrayList<ArrayList<String>> getMessages () {

        // Tmp array list for testing
        ArrayList<ArrayList<String>> messages = new ArrayList<ArrayList<String>>();

        for (int i =0; i < 20; i++) {

            ArrayList<String> tmp = new ArrayList<String>();
            String sender = "Sender" + i;
            tmp.add(sender);
            tmp.add("Description of the thing that needs to be" +
                    " really really long so I can see how well it manages " +
                    "but since I am running out of ideas I'll stop here! ");
            tmp.add("Message 1");
            tmp.add("Message 2");
            tmp.add("Message 3");
            tmp.add("Message 4");

            messages.add(tmp);

        }


        // Ask DataBase for messages

        return messages;

    }



}
