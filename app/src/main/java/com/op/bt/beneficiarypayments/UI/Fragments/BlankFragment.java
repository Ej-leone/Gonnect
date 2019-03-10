package com.op.bt.beneficiarypayments.UI.Fragments;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.op.bt.beneficiarypayments.Data.Constants;
import com.op.bt.beneficiarypayments.Data.PrefManager;
import com.op.bt.beneficiarypayments.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment  implements BlockingStep,View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    TextView lth, lfi ,lmi ,lri ,lpinky,rth, rfi ,rmi ,rri ,rpinky;
    TableRow rt,rff,mf,rf,pf,lt,lff,lmf,lrf,lpf;

    AppCompatButton rbtn ,lbtn;
    PrefManager pref;

    boolean selected = true;
    ArrayList leftprints = new ArrayList();

    ArrayList rightprints = new ArrayList();



    public BlankFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pref = new PrefManager(getActivity());
            View sd=  inflater.inflate(R.layout.fragment_blank, container, false);
                init_view(sd);
        return sd;
    }

    public void init_view (View d){


        lth = d.findViewById(R.id.atv_lthumb);
        lfi  = d.findViewById(R.id.atv_lindexfinger);
        lmi  = d.findViewById(R.id.atv_lmiddlefinger);
        lri  = d.findViewById(R.id.atv_lringfinger);
        lpinky = d.findViewById(R.id.atv_lpinkyfinger);

        rth = d.findViewById(R.id.atv_rthumb);
        rfi  = d.findViewById(R.id.atv_rindexfinger);
        rmi  = d.findViewById(R.id.atv_rmiddlefinger);
        rri  = d.findViewById(R.id.atv_rringfinger);
        rpinky = d.findViewById(R.id.atv_rmiddlefinger);
        rbtn = d.findViewById(R.id.abtn_rhand);
        lbtn = d.findViewById(R.id.abtn_lhand);


        rt= d.findViewById(R.id.atr_rt);
        rff= d.findViewById(R.id.atr_if);
        mf= d.findViewById(R.id.atr_rm);
        rf= d.findViewById(R.id.atr_rf);
        pf= d.findViewById(R.id.atr_pf);


        lt = d.findViewById(R.id.atr_lt);
        lff=d.findViewById(R.id.atr_lif);
        lmf=d.findViewById(R.id.atr_lmf);
        lrf=d.findViewById(R.id.atr_lrf);
        lpf=d.findViewById(R.id.atr_lpf);;

        rt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect_print_dialog("Right Thumb");
            }
        });
        rff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect_print_dialog("Right Index finger");
            }
        });
        mf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect_print_dialog("Middle Finger");
            }
        });
        pf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect_print_dialog("Pinky Finger");
            }
        });
        rf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect_print_dialog("Ring Finger");
            }
        });


        lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect_print_dialog("Left Thumb");
            }
        });
        lff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect_print_dialog("Left Index Finger");
            }
        });
        lmf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect_print_dialog("Left Middle Finger");
            }
        });
        lrf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect_print_dialog("Left Ring Finger");
            }
        });
        lpf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collect_print_dialog("Left Pinky Finger");
            }
        });

    }




    public void collect_print_dialog (String fingertype){
        Log.e("sss","sdsd");
        AlertDialog.Builder swas = new AlertDialog.Builder(getActivity());
        swas.setCancelable(false);
        swas.setTitle("Collect" + fingertype);
        swas.setPositiveButton("Collectprint", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        swas.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dg = swas.create();
        dg.show();





    }
    @Override
    public void onClick(View view) {
        int s = view.getId();


    }

    @Override
    public void onNextClicked(StepperLayout.OnNextClickedCallback callback) {

    }

    @Override
    public void onCompleteClicked(StepperLayout.OnCompleteClickedCallback callback) {

    }

    @Override
    public void onBackClicked(StepperLayout.OnBackClickedCallback callback) {

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

    public void upload_prints (ArrayList filepaths , String url , String Cardno){

        for (int i=0; i< filepaths.size(); i++ ){

            indiv_print(filepaths.get(i).toString(),url,Cardno,"");
        }
    }


    private void indiv_print (String Filepath,String url ,String Cardno,String Whose){
        Log.e("Cardno", Cardno);
        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        OkHttpClient client = new OkHttpClient();

        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("title", "Passport Picture")
                .addFormDataPart(Whose, "logo-square",
                        RequestBody.create(MEDIA_TYPE_PNG, new File("website/static/logo-square.png")))
                .build();

        okhttp3.Request request = new okhttp3.Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url(Constants.image_url + Cardno)
                .post(requestBody)
                .build();

        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("exception", e.getLocalizedMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("response" , response.message());
                    }
                });

    }

}
//"http://"+ pref.getLocalIP()+