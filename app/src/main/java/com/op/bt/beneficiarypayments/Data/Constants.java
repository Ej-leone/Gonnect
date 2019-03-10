package com.op.bt.beneficiarypayments.Data;

public class Constants {

    private static String burl = "http://192.168.10.217";
    public static String login_url = burl +":8899"+ "/api/login";
    public static String getorders_url = burl + "/m_orders/";
    public static String getmanifest_url = burl + "/m_get_manifest/";
    public static String upload_payment = burl + "/synq_to_server/";


    //TODo:Return to local Ip

    public static String confirm_payment =  "/ispaid";
    public static String make_payment ="/api/pay";
    public static String local_login =  "/api/login";


    //Registering urls

    public static String get_cardno =  "/api/beneficiary/new";
    public static String data_url =  "/api/beneficiary/basicdetails/";
    public static  String image_url= "/api/beneficiary/passportphotos/";
    public static String plefthand  =  "/api/beneficiary/lefthand/";
    public static String prighthand  =  "/api/beneficiary/righthand/";
    public static String olefthand  =  "/api/beneficiary/nok/lefthand/";
    public static String orighthand  =  "/api/beneficiary/nok/righthand/";
    public static String otherdetails =  "/api/beneficiary/otherdetails/";





    public static String search_url = "/api/search";
    public static final String LOG_FILE      = "relay_remote_log";

    public static final int SUCCESS = 0;
    public static final int FAILURE = 1;

    public static final int ADD_EDIT_CODE = 42;

    public static final int NETWORK_TIMEOUT = 3000;

    public static final int DEFAULT_PORT = 2424;
    public static final int DEFAULT_PIN  = 9;

    public static final char OP_SET     = 's';
    public static final char OP_GET     = 'g';
    public static final char CMD_OFF    = '0';
    public static final char CMD_ON     = '1';
    public static final char CMD_TOGGLE = 't';

    public static final int WIDGET_RELAY = 0;
    public static final int WIDGET_GROUP = 1;

    public static final int NFC_RELAY = 0;
    public static final int NFC_GROUP = 1;

    public static final int ABOUT_THIS_APP = 0;
    public static final int CHANGELOG = 1;
}
