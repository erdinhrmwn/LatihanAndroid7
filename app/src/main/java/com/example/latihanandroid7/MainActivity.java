package com.example.latihanandroid7;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.latihanandroid7.dataController.PrefsHelper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        boolean s = PrefsHelper.sharedInstance(getApplicationContext()).isSplash();

        if (s) {
            startActivity(new Intent(MainActivity.this, Splash.class));
            PrefsHelper.sharedInstance(getApplicationContext()).setSplash(false);
            finish();
        } else {
            startActivity(new Intent(MainActivity.this, TampilData.class));
            finish();
        }
    }
}
