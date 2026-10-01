package com.uberspot.a2048;

import android.os.Bundle;
import android.view.KeyEvent;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.LoadAdError;
import androidx.annotation.NonNull;

public class MainActivity extends android.app.Activity {

    private MainView view;
    private InterstitialAd mInterstitialAd;
    private AdView adView;
    private int gameOverCount = 0;

    // YOUR AD IDs
    private static final String BANNER_ID = "ca-app-pub-8859848812165923/2080950297";
    private static final String INTERSTITIAL_ID = "ca-app-pub-8859848812165923/7001748199";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        // Init AdMob
        MobileAds.initialize(this, initializationStatus -> {});
        
        // Banner Ad
        adView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        
        // Load Interstitial
        loadInterstitial();
        
        view = findViewById(R.id.mainView);
    }

    private void loadInterstitial() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(this, INTERSTITIAL_ID, adRequest,
            new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                    mInterstitialAd = interstitialAd;
                }
                @Override
                public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                    mInterstitialAd = null;
                }
            });
    }

    public void showInterstitialIfReady() {
        gameOverCount++;
        if (gameOverCount % 3 == 0) { // Har 3rd game over pe ad
            if (mInterstitialAd != null) {
                mInterstitialAd.show(this);
                loadInterstitial(); // next ke liye reload
            }
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            view.game.aAction();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            view.game.aAction();
            view.game.aAction();
            view.game.aAction();
            view.game.aAction();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            view.game.aAction();
            view.game.aAction();
            view.game.aAction();
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            view.game.aAction();
            view.game.aAction();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
