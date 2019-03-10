package com.op.bt.beneficiarypayments.UI;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.nfc.FormatException;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.TagLostException;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.op.bt.beneficiarypayments.Data.PrefManager;
import com.op.bt.beneficiarypayments.R;
import com.op.bt.beneficiarypayments.UI.Adapter.MyStepperAdapter;
import com.op.bt.beneficiarypayments.UI.Fragments.OnFragmentInteractionListener;
import com.op.bt.beneficiarypayments.Util.DialogUtils;
import com.stepstone.stepper.StepperLayout;
import com.telpo.tps550.api.TelpoException;

import java.io.IOException;
import java.nio.charset.Charset;

public class RegisterUser extends AppCompatActivity implements OnFragmentInteractionListener{

    PrefManager pref;
    private NfcAdapter mNfcAdapter;
    String data = "information";
    private StepperLayout sl ;
    String Caard_no ="0";
    int rets, bytelength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        sl = (StepperLayout) findViewById(R.id.stepperLayout);
        sl.setAdapter(new MyStepperAdapter(getSupportFragmentManager(),this));

        initNFC();
        init_fingerprint();
    }

    public  void init_fingerprint (){
        Log.e("Pscreen", "initiate");
        rets = com.telpo.usb.finger.Finger.initialize();

        if (rets == 0) {

            Toast.makeText(this, "Initialize success: " + String.format("0x%02x", rets), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Initialize failed: " + String.format("0x%02x", rets), Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    protected void onNewIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);

        // Record to launch Play Store if app is not installed
        NdefRecord appRecord = NdefRecord.createApplicationRecord(getPackageName());

        // Record with actual data we care about
        NdefRecord relayRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
                new String("application/" + getPackageName())
                        .getBytes(Charset.forName("US-ASCII")),
                null, data.getBytes());

        // Complete NDEF message with both records
        NdefMessage message = new NdefMessage(new NdefRecord[] {relayRecord, appRecord});

        Log.d("TAG", "onNewIntent: "+intent.getAction());

        if(tag != null) {
            Toast.makeText(this, "message_tag_detected", Toast.LENGTH_SHORT).show();
            android.nfc.tech.Ndef ndef = android.nfc.tech.Ndef.get(tag);

            Log.e("Tag Present","sssss");


            try {

                ndef.connect();


                if(!ndef.isWritable()) {
                   // DialogUtils.displayErrorDialog(context, R.string.nfcReadOnlyErrorTitle, R.string.nfcReadOnlyError);
                    //return false;
                    Log.e("sds","sdsd");
                }


                // Check if there's enough space on the tag for the message
                //int size = message.toByteArray().length;
               // if(ndef.getMaxSize() &lt; size) {
                   // DialogUtils.displayErrorDialog(context, R.string.nfcBadSpaceErrorTitle, R.string.nfcBadSpaceError);
                    //return false;
                //}

                try {
                    // Write the data to the tag
                    ndef.writeNdefMessage(message);

                  //  DialogUtils.displayInfoDialog(context, R.string.nfcWrittenTitle, R.string.nfcWritten);
                   // return true;
                } catch (TagLostException tle) {
                  //  DialogUtils.displayErrorDialog(context, R.string.nfcTagLostErrorTitle, R.string.nfcTagLostError);
                  //  return false;
                } catch (IOException ioe) {
                  //  DialogUtils.displayErrorDialog(context, R.string.nfcFormattingErrorTitle, R.string.nfcFormattingError);
                  //  return false;
                } catch (FormatException fe) {
                   // DialogUtils.displayErrorDialog(context, R.string.nfcFormattingErrorTitle, R.string.nfcFormattingError);
                   // return false;
                }


                ndef.close();

            } catch (IOException  e) {
                e.printStackTrace();

            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter tagDetected = new IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED);
        IntentFilter ndefDetected = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        IntentFilter techDetected = new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED);
        IntentFilter[] nfcIntentFilter = new IntentFilter[]{techDetected,tagDetected,ndefDetected};

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

    private void initNFC(){

        mNfcAdapter = NfcAdapter.getDefaultAdapter(this);
    }

    public  boolean write_nfc (Context context, Tag tag, String data){

        // Record to launch Play Store if app is not installed
        NdefRecord appRecord = NdefRecord.createApplicationRecord(context.getPackageName());

        // Record with actual data we care about
        NdefRecord relayRecord = new NdefRecord(NdefRecord.TNF_MIME_MEDIA,
                new String("application/" + context.getPackageName())
                        .getBytes(Charset.forName("US-ASCII")),
                null, data.getBytes());

        // Complete NDEF message with both records
        NdefMessage message = new NdefMessage(new NdefRecord[] {relayRecord, appRecord});

        try {
            // If the tag is already formatted, just write the message to it
            Ndef ndef = Ndef.get(tag);
            if(ndef != null) {
                ndef.connect();

                // Make sure the tag is writable
                if(!ndef.isWritable()) {
                    DialogUtils.displayErrorDialog(context, R.string.nfcReadOnlyErrorTitle, R.string.nfcReadOnlyError);
                    return false;
                }

                // Check if there's enough space on the tag for the message
                int size = message.toByteArray().length;
                if(ndef.getMaxSize() > size) {
                    DialogUtils.displayErrorDialog(context, R.string.nfcBadSpaceErrorTitle, R.string.nfcBadSpaceError);
                    return false;
                }


                try {
                    // Write the data to the tag
                    ndef.writeNdefMessage(message);

                    DialogUtils.displayInfoDialog(context, R.string.nfcWrittenTitle, R.string.nfcWritten);
                    return true;
                } catch (TagLostException tle) {
                    DialogUtils.displayErrorDialog(context, R.string.nfcTagLostErrorTitle, R.string.nfcTagLostError);
                    return false;
                } catch (IOException ioe) {
                    DialogUtils.displayErrorDialog(context, R.string.nfcFormattingErrorTitle, R.string.nfcFormattingError);
                    return false;
                } catch (FormatException fe) {
                    DialogUtils.displayErrorDialog(context, R.string.nfcFormattingErrorTitle, R.string.nfcFormattingError);
                    return false;
                }
                // If the tag is not formatted, format it with the message
            } else {
                NdefFormatable format = NdefFormatable.get(tag);
                if(format != null) {
                    try {
                        format.connect();
                        format.format(message);

                        DialogUtils.displayInfoDialog(context, R.string.nfcWrittenTitle, R.string.nfcWritten);
                        return true;
                    } catch (TagLostException tle) {
                        DialogUtils.displayErrorDialog(context, R.string.nfcTagLostErrorTitle, R.string.nfcTagLostError);
                        return false;
                    } catch (IOException ioe) {
                        DialogUtils.displayErrorDialog(context, R.string.nfcFormattingErrorTitle, R.string.nfcFormattingError);
                        return false;
                    } catch (FormatException fe) {
                        DialogUtils.displayErrorDialog(context, R.string.nfcFormattingErrorTitle, R.string.nfcFormattingError);
                        return false;
                    }
                } else {
                    DialogUtils.displayErrorDialog(context, R.string.nfcNoNdefErrorTitle, R.string.nfcNoNdefError);
                    return false;
                }
            }
        } catch(Exception e) {
            DialogUtils.displayErrorDialog(context, R.string.nfcUnknownErrorTitle, R.string.nfcUnknownError);
        }

        return false;
    }

    @Override
    public void saveData(String Cardno) {

        Caard_no =  Cardno;
    }

    @Override
    public String getData() {
        return Caard_no;
    }
}

