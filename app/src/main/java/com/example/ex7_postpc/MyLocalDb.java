package com.example.ex7_postpc;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.gson.Gson;

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
        savedOrderLocally(order);
    }

    public String getCurrentId(){
        return this.currentId;
    }

    public void savedOrderLocally(Order order){
        String orderId = order.getId();
        Gson gson = new Gson();
        String id = gson.toJson(orderId);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("currentId", id);
        editor.apply();
    }

    public String loadFromLocal(){
        String currentId = sp.getString("currentId", "");
        Gson gson = new Gson();
        if (currentId.equals("")){
            return "";
        }
        return gson.fromJson(currentId, String.class);
    }

    public void deleteOrder(String currentId){
        firestore.collection("orders").document(currentId).delete();
        deleteSp();
    }

    private void deleteSp(){
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }
}
