package com.op.bt.beneficiarypayments.Data;

public class Constants {

    private static String burl = "http://159.89.154.249:8001";
    public static String login_url = burl + "/m_login/";
    public static String getorders_url = burl + "/m_orders/";
    public static String getmanifest_url = burl + "/m_get_manifest/";
    public static String upload_payment = burl + "/synq_to_server/";


    public static String confirm_payment = "/ispaid";
    public static String make_payment = "/payment";

}
