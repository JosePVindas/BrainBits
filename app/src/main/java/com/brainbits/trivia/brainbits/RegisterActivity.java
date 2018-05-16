package com.brainbits.trivia.brainbits;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {

    //Vars
    private static final String TAG = "RegisterActivity";
    private ArrayList<String> countries;
    private String Country;
    private SessionManager manager;

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

        name = (EditText) findViewById(R.id.name);
        lastName = (EditText) findViewById(R.id.lastName);
        sLastName = (EditText) findViewById(R.id.secondLastName);

        username = (EditText) findViewById(R.id.usr);
        email = (EditText) findViewById(R.id.mail);
        password = (EditText) findViewById(R.id.pswd);
        passwordR = (EditText) findViewById(R.id.pswdR);
        country = (Spinner) findViewById(R.id.country_spinner);
        city = (Spinner) findViewById(R.id.city_spinner);

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

                //String ctry = country.getSelectedItem().toString();
                //String cty = city.getSelectedItem().toString();

                String ctry = "hello";
                String state = "Hello";
                String cty = "hello";
                String add = address.getText().toString();

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
