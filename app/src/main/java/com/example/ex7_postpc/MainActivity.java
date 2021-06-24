package com.example.ex7_postpc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MyLocalDb myLocalDb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (myLocalDb == null){
            myLocalDb = MyAppActivity.getLocalDb();
        }
    }
}