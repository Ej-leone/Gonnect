package com.op.bt.beneficiarypayments.Order;

import java.util.ArrayList;

public interface OrderDao {

    /**
     * initiate Payment
     *
     * @param order Payment to be added to the database
     */
    Boolean Addorder(Order order);

    /**
     * Return  All orders Downloaded in Orders
     */
    ArrayList<Order> getAllPayment();


    /**
     * Return  Order
     *
     * @param id this is the manifest id
     */
    Order getOrderById(String id);


    /**
     * Clear an Order From the local Database
     *
     * @param id to be used to delete the id
     *
     */
    void DeleteOrder(String id);


    /**
     * Clear an Order From the local Database
     *
     * @param id to be used to delete the id
     *
     */

    Boolean UpdateOrder(String id);

}
