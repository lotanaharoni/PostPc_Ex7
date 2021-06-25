package com.example.ex7_postpc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class ReadyOrderActivity extends AppCompatActivity {

    Button gotItButton;
    Order currentOrder;
    FirebaseFirestore firestore;
    MyLocalDb myLocalDb = null;
    String currentId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ready_order);

        if (myLocalDb == null){
            myLocalDb = MyAppActivity.getLocalDb();
        }

        gotItButton = findViewById(R.id.gotItButton);

        currentId = myLocalDb.loadFromLocal();
        firestore = myLocalDb.getFirestore();
        firestore.collection("orders").document(currentId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        currentOrder = documentSnapshot.toObject(Order.class);
                    }
                });

        gotItButton.setOnClickListener(view -> {
            currentOrder.setStatus("done");
                firestore.collection("orders").document(currentOrder.getId()).set(currentOrder)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ReadyOrderActivity.this,
                                        "Got it! you are welcome to order another one!", Toast.LENGTH_SHORT).show();
                                myLocalDb.deleteSp();
                                Intent newIntent = new Intent(ReadyOrderActivity.this, MainActivity.class);
                                startActivity(newIntent);
                                finish();
                            }
                        });
            });
    }
}
