package com.op.bt.beneficiarypayments.UI;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.common.pos.api.util.posutil.PosUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.op.bt.beneficiarypayments.Payment.PaymentLedger;
import com.op.bt.beneficiarypayments.R;
import com.op.bt.beneficiarypayments.Util.RawToBitMap;
import com.telpo.tps550.api.printer.UsbThermalPrinter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;


public class PaymentScreen extends AppCompatActivity implements View.OnClickListener {


    private final static int MAX_LEFT_DISTANCE = 255;
    public static String barcodeStr;
    public static String qrcodeStr;
    public static int paperWalk;
    public static String printContent;
    private final int NOPAPER = 3;
    private final int LOWBATTERY = 4;
    private final int PRINTVERSION = 5;
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
    int rets, bytelength;
    byte[] ISOTmpl = new byte[810];
    byte[] FPTAddr;
    byte[] img_data = new byte[250 * 360];
    int[] TMPL_No = new int[1];
    int[] pnDuplNo = new int[1];
    byte[] wsq_data = new byte[250 * 360];
    byte[] ISOAddrs = new byte[820];
    String tmplstr = "9C7B63ECA1C1C011C6FACAA9FCD448DF587DC637D3550CC74186A2EBDB91D9DFA58365E057E6265895F1F9C6B5C38914ABF4B096016035255F730F014395051D9537AE792E60AA61A4E34729B5A65EE20B02A0DFF6C03429C7DBE74C79AF59F12C231BA04F0C72AF37198C8DB689FDCEA36A515A6E8BA350CAAEC2F74EC75C44326237822DDE7C49645CF2D8969834AD5212855ABD13AB6A1391EACA0A3805C0C0B4B35AEFDFF079733FA9F462CDCB73CF5249D6C39682095F2E5EB10753E0A4A112B1CE8A2E8860E9E207B34D35819F6C4D91C30775E6FC213874A414556B280B554AD4F7E53048A5490AAEE971311CB09A0083D9BF38E10904B9BC5A05E0B9D44B71D44C91271D4EF71CE17B39815B555B83B7FFE1271098191A6BDDDCDBFCD42EEDFBE353D2F0F0C2E0BCE3E773D2956CEAEDF1E4A24E668DBD76030AAA9E8643C45D36EA8DA41C7B0E78B50E9FB949AFCDB8DCCCA9C1A48BA82607504C9E00DD243EA0A2205D1BE966FFAE65DF7DABF99B0B38B63CCA9E3BA4CBDF90FAED78A7B657DA4DD0F69FC9BEB170F99B5AD8D3205596ADB55FA10F9B27A95F3FC645D7BE1EDCA924603C18BF68933E9A2B3B497C946BCB9DF634E6FABF790B573392183C2FB240E3C8E057DA9FA2529924394CADD567C02444FA947249096A72F99CCF21AE6796E07C5E6FD85279756DED42513A8C5468909CC6C6A8091036EBA5";
    ProgressDialog dialog;
    UsbThermalPrinter mUsbThermalPrinter = new UsbThermalPrinter(PaymentScreen.this);
    MyHandler handler;
    AppCompatButton init, Collectprint, fin;
    TextView name, iden, amount, textPrintVersion;
    ImageView imgv;
    PaymentLedger pledger;
    int swa = 0;
    //Print
    private String printVersion;
    private String Result;
    private Boolean nopaper = false;
    private boolean LowBattery = false;
    private int leftDistance = 0;
    private int lineDistance;
    private int wordFont;
    private int printGray;
    private ProgressDialog progressDialog;
    private String picturePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/111.bmp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_screen);


        init = findViewById(R.id.fin_init);
        Collectprint = findViewById(R.id.collect_print);
        name = findViewById(R.id.tot_name);
        iden = findViewById(R.id.tot_identification);
        amount = findViewById(R.id.tot_amount);
        imgv = findViewById(R.id.print_image);
        fin = findViewById(R.id.b_init);
        textPrintVersion = findViewById(R.id.print_version);

        handler = new MyHandler();

        init.setOnClickListener(this);
        Collectprint.setOnClickListener(this);
        fin.setOnClickListener(this);

        init_ui();


        // Bundle z = getIntent().getBundleExtra("payment");
        // String nm = z.getString("name");
        // String nid = z.getString("nid");
        // String uid =  z.getString("uid");
        // String gender = z.getString("gender");
        // String amont =  z.getString("amount");


        //name.setText(nm);
        // iden.setText(nid);
        // amount.setText(amont);


        try {
            if (PosUtil.setFingerPrintPower(PosUtil.FINGERPRINT_POWER_ON) == 0) {
                init_btn();
            } else {
                Log.e("sdsds", "wese");
            }
        } catch (Exception we) {
            we.printStackTrace();
        }


    }

    public void init_ui() {

        init.setClickable(false);
        Collectprint.setClickable(false);
        Collectprint.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        init.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

    }

    public void init_btn() {


        init.setClickable(true);

        init.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

    }

    public void init_btntwo() {
        Collectprint.setClickable(true);
        Collectprint.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PosUtil.setFingerPrintPower(PosUtil.FINGERPRINT_POWER_OFF);
    }

    @Override
    public void onClick(View view) {

        int idd = view.getId();
        int ret, en_Step;
        switch (idd) {
            case R.id.b_init:
                Log.e("Pscreen", "Finialise payment");
                finishpayment();
                break;

            case R.id.collect_print:
                Log.e("Pscreen", "collect print");
                ret = com.telpo.usb.finger.Finger.get_image(img_data);
                if (ret == 0) {
                    Bitmap bmp = RawToBitMap.convert8bit(img_data, 242, 266);
                    saveImage(bmp);
                    if (bmp != null) {
                        imgv.setImageBitmap(bmp);
                    }
                    return;
                }
                Toast.makeText(this, String.format("0x%02x", ret), Toast.LENGTH_SHORT).show();

                break;

            case R.id.fin_init:
                Log.e("Pscreen", "initiate");
                rets = com.telpo.usb.finger.Finger.initialize();
                if (rets == 0) {
                    init_btntwo();
                    Toast.makeText(this, "Initialize success: " + String.format("0x%02x", rets), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Initialize failed: " + String.format("0x%02x", rets), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void finishpayment() {
        String exditText;
        exditText = "0";
        if (exditText == null || exditText.length() < 1) {
            Toast.makeText(PaymentScreen.this, getString(R.string.left_margin) + getString(R.string.lengthNotEnougth), Toast.LENGTH_LONG).show();
            return;
        }
        leftDistance = Integer.parseInt(exditText);
        exditText = "0";
        if (exditText == null || exditText.length() < 1) {
            Toast.makeText(PaymentScreen.this, getString(R.string.row_space) + getString(R.string.lengthNotEnougth), Toast.LENGTH_LONG).show();
            return;
        }
        lineDistance = Integer.parseInt(exditText);
        printContent = "About to print this";
        exditText = "2";
        if (exditText == null || exditText.length() < 1) {
            Toast.makeText(PaymentScreen.this, getString(R.string.font_size) + getString(R.string.lengthNotEnougth), Toast.LENGTH_LONG).show();
            return;
        }
        wordFont = Integer.parseInt(exditText);
        exditText = "1";
        if (exditText == null || exditText.length() < 1) {
            Toast.makeText(PaymentScreen.this, getString(R.string.gray_level) + getString(R.string.lengthNotEnougth), Toast.LENGTH_LONG).show();
            return;
        }
        printGray = Integer.parseInt(exditText);
        if (leftDistance > MAX_LEFT_DISTANCE) {
            Toast.makeText(PaymentScreen.this, getString(R.string.outOfLeft), Toast.LENGTH_LONG).show();
            return;
        }
        if (lineDistance > 255) {
            Toast.makeText(PaymentScreen.this, getString(R.string.outOfLine), Toast.LENGTH_LONG).show();
            return;
        }
        if (wordFont > 4 || wordFont < 1) {
            Toast.makeText(PaymentScreen.this, getString(R.string.outOfFont), Toast.LENGTH_LONG).show();
            return;
        }
        if (printGray < 0 || printGray > 7) {
            Toast.makeText(PaymentScreen.this, getString(R.string.outOfGray), Toast.LENGTH_LONG).show();
            return;
        }
        if (printContent == null || printContent.length() == 0) {
            Toast.makeText(PaymentScreen.this, getString(R.string.empty), Toast.LENGTH_LONG).show();
            return;
        }
        if (LowBattery == true) {
            handler.sendMessage(handler.obtainMessage(LOWBATTERY, 1, 0, null));
        } else {
            if (!nopaper) {
                progressDialog = ProgressDialog.show(PaymentScreen.this, getString(R.string.bl_dy), getString(R.string.printing_wait));
                handler.sendMessage(handler.obtainMessage(PRINTCONTENT, 1, 0, null));
            } else {
                Toast.makeText(PaymentScreen.this, getString(R.string.ptintInit), Toast.LENGTH_LONG).show();
            }
        }

    }

    private void noPaperDlg() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(PaymentScreen.this);
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

    public Bitmap CreateCode(String str, com.google.zxing.BarcodeFormat type, int bmpWidth, int bmpHeight) throws WriterException {
        Hashtable<EncodeHintType, String> mHashtable = new Hashtable<EncodeHintType, String>();
        mHashtable.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        // 生成二维矩阵,编码时要指定大小,不要生成了图片以后再进行缩放,以防模糊导致识别失败
        BitMatrix matrix = new MultiFormatWriter().encode(str, type, bmpWidth, bmpHeight, mHashtable);
        int width = matrix.getWidth();
        int height = matrix.getHeight();
        // 二维矩阵转为一维像素数组（一直横着排）
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix.get(x, y)) {
                    pixels[y * width + x] = 0xff000000;
                } else {
                    pixels[y * width + x] = 0xffffffff;
                }
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap,具体参考api
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private void saveImage(Bitmap bmp) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "Boohee");
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

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case NOPAPER:
                    noPaperDlg();
                    break;
                case LOWBATTERY:
                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(PaymentScreen.this);
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
                    Toast.makeText(PaymentScreen.this, R.string.maker_not_find, Toast.LENGTH_SHORT).show();
                    break;
                case PRINTVERSION:
                    dialog.dismiss();
                    if (msg.obj.equals("1")) {
                        textPrintVersion.setText(printVersion);
                    } else {
                        Toast.makeText(PaymentScreen.this, R.string.operation_fail, Toast.LENGTH_LONG).show();
                    }
                    break;
                case PRINTBARCODE:
                    new PaymentScreen.barcodePrintThread().start();
                    break;
                case PRINTQRCODE:
                    new PaymentScreen.qrcodePrintThread().start();
                    break;
                case PRINTPAPERWALK:
                    new PaymentScreen.paperWalkPrintThread().start();
                    break;
                case PRINTCONTENT:
                    new PaymentScreen.contentPrintThread().start();
                    break;
                case MAKER:
                    new PaymentScreen.MakerThread().start();
                    break;
                case PRINTPICTURE:
                    new PaymentScreen.printPicture().start();
                    break;
                case CANCELPROMPT:
                    if (progressDialog != null && !PaymentScreen.this.isFinishing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                    break;
                case OVERHEAT:
                    AlertDialog.Builder overHeatDialog = new AlertDialog.Builder(PaymentScreen.this);
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
                    Toast.makeText(PaymentScreen.this, "Print Error!", Toast.LENGTH_LONG).show();
                    break;
            }
        }
    }

    private class paperWalkPrintThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                mUsbThermalPrinter.reset();
                mUsbThermalPrinter.walkPaper(paperWalk);
            } catch (Exception e) {
                e.printStackTrace();
                Result = e.toString();
                if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
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

    private class barcodePrintThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                mUsbThermalPrinter.reset();
                mUsbThermalPrinter.setGray(printGray);
                Bitmap bitmap = CreateCode(barcodeStr, BarcodeFormat.CODE_128, 320, 176);
                if (bitmap != null) {
                    mUsbThermalPrinter.printLogo(bitmap, true);
                }
                mUsbThermalPrinter.addString(barcodeStr);
                mUsbThermalPrinter.printString();
                mUsbThermalPrinter.walkPaper(20);
            } catch (Exception e) {
                e.printStackTrace();
                Result = e.toString();
                if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
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

    private class qrcodePrintThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                mUsbThermalPrinter.reset();
                mUsbThermalPrinter.setGray(printGray);
                Bitmap bitmap = CreateCode(qrcodeStr, BarcodeFormat.QR_CODE, 256, 256);
                if (bitmap != null) {
                    mUsbThermalPrinter.printLogo(bitmap, true);
                }
                mUsbThermalPrinter.addString(qrcodeStr);
                mUsbThermalPrinter.printString();
                mUsbThermalPrinter.walkPaper(20);
            } catch (Exception e) {
                e.printStackTrace();
                Result = e.toString();
                if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
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

    private class MakerThread extends Thread {

        @Override
        public void run() {
            super.run();
            try {
                mUsbThermalPrinter.reset();
                // mUsbThermalPrinter.searchMark(Integer.parseInt(edittext_maker_search_distance.getText().toString()),
                //       Integer.parseInt(edittext_maker_walk_distance.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
                Result = e.toString();
                if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
                    nopaper = true;
                } else if (Result.equals("com.telpo.tps550.api.printer.OverHeatException")) {
                    handler.sendMessage(handler.obtainMessage(OVERHEAT, 1, 0, null));
                } else if (Result.equals("com.telpo.tps550.api.printer.BlackBlockNotFoundException")) {
                    handler.sendMessage(handler.obtainMessage(NOBLACKBLOCK, 1, 0, null));
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

    private class printPicture extends Thread {

        @Override
        public void run() {
            super.run();
            try {
                mUsbThermalPrinter.reset();
                mUsbThermalPrinter.setGray(printGray);
                mUsbThermalPrinter.setAlgin(UsbThermalPrinter.ALGIN_MIDDLE);
                File file = new File(picturePath);
                if (file.exists()) {
                    mUsbThermalPrinter.printLogo(BitmapFactory.decodeFile(picturePath), false);
                    mUsbThermalPrinter.walkPaper(20);
                } else {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            Toast.makeText(PaymentScreen.this, getString(R.string.not_find_picture), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
                Result = e.toString();
                if (Result.equals("com.telpo.tps550.api.printer.NoPaperException")) {
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


/*
*    try {
            if(PosUtil.setFingerPrintPower(PosUtil.FINGERPRINT_POWER_ON) ==0){
                init_btn();
            }
            else {
                Log.e("sdsds","wese");
            }
        }
        catch (Exception we){ we.printStackTrace();}
        finally {
            Toast.makeText(this, "unable to Start Finger Print Power", Toast.LENGTH_SHORT).show();
        }*/