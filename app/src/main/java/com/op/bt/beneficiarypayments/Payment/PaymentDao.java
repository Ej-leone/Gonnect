package com.op.bt.beneficiarypayments.Payment;

import java.util.ArrayList;

public interface PaymentDao {


    /**
     * initiate Payment
     *
     * @param payment Payment to be added to the database
     */
    Boolean Addpayment(Payment payment);

    /**
     * initiate Payment
     *
     * @param Starttime Payment to be ended.
     */
    Boolean startPayment(String Starttime);

    /**
     * End Payment
     *
     * @param payment Sale to be ended.
     * @param endTime time that Sale ended.
     */
    void endPayment(Payment payment, String endTime);


    /**
     * Return  Payment
     */
    ArrayList<Payment> getAllPayment();

    /**
     * Return  Payment
     *
     * @param id this is the manifest id
     */
    Payment getPaymentById(String id);


    /**
     * Cancel the Payment.
     *
     * @param payment to be cancel.
     * @param endTime time that cancelled.
     */
    void cancelSale(Payment payment, String endTime);


    void UpdatePayment(String uuid);


    ArrayList<Payment> tobesyncedlist();
}
