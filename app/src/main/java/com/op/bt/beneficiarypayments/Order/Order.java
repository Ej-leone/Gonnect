package com.op.bt.beneficiarypayments.Order;

public class Order {

    String name ;
    String id ;
    String Location;
    String dateoforder;
    boolean available_locally;

    public Order(String name, String id, String location, String dateoforder, boolean available_locally) {
        this.name = name;
        this.id = id;
        Location = location;
        this.dateoforder = dateoforder;
        this.available_locally = available_locally;
    }


    public boolean isAvailable_locally() {
        return available_locally;
    }

    public void setAvailable_locally(boolean available_locally) {
        this.available_locally = available_locally;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getDateoforder() {
        return dateoforder;
    }

    public void setDateoforder(String dateoforder) {
        this.dateoforder = dateoforder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
