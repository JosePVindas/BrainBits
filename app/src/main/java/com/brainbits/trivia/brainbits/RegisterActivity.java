package com.brainbits.trivia.brainbits;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    //Vars
    private static final String TAG = "RegisterActivity";
    private TextView birthday;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private Locale[] locale;
    private ArrayList<String> countries;
    private String Country;

    // Widgets
    private Button register;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText passwordR;
    private Spinner country;
    private Spinner city;

    // Handles everything to be done once the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = (EditText) findViewById(R.id.usr);
        email = (EditText) findViewById(R.id.mail);
        password = (EditText) findViewById(R.id.pswd);
        passwordR = (EditText) findViewById(R.id.pswdR);
        country = (Spinner) findViewById(R.id.country_spinner);
        city = (Spinner) findViewById(R.id.city_spinner);


        register = (Button) findViewById(R.id.registerBtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr = username.getText().toString();
                String mail = email.getText().toString();
                String pswd = password.getText().toString();
                String pswdR = passwordR.getText().toString();
                String ctry = country.getSelectedItem().toString();
                String cty = city.getSelectedItem().toString();

                    writeToFile("Something",RegisterActivity.this);
                    registerUser(usr,mail,pswd,pswdR,ctry,cty);

            }
        });

        locale = Locale.getAvailableLocales();
        Log.d("Locales", "Reached here");

        for( Locale loc : locale ){
            Country = loc.getDisplayCountry();
            Log.d("Locales", "Reached somewhere");
            if( Country.length() > 0 && !countries.contains(Country) ){
                countries.add( Country );
            }
        }
        Log.d("RegisterActivity","About surpassed the for");
        Collections.sort(countries, String.CASE_INSENSITIVE_ORDER);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, countries);
        country.setAdapter(adapter);



    }

    // writes file to external memory
    private void writeToFile(String data,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    // Jumps to mapsActivity
    private void goToMaps () {
        final Intent i = new Intent(this,MapsActivity.class);
        startActivity(i);
    }

    // Registers the user
    private void registerUser(String usr, String mail, String pswd, String pswdR, String ctry, String cty){

        boolean isInfoOk = checkUser(usr,mail);

        if (pswd.equals(pswdR)){

            if (isInfoOk){

                writeToDB(usr,mail,pswd,ctry,cty);
                Toast.makeText(RegisterActivity.this,"Welcome to BrainBits ",Toast.LENGTH_LONG).show();
                final Intent i = new Intent(RegisterActivity.this,MapsActivity.class);
                startActivity(i);
                finish();

            } else {

                username.setText("");
                email.setText("");
                password.setText("");
                passwordR.setText("");
                Toast.makeText(RegisterActivity.this,"Username or Email already taken",Toast.LENGTH_LONG).show();

            }

        } else {
            username.setText("");
            email.setText("");
            password.setText("");
            passwordR.setText("");
            Toast.makeText(RegisterActivity.this,"Password doesn't match",Toast.LENGTH_LONG).show();
        }

    }

    // Check with database to see if username and email are available
    private boolean checkUser(String usr, String mail){

        // Check with database
        return true;
    }

    // insert data into the database
    private void writeToDB(String usr, String mail, String pswd, String ctry, String cty){

        // Write all data to the database
    }
}
