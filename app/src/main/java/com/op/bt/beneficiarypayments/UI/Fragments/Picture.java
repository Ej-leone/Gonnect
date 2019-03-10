package com.op.bt.beneficiarypayments.UI.Fragments;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.op.bt.beneficiarypayments.Data.Constants;
import com.op.bt.beneficiarypayments.Data.PrefManager;
import com.op.bt.beneficiarypayments.R;
import com.stepstone.stepper.BlockingStep;
import com.stepstone.stepper.StepperLayout;
import com.stepstone.stepper.VerificationError;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class Picture extends Fragment implements BlockingStep ,View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
    int RC_HANDLE_CAMERA_PERM = 4;
    int REQUEST_TAKE_PHOTO  = 7;

    ImageView p_imv , o_imv ;
    AppCompatButton tp ,btn_up;
    String mCurrentPhotoPath;
    PrefManager pref;


    public Picture() {
        // Required empty public constructor
    }

    static final int REQUEST_IMAGE_CAPTURE = 1;

    private void dispatchTakePictureIntent() {

        int rc = ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);

        if (rc == PackageManager.PERMISSION_GRANTED) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                // Create the File where the photo should go
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException ex) {
                    // Error occurred while creating the File

                }
                // Continue only if the File was successfully created
                if (photoFile != null) {
                    Uri photoURI = FileProvider.getUriForFile(getActivity(),
                            getActivity().getPackageName() + ".share",
                            photoFile);
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
                }
            }

        }
        else {
            requestCameraPermission();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        pref = new PrefManager(getActivity());
      View da= inflater.inflate(R.layout.fragment_picture, container, false);
      init_vw(da);


        return da;
    }

    private  void init_vw (View x ){
        p_imv = x.findViewById(R.id.pimgview);
        o_imv = x.findViewById(R.id.oimgview);
        tp = x.findViewById(R.id.btn_tp);
        btn_up = x.findViewById(R.id.btn_upload);

        tp.setOnClickListener(this);
        btn_up.setOnClickListener(this);
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
        callback.goToPrevStep();
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




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)  {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("response ", "marinaatee");
        Log.e("request Code", String.valueOf(requestCode));
        //Handle Code
        if (requestCode == 7 && resultCode == Activity.RESULT_OK) {
         // Log.e("Logging",data.getData().toString());
          //  Bundle extras = data.getExtras();
           // Bitmap imageBitmap = (Bitmap) extras.get("data");
            // p_imv.setImageBitmap(imageBitmap);
        }
        else {
            Log.e("Result code", String.valueOf(resultCode));
            Log.e("tag","WE dont knwow whats up");
        }
    }

    public boolean validate (){
        return true;
    }


    public void uploadrun(String cardno ,String Whose) throws Exception {
        Log.e("Cardno", cardno);
        Log.e("filepath",mCurrentPhotoPath);

        OkHttpClient client = new OkHttpClient();
        // Use the imgur image upload API as documented at https://api.imgur.com/endpoints/image
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                //.addFormDataPart("title", "Passport Picture")
                .addFormDataPart("beneficiary", "beneficiary",
                        RequestBody.create(MEDIA_TYPE_PNG,mCurrentPhotoPath
                        ))
                .addFormDataPart("nextOfKin", "nextOfKin",
                        RequestBody.create(MEDIA_TYPE_PNG,mCurrentPhotoPath
                        ))
                .build();


        Log.e("RequestBody",requestBody.toString());
        Request request = new Request.Builder()
                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                .url("http://"+ pref.getLocalIP()+Constants.image_url + cardno)
                .post(requestBody)
                .build();


        client.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("Failed","Failed response");
                        Log.e("exception", e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        Log.e("Sucess","Succeeded response");
                        Log.e("response" , response.body().toString());
                    }
                });


        

       // try (Response response = client.newCall(request).execute()) {
         //   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

           // System.out.println(response.body().string());
        //}
    }

    @Override
    public void onClick(View view) {
        int s = view.getId();

        switch (s){
            case R.id.btn_upload:
                try {
                    uploadrun(mListener.getData(), "beneficiary");
                    //nextOfKin
                }
                catch (Exception e){
                    Log.e("Logging" ,e.toString());
                }
                break;
            case R.id.btn_tp:
                dispatchTakePictureIntent();
                break;
        }
    }
    /*
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */
    private void requestCameraPermission() {
        Log.w("TAG", "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(getActivity(), permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = getActivity();

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
