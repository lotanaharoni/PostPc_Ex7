package com.example.ex7_postpc;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

public class EditOrderActivity extends AppCompatActivity {

    MyLocalDb myLocalDb = null;
    String currentId;
    EditText customerName;
    EditText pickelsAmount;
    CheckBox isHumusChecked;
    CheckBox isTahiniChecked;
    EditText userComment;
    Button sendOrderButton;
    Button deleteOrderButton;
    Order currentOrder;

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

        currentId = myLocalDb.loadFromLocal();
        currentOrder = myLocalDb.getCurrentOrder();
//        customerName.setText(currentOrder.getCustomerName());
//        pickelsAmount.setText(String.valueOf(currentOrder.getPickles()));
//        isHumusChecked.setChecked(currentOrder.isHummus());
//        isTahiniChecked.setChecked(currentOrder.isTahini());
//        userComment.setText(currentOrder.getComment());

        sendOrderButton.setOnClickListener(view -> {
            if (customerName.getText().toString().equals("")){
                Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
            } else {
                String name = customerName.getText().toString();
                int pickles = Integer.parseInt(pickelsAmount.getText().toString());
                boolean hummus = isHumusChecked.isChecked();
                boolean tahini = isTahiniChecked.isChecked();
                String comment = userComment.getText().toString();
                currentOrder.setCustomerName(name);
                currentOrder.setHummus(hummus);
                currentOrder.setTahini(tahini);
                currentOrder.setPickles(pickles);
                currentOrder.setComment(comment);
//                myLocalDb.updateOrder(currentOrder);
//                Intent editOrderIntent = new Intent(EditOrderActivity.this, MainActivity.class);
//                startActivity(editOrderIntent);
//                finish();
            }

        });

        deleteOrderButton.setOnClickListener(view -> {
            myLocalDb.deleteOrder(currentId);
            Intent newActivityIntent = new Intent(EditOrderActivity.this, NewOrderActivity.class);
            startActivity(newActivityIntent);
            finish();
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
