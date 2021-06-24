package com.example.ex7_postpc;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

public class MyLocalDb {

    private final SharedPreferences sp;
    private final FirebaseFirestore firestore;
    private ListenerRegistration listenerRegistration = null;
    String currentId;

    public MyLocalDb(Context context){
        this.sp = context.getSharedPreferences("", Context.MODE_PRIVATE);
        this.firestore = FirebaseFirestore.getInstance();
        this.currentId = sp.getString("currernt_id", null);
    }

    public void addOrder(Order order){
        firestore.collection("orders").document(order.getId()).set(order);
    }
}
