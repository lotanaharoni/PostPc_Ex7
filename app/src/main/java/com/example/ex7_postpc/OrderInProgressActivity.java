package com.example.ex7_postpc;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

public class OrderInProgressActivity extends AppCompatActivity {

    MyLocalDb myLocalDb = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_in_progress);

        if (myLocalDb == null){
            myLocalDb = MyAppActivity.getLocalDb();
        }
    }
}
