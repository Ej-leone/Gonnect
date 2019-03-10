package com.op.bt.beneficiarypayments.UI;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.op.bt.beneficiarypayments.Data.Constants;
import com.op.bt.beneficiarypayments.Data.PrefManager;
import com.op.bt.beneficiarypayments.IntroActivity;
import com.op.bt.beneficiarypayments.R;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SearchActivity extends AppCompatActivity {

    EditText text;
    Button deswe ,deswd ,deswq;
    PrefManager pref;
    private NfcAdapter mNfcAdapter;

    ArrayList<BarcodeFormat> barcodes = new ArrayList<>();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        pref = new PrefManager(SearchActivity.this);

        init();
        initNFC();

       // barcodes.add("UPC-A");
       // barcodes.add("UPC-E");
       // barcodes.add("ITF");
       // barcodes.add("EAN-8");
       // barcodes.add("EAN-13");
       // barcodes.add("UPC-A");
       // barcodes.add("CODE_39");
       // barcodes.add("CODE39");
        barcodes.add(BarcodeFormat.CODE_39);
        barcodes.add(BarcodeFormat.ITF);


        deswe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                make_request();
            }
        });

        deswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // startActivity(new Intent(SearchActivity.this,BarcodeScanner.class));
                //IntentIntegrator integrator = new IntentIntegrator(SearchActivity.this);
               // integrator.setBeepEnabled(true);
               // integrator.setTimeout(30000);

                //integrator.setOrientationLocked(false);
                //integrator.setPrompt("Enter the beneficiary barcode in the red line to scan it");
                //integrator.setBarcodeImageEnabled(true);
                //integrator.initiateScan();
                Actual_barcode();
            }
        });

        deswq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text.setText("B1");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected,tagDetected,ndefDetected};

        text.setText("B1");

        PendingIntent pendingIntent = PendingIntent.getActivity(
                this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
        if(mNfcAdapter!= null)
            mNfcAdapter.enableForegroundDispatch(this, pendingIntent, nfcIntentFilter, null);

    }


     @Override
    protected void onPause() {
        super.onPause();
        if(mNfcAdapter!= null)
            mNfcAdapter.disableForegroundDispatch(this);
    }


     @Override
    protected void onNewIntent(Intent intent) {
         Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        Log.d("TAG", "onNewIntent: "+intent.getAction());

        if(tag != null) {
            Toast.makeText(this, "message_tag_detected", Toast.LENGTH_SHORT).show();
            android.nfc.tech.Ndef ndef = android.nfc.tech.Ndef.get(tag);

            Log.e("Tag Present","sssss");


            try {
                ndef.connect();
                NdefMessage ndefMessage = ndef.getNdefMessage();
                String message = new String(ndefMessage.getRecords()[0].getPayload());
                Log.d("TAG", "readFromNFC: "+message);
                text.setText(message.substring(3));
                r_request(message.substring(3));

                ndef.close();

            } catch (IOException | FormatException e) {
                e.printStackTrace();

            }


        }
    }


    private void init (){
        text = findViewById(R.id.txt_id);
        deswe = findViewById(R.id.txt_btn);
        deswd = findViewById(R.id.txt_btn2);
        deswq = findViewById(R.id.txt_btn3);
    }


    public  void make_request (){


        if(text.getText().toString().trim().length() > 0){

            String tt = text.getText().toString();

               r_request(tt);


        }
        else{
            Toast.makeText(this,"No id entered", Toast.LENGTH_LONG).show();
        }
    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
       // if(result != null) {
        //    if(result.getContents() == null) {
          //      Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
           // } else {
             //   Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
              //  r_request(result.getContents());
           // }
       // }

        if (requestCode == 0x124) {
            if (resultCode == 0) {
                if (data != null) {
                    //mBeepManager.playBeepSoundAndVibrate();
                    String qrcode = data.getStringExtra("qrCode");
                    Toast.makeText(SearchActivity.this, "Scan result:" + qrcode, Toast.LENGTH_LONG).show();
                    r_request(qrcode);
                    // return;
                }
            }
        }

        else {
            super.onActivityResult(requestCode, resultCode, data);
            Toast.makeText(SearchActivity.this, "Scan Failed", Toast.LENGTH_LONG).show();
        }
    }

    public void r_request (String id){


        final ProgressDialog pdia;
        pdia = new ProgressDialog(SearchActivity.this);
        pdia.setMessage("Loading...");
        pdia.show();

        JSONObject parameters = new JSONObject();
        try {
           // parameters.put("username", user);
            parameters.put("cardNo", id);
            parameters.put("staffId",pref.Getid());

        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("Sss",parameters.toString());
        JsonObjectRequest saad = new JsonObjectRequest(Request.Method.POST, "http://"+ pref.getLocalIP()+Constants.search_url, parameters,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("search response",response.toString());
                        pdia.dismiss();


                      try{
                          Boolean suc = response.getBoolean("success");
                          Log.e("Sucess","Succeess");
                          if (suc){
                              Bundle nw = new Bundle();
                              nw.putString("name", response.getString("otherNames"));
                              nw.putString("nid", response.getString("nationalId"));
                              nw.putString("uid", response.getString("manifestId"));
                              nw.putString("gender", response.getString("gender"));
                              nw.putString("amount", response.getString("totalAmountPayable"));
                              nw.putString("Benid",response.getString("cardNo"));
                              nw.putString("otherdetails", response.getString("other_details"));

                              Boolean check = true;

                              if(check){
                                  Intent sa = new Intent(SearchActivity.this,Payed_des.class);
                                  sa.putExtra("payment", nw);

                                  startActivity(sa);
                              }else {
                                Pop_Dialog("Wrong family size. Amount is " + response.getString("totalAmountPayable"));
                              }


                          }
                          else {
                             // Toast.makeText(SearchActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                              Pop_Dialog(response.getString("message"));
                          }
                      }
                      catch (Exception e ){
                       e.printStackTrace();
                          Log.e("Sucess","False");
                      }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        pdia.dismiss();
                        VolleyLog.d("ErrorVolley", "Error: " + error.getStackTrace());
                        Log.e("search response error",error.toString());
                        Toast.makeText(SearchActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // Basic Authentication
                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);
                headers.put("Content-Type", "application/json; charset=utf-8");
                //headers.put("Authorization", "Bearer " + "accesstoken");
                return headers;
            }

        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(saad);

    }


    private void initNFC(){

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }


   private void Pop_Dialog (String message){

       AlertDialog.Builder dsa = new AlertDialog.Builder(this);
       dsa.setTitle("Response");
       dsa.setMessage(message);
       dsa.setCancelable(true);

       AlertDialog asd = dsa.create();
       asd.show();

   }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



    public boolean sss (Double a , Double b){

        Double z  = a-b;

        Log.e("122",String.valueOf(z));
        if (z== 0.0){
            return true;
        }
        else {
            return false;
        }


    }

    private void Pp_Dialog (String message, final String id){

        AlertDialog.Builder dsa = new AlertDialog.Builder(this);
        dsa.setTitle("Response");
        dsa.setMessage(message);
        dsa.setCancelable(true);
        dsa.setPositiveButton("Verified", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                r_request(id);
            }
        });

        AlertDialog asd = dsa.create();
        asd.show();

    }

    public void Actual_barcode(){
        if (checkPackage("com.telpo.tps550.api")) {
            Intent intent = new Intent();
            intent.setClassName("com.telpo.tps550.api", "com.telpo.tps550.api.barcode.Capture");
            try {
                startActivityForResult(intent, 0x124);
            } catch (ActivityNotFoundException e) {
                Toast.makeText(SearchActivity.this, "identify_fail", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(SearchActivity.this, "identify_fail", Toast.LENGTH_LONG).show();
        }
    }

    private boolean checkPackage(String packageName) {
        PackageManager manager = this.getPackageManager();
        Intent intent = new Intent().setPackage(packageName);
        List<ResolveInfo> infos = manager.queryIntentActivities(intent, PackageManager.GET_INTENT_FILTERS);
        if (infos == null || infos.size() < 1) {
            return false;
        }
        return true;
    }

}


