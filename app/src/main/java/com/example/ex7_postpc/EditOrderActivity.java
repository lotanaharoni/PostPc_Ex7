package com.example.ex7_postpc;


import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class EditOrderActivity extends AppCompatActivity {

    MyLocalDb myLocalDb = null;
    EditText customerName;
    EditText pickelsAmount;
    CheckBox isHumusChecked;
    CheckBox isTahiniChecked;
    EditText userComment;
    Button sendOrderButton;
    Button deleteOrderButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_order);

        if (myLocalDb == null){
            myLocalDb = MyAppActivity.getLocalDb();
        }

        customerName = findViewById(R.id.CustomerName);
        pickelsAmount = findViewById(R.id.PickelsEditText);
        isHumusChecked = findViewById(R.id.HummusCheckbox);
        isTahiniChecked = findViewById(R.id.TahiniCheckbox);
        userComment = findViewById(R.id.AddCommentEditText);
        sendOrderButton = findViewById(R.id.sendOrderButton);
        deleteOrderButton = findViewById(R.id.deleteOrderButton);

        sendOrderButton.setOnClickListener(view -> {

        });

        deleteOrderButton.setOnClickListener(view -> {

        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("customerName", customerName.getText().toString());
        outState.putString("pickelsAmount", pickelsAmount.getText().toString());
        outState.putBoolean("isTahini", isTahiniChecked.isChecked());
        outState.putBoolean("isHummus", isHumusChecked.isChecked());
        outState.putString("customerComment", userComment.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        customerName.setText(savedInstanceState.getString("customerName"));
        pickelsAmount.setText(savedInstanceState.getString("pickelsAmount"));
        isTahiniChecked.setChecked(savedInstanceState.getBoolean("isTahini"));
        isHumusChecked.setChecked(savedInstanceState.getBoolean("isHummus"));
        userComment.setText(savedInstanceState.getString("customerComment"));
    }
}
