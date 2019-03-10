package com.op.bt.beneficiarypayments.Order;

import com.op.bt.beneficiarypayments.Data.NoDaoSetException;

import java.util.ArrayList;



public class OrderLedger {


    private static OrderLedger instance = null;
    private static OrderDao saleDao = null;


    private OrderLedger() throws NoDaoSetException {
        if (!isDaoSet()) {
            throw new NoDaoSetException();
        }
    }

    /**
     * Determines whether the DAO already set or not.
     *
     * @return true if the DAO already set; otherwise false.
     */
    public static boolean isDaoSet() {
        return saleDao != null;
    }



    public static OrderLedger getInstance() throws NoDaoSetException {
        if (instance == null) instance = new OrderLedger();
        return instance;
    }




    /**
     * Sets the database connector.
     *
     * @param dao Data Access Object of Payment.
     */
    public static void setOrderDao(OrderDao dao) {
        saleDao = dao;
    }

    /**
     * Sets the database connector.
     *
     * @param startTime Data Access Object of Payment.
     */
    public boolean addOrder(Order startTime) {

        saleDao.Addorder(startTime);
        return true;
    }

    /**
     * List all payments
     */
    public ArrayList<Order> ListAll() {
        ArrayList<Order> dswa = saleDao.getAllPayment();
        return dswa;
    }



    public  void clear_order (String id){

        saleDao.DeleteOrder(id);
    }


    public  void update_order (String id){

        saleDao.UpdateOrder(id);
    }


}
