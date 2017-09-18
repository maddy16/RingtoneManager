package com.logixity.apps.ringtonesmanager;

import android.app.Application;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

/**
 * Created by ahmed on 13/06/2017.
 */

public class App extends Application {
    InterstitialAd mInterstitialAd;
    static App instance;
    boolean testingMode = false;
    int countIntAd = 0;
    void loadAds(){
        MobileAds.initialize(getApplicationContext(), "ca-app-pub-1474727696191193~7992243864"); // App Id admob
        mInterstitialAd = new InterstitialAd(this);
        if(testingMode){
            mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712"); // Test Ad Id
        } else{
            mInterstitialAd.setAdUnitId("ca-app-pub-1474727696191193/6269675062");
        }



        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
//                requestNewInterstitial();
            }
        });
        requestNewInterstitial();
    }
    public void requestNewInterstitial() {
        AdRequest adRequest = null;
        if(testingMode){
            adRequest= new AdRequest.Builder()
                    .addTestDevice("55757F6B6D6116FAC42122EC92E5A58C")
                    .build();
        } else {
            adRequest = new AdRequest.Builder()
                    .build();
        }
        mInterstitialAd.loadAd(adRequest);
    }
    boolean shouldShowAd(){
        boolean show=false;
        if(countIntAd==0)
            show=true;
        countIntAd++;
        if(countIntAd==1)
            countIntAd=0;
        return show;
    }
    public InterstitialAd getFullScreenAd(){
        return mInterstitialAd;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
//        Toast.makeText(this, "Application Created", Toast.LENGTH_SHORT).show();
    }
}
