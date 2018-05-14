package com.brainbits.trivia.brainbits;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        SessionManager manager = new SessionManager(getActivity());
        final ArrayList<String> messages = new ArrayList<String>();

        JSONObject json = manager.getMessages();


        try {


            JSONArray messageData = json.getJSONArray(manager.CLUE_LIST);

            for (int i = 0; i < messageData.length(); i++) {
                JSONObject mission = messageData.getJSONObject(i);
                String missionName = mission.getString(manager.MISSION_NAME_TAG);
                Log.d("Adding names", missionName);
                messages.add(missionName);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }



        ListView messageView = (ListView)view.findViewById(R.id.inbox_feed);

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,messages);
        messageView.setAdapter(adapter);

        messageView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Intent i = new Intent(getActivity(),messageViewActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("message Name",messages.get(position));
                i.putExtras(bundle);
                startActivity(i);
            }
        });





        return view;
    }






}
