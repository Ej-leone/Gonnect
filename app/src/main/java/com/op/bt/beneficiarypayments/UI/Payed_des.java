package com.op.bt.beneficiarypayments.UI;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.common.pos.api.util.posutil.PosUtil;
import com.op.bt.beneficiarypayments.Data.Constants;
import com.op.bt.beneficiarypayments.Data.PrefManager;
import com.op.bt.beneficiarypayments.R;
import com.op.bt.beneficiarypayments.Util.RawToBitMap;
import com.telpo.tps550.api.TelpoException;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

public class Payed_des extends AppCompatActivity {

    TextView name, iden, amount, textPrintVersion ,printinit;
    String uid,nm ,amont, manifestIDD, benIDD, AmountIDD, natIDD ,otherdetails ;
    FloatingActionButton init_btn;
    Button c_btn, c_pay;
    PrefManager pref;
    private ProgressDialog  dialog ,progressDialog;
    private final int PRINTVERSION = 5;
    UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(Payed_des.this);
    ImageView imgv;

    private final static int MAX_LEFT_DISTANCE = 255;
    public static String barcodeStr;
    public static String qrcodeStr;
    public static int paperWalk;
    public static String printContent;
    private final int NOPAPER = 3;
    private final int LOWBATTERY = 4;

    private final int PRINTBARCODE = 6;
    private final int PRINTQRCODE = 7;
    private final int PRINTPAPERWALK = 8;
    private final int PRINTCONTENT = 9;
    private final int CANCELPROMPT = 10;
    private final int PRINTERR = 11;
    private final int OVERHEAT = 12;
    private final int MAKER = 13;
    private final int PRINTPICTURE = 14;
    private final int NOBLACKBLOCK = 15;


    //Print
    private String printVersion;
    private String Result;
    private Boolean nopaper = false;
    private boolean LowBattery = false;
    private int leftDistance = 0;
    private int lineDistance;
    private int wordFont;
    private int printGray;
    private boolean TAKEN_PRINT = false;


    MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payed_des);
        pref = new PrefManager(this);

        if(PosUtil.setFingerPrintPower(PosUtil.FINGERPRINT_POWER_ON) ==0){
          // init_btn();
        }


        init_ui();
        UpdateUI();
        init_fingerprint();
        init_print();
    }

    public  void init_ui (){
        name = findViewById(R.id.tot_name);
        iden = findViewById(R.id.tot_identification);
        amount = findViewById(R.id.tot_amount);
        printinit = findViewById(R.id.tv_tv);
        init_btn = findViewById(R.id.tb_p);
        imgv= findViewById(R.id.print_vw);
        c_btn = findViewById(R.id.btn_Collect);
        c_pay = findViewById(R.id.btn_pay);
        textPrintVersion = findViewById(R.id.tot_cmonths);
       // imgv = findViewById(R.id.print_image);
       // fin = findViewById(R.id.b_init);

        handler = new MyHandler();

        c_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Make_Paymenter(manifestIDD,AmountIDD,nm + ""+ natIDD );
            }
        });

        c_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect_printt();
            }
        });
        init_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init_fingerprint();
            }
        });
    }

    public void init_print()
    {
        dialog = new ProgressDialog(Payed_des.this);
        dialog.setTitle(R.string.idcard_czz);
        dialog.setMessage(getText(R.string.watting));
        dialog.setCancelable(false);
        dialog.show();

         new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    mUsbThermalPrinter.start(0);
                    mUsbThermalPrinter.reset();
                    printVersion = mUsbThermalPrinter.getVersion();
                } catch (com.telpo.tps550.api.TelpoException e) {
                    e.printStackTrace();
                } finally {
                    if (printVersion != null) {
                        Message message = new Message();
                        message.what = PRINTVERSION;
                        message.obj = "1";
                        handler.sendMessage(message);
                    } else {
                        Message message = new Message();
                        message.what = PRINTVERSION;
                        message.obj = "0";
                        handler.sendMessage(message);
                    }
                }
            }
        }).start();

    }

    public void UpdateUI() {
        Bundle z = getIntent().getBundleExtra("payment");
        nm = z.getString("name");
        String nid = z.getString("nid");
        uid = z.getString("uid");
        String gender = z.getString("gender");
        amont = z.getString("amount");
        manifestIDD = z.getString("uid");
        benIDD = z.getString("Benid");
        AmountIDD = z.getString("amount");
        natIDD = z.getString("nid");
        otherdetails = z.getString("otherdetails");


        Log.e("bundles",z.toString());
        JSONObject otherdetailsjson ;
        try {
            otherdetailsjson = new JSONObject(otherdetails);

            Iterator<?> keys = otherdetailsjson.keys();

            RelativeLayout rLayout = (RelativeLayout) findViewById(R.id.Relativelayout);

            RelativeLayout.LayoutParams lprams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    RelativeLayout.LayoutParams.MATCH_PARENT);

            TableLayout tll =  findViewById(R.id.below_table);
            TableRow row=(TableRow)findViewById(R.id.display_row);

            while( keys.hasNext() ) {
                String key = (String) keys.next();
                String Value  = otherdetailsjson.getString(key);


                TextView swa =  new TextView(Payed_des.this);
                TextView swb =  new TextView(Payed_des.this);

                swa.setText(key);
                swb.setText(Value);

                row.addView(swa);
                row.addView(swb);

                tll.addView(row);
            }


        }
        catch (Exception er ){
            er.printStackTrace();
        }
        name.setText(nm);
        iden.setText(nid);
        amount.setText(amont);

    }

    public  void init_fingerprint (){
        Log.e("Pscreen", "initiate");
        int rets, bytelength;
        rets = com.telpo.usb.finger.Finger.initialize();

        if (rets == 0) {

            Toast.makeText(this, "Initialize success: " + String.format("0x%02x", rets), Toast.LENGTH_SHORT).show();
            printinit.setText("Initialise");
            printinit.setTextColor(getResources().getColor(android.R.color.holo_green_light));
        } else {
            Toast.makeText(this, "Initialize failed: " + String.format("0x%02x", rets), Toast.LENGTH_SHORT).show();
            printinit.setText("Not initialised");
            printinit.setTextColor(getResources().getColor(android.R.color.holo_red_light));
        }
    }

    public void collect_printt (){
        int ret ;
        byte[] img_data = new byte[250 * 360];
        ret = com.telpo.usb.finger.Finger.get_image(img_data);
        if (ret == 0) {
            Bitmap bmp = RawToBitMap.convert8bit(img_data, 242, 266);

            saveImage(bmp);
            if (bmp != null) {
                imgv.setImageBitmap(bmp);



            }
            return;
        }
        //String.format("0x%02x", ret)
        Toast.makeText(this,"Did not collect print" , Toast.LENGTH_SHORT).show();

    }

    private void saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "Benefeciary");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        String fileName = System.currentTimeMillis() + ".jpg";
        File file = new File(appDir, fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Make_Paymenter(String Manifestid , String Amount , String benid) {
        final ProgressDialog pdia;
        pdia = new ProgressDialog(Payed_des.this);
        pdia.setMessage("making payment...");
        pdia.show();
        String BaseString;
        try {
            Bitmap fr = ((BitmapDrawable) imgv.getDrawable()).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            fr.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            BaseString = Base64.encodeToString(byteArray, Base64.DEFAULT);
            // Toast.makeText(PaymentScreen.this, BaseString, Toast.LENGTH_LONG).show();
        } catch (Exception er) {
            er.printStackTrace();
            BaseString = er.getLocalizedMessage();
        }

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("manifestId", Manifestid);
            parameters.put("thumbprint", BaseString);
            parameters.put("paid", true);
            parameters.put("paidAt", "Anywhere");
            parameters.put("remarks", "We apreciate you");
            parameters.put("amountPaid", Amount);
            parameters.put("staffId", pref.Getid());
            parameters.put("staffNames", pref.GetUSer());
            parameters.put("beneficiaryNames", benid);
            //
        } catch (Exception e) {
        }

       //Toast.makeText( this, pref.Getid(), Toast.LENGTH_SHORT).show();

       // Toast.makeText( this, pref.GetUSer(), Toast.LENGTH_SHORT).show();
       // Toast.makeText(this, "http://"+ pref.getLocalIP()+ Constants.make_payment, Toast.LENGTH_SHORT).show();


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,"http://"+ pref.getLocalIP()+Constants.make_payment, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pdia.dismiss();
                Log.e("onResponse", response.toString());
              //  Toast.makeText(Payed_des.this,response.toString() , Toast.LENGTH_SHORT).show();

                try {
                    boolean succ = response.getBoolean("isPaidResult");


                   // Toast.makeText(Payed_des.this, "Successfully paid", Toast.LENGTH_LONG).show();
                    if (succ) {

                        finishpayment();

                        Intent des = new Intent(Payed_des.this, SearchActivity.class);
                        startActivity(des);
                        finish();
                    } else {
                        Toast.makeText(Payed_des.this, "payment Failed", Toast.LENGTH_LONG).show();
                    }
                } catch (Exception er) {
                    er.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdia.dismiss();
                Log.e("onErrorResponse",error.toString() );

                Toast.makeText(Payed_des.this, error.toString(), Toast.LENGTH_SHORT).show();
                if (error instanceof TimeoutError) {
                    Toast.makeText(Payed_des.this, "Time out error connecting", Toast.LENGTH_SHORT).show();
                   // finishpayment();
                } else if (error instanceof NetworkError) {
                    Toast.makeText(Payed_des.this, "Network connecting", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ServerError) {
                    Toast.makeText(Payed_des.this, "Server error connecting" + error.getLocalizedMessage() + error.toString() + error.getMessage(), Toast.LENGTH_LONG).show();

                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(Payed_des.this, "Auth Failure connecting", Toast.LENGTH_SHORT).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(Payed_des.this, "Parse error connecting", Toast.LENGTH_SHORT).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(Payed_des.this, "No connection connecting", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Payed_des.this, error.getLocalizedMessage() + error + error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();

                //headers.put("Authorization", "Bearer " + "accesstoken");
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void noPaperDlg() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(Payed_des.this);
        dlg.setTitle(getString(R.string.noPaper));
        dlg.setMessage(getString(R.string.noPaperNotice));
        dlg.setCancelable(false);
        dlg.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        dlg.show();
    }

    public void finishpayment() {
        String exditText;
        exditText = "0";
        if (exditText == null || exditText.length() < 1) {
            Toast.makeText(Payed_des.this, getString(R.string.left_margin) + getString(R.string.lengthNotEnougth), Toast.LENGTH_LONG).show();
            return;
        }
        leftDistance = Integer.parseInt(exditText);
        exditText = "0";
        if (exditText == null || exditText.length() < 1) {
            Toast.makeText(Payed_des.this, getString(R.string.row_space) + getString(R.string.lengthNotEnougth), Toast.LENGTH_LONG).show();
            return;
        }
        lineDistance = Integer.parseInt(exditText);
        Calendar calendar = Calendar.getInstance(Locale.getDefault());
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
        printContent = "\n---------------------------\n" +
                "Name:\t"+ nm + "\n"  +
                "Paid by:\t"+pref.GetUSer()+"\n"+
                "Amount:\t"+AmountIDD+"\n" +
                "Date:\t"+ year +":"+ (month+1) +":"+ date +"\n"+
                "Time:\t"+ hour+ ":"+minute+":"+second +"\n" +
                "ID number:\t"+natIDD+"\n" +
                "---------------------------\n" +
                "---------------------------\n" ;

        exditText = "2";
        if (exditText == null || exditText.length() < 1) {
            Toast.makeText(Payed_des.this, getString(R.string.font_size) + getString(R.string.lengthNotEnougth), Toast.LENGTH_LONG).show();
            return;
        }
        wordFont = Integer.parseInt(exditText);
        exditText = "1";
        if (exditText == null || exditText.length() < 1) {
            Toast.makeText(Payed_des.this, getString(R.string.gray_level) + getString(R.string.lengthNotEnougth), Toast.LENGTH_LONG).show();
            return;
        }
        printGray = Integer.parseInt(exditText);
        if (leftDistance > MAX_LEFT_DISTANCE) {
            Toast.makeText(Payed_des.this, getString(R.string.outOfLeft), Toast.LENGTH_LONG).show();
            return;
        }
        if (lineDistance > 255) {
            Toast.makeText(Payed_des.this, getString(R.string.outOfLine), Toast.LENGTH_LONG).show();
            return;
        }
        if (wordFont > 4 || wordFont < 1) {
            Toast.makeText(Payed_des.this, getString(R.string.outOfFont), Toast.LENGTH_LONG).show();
            return;
        }
        if (printGray < 0 || printGray > 7) {
            Toast.makeText(Payed_des.this, getString(R.string.outOfGray), Toast.LENGTH_LONG).show();
            return;
        }
        if (printContent == null || printContent.length() == 0) {
            Toast.makeText(Payed_des.this, getString(R.string.empty), Toast.LENGTH_LONG).show();
            return;
        }
        if (LowBattery == true) {
            handler.sendMessage(handler.obtainMessage(LOWBATTERY, 1, 0, null));
        } else {
            if (!nopaper) {
                progressDialog = ProgressDialog.show(Payed_des.this, getString(R.string.bl_dy), getString(R.string.printing_wait));
                handler.sendMessage(handler.obtainMessage(PRINTCONTENT, 1, 0, null));
            } else {
                Toast.makeText(Payed_des.this, getString(R.string.ptintInit), Toast.LENGTH_LONG).show();
            }
        }

    }

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case PRINTCONTENT:
                    new contentPrintThread().start();
                    break;
                case NOPAPER:
                    noPaperDlg();
                    break;
                case LOWBATTERY:
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(Payed_des.this);
                    alertDialog.setTitle(R.string.operation_result);
                    alertDialog.setMessage(getString(R.string.LowBattery));
                    alertDialog.setPositiveButton(getString(R.string.dialog_comfirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    alertDialog.show();
                    break;
                case NOBLACKBLOCK:
                    Toast.makeText(Payed_des.this, R.string.maker_not_find, Toast.LENGTH_SHORT).show();
                    break;
                case PRINTVERSION:
                    dialog.dismiss();
                    if (msg.obj.equals("1")) {
                        textPrintVersion.setText(printVersion);
                    } else {
                       // Toast.makeText(Payed_des.this, "operation_fail", Toast.LENGTH_LONG).show();
                    }
                    break;

                case CANCELPROMPT:
                    if (progressDialog != null && !Payed_des.this.isFinishing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                    break;
                case OVERHEAT:
                    AlertDialog.Builder overHeatDialog = new AlertDialog.Builder(Payed_des.this);
                    overHeatDialog.setTitle(R.string.operation_result);
                    overHeatDialog.setMessage(getString(R.string.overTemp));
                    overHeatDialog.setPositiveButton(getString(R.string.dialog_comfirm), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });
                    overHeatDialog.show();
                    break;
                default:
                    Toast.makeText(Payed_des.this, "Print Error!", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    private class contentPrintThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                mUsbThermalPrinter.reset();
                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_LEFT);
                mUsbThermalPrinter.setLeftIndent(leftDistance);
                mUsbThermalPrinter.setLineSpace(lineDistance);
                if (wordFont == 4) {
                    mUsbThermalPrinter.setFontSize(2);
                    mUsbThermalPrinter.enlargeFontSize(2, 2);
                } else if (wordFont == 3) {
                    mUsbThermalPrinter.setFontSize(1);
                    mUsbThermalPrinter.enlargeFontSize(2, 2);
                } else if (wordFont == 2) {
                    mUsbThermalPrinter.setFontSize(2);
                } else if (wordFont == 1) {
                    mUsbThermalPrinter.setFontSize(1);
                }
                mUsbThermalPrinter.setGray(printGray);
                mUsbThermalPrinter.addString(printContent);
                mUsbThermalPrinter.printString();
                mUsbThermalPrinter.walkPaper(20);
            } catch (Exception e) {
                e.printStackTrace();

                Result = e.toString();
                if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                    Log.e("Exception", "no papers");
                    nopaper = true;
                } else if (Result.equals("com.telpo.tps550.api.printer.OverHeatException")) {

                    handler.sendMessage(handler.obtainMessage(OVERHEAT, 1, 0, null));
                } else {
                    handler.sendMessage(handler.obtainMessage(PRINTERR, 1, 0, null));
                }
            } finally {
                handler.sendMessage(handler.obtainMessage(CANCELPROMPT, 1, 0, null));
                if (nopaper) {
                    handler.sendMessage(handler.obtainMessage(NOPAPER, 1, 0, null));
                    nopaper = false;
                    return;
                }
            }
        }
    }
}
