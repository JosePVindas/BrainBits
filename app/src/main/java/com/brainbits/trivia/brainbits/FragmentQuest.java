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
          final ArrayList<String> mMissions = getMissions();
        final ArrayList<String> pMissions = getAvailableMissions();

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

                // Set the mission title
                TextView title = (TextView) missionInfo.findViewById(R.id.dialog_mission_title);
                String tmp = mMissions.get(position);
                title.setText(tmp);

                // Get mission data from db

                // Set mission info in the dialog
                TextView sponsor = (TextView) missionInfo.findViewById(R.id.dialog_mission_sponsor);
                TextView description = (TextView) missionInfo.findViewById(R.id.dialog_mission_description);
                TextView clue = (TextView) missionInfo.findViewById(R.id.dialog_mission_first_clue);

                // ImageView
                ImageView sponsorImg = (ImageView) missionInfo.findViewById(R.id.dialog_mission_sponsor_img);

                // Buttons
                Button ok = (Button) missionInfo.findViewById(R.id.dialog_mission_ok);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        missionInfo.dismiss();
                    }
                });

                Button showClue = (Button) missionInfo.findViewById(R.id.dialog_mission_show_clues);
                showClue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        pMissions.add(mMissions.get(position));
                        adapter1.notifyDataSetChanged();
                        abortMission(mMissions.get(position));
                        mMissions.remove(position);
                        adapter.notifyDataSetChanged();
                        missionInfo.dismiss();
                    }
                });

                // Setting the background to transparent
                missionInfo.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                // Showing Dialog
                missionInfo.show();
            }
        });

        // Adding a listener to evaluate clicks on each item
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


                // ImageView
                ImageView sponsorImg = (ImageView) missionInfoav.findViewById(R.id.dialog_missionav_sponsor_img);

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

    private void joinMission (String mission){

        // Getting the username for it's use in the database
        SessionManager manager = new SessionManager(getActivity());
        String username = manager.getUsername();

       // Notify DB of new mission

    }

    private void abortMission (String mission){

        // Getting the username for it's use in the database
        SessionManager manager = new SessionManager(getActivity());
        String username = manager.getUsername();

        // Notify DB to remove mission

    }

    private ArrayList<String> getAvailableMissions() {

        // Getting the username for it's use in the database
        SessionManager manager = new SessionManager(getActivity());
        String username = manager.getUsername();

        // Tmp ArrayList for testing
        ArrayList<String> missions = new ArrayList<String>(
                Arrays.asList("Mission 10", "Mission 11", "Mission 12",
                              "Mission 13", "Mission 14", "Mission 15"));

        // Get data from DataBase


        return missions;

    }

    private ArrayList<String> getMissions() {

        // Getting the username for it's use in the database
        SharedPreferences preferences = getActivity().getSharedPreferences("Login", MODE_PRIVATE);
        String usr = preferences.getString("usr", null);

        // Tmp ArrayList for testing
        ArrayList<String> missions = new ArrayList<String>(
                Arrays.asList("Mission 1", "Mission 2", "Mission 3", "Mission 4",
                        "Mission 5", "Mission 6", "Mission 7", "Mission 8",
                        "Mission 9"));

        // Get data from DataBase


        return missions;
        
    }
}
