package com.example.ex7_postpc;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

public class NewOrderActivity extends AppCompatActivity {

    MyLocalDb myLocalDb = null;
    EditText customerName;
    EditText pickelsAmount;
    CheckBox isHumusChecked;
    CheckBox isTahiniChecked;
    EditText userComment;
    Button sendOrderButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        if (myLocalDb == null){
            myLocalDb = MyAppActivity.getLocalDb();
        }

        customerName = findViewById(R.id.CustomerName);
        pickelsAmount = findViewById(R.id.PickelsEditText);
        isHumusChecked = findViewById(R.id.HummusCheckbox);
        isTahiniChecked = findViewById(R.id.TahiniCheckbox);
        userComment = findViewById(R.id.AddCommentEditText);
        sendOrderButton = findViewById(R.id.sendOrderButton);

        sendOrderButton.setOnClickListener(view -> {
            if (customerName.getText().toString().equals("")){
                Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
            } else if (!checkNumOfPickles(pickelsAmount.getText().toString())){
                Toast.makeText(this, "Choose pickles from 0 to 10", Toast.LENGTH_SHORT).show();
            } else {
                String name = customerName.getText().toString();
                int pickles;
                if (pickelsAmount.getText().toString().equals("")){
                    pickles = 0;
                } else {
                    pickles = Integer.parseInt(pickelsAmount.getText().toString());
                }
                boolean hummus = isHumusChecked.isChecked();
                boolean tahini = isTahiniChecked.isChecked();
                String comment = userComment.getText().toString();
                Order newOrder = new Order(name, pickles, hummus, tahini, comment);
                myLocalDb.addOrder(newOrder);
                Intent editOrderIntent = new Intent(NewOrderActivity.this, EditOrderActivity.class);
                startActivity(editOrderIntent);
                finish();
            }
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

    private boolean checkNumOfPickles(String num){
        if (num .equals("")){
            return true;
        }
        int numOfPickles = Integer.parseInt(num);
        return !(numOfPickles > 10 || numOfPickles < 0);
    }
}
