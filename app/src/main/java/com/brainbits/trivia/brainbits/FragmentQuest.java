package com.brainbits.trivia.brainbits;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

public class FragmentQuest  extends Fragment{

    View view;

    public FragmentQuest() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        // Creating a view to set up the layout inside the fragment
        view = inflater.inflate(R.layout.quest_info_fragment,container,false);

        // Manage dialog popup
        final Dialog missionInfo = new Dialog(getActivity());
        final Dialog missionInfoav = new Dialog(getActivity());


        // String Arrays for the list Views
        // Access Database to getInfo

        final SessionManager manager = new SessionManager(getActivity());
        final JSONArray Missionarray = manager.retrieveMissions();


          final ArrayList<String> mMissions = new ArrayList<>();

          for (int i = 0; i < Missionarray.length(); i++) {

              try {

                  JSONObject json = Missionarray.getJSONObject(i);
                  String mission_name = json.getString(manager.MISSION_NAME_TAG);
                  mMissions.add(mission_name);

              } catch (JSONException e) {

                  e.printStackTrace();
              }


          }


        final ArrayList<String> pMissions = new ArrayList<>();


          JSONObject AvMiss = manager.getMissions();
        JSONArray miss = null;

        try {


            miss = AvMiss.getJSONArray(manager.MISSION_COMPLETED);

            for (int i = 0; i < AvMiss.length(); i++) {

                JSONObject tmpmiss;
                tmpmiss = miss.getJSONObject(i);
                pMissions.add(tmpmiss.getString(manager.MISSION_NAME_TAG));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // List Views from the Layout File
        final ListView cMissions = (ListView)view.findViewById(R.id.current_missions);
        ListView avMissions = (ListView)view.findViewById(R.id.available_missions);

        // Creating Array Adapters for each listView
        final ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,mMissions);
        final ArrayAdapter adapter1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,pMissions);

        // Setting the Adapters to their respective listView
        cMissions.setAdapter(adapter);
        avMissions.setAdapter(adapter1);

        // Adding a listener to evaluate clicks on each item
        cMissions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                missionInfo.setContentView(R.layout.dialog_mission_description);

                // Find dialog elements

                // TextViews
                TextView title = (TextView) missionInfo.findViewById(R.id.dialog_mission_title);
                TextView description = (TextView) missionInfo.findViewById(R.id.dialog_mission_description);
                TextView quest = (TextView) missionInfo.findViewById(R.id.dialog_mission_parent);

                // ImageView


                String NAME = null;
                String DESCRIPTION = null;
                String SPONSOR = null;
                String QUEST = null;


                // Set the mission title
                String tmp = mMissions.get(position);
                try {

                    JSONObject json = Missionarray.getJSONObject(position);

                    NAME = json.getString(manager.MISSION_NAME_TAG);
                    DESCRIPTION = "Ends: " + json.getString(manager.MISSION_END_DATE_TAG);
                    SPONSOR = json.getString(manager.MISSION_SPONSOR_TAG);
                    QUEST = json.getString(manager.MISSION_QUEST_TAG);


                } catch (JSONException e) {

                    e.printStackTrace();
                }

                title.setText(NAME);
                description.setText(DESCRIPTION);
                quest.setText(QUEST);

                // Buttons
                Button ok = (Button) missionInfo.findViewById(R.id.dialog_mission_ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        missionInfo.dismiss();
                    }
                });

                Button showClue = (Button) missionInfo.findViewById(R.id.dialog_mission_abort);
                final String finalQUEST = QUEST;

                showClue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        abortMission(finalQUEST);

                    }
                });

                // Setting the background to transparent
                missionInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // Showing Dialog
                missionInfo.show();
            }
        });

        // Adding a listener to evaluate clicks on each item
        final JSONArray finalMiss = miss;
        avMissions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                missionInfoav.setContentView(R.layout.dialog_missionav_description);
                // Find dialog elements

                // TextViews

                // Set the mission title
                TextView title = (TextView) missionInfoav.findViewById(R.id.dialog_missionav_title);
                title.setText(pMissions.get(position));

                // Get mission data from db

                // Set mission info in the dialog
                TextView sponsor = (TextView) missionInfoav.findViewById(R.id.dialog_missionav_sponsor);
                TextView description = (TextView) missionInfoav.findViewById(R.id.dialog_missionav_description);

                String SPONSOR = null;
                String DESCRIPTION = null;

                try {

                    JSONObject tmp = finalMiss.getJSONObject(position);
                    SPONSOR = tmp.getString(manager.MISSION_NAME_TAG);
                    DESCRIPTION = tmp.getString(manager.MISSION_DESCRIPTION_TAG);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                sponsor.setText(SPONSOR);
                description.setText(DESCRIPTION);


                // ImageView
                ImageView sponsorImg = (ImageView) missionInfoav.findViewById(R.id.dialog_missionav_sponsor_img);

                if (SPONSOR.equals("Banco Nacional")) {
                    sponsorImg.setImageResource(R.mipmap.sponsor_banco);
                }

                if (SPONSOR.equals("Almacen Jerusalem")) {
                    sponsorImg.setImageResource(R.mipmap.sponsor_jerusalem);
                }

                if (SPONSOR.equals("Jazz Cafe Club")) {
                    sponsorImg.setImageResource(R.mipmap.sponsor_jazz);
                }

                if (SPONSOR.equals("Dein Buster-Die Familienvideothek An und Verkauf")) {
                    sponsorImg.setImageResource(R.mipmap.sponsor_buster);
                }

                else {
                    sponsorImg.setImageResource(R.mipmap.sponsor_pepsi);
                }

                // Buttons
                Button ok = (Button) missionInfoav.findViewById(R.id.dialog_missionav_ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        missionInfoav.dismiss();
                    }
                });
                final Button joinMission = (Button) missionInfoav.findViewById(R.id.dialog_missionav_join);
                joinMission.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        mMissions.add(pMissions.get(position));
                        adapter.notifyDataSetChanged();
                        joinMission(pMissions.get(position));
                        pMissions.remove(position);
                        adapter1.notifyDataSetChanged();
                        missionInfoav.dismiss();

                    }
                });

                // Setting the background to transparent
                missionInfoav.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // Showing Dialog
                missionInfoav.show();
            }
        });

        // returning the view so it can be displayed in GeneralActivity
        return view;
    }

    // notifies the server that the user wishes to join a mission.
    private void joinMission (String mission){

        SessionManager manager = new SessionManager(getActivity());
        manager.joinMission(mission);
    }

    // notifies the server that the user wishes to abort a mission.
    private void abortMission (String mission){

        // Getting the username for it's use in the database
        SessionManager manager = new SessionManager(getActivity());
        manager.abortMission(mission);


    }

}
