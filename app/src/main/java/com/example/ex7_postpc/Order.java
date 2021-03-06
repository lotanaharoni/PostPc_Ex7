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
        this.status = "waiting";
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

    public String getCustomerName() {
        return customerName;
    }

    public int getPickles() {
        return pickles;
    }

    public boolean isHummus() {
        return hummus;
    }

    public boolean isTahini() {
        return tahini;
    }

    public String getComment() {
        return comment;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setPickles(int pickles) {
        this.pickles = pickles;
    }

    public void setHummus(boolean hummus) {
        this.hummus = hummus;
    }

    public void setTahini(boolean tahini) {
        this.tahini = tahini;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
