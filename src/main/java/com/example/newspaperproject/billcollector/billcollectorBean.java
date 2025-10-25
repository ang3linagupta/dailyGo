package com.example.newspaperproject.billcollector;

public class billcollectorBean {

    String doss,doee,bill;

    public billcollectorBean(String doss, String doee, String bill) {
        this.doss = doss;
        this.doee = doee;
        this.bill = bill;
    }

    public String getDoss() {
        return doss;
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
