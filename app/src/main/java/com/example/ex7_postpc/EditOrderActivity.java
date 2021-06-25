package com.example.ex7_postpc;


import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

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
    FirebaseFirestore firestore;
    ListenerRegistration listenerRegistration;

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
        firestore = myLocalDb.getFirestore();
        firestore.collection("orders").document(currentId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        currentOrder = documentSnapshot.toObject(Order.class);
                        customerName.setText(currentOrder.getCustomerName());
                        pickelsAmount.setText(String.valueOf(currentOrder.getPickles()));
                        isHumusChecked.setChecked(currentOrder.isHummus());
                        isTahiniChecked.setChecked(currentOrder.isTahini());
                        userComment.setText(currentOrder.getComment());
                    }
                });

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
                currentOrder.setCustomerName(name);
                currentOrder.setHummus(hummus);
                currentOrder.setTahini(tahini);
                currentOrder.setPickles(pickles);
                currentOrder.setComment(comment);
                firestore.collection("orders").document(currentOrder.getId()).set(currentOrder)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(EditOrderActivity.this, "The order updated successfuly", Toast.LENGTH_SHORT).show();
                            }
                        });
            }

        });

        deleteOrderButton.setOnClickListener(view -> {
            myLocalDb.deleteOrder(currentId);
            Intent newActivityIntent = new Intent(EditOrderActivity.this, NewOrderActivity.class);
            startActivity(newActivityIntent);
            finish();
        });

        listenerRegistration = firestore.collection("orders").document(currentId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Toast.makeText(EditOrderActivity.this, "There was a problem", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Order order = value.toObject(Order.class);
                            assert order != null;
                            if (order.getStatus().equals("in-progress")){
                                Intent newIntent = new Intent(EditOrderActivity.this, OrderInProgressActivity.class);
                                startActivity(newIntent);
                                finish();
                            }else if (order.getStatus().equals("ready")){
                                Intent newIntent = new Intent(EditOrderActivity.this, ReadyOrderActivity.class);
                                startActivity(newIntent);
                                finish();
                            }
                        }
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (listenerRegistration != null){
            listenerRegistration.remove();
        }
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
