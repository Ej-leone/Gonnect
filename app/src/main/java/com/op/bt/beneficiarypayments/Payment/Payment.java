package com.op.bt.beneficiarypayments.Payment;

public class Payment {

    private String OrderId;
    private String Ordername;
    private String benid;
    private String benname;
    private String bencardno;
    private String benbankacc;
    private String bennatid;
    private String gender;
    private String manifestid;
    private String totamount;
    private String quantity;
    private Boolean synced;
    private Boolean completed;
    private String otherdetails;


    public Payment(String orderId, String ordername, String benid, String benname, String bencardno, String benbankacc, String bennatid, String gender, String manifestid, String totamount, String quantity, Boolean synced, Boolean completed ,String otherdetails) {
        OrderId = orderId;
        Ordername = ordername;
        this.benid = benid;
        this.benname = benname;
        this.bencardno = bencardno;
        this.benbankacc = benbankacc;
        this.bennatid = bennatid;
        this.gender = gender;
        this.manifestid = manifestid;
        this.totamount = totamount;
        this.quantity = quantity;
        this.synced = synced;
        this.completed = completed;
        this.otherdetails = otherdetails;
    }


    public String getOtherdetails() {
        return otherdetails;
    }

    public void setOtherdetails(String otherdetails) {
        this.otherdetails = otherdetails;
    }

    public Boolean getSynced() {
        return synced;
    }

    public void setSynced(Boolean synced) {
        this.synced = synced;
    }

    public Boolean getCompleted() {
        return completed;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public String getOrdername() {
        return Ordername;
    }

    public void setOrdername(String ordername) {
        Ordername = ordername;
    }

    public String getBenid() {
        return benid;
    }

    public void setBenid(String benid) {
        this.benid = benid;
    }

    public String getBenname() {
        return benname;
    }

    public void setBenname(String benname) {
        this.benname = benname;
    }

    public String getBencardno() {
        return bencardno;
    }

    public void setBencardno(String bencardno) {
        this.bencardno = bencardno;
    }

    public String getBenbankacc() {
        return benbankacc;
    }

    public void setBenbankacc(String benbankacc) {
        this.benbankacc = benbankacc;
    }

    public String getBennatid() {
        return bennatid;
    }

    public void setBennatid(String bennatid) {
        this.bennatid = bennatid;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getManifestid() {
        return manifestid;
    }

    public void setManifestid(String manifestid) {
        this.manifestid = manifestid;
    }

    public String getTotamount() {
        return totamount;
    }

    public void setTotamount(String totamount) {
        this.totamount = totamount;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}



