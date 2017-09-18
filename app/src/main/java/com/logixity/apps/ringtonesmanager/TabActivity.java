package com.logixity.apps.ringtonesmanager;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.InterstitialAd;


public class TabActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    static MediaPlayer player;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        final android.support.v7.app.ActionBar actionBar = getSupportActionBar();

        actionBar.setNavigationMode(android.support.v7.app.ActionBar.NAVIGATION_MODE_TABS);
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MediaListAdapter.stopPlayback();
                actionBar.setSelectedNavigationItem(position);
                try {
                    InterstitialAd fullScreenAd = App.instance.getFullScreenAd();
                    if (fullScreenAd!=null && fullScreenAd.isLoaded()) {
                        fullScreenAd.show();
                    } else {
                        Log.d("MADDY", "Interstitial Not Loaded");
                        App.instance.requestNewInterstitial();
                    }
                } catch (Exception ex) {}
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        android.support.v7.app.ActionBar.TabListener tabListener = new android.support.v7.app.ActionBar.TabListener() {

            @Override
            public void onTabSelected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {
                mViewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {

            }

            @Override
            public void onTabReselected(android.support.v7.app.ActionBar.Tab tab, FragmentTransaction ft) {

            }
        };

        // Add 3 tabs, specifying the tab's text and TabListener
        actionBar.addTab(actionBar.newTab().setText("Ringtones").setTabListener(tabListener));
        actionBar.addTab(actionBar.newTab().setText("Notifications").setTabListener(tabListener));
//        for (int i = 0; i < 3; i++) {
//            actionBar.addTab(
//                    actionBar.newTab()
//                            .setText("Tab " + (i + 1))
//                            .setTabListener(tabListener));
//        }





    }

    @Override
    protected void onPause() {
        MediaListAdapter.stopPlayback();
        super.onPause();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_tab, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_rate) {
            rateApp();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    void rateApp(){
        Uri uri = Uri.parse("market://details?id="+getPackageName());
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }


    }

    public void getWallpapersClicked(View view) {
        Uri uri = Uri.parse("market://details?id=com.logixity.apps.bestnews8wallpapers");
        Intent myAppLinkToMarket = new Intent(Intent.ACTION_VIEW, uri);
        try {
            startActivity(myAppLinkToMarket);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, " unable to find market app", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a RingtonesFragment (defined as a static inner class below).
            if(position==0){
                return RingtonesFragment.newInstance(position + 1);
            } else {
                return NotificationsFragment.newInstance(position + 1);
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Ringtones";
                case 1:
                    return "Notifications";
            }
            return null;
        }
    }

}

/**
 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */

