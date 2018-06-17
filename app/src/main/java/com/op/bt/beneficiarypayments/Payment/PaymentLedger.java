package com.op.bt.beneficiarypayments.Payment;

import com.op.bt.beneficiarypayments.Data.NoDaoSetException;

import java.util.ArrayList;

public class PaymentLedger {

    private static PaymentLedger instance = null;
    private static PaymentDao saleDao = null;
    private Payment currentSale;


    private PaymentLedger() throws NoDaoSetException {
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


    public static PaymentLedger getInstance() throws NoDaoSetException {
        if (instance == null) instance = new PaymentLedger();
        return instance;
    }


    /**
     * Sets the database connector.
     *
     * @param dao Data Access Object of Payment.
     */
    public static void setPaymentDao(PaymentDao dao) {
        saleDao = dao;
    }

    /**
     * Sets the database connector.
     *
     * @param startTime Data Access Object of Payment.
     */
    public boolean initiateSale(Payment startTime) {

        saleDao.Addpayment(startTime);
        return true;
    }

    /**
     * List all payments
     */
    public ArrayList<Payment> ListAll() {
        ArrayList<Payment> dswa = saleDao.getAllPayment();
        return dswa;
    }

    public void Updatemanifest(String uuid) {
        saleDao.UpdatePayment(uuid);

    }

    public ArrayList<Payment> tobepayed() {
        ArrayList<Payment> dswa = saleDao.tobesyncedlist();
        return dswa;
    }

}
