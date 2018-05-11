package com.brainbits.trivia.brainbits;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class SessionManager {

    // Vars
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "BrainBitsPreferences";
    private ArrayList<MissionInfo> Missions;

    // Shared Preferences Keys for login
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_PASSWORD = "USER_PASSWORD";
    private static final String KEY_EMAIL = "USER_EMAIL";

    // Shared Preferences Keys for gameplay
    private static final String KEY_RANK = "USER_RANK";
    private static final String KEY_COMPLETED_MISSIONS = "COMPLETED_MISSIONS";

    // Json tags
    private static final String NAME_TAG = "NAME";
    private static final String LAST_NAME_TAG = "LAST_NAME";
    private static final String SECOND_LAST_NAME_TAG = "SECOND_LAST_NAME";

    private static final String USERNAME_TAG = "USERNAME";
    private static final String EMAIL_TAG = "EMAIL";
    private static final String PASSWORD_TAG = "PASSWORD";

    private static final String COUNTRY_TAG = "COUNTRY";
    private static final String STATE_TAG = "STATE";
    private static final String CITY_TAG = "CITY";

    private static final String RANK_TAG = "RANK";

//    private static final String KEY_INBOX = "USER_MESSAGES";
//    private static final String KEY_MISSIONS = "USER_MISSIONS";
//    private static final String KEY_NEW_MISSIONS = "USER_AVAILABLE_MISSIONS";



    // CONSTRUCTOR


    // constructor uses context to jump between activities and make toast messages show.
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }



    // SHARED PREFERENCES METHODS



    // Create a login session where all the important information about the user is stored
    public void createLoginSession (
            String name, String lastName, String sLastName, String username,
            String email, String password, String country, String state, String city, int rank) {

        // validate information before storing in Shared Preferences
        if (isUserInfoOk(username,email)){

            // Store all data in Shared Preferences
            editor.putBoolean(IS_LOGIN, true);

            editor.putString(KEY_USERNAME, username);
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_PASSWORD, password);
            editor.putInt(KEY_RANK, 0);
            editor.putInt(KEY_COMPLETED_MISSIONS, 0);
            editor.commit();

            createUserAccount(name, lastName, sLastName, username,email,password,country, state, city,0);

            final Intent i = new Intent(_context ,MapsActivity.class);
            _context.startActivity(i);


        } else {

            Toast.makeText(this._context,"Username or password already taken",Toast.LENGTH_LONG).show();

        }

    }

    // login the user to access the application
    public void loginUser (String username, String password) {

        if (isUserInfoOk(username,password,0)) {

            openSession(username,password);
            final Intent i = new Intent(_context,MapsActivity.class);
            _context.startActivity(i);

        } else {
            Toast.makeText(_context,"Username or Password incorrect",Toast.LENGTH_LONG).show();
        }
    }

    // clear all the data from shared preferences
    public void logoutUser() {

        editor.clear();
        editor.commit();
    }

    // Check if the user is logged in
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }

    // get the username from shared preferences
    public String getUsername () {

        String username = pref.getString(KEY_USERNAME, null);
        return username;

    }

    // get the email from shared preferences
    public String getEmail () {

        String email = pref.getString(KEY_EMAIL, null);
        return email;

    }

    // get rank from shared preferences
    public int getRank() {
        int rank = pref.getInt(KEY_RANK, 0);
        return rank;
    }

    // update user rank in shared preferences
    public void setRank (int rank) {

        editor.putInt(KEY_RANK, rank);
        editor.commit();
    }



    // SERVER METHODS



    // Save user details in shared preferences and data base
    private void createUserAccount (
            String name, String lastName, String sLastName, String username,
            String email, String password, String country, String state, String city, int rank) {

        // Submit all information to database
        JSONObject user = new JSONObject();

        try {

            user.put(NAME_TAG, name);
            user.put(LAST_NAME_TAG, lastName);
            user.put(SECOND_LAST_NAME_TAG, sLastName);

            user.put(USERNAME_TAG, username);
            user.put(EMAIL_TAG, email);
            user.put(PASSWORD_TAG, password);

            user.put(COUNTRY_TAG, country);
            user.put(STATE_TAG, state);
            user.put(CITY_TAG, city);

            user.put(RANK_TAG, rank);


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    // Validate username or email against password
    private boolean isUserInfoOk (String username, String password, int diferentiator) {

        //boolean tmp = false; // it will be false by default but true for testing purposes
        boolean tmp = true;
        // Validate username or email against password on the database

        return tmp;
    }

    // Validate username and email with database to make sure they're available
    private boolean isUserInfoOk (String username, String email) {

        //boolean tmp = false; // it will be false by default but true for testing purposes
        boolean tmp = true;


        return tmp;
    }

    // Login user and get all data from database
    private void openSession(String username, String password) {

        String email = new String();
        int rank = 0;
        int completed_missions = 0;

        // get email and rank from database

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_EMAIL, email);
        editor.putInt(KEY_RANK,rank);
        editor.putInt(KEY_COMPLETED_MISSIONS,completed_missions);

        editor.commit();
    }

    // update user rank in data base
    private void updateRank(int rank) {

        // update rank in data base

    }

    //set rank
    public void setRank () {

        int completed_missions = pref.getInt(KEY_COMPLETED_MISSIONS,0);


        if (completed_missions >= 40) {
            editor.putInt(KEY_RANK, 7);
        }

        if (completed_missions >= 20 && completed_missions < 30) {
            editor.putInt(KEY_RANK, 6);
        }

        if (completed_missions >= 15 && completed_missions < 20) {
            editor.putInt(KEY_RANK, 5);
        }

        if (completed_missions >= 10 && completed_missions < 15) {
            editor.putInt(KEY_RANK, 4);
        }

        if (completed_missions >= 5 && completed_missions < 10) {
            editor.putInt(KEY_RANK, 3);
        }

        if (completed_missions >= 5 && completed_missions < 10) {
            editor.putInt(KEY_RANK, 2);
        }

        if (completed_missions >= 1 && completed_missions < 5) {
            editor.putInt(KEY_RANK, 1);
        }

        if (completed_missions == 0) {
            editor.putInt(KEY_RANK, 0);
        }

    }

    // Get the current task location from the database
    public LatLng getCurrentTaskLocation () {

        LatLng tmp = new LatLng(32,45);
        return tmp;
    }

    // refresh Missions
    public ArrayList<String> refreshMissions() {

        // Ask database for missions


        // Tmp ArrayList for testing
        ArrayList<String> missions = new ArrayList<String>(
                Arrays.asList("Mission 1", "Mission 2", "Mission 3", "Mission 4",
                        "Mission 5", "Mission 6", "Mission 7", "Mission 8",
                        "Mission 9"));
        return missions;
    }

    // Get available missions
    public void retrieveMissions() {

        ArrayList<MissionInfo> missions = new ArrayList<>();

        // Ask database for available missions


        // mission name Arrays
        ArrayList<String> names = new ArrayList<String>(
                Arrays.asList("Mission 10", "Mission 11", "Mission 12",
                        "Mission 13", "Mission 14", "Mission 15")
        );

        // mission descriptiono Arrays
        ArrayList<String> descriptions = new ArrayList<String>(
                Arrays.asList("Description 1", "Description 2", "Description 3",
                        "Description 4", "Description 5", "Description 6")
        );



    }

    public ArrayList<String> getMissions() {

        ArrayList<String> names = new ArrayList<String>(
                Arrays.asList("Mission 10", "Mission 11", "Mission 12",
                        "Mission 13", "Mission 14", "Mission 15"));
        return names;

    }

    // INBOX REFRESH METHODS



    // Asks data base for new messages


}