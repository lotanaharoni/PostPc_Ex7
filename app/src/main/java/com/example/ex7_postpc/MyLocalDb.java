package com.example.ex7_postpc;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.firebase.firestore.FirebaseFirestore;

public class MyLocalDb {

    private final SharedPreferences sp;
    private final FirebaseFirestore firestore = null;

    public MyLocalDb(Context context){
        this.sp = context.getSharedPreferences("", Context.MODE_PRIVATE);
    }
}
