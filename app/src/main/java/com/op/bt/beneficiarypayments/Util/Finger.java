package com.telpo.usb.finger;

public class Finger {
    static {
        System.loadLibrary("Android_Host_Commu");
        System.loadLibrary("fp_jni");
    }

    public static native int initialize();

    public static native int identify(int[] idBuff);

    public static native int enroll(int nFingerNumber, int nTmplNo, int[] pnDuplNo);

    public static native int clear_template(int nTmplNo, int Tmpl_Count);

    public static native int clear_alltemplate();

    public static native int get_empty_id(int[] img_data);

    public static native int get_image(byte[] img_data);

    public static native int read_template(int nTmplNo, byte[] ppBff);

    public static native int write_template(int p_nTmplNo, byte[] p_pBff, int[] dup_ID);

    // public static native int capture_image(byte[] p_pBff);

    // public static native int verify_ga_tmpl(byte[] ga_tmpl);

    public static native int store_ram(int RamAddr, int Type);

    public static native int read_ram(int RamAddr, byte[] p_nTmplAddr);

    public static native int generate_ram(int RamAddr, int GenCount);

    public static native int update_tmpl(int[] result);

    public static native int verify_ISO_tmpl(byte[] ISOAddr1, byte[] ISOAddr2);

    public static native int verify_ISO2011_tmpl(byte[] ISO2011Addr1, byte[] ISO2011Addr2);

    public static native int convert_ISO_to_FPT(byte[] p_nTmplAddr, byte[] p_nTmplISOAddr, int ISO_Tmpl_Len);

    public static native int convert_ISO_to_FPT_local(byte[] p_nTmplAddr, byte[] p_nTmplISOAddr, int ISO_Tmpl_Len);

    public static native int convert_FPT_to_ISO(byte[] p_nTmplISOAddr, byte[] p_nTmplAddr, int[] ISO_Tmpl_Len);

    public static native int convert_FPT_to_ISO2011(byte[] p_nTmplISOAddr, byte[] p_nTmplAddr, int[] ISO_Tmpl_Len);

    public static native int convert_IMG_to_WSQ(byte[] Img_data, byte[] Img_data_WSQ, int[] WSQImg_Len);

    public static native int convert_WSQ_to_IMG(byte[] Img_data, byte[] Img_data_WSQ, int[] WSQImg_Len);

}
