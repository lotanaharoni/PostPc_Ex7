package com.example.ex7_postpc;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MyLocalDb myLocalDb;
    String currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (myLocalDb == null){
            myLocalDb = MyAppActivity.getLocalDb();
        }
        currentId = myLocalDb.loadFromLocal();
        if (currentId.equals("")){
            Intent newOrderIntent = new Intent(MainActivity.this, NewOrderActivity.class);
            startActivity(newOrderIntent);
            finish();
        }
        else {
            // check what status
            Intent newIntent = new Intent(MainActivity.this, EditOrderActivity.class);
            startActivity(newIntent);
            finish();
        }
    }
}