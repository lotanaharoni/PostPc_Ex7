package com.example.ex7_postpc;

import java.io.Serializable;
import java.util.UUID;

public class Order implements Serializable {

    public String id;
    public String customerName;
    public int pickles;
    public boolean hummus;
    public boolean tahini;
    public String comment;
    public String status;

    public Order(){

    }

    public Order(String customerName, int pickles, boolean hummus, boolean tahini, String comment){
        this.customerName = customerName;
        this.pickles = pickles;
        this.hummus = hummus;
        this.tahini = tahini;
        this.comment = comment;
        this.id = UUID.randomUUID().toString();
        this.status = "in progress";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }
}
