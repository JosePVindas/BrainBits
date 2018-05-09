package com.brainbits.trivia.brainbits;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Splash extends AppCompatActivity {

    // Vars
    SessionManager manager;


    // Widgets
    private TextView text;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        manager = new SessionManager(this);

        text = (TextView) findViewById(R.id.text);
        image = (ImageView) findViewById(R.id.image);

        Animation fade = AnimationUtils.loadAnimation(this,R.anim.splash_animation);

        text.startAnimation(fade);
        image.startAnimation(fade);

        final Intent i = new Intent(this,LoginActivity.class);
        final Intent j = new Intent(this,MapsActivity.class);
        Thread timer = new Thread(){
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if (manager.isLoggedIn()) {
                        Bundle bundle = new Bundle();
                        String usrname = "Splash";
                        bundle.putString("UserName",usrname);
                        j.putExtras(bundle);
                        startActivity(j);
                        finish();
                    } else {
                        startActivity(i);
                        finish();

                    }
                }
            }
        };
        timer.start();
    }

}
