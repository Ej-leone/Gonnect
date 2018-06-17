package com.op.bt.beneficiarypayments.UI;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.op.bt.beneficiarypayments.Data.AndroidDatabase;
import com.op.bt.beneficiarypayments.Data.Constants;
import com.op.bt.beneficiarypayments.Data.Database;
import com.op.bt.beneficiarypayments.Data.DatabaseExecutor;
import com.op.bt.beneficiarypayments.Data.PrefManager;
import com.op.bt.beneficiarypayments.Payment.Crypto;
import com.op.bt.beneficiarypayments.Payment.Payment;
import com.op.bt.beneficiarypayments.Payment.PaymentDao;
import com.op.bt.beneficiarypayments.Payment.PaymentDaoAndroid;
import com.op.bt.beneficiarypayments.Payment.PaymentLedger;
import com.op.bt.beneficiarypayments.R;
import com.op.bt.beneficiarypayments.UI.Adapter.mnadapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SyncActivity extends AppCompatActivity {

    PrefManager re;
    RecyclerView des;
    AppCompatButton btn;
    ArrayList<Crypto> All_cton = new ArrayList<>();
    mnadapter desttt;
    // List<String> x = new ArrayList<String>();
    JSONArray x = new JSONArray();
    PaymentLedger pleg;
    private String accesstoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_sync);
        re = new PrefManager(this);
        accesstoken = re.getAccessToken();

        IntiateCoreApp();
        initViews();
        DownloadOrders();
    }


    /**
     * Loads database and DAO.
     */
    public void IntiateCoreApp() {

        Database database = new AndroidDatabase(this);
        PaymentDao paymentdao = new PaymentDaoAndroid(database);
        PaymentLedger.setPaymentDao(paymentdao);
        DatabaseExecutor.setDatabase(database);


        Log.d("Core App", "INITIATE");

    }


    public void initViews() {

        btn = findViewById(R.id.get_manifest);
        desttt = new mnadapter(All_cton, this, new mnadapter.OnItemCheckListener() {
            @Override
            public void onchecked(Crypto item) {

                //x.add();
                x.put(item.getId());

            }

            @Override
            public void onunchecked(Crypto item) {

                //Todo: Remeber to add
                //x.remove()
            }
        });
        des = findViewById(R.id.recycler_viewaa);
        des.setHasFixedSize(true);
        des.setLayoutManager(new LinearLayoutManager(this));
        des.setAdapter(desttt);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DownloadManifests(x);
                Downloadmaan(x);
            }
        });


    }


    /**
     * Initial Syncronisation
     * for bearer Authorisation
     */
    public void DownloadOrders() {

        final ProgressDialog pdia;
        pdia = new ProgressDialog(SyncActivity.this);
        pdia.setMessage("Getting Orders...");
        pdia.show();
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("key", "value");
        } catch (Exception e) {
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.getorders_url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.i("onResponse", response.toString());
                pdia.dismiss();
                try {
                    JSONArray orderarray = response.getJSONArray("data");


                    for (int i = 0; i < orderarray.length(); i++) {
                        Log.e("number", String.valueOf(i));
                        JSONObject order = orderarray.getJSONObject(i);
                        String location = order.getString("location");
                        String id = order.getString("id");
                        String dod = order.getString("dateOfOrder");
                        String name = order.getString("name");

                        Crypto sw = new Crypto(name, id, location, dod);
                        All_cton.add(sw);
                    }

                } catch (Exception rt) {
                    rt.printStackTrace();
                }

                desttt.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());
                pdia.dismiss();
                Toast.makeText(SyncActivity.this, "Couldnot get Orders", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // Basic Authentication
                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                headers.put("Authorization", "Bearer " + accesstoken);
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    /**
     * Move to main screen
     * for bearer Authorisation
     */
    public void movetopayment() {

        Log.e("des", "Moving to payment");
        Intent des = new Intent(SyncActivity.this, MainActivity.class);
        startActivity(des);
    }


    public void DownloadManifests(List man) {
        JSONObject parameters = new JSONObject();
        try {
            parameters.put("ids", man.toString());

        } catch (Exception r) {
            r.printStackTrace();
        }

        Log.e("Des", man.toString());

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.getmanifest_url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("ManifestonResponse", response.toString());

                //Payment re = new Payment();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onErrorResponse", error.toString());
                Toast.makeText(SyncActivity.this, "Unable to Download manifest", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // Basic Authentication
                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                headers.put("Authorization", "Bearer " + accesstoken);
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);


    }

    public void Downloadmaan(JSONArray man) {

        final ProgressDialog pdia;
        pdia = new ProgressDialog(SyncActivity.this);
        pdia.setMessage("Downloading Manifest...");
        pdia.show();
        JsonArrayRequest arrayreq = new JsonArrayRequest(Request.Method.POST, Constants.getmanifest_url, man,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.e("Response", response.toString());
                        Log.e("Response length", String.valueOf(response.length()));

                        for (int i = 0; i < response.length(); i++) {

                            try {
                                JSONObject dd = response.getJSONObject(i);
                                Log.e("JsonOBject", dd.toString());

                                try {
                                    String orderid = dd.getString("orderId");
                                    String ordername = dd.getString("orderName");
                                    JSONArray ds = dd.getJSONArray("manifest");
                                    Log.e("Manifest ", ds.length() + " ::" + ds.toString());

                                    for (int z = 0; z < ds.length(); z++) {
                                        Log.e("manifest details", String.valueOf(z) + ds.getJSONObject(z));
                                        JSONObject mnb = ds.getJSONObject(z).getJSONObject("beneficiary");
                                        String bname = mnb.getString("surname");
                                        String bnatid = mnb.getString("nationalId");
                                        String bamount = ds.getJSONObject(z).getString("totalAmountPayable");
                                        //String bname = mnb.getString("");


                                        Payment sa = new Payment(orderid, ordername, "", bname, "", "", bnatid, "", "", bamount, "", false, false);

                                        pleg = PaymentLedger.getInstance();
                                        pleg.initiateSale(sa);
                                    }


                                } catch (Exception er) {
                                    er.printStackTrace();
                                }

                            } catch (Exception rt) {
                                rt.printStackTrace();
                            }

                        }

                        pdia.dismiss();
                        movetopayment();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("response error", error.toString());
                pdia.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // Basic Authentication
                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                headers.put("Authorization", "Bearer " + accesstoken);
                return headers;
            }
        };
        RequestQueue mnb = Volley.newRequestQueue(this);
        mnb.add(arrayreq);
    }

}
