package com.droidbee.tipcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    Button submitButton;
    EditText amount,tips;
    TextView tipsAmount,setTipsAmount,totalAmount,setTotalAmount;
    AdView bannerAd;
    InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        submitButton = findViewById(R.id.button);
        amount = findViewById(R.id.totalPayment);
        tips = findViewById(R.id.percentageOfTips);
        tipsAmount = findViewById(R.id.tipsAmountText);
        setTipsAmount = findViewById(R.id.tipsAmount);
        totalAmount = findViewById(R.id.totalAmountText);
        setTotalAmount = findViewById(R.id.totalAmountSHow);
        bannerAd = findViewById(R.id.adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        bannerAd.loadAd(adRequest);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mainAmount = amount.getText().toString();
                String tipsPercentage = tips.getText().toString();
                if (TextUtils.isEmpty(tipsPercentage) && TextUtils.isEmpty(mainAmount)) {
                    Toast.makeText(MainActivity.this, "Please Enter the Amounts", Toast.LENGTH_SHORT).show();
                    ShowInterstitialAds();

                } else {
                    int mainAMount = Integer.parseInt(mainAmount);
                    int tipsPersentage = Integer.parseInt(tipsPercentage);
                    int tipzAmount = mainAMount * tipsPersentage / 100;
                    int totalPaymentWithTips = mainAMount + tipzAmount;
                    setTipsAmount.setText(String.valueOf(tipzAmount));
                    setTotalAmount.setText(String.valueOf(totalPaymentWithTips));
                    ShowInterstitialAds();
                }
            }
        });

        mInterstitialAd.setAdListener(new AdListener(){
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });

    }

    private void ShowInterstitialAds() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(MainActivity.this, "Ad load failed", Toast.LENGTH_SHORT).show();
        }
    }
}