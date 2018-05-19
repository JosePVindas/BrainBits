package com.brainbits.trivia.brainbits;


import android.database.DataSetObserver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

public class RegisterActivity extends AppCompatActivity {

    //Vars
    private static final String TAG = "RegisterActivity";
    private ArrayList<String> countries = new ArrayList<>(Arrays.asList("Select a country"));
    private ArrayList<String> states = new ArrayList<>(Arrays.asList("Select a state"));
    private ArrayList<String> cities = new ArrayList<>(Arrays.asList("Select a city"));

    private String Country;
    private String State;
    private String City;

    private int CountryID;
    private int StateID;
    private int CityID;

    private SessionManager manager;

    private JSONObject countryList = new JSONObject();
    private JSONObject stateList = new JSONObject();
    private JSONObject cityList = new JSONObject();

    // Widgets
    private Button register;

    private EditText name;
    private EditText lastName;
    private EditText sLastName;

    private EditText username;
    private EditText email;
    private EditText password;
    private EditText passwordR;

    private EditText address;

    private Spinner country;
    private Spinner state;
    private Spinner city;



    // Handles everything to be done once the activity is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        manager = new SessionManager(this);

        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(

                this,android.R.layout.simple_spinner_dropdown_item,countries

        );


        final ArrayAdapter<String> stateAdapter = new ArrayAdapter<>(
                this,android.R.layout.simple_spinner_dropdown_item,states
        );

        final ArrayAdapter<String> cityAdapter = new ArrayAdapter<>(
                this,android.R.layout.simple_spinner_dropdown_item,cities
        );

        countryList = manager.getCountries();
        try {

            JSONArray countryOptions = countryList.getJSONArray(manager.COUNTRY_TAG);

            for (int i = 0; i < countryOptions.length(); i++) {

                JSONObject tmp = countryOptions.getJSONObject(i);
                String countryName = tmp.getString(manager.COUNTRY_TAG);

                countries.add(countryName);

            }


        } catch (JSONException e) {

            e.printStackTrace();
        }



        name = (EditText) findViewById(R.id.name);
        lastName = (EditText) findViewById(R.id.lastName);
        sLastName = (EditText) findViewById(R.id.secondLastName);

        username = (EditText) findViewById(R.id.usr);
        email = (EditText) findViewById(R.id.mail);
        password = (EditText) findViewById(R.id.pswd);
        passwordR = (EditText) findViewById(R.id.pswdR);


        country = (Spinner) findViewById(R.id.country_spinner);
       country.setAdapter(countryAdapter);
       country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

               if (position != 0) {
                   try {

                       JSONArray countryOptions = countryList.getJSONArray(manager.COUNTRY_TAG);


                       JSONObject tmp = countryOptions.getJSONObject(position - 1);
                       String countryName = tmp.getString(manager.COUNTRY_TAG);
                       int countryID = tmp.getInt(manager.COUNTRY_ID);

                       Country = countryName;
                       CountryID = countryID;

                       states.clear();

                       stateList = manager.getStates(countryID);

                       JSONArray stateOptions = stateList.getJSONArray(manager.STATE_TAG);

                       for (int i = 0; i < stateOptions.length(); i++) {

                           JSONObject tmps = stateOptions.getJSONObject(i);
                           String stateName = tmps.getString(manager.STATE_TAG);


                           states.add(stateName);
                           stateAdapter.notifyDataSetChanged();

                       }


                   } catch (JSONException e) {

                       e.printStackTrace();
                   }
               }
           }


           @Override
           public void onNothingSelected(AdapterView<?> parent) {

           }
       });

        state = (Spinner) findViewById(R.id.state_spinner);
        state.setAdapter(stateAdapter);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                cities.clear();

                try {

                    State = states.get(position);
                    JSONArray stateOptions = stateList.getJSONArray(manager.STATE_TAG);

                    for (int i = 0; i < stateOptions.length(); i++) {

                        JSONObject tmps = stateOptions.getJSONObject(i);
                        String stateName = tmps.getString(manager.STATE_TAG);
                        int stateID = tmps.getInt(manager.STATE_ID);

                        if (stateName.equals(State)){

                            StateID = stateID;

                            cityList = manager.getCities(stateID);

                            JSONArray cityOptions = cityList.getJSONArray(manager.CITY_TAG);

                            for (int j = 0; j < cityOptions.length(); j++) {

                                JSONObject tmpCty = cityOptions.getJSONObject(j);
                                String cityName = tmpCty.getString(manager.CITY_TAG);
                                int cityID = tmpCty.getInt(manager.CITY_ID);

                                CityID = cityID;

                                cities.add(cityName);
                                cityAdapter.notifyDataSetChanged();


                            }

                        }

                    }





                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        city = (Spinner) findViewById(R.id.city_spinner);
        city.setAdapter(cityAdapter);
        city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        address = (EditText) findViewById(R.id.address);


        register = (Button) findViewById(R.id.registerBtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nm = name.getText().toString();
                String lnm = lastName.getText().toString();
                String slnm = sLastName.getText().toString();

                String usr = username.getText().toString();
                String mail = email.getText().toString();
                String pswd = password.getText().toString();
                String pswdR = passwordR.getText().toString();

                String ctry = Country;
                String state = State;
                String cty = "" + CityID;
                String add = address.getText().toString();

                String ctr = "" + CountryID;

                manager.setCountryID(ctr);
                manager.setCityID(cty);

                if (pswd.equals(pswdR)){

                    Toast.makeText(RegisterActivity.this, add,Toast.LENGTH_LONG).show();

                    manager.createLoginSession(nm, lnm, slnm, usr,mail,pswd,ctry, state, cty, add);

                } else {
                    username.setText("");
                    email.setText("");
                    password.setText("");
                    passwordR.setText("");

                    Toast.makeText(RegisterActivity.this,"Password doesn't match",Toast.LENGTH_LONG).show();
                }

            }
        });

    }

}
