package com.op.bt.beneficiarypayments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.op.bt.beneficiarypayments.Data.Constants;
import com.op.bt.beneficiarypayments.Data.PrefManager;
import com.op.bt.beneficiarypayments.UI.Meennuu;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class IntroActivity extends AppCompatActivity {

    EditText uname, password;
    TextView buton;
    PrefManager pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new PrefManager(IntroActivity.this);

        setContentView(R.layout.activity_intro);
        init_viws();

        Display d = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = d.getWidth();
        int height = d.getHeight();


        Log.e("bang bang", "width" + String.valueOf(width) + " height:" + String.valueOf(height));

    }


    public void init_viws() {
        uname = findViewById(R.id.input_login);
        password = findViewById(R.id.input_loginpassword);
        buton = findViewById(R.id.btn_login);


        buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = uname.getText().toString();
                String pdd = password.getText().toString();

                Login(user, pdd);
            }
        });
    }

    public void Login(String user, String pwd) {
        final ProgressDialog pdia;
        pdia = new ProgressDialog(IntroActivity.this);
        pdia.setMessage("Loading...");
        pdia.show();

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("username", user);
            parameters.put("pin", pwd);

        } catch (Exception e) {
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, Constants.login_url, parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pdia.dismiss();
                Log.e("onResponse", response.toString());

                try {
                    boolean succ = response.getBoolean("success");
                    String desper = response.getString("message");

                    Toast.makeText(IntroActivity.this, desper, Toast.LENGTH_LONG).show();
                    if (succ) {
                        String acctokn = response.getString("accessToken");
                        String username = response.getString("surname");
                        String photourl = response.getString("photo");
                        String uid = response.getString("staffId");

                        pref.setAccesstoken(acctokn);
                        pref.setUser(username, photourl, uid);

                        Intent des = new Intent(IntroActivity.this, Meennuu.class);
                        startActivity(des);
                    }
                } catch (Exception er) {
                    er.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdia.dismiss();
                Log.e("onErrorResponse", error.toString());
                Toast.makeText(IntroActivity.this, "Error connecting", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                // Basic Authentication
                //String auth = "Basic " + Base64.encodeToString(CONSUMER_KEY_AND_SECRET.getBytes(), Base64.NO_WRAP);

                headers.put("Authorization", "Bearer " + "accesstoken");
                return headers;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

}
