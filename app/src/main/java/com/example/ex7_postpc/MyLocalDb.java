package com.example.ex7_postpc;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.gson.Gson;

public class MyLocalDb {

    private SharedPreferences sp = null;
    private FirebaseFirestore firestore;
    private ListenerRegistration listenerRegistration = null;
    Order currentOrder;

    public SharedPreferences getSp(){
        return this.sp;
    }

    public MyLocalDb(Context context){
        FirebaseApp.initializeApp(context);
        this.sp = context.getSharedPreferences("local_db", Context.MODE_PRIVATE);
        this.firestore = FirebaseFirestore.getInstance();
        String currentId = loadFromLocal();
        if (!currentId.equals("")){
            currentOrder = getSavedOrderById(currentId);
        }
    }

    public void addOrder(Order order){
        firestore.collection("orders").document(order.getId()).set(order);
        savedOrderLocally(order);
        updateCurrentOrder(order);
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

    public void deleteSp(){
        Gson gson = new Gson();
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.apply();
    }

    public Order getSavedOrderById(String currentId){
        firestore.collection("orders").document(currentId).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        currentOrder = documentSnapshot.toObject(Order.class);
                    }
                });
        return currentOrder;
    }

    public void updateCurrentOrder(Order newOrder){
        this.currentOrder = newOrder;
    }


    public FirebaseFirestore getFirestore(){
        return this.firestore;
    }
}
