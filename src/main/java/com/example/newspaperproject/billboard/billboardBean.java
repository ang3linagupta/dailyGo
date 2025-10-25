package com.example.newspaperproject.billboard;

public class billboardBean {

    String doss,doee,bill,mobilenumber;


    public billboardBean(String doss, String doee, String bill, String mobilenumber) {
        this.doss = doss;
        this.doee = doee;
        this.bill = bill;
        this.mobilenumber = mobilenumber;
    }

    public String getDoss() {
        return doss;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public void setDoss(String doss) {
        this.doss = doss;
    }

    public String getDoee() {
        return doee;
    }

    public void setDoee(String doee) {
        this.doee = doee;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

}
