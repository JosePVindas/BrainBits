package com.brainbits.trivia.brainbits;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;

public class SessionManager {

    // Vars
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context _context;
    int PRIVATE_MODE = 0;
    private static final String PREF_NAME = "BrainBitsPreferences";

    // Shared Preferences Keys for login
    private static final String IS_LOGIN = "IsLoggedIn";
    private static final String KEY_USERNAME = "USERNAME";
    private static final String KEY_PASSWORD = "USER_PASSWORD";
    private static final String KEY_EMAIL = "USER_EMAIL";

    // Shared Preferences Keys for gameplay
    private static final String KEY_RANK = "USER_RANK";

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
    public void createLoginSession(String username, String email, String password , String country, String city ) {

        // validate information before storing in Shared Preferences
        if (isUserInfoOk(username,email)){

            // Store all data in Shared Preferences
            editor.putBoolean(IS_LOGIN, true);

            editor.putString(KEY_USERNAME, username);
            editor.putString(KEY_EMAIL, email);
            editor.putString(KEY_PASSWORD, password);
            editor.putInt(KEY_RANK, 0);
            editor.commit();

            createUserAccount(username,email,password,country,city,0);

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
    private void createUserAccount (String username, String email, String password, String country, String city, int rank) {

        // Submit all information to database

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

        // get email and rank from database

        editor.putBoolean(IS_LOGIN, true);

        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_PASSWORD, password);
        editor.putString(KEY_EMAIL, email);
        editor.putInt(KEY_RANK,rank);

        editor.commit();
    }

    // update user rank in data base
    private void updateRank(int rank) {

        // update rank in data base

    }

    // Get the current task location from the database
    public LatLng getCurrentTaskLocation () {

        LatLng tmp = new LatLng(32,45);
        return tmp;
    }



    // INBOX REFRESH METHODS



    // Asks data base for new messages


}