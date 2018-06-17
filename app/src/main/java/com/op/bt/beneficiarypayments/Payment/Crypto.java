package com.op.bt.beneficiarypayments.Payment;

public class Crypto {

    String Name;
    String id;
    String Location;
    String DateOfOrder;


    public Crypto(String name, String id, String location, String dateOfOrder) {
        Name = name;
        this.id = id;
        Location = location;
        DateOfOrder = dateOfOrder;
    }

    public Crypto(String name, String id) {
        Name = name;
        this.id = id;
    }


    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
