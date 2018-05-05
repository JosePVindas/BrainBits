package com.brainbits.trivia.brainbits;

import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ProfileActivity extends AppCompatActivity {

    // Widgets
    private TabLayout tablayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewpager;
    private ImageView logo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tablayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbar_id);
        viewpager = (ViewPager) findViewById(R.id.viewpager_id);
        logo = (ImageView) findViewById(R.id.profile_logo);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentQuest(), "Quests");
        adapter.AddFragment(new FragmentProfile(), "Profile");
        adapter.AddFragment(new FragmentInbox(), "Inbox");

        viewpager.setAdapter(adapter);
        tablayout.setupWithViewPager(viewpager);

        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
