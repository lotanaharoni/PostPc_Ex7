package com.example.ex7_postpc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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
            FirebaseFirestore firestore = myLocalDb.getFirestore();
            firestore.collection("orders").document(currentId).get()
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            if (documentSnapshot == null){
                                Intent newOrderIntent = new Intent(MainActivity.this, NewOrderActivity.class);
                                startActivity(newOrderIntent);
                                finish();
                            }
                            Order currentOrder = documentSnapshot.toObject(Order.class);
                            String orderStatus = currentOrder.getStatus();
                            Intent newIntent;
                            switch (orderStatus) {
                                case "waiting":
                                    newIntent = new Intent(MainActivity.this, EditOrderActivity.class);
                                    startActivity(newIntent);
                                    break;
                                case "in-progress":
                                    newIntent = new Intent(MainActivity.this, OrderInProgressActivity.class);
                                    startActivity(newIntent);
                                    break;
                                case "ready":
                                    newIntent = new Intent(MainActivity.this, ReadyOrderActivity.class);
                                    startActivity(newIntent);
                                    break;
                                default:
                                    Toast.makeText(MainActivity.this, "There was a problem", Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "There was a problem", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}