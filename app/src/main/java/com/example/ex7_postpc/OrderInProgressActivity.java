package com.example.ex7_postpc;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

public class OrderInProgressActivity extends AppCompatActivity {

    MyLocalDb myLocalDb = null;
    FirebaseFirestore firestore;
    ListenerRegistration listenerRegistration;
    String currentId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_in_progress);

        if (myLocalDb == null){
            myLocalDb = MyAppActivity.getLocalDb();
        }

        currentId = myLocalDb.loadFromLocal();
        firestore = myLocalDb.getFirestore();
        listenerRegistration = firestore.collection("orders").document(currentId)
                .addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null){
                            Toast.makeText(OrderInProgressActivity.this, "There was a problem", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            Order order = value.toObject(Order.class);
                            assert order != null;
                            if (order.getStatus().equals("ready")){
                                Intent newIntent = new Intent(OrderInProgressActivity.this, ReadyOrderActivity.class);
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
}
