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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private TextView birthday;
    private DatePickerDialog.OnDateSetListener mDateSetListener;

    private Button register;
    private EditText username;
    private EditText email;
    private EditText password;
    private EditText passwordR;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        register = (Button) findViewById(R.id.registerBtn);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usr = username.getText().toString();
                String mail = email.getText().toString();
                String pswd = password.getText().toString();
                String pswdR = passwordR.getText().toString();
                String birth = birthday.getText().toString();


                    writeToFile("Something",RegisterActivity.this);
                    goToMaps();

            }
        });

        username = (EditText) findViewById(R.id.usr);
        email = (EditText) findViewById(R.id.mail);
        password = (EditText) findViewById(R.id.pswd);
        passwordR = (EditText) findViewById(R.id.pswdR);

        birthday = (TextView) findViewById(R.id.bdTxt);
        birthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        RegisterActivity.this,
                        android.R.style.Theme_Black,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = month + "/" + dayOfMonth + "/" + year;
                birthday.setText(date);
            }
        };

    }

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

    private void goToMaps () {
        final Intent i = new Intent(this,MapsActivity.class);
        startActivity(i);
    }
}
