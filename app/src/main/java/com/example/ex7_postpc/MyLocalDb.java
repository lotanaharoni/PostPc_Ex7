package com.example.ex7_postpc;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;

public class MyLocalDb {

    private SharedPreferences sp = null;
    private FirebaseFirestore firestore;
    private ListenerRegistration listenerRegistration = null;
    String currentId;

    public SharedPreferences getSp(){
        return this.sp;
    }

    public MyLocalDb(Context context){
        this.sp = context.getSharedPreferences("local_db", Context.MODE_PRIVATE);
        this.firestore = FirebaseFirestore.getInstance();
        this.currentId = "";
    }

    public void addOrder(Order order){
        firestore.collection("orders").document(order.getId()).set(order);
    }

    public String getCurrentId(){
        return this.currentId;
    }
}
