package com.example.newspaperproject.customerboard;

public class customerboardBean {

    String mobile;
    String cname;

    public customerboardBean(String mobile, String cname, String emailid, String address, String dos, String area, String hawkerid, String papers, String prices) {
        this.mobile = mobile;
        this.cname = cname;
        this.emailid = emailid;
        this.address = address;
        this.dos = dos;
        this.area = area;
        this.hawkerid = hawkerid;
        this.papers = papers;
        this.prices = prices;
    }

    String emailid;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getHawkerid() {
        return hawkerid;
    }

    public void setHawkerid(String hawkerid) {
        this.hawkerid = hawkerid;
    }

    public String getDos() {
        return dos;
    }

    public void setDos(String dos) {
        this.dos = dos;
    }

    public String getPapers() {
        return papers;
    }

    public void setPapers(String papers) {
        this.papers = papers;
    }

    public String getPrices() {
        return prices;
    }

    public void setPrices(String prices) {
        this.prices = prices;
    }

    String address;
    String dos;
    String area;
    String hawkerid;
    String papers;
    String prices;


}
