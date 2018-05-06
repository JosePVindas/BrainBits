package com.brainbits.trivia.brainbits;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

public class GeneralActivity extends AppCompatActivity {

    // Activity Widgets
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private ImageView logo;
//    private Dialog logOutDialog;

    // Fragments
    FragmentQuest mFragmentQuest;
    FragmentProfile mFragmentProfile;
    FragmentInbox mFragmentInbox;

//    // Quest Fragment Widgets
//    TableLayout current_quest_feed;
//    TableLayout available_quest_feed;
//
//    // Profile Fragment Widgets
//    TextView profile_username;
//    TextView profile_mail;
//    TextView profile_rank;
//    ImageView profile_rank_img;
//    Button profile_logout;
//
//    // Inbox Fragment Widgets
//    TableLayout inbox_feed;


    // Handle everything once activity is initialized
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_general);

        // Initialize Activity Widgets
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        logo = (ImageView)findViewById(R.id.general_logo);
//        logOutDialog = new Dialog(this);

        //Initialize Fragments
        mFragmentQuest = new FragmentQuest();
        mFragmentProfile = new FragmentProfile();
        mFragmentInbox = new FragmentInbox();

        // Add custom adapter to the ViewPager
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(mFragmentQuest, "Quests");
        adapter.AddFragment(mFragmentProfile, "Profile");
        adapter.AddFragment(mFragmentInbox, "Inbox");
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);

        // Change context logo depending on tab selected
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()) {
                    case 0: {
                        logo.setImageResource(R.drawable.logo_quest);
                        break;
                    }
                    case 1: {
                        logo.setImageResource(R.drawable.logo_profile);
                        break;
                    }
                    case 2: {
                        logo.setImageResource(R.drawable.logo_inbox);
                        break;
                    }

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

//        // Initialize Quest Fragment Widgets
//        current_quest_feed = (TableLayout) findViewById(R.id.current_quest_feed);
//        available_quest_feed = (TableLayout) findViewById(R.id.available_quest_feed);
//
//        // Initialize Profile Fragment Widgets
//        profile_username = (TextView) findViewById(R.id.profile_username);
//        profile_mail = (TextView) findViewById(R.id.profile_mail);
//        profile_rank = (TextView) findViewById(R.id.profile_rank);
//        profile_rank_img = (ImageView) findViewById(R.id.profile_rank_img);
//
//
//
//        // Initialize Inbox Fragment Widgets
//        inbox_feed = (TableLayout) findViewById(R.id.inbox_feed);

    }

}
