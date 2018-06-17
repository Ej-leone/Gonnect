package com.op.bt.beneficiarypayments.Payment;

import android.content.ContentValues;

import com.op.bt.beneficiarypayments.Data.Database;
import com.op.bt.beneficiarypayments.Data.DatabaseContents;

import java.util.ArrayList;
import java.util.List;

public class PaymentDaoAndroid implements PaymentDao {

    Database database;

    public PaymentDaoAndroid(Database database) {
        this.database = database;
    }


    @Override
    public Boolean Addpayment(Payment payment) {
        ContentValues content = new ContentValues();
        content.put("OrderId", payment.getOrderId());
        content.put("Ordername", payment.getOrdername());
        content.put("benid", payment.getBenid());
        content.put("benname", payment.getBenname());
        content.put("bencardno", payment.getBencardno());
        content.put("benbankacc", payment.getBenbankacc());
        content.put("bennatid", payment.getBennatid());
        content.put("gender", payment.getGender());
        content.put("manifestid", payment.getManifestid());
        content.put("totamount", payment.getTotamount());
        content.put("quantity", payment.getQuantity());
        content.put("synced", payment.getSynced().toString());
        content.put("completed", payment.getCompleted().toString());


        int add = database.insert(DatabaseContents.TABLE_PAYMENTS.toString(), content);

        return null;
    }

    @Override
    public Boolean startPayment(String Starttime) {
        return null;
    }

    @Override
    public void endPayment(Payment payment, String endTime) {

    }

    @Override
    public ArrayList<Payment> getAllPayment() {
        String queryString = "SELECT * FROM " + DatabaseContents.TABLE_PAYMENTS;
        List<Object> objectList = database.select(queryString);
        ArrayList<Payment> list = new ArrayList<Payment>();
        for (Object object : objectList) {
            ContentValues content = (ContentValues) object;
            list.add(new Payment(

                            content.getAsString("OrderId"),
                            content.getAsString("Ordername"),
                            content.getAsString("benid"),
                            content.getAsString("benname"),
                            content.getAsString("bencardno"),
                            content.getAsString("benbankacc"),
                            content.getAsString("bennatid"),
                            content.getAsString("gender"),
                            content.getAsString(" manifestid"),
                            content.getAsString("totamount"),
                            content.getAsString(" quantity"),
                            content.getAsBoolean("synced"),
                            content.getAsBoolean("completed")


                    )
            );
        }

        return list;
    }


    @Override
    public Payment getPaymentById(String id) {
        return null;
    }

    @Override
    public void cancelSale(Payment payment, String endTime) {

    }

    @Override
    public void UpdatePayment(String uuid) {
        String sql = "UPDATE " + DatabaseContents.TABLE_PAYMENTS + " SET completed  =  true  WHERE  manifestid = " + uuid + ";";
        database.execute(sql);

    }

    @Override
    public ArrayList<Payment> tobesyncedlist() {
        String queryString = "SELECT * FROM " + DatabaseContents.TABLE_PAYMENTS + "WHERE completed = true";
        List<Object> objectList = database.select(queryString);
        ArrayList<Payment> list = new ArrayList<Payment>();
        for (Object object : objectList) {
            ContentValues content = (ContentValues) object;
            list.add(new Payment(

                            content.getAsString("OrderId"),
                            content.getAsString("Ordername"),
                            content.getAsString("benid"),
                            content.getAsString("benname"),
                            content.getAsString("bencardno"),
                            content.getAsString("benbankacc"),
                            content.getAsString("bennatid"),
                            content.getAsString("gender"),
                            content.getAsString(" manifestid"),
                            content.getAsString("totamount"),
                            content.getAsString(" quantity"),
                            content.getAsBoolean("synced"),
                            content.getAsBoolean("completed")


                    )
            );
        }

        return list;
    }


}
