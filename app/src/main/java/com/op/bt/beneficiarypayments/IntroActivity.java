package com.op.bt.beneficiarypayments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.op.bt.beneficiarypayments.Data.Constants;
import com.op.bt.beneficiarypayments.Data.LocalIp;
import com.op.bt.beneficiarypayments.Data.PrefManager;
import com.op.bt.beneficiarypayments.UI.Meennuu;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IntroActivity extends AppCompatActivity {

    EditText uname, password;
    Button lip;
    Switch dd;
    TextView buton ,conn;
    PrefManager pref;
    LocalIp llp;
   // AppCompatSpinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = new PrefManager(IntroActivity.this);
        setContentView(R.layout.activity_intro);
        llp = new LocalIp();
        init_viws();

        connection();
        Display d = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = d.getWidth();
        int height = d.getHeight();


        //Log.e("bang bang", "width" + String.valueOf(width) + " height:" + String.valueOf(height));

    }


    public void connection (){

        conn.setText("Connected to :" + pref.getLocalIP());
        conn.setBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
    }


    @Override
    protected void onResume() {
        super.onResume();
        uname.setText("");
        password.setText("");
    }

    public void init_viws() {
        uname = findViewById(R.id.input_login);
        password = findViewById(R.id.input_loginpassword);
        buton = findViewById(R.id.btn_login);
        dd = findViewById(R.id.lip_switch);
        lip = findViewById(R.id.lip_btn);
        conn = findViewById(R.id.tv_conn);
       // spinner = findViewById(R.id.ws_btn);


       /* spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("selected",String.valueOf(i+1));
                pref.SetFamilySize(i+1);

                Toast.makeText(IntroActivity.this,"set Family size" + String.valueOf(i+1),Toast.LENGTH_LONG).show();


            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });*/


        dd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    lip.setClickable(true);
                    lip.setVisibility(View.VISIBLE);
                    pref.Set_locals(true);
                    Log.e("setlocal","true");
                } else {
                    lip.setClickable(false);
                    lip.setVisibility(View.GONE);
                    pref.Set_locals(false);
                    Log.e("setlocal","false");
                }
            }
        });

        lip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alert = new AlertDialog.Builder(IntroActivity.this);
                alert.setTitle("Setting");
                alert.setMessage("Enter IP");

                // Set an EditText view to get user input
                final EditText input = new EditText(IntroActivity.this);
                alert.setView(input);


                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String value = input.getText().toString();
                        Log.d("TAG", "Ip value " + value);

                        llp.setIp(value);
                        pref.setLocalIP(value);

                        lip.setText(pref.getLocalIP());
                        connection();

                        //return;
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        dialog.dismiss();
                        //return;
                    }
                });
                alert.show();

            }
        });


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


        String urrr;

        if (pref.Get_locals()){
            urrr = "http://"+ pref.getLocalIP()+Constants.local_login;

        }
       else {
            urrr = "http://"+ pref.getLocalIP()+Constants.local_login;
        }

        JSONObject parameters = new JSONObject();
        try {
            parameters.put("username", user);
            parameters.put("pin", pwd);

        } catch (Exception e) {
        }


        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,urrr , parameters, new Response.Listener<JSONObject>() {
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

                        Log.e("util", uid);
                        pref.setAccesstoken(acctokn);
                        pref.setUser(username, photourl, uid);

                        if(back_up()){
                            Intent des = new Intent(IntroActivity.this, Meennuu.class);
                            startActivity(des);
                        }else {
                            //TODO:REVERT
                            //Toast.makeText(IntroActivity.this, "Could not Login..contact admin for issues", Toast.LENGTH_SHORT).show();
                            Intent des = new Intent(IntroActivity.this, Meennuu.class);
                            startActivity(des);
                        }
                    }
                    else {
                        Toast.makeText(IntroActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
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
        })        {
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


    private  boolean back_up(){
        Calendar c = Calendar.getInstance();

        // set the calendar to start of today
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        // and get that as a Date
        Date today = c.getTime();



        // user-specified date which you are testing
        // let's say the components come from a form or something
        int year = 2018;
        int month = 7;
        int dayOfMonth = 15;

        // reuse the calendar to set user specified date
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        Date datebefore = c.getTime();


        // user-specified date which you are testing
        // let's say the components come from a form or something
        int yearr = 2018;
        int monthr = 9;
        int dayOfMonthr = 10;

        // reuse the calendar to set user specified date
        c.set(Calendar.YEAR, yearr);
        c.set(Calendar.MONTH, monthr);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonthr);

        Date dateBelow = c.getTime();

        Log.e("Taged",datebefore.toString());
        Log.e("Taged",dateBelow.toString());
        Log.e("Taged",today.toString());

        // test your condition
        if (datebefore.before(today) && dateBelow.after(today)) {
            return true;
        }
        else{
            return false;
        }


    }

}
