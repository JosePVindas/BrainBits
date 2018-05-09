package com.brainbits.trivia.brainbits;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.widget.ImageView;

public class GeneralActivity extends AppCompatActivity {

    // Activity Widgets
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private ImageView logo;

    // Fragments
    FragmentQuest mFragmentQuest;
    FragmentProfile mFragmentProfile;
    FragmentInbox mFragmentInbox;

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
        adapter.AddFragment(mFragmentQuest, "Missions");
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

    }

}
