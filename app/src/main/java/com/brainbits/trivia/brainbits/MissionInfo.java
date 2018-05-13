package com.brainbits.trivia.brainbits;

import android.util.JsonWriter;

import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MissionInfo {

    // Vars
    private String mTitle;
    private String mDescription;
    private String mSponsor;
    private ArrayList<JSONObject> mQuests = new ArrayList<>();

    private static final String NAME_TAG = "Name";
    private static final String CLUE_LIST = "clueList";
    private static final String SOLUTION_TAG = "Solution";

    // CONSTRUCTOR
    public MissionInfo () {

    }


    // SETTERS


    // Set the list of quests with their clues
    public void setmQuests (ArrayList<String> names, ArrayList<ArrayList<String>> clues, ArrayList<LatLng> solutions) {

        if (names.size() == clues.size() && solutions.size() == names.size()) {

            for (int i = 0; i < clues.size(); i++) {

                JSONObject tmp = new JSONObject();
                try {

                    tmp.put(NAME_TAG, names.get(i));
                    tmp.put(CLUE_LIST, clues.get(i));
                    tmp.put(SOLUTION_TAG,solutions.get(i));
                    this.mQuests.add(tmp);


                } catch (JSONException e) {

                }

            }
        }
    }

    // Set Mission title
    public void setTitle (String title) {
        this.mTitle = title;
    }

    // Set Mission description
    public void setDescription (String description) {
        this.mDescription = description;
    }

    // Set Mission Sponsor
    public void setSponsor (String sponsor) {
        this.mSponsor = sponsor;
    }


    // GETTERS


    // Get mission quest list
    public ArrayList<JSONObject> getQuests() {
        return mQuests;
    }

    // Get mission title
    public String getTitle() {
        return mTitle;
    }

    // Get mission description
    public String getDescription() {
        return mDescription;
    }

    // Get mission sponsor
    public String getSponsor() {
        return this.mSponsor;
    }


}

