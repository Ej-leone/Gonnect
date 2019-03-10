package com.op.bt.beneficiarypayments.Order;

import android.content.ContentValues;
import android.util.Log;

import com.op.bt.beneficiarypayments.Data.Database;
import com.op.bt.beneficiarypayments.Data.DatabaseContents;

import java.util.ArrayList;
import java.util.List;

public class OrderDaoAndroid implements  OrderDao {

    Database database;


    public OrderDaoAndroid(Database database) {
        this.database = database;
    }


    @Override
    public Boolean Addorder(Order order) {
        ContentValues content = new ContentValues();
        content.put("name", order.getId());
        content.put("location", order.getLocation());
        content.put("dateoforder", order.getDateoforder());
        content.put("order_id", order.getName());
        content.put("available_locally", order.isAvailable_locally());

        int add = database.insert(DatabaseContents.TABLE_ORDER.toString(), content);
        return null;
    }

    @Override
    public ArrayList<Order> getAllPayment() {

        String queryString = "SELECT * FROM " + DatabaseContents.TABLE_ORDER;
        List<Object> objectList = database.select(queryString);
        Log.e("OBjects", objectList.toString());
        ArrayList<Order> list = new ArrayList<Order>();
        for (Object object : objectList) {
            ContentValues content = (ContentValues) object;
            list.add(new Order(
                    content.getAsString("name"),
                    content.getAsString("order_id"),
                    content.getAsString("location"),
                    content.getAsString("dateoforder"),
                    content.getAsBoolean("available_locally")
            ));
        }



        return null;
    }

    @Override
    public Order getOrderById(String id) {
        return null;
    }

    @Override
    public void DeleteOrder(String id) {

        database.execute("DELETE FROM " + DatabaseContents.TABLE_ORDER  + " WHERE order_id =  '" + id + "'");
        database.execute("DELETE FROM " + DatabaseContents.TABLE_PAYMENTS + " WHERE OrderId = '" + id + "'" );

    }

    @Override
    public Boolean UpdateOrder(String id) {

       // return database.update(DatabaseContents.TABLE_ORDER.toString(), content);
        return database.updateOrder(DatabaseContents.TABLE_ORDER.toString(),id);
    }

}
