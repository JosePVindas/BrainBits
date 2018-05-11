package com.brainbits.trivia.brainbits;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class LoginActivity extends AppCompatActivity {

    // Vars
    private static final String TAG = "MapsActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;
    SessionManager manager;
    Display display;

    // Widgets
    Button register;
    Button instructions;
    Button logn;
    EditText username;
    EditText password;

    // Handle everything after activity is entered
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        manager = new SessionManager(this);

        register = (Button) findViewById(R.id.RegisterBtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });
        instructions = (Button) findViewById(R.id.learnBtn);
        instructions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showAbout();

            }
        });
        logn = (Button) findViewById(R.id.loginBtn);
        logn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String usr = username.getText().toString();
                String pswd = password.getText().toString();

                if (isServiceOk()) {
                    loginUser(usr, pswd);
                }

            }
        });

        username = (EditText)findViewById(R.id.usrnameTxt);
        password = (EditText)findViewById(R.id.pswdTxt);


    }

    // Login the user. cheks the credentials against the database
    private void loginUser(String usr, String pswd){

        manager.loginUser(usr,pswd);

    }


    // Jumps to the AboutActivity
    private void showAbout(){

        // do something with database to check credentials here


        final Intent i = new Intent(this,AboutActivity.class);
        startActivity(i);

    }

    // Jumps to the RegisterActivity
    private void goToRegister(){


        final Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);

    }

    // checks to see if the GoogleServices are available
    public boolean isServiceOk () {
        Log.d(TAG,"Checking Google Services");

        int available = GoogleApiAvailability.
                getInstance().
                isGooglePlayServicesAvailable(this);

        if (available == ConnectionResult.SUCCESS){
            // everything checks out
            Log.d(TAG,"Google Services Working");
            return true;
        } else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)) {
            // There is an error but it is resolvable
            Log.d(TAG,"There is an error but we can fix it");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this,available,ERROR_DIALOG_REQUEST);
        } else {
            Toast.makeText(this,"Google Services Unavailable",Toast.LENGTH_LONG).show();
        }
        return false;
    }

}
