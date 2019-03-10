package com.op.bt.beneficiarypayments.UI.Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.op.bt.beneficiarypayments.Data.Constants;
import com.op.bt.beneficiarypayments.Data.PrefManager;
import com.op.bt.beneficiarypayments.IntroActivity;
import com.op.bt.beneficiarypayments.R;
import com.op.bt.beneficiarypayments.UI.Meennuu;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */

public class Data extends Fragment implements BlockingStep ,View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    TextInputEditText psn , pon, osn , oon;
    AppCompatButton upload_btn ;
    String Card_no = "000";
    PrefManager pref;
    boolean  data = false;
    public Data() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pref = new PrefManager(getActivity());
        View sd = inflater.inflate(R.layout.fragment_data, container, false);
        getCardno();
            init(sd);

        return sd;
    }


    public void init (View x ){
        psn = x.findViewById(R.id.ed_psn);
        pon = x.findViewById(R.id.ed_pon);
        osn = x.findViewById(R.id.ed_osn);
        oon = x.findViewById(R.id.ed_oon);
        upload_btn = x.findViewById(R.id.btn_statuse);

        upload_btn.setOnClickListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {
        if(validate()){
            callback.goToNextStep();
        }

        Toast.makeText(getActivity(),"Something wrong",Toast.LENGTH_LONG);
    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {
        Toast.makeText(getActivity(),"At the frst step",Toast.LENGTH_LONG).show();
    }

    @Override
    public VerificationError verifyStep() {
        return null;
    }

    @Override
    public void onSelected() {

    }

    @Override
    public void onError(@NonNull VerificationError error) {

    }

    /**
    * @validate function checks the inputs first then whether the upload  was successful
    */

    public boolean validate ()  {

        if(psn.getText().toString().isEmpty()){
            psn.setError("Cannot be empty");
             return false;
        }

        if(osn.getText().toString().isEmpty()){
            osn.setError("Cannot be empty");
             return false;
        }

        if(osn.getText().toString().isEmpty()){
            osn.setError("Cannot be empty");
             return false;
        }
        if(oon.getText().toString().isEmpty()){
            oon.setError("Cannot be empty");
             return false;
        }
        //TODo:Check if upload was succesful
        return true;
    }


    public  void finup (){
        String de = psn.getText().toString();
        String des = pon.getText().toString();
        String dess = osn.getText().toString();
        String desss = oon.getText().toString();

        if (validate()){
            Upload_data(de,des,dess,desss,Card_no);
        }

    }


    public void Upload_data(String pname, String poname , String aname, String aoname ,String Cardno) {
        final ProgressDialog pdia;
        pdia = new ProgressDialog(getActivity());
        pdia.setMessage("Adding  Basic details...");
        pdia.show();





        JSONObject parameters = new JSONObject();
        try {
            parameters.put("surname",pname );
            parameters.put("nokSurname", aname);
            parameters.put("surname", poname);
            parameters.put("otherNames",aoname);

        } catch (Exception e) {
            Log.e("Some", e.getMessage());
        }

        Log.e("url" ,Constants.data_url + Cardno);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,"http://"+ pref.getLocalIP()+Constants.data_url + Cardno , parameters, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                pdia.dismiss();
                Log.e("onResponse", response.toString());

                try {
                    if (response.getBoolean("success")){

                        Toast.makeText(getActivity(), "Worked you can move to the next page ", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(getActivity(),"error uploading" ,Toast.LENGTH_LONG);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdia.dismiss();
                Log.e("onErrorResponse", error.toString());
                Toast.makeText(getActivity(), "Error connecting", Toast.LENGTH_SHORT).show();
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

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(request);
    }


    public void getCardno (){
        final ProgressDialog pdia;
        pdia = new ProgressDialog(getActivity());
        pdia.setMessage("Starting up...");
        pdia.show();

        Log.e("Url", "http://"+ pref.getLocalIP()+Constants.get_cardno);

        Log.e("Url",Constants.get_cardno);
        JsonObjectRequest ds = new JsonObjectRequest(Request.Method.GET, "http://"+ pref.getLocalIP()+Constants.get_cardno, new JSONObject(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.e("response",response.toString());
                pdia.dismiss();
                try{
                    //Todo:Notify Response
                   Card_no = response.getString("cardNumber");
                   mListener.saveData(Card_no);

                }
                catch (Exception e){

                    Log.e("Exception",e.getMessage());
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Todo:Notify Error
                pdia.dismiss();
                Log.e("error",error.toString());
            }
        });


        RequestQueue queue = Volley.newRequestQueue(getActivity());
        queue.add(ds);
    }


    @Override
    public void onClick(View view) {
        int d = view.getId();

        switch (d){
            case R.id.btn_statuse:
                Log.e("Btn ","Clicked");
               finup();
                break;
        }

    }
}
