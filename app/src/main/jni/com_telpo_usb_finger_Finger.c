#include <jni.h>
#include <string.h>
#include <dlfcn.h>
#include <stdio.h>
#include <stdlib.h>
#include "log.h"

#ifndef _Included_com_telpo_usb_finger_Finger
#define _Included_com_telpo_usb_finger_Finger

//----------------------------------------------------------------------------
// Define FpCommand Function Command Code
//----------------------------------------------------------------------------
#define  FF_INITIALIZE_CODE           0x0000
#define  FF_IDENTIFY_CODE             0x0002
#define  FF_ENROLL_CODE               0x0004
#define  FF_CLEAR_TEMPLATE_CODE       0x0006
#define  FF_CLEAR_ALLTEMPLATE_CODE    0x0007
#define  FF_GET_EMPTY_ID_CODE         0x0008
#define  FF_GET_IMAGE_CODE			  0x000A
#define  FF_READ_TEMPLATE_CODE        0x000C
#define  FF_WRITE_TEMPLATE_CODE       0x000D
#define  FF_STORE_FP_RAM_CODE		  0x0014
#define  FF_READ_FP_RAM_CODE		  0x0016

#define  FF_UPDATE_TMPL_CODE          0x0032

#define  FF_CONVERT_FMR2005_TO_FPT_CODE		0x0142
#define  FF_CONVERT_FPT_TO_FMR2005_CODE		0x0143
#define  FF_CONVERT_FPT_TO_FMR2011_CODE		0x0145

#define  FF_CONVERT_IMG_TO_WSQ_CODE	  0x0202  //not implented in device
#define  FF_CONVERT_WSQ_TO_IMG_CODE	  0x0203  //not implented in device

#define  FF_CONVERT_FMR2005_TO_FPT_CODE_LOCAL	0x0146

#define  FF_CAPTURE_IMAGE_CODE      0x0300
#define  FF_VERIFY_GA_TMPL_CODE		0x3001
#define  FF_VERIFY_2005_TMPLS		0x3002
#define  FF_VERIFY_2011_TMPLS		0x3003

#define  FF_VERIFY_2005_TMPLS_LOCAL		0x3004

//----------------------------------------------------------------------------
// Define FpCommand Function Return Code
//----------------------------------------------------------------------------
#define		FF_SUCCESS						0x00
#define		FF_FAIL							0x01
#define		FF_ERR_VERIFY					0x11
#define		FF_ERR_IDENTIFY					0x12
#define		FF_ERR_TMPL_EMPTY				0x13
#define		FF_ERR_TMPL_NOT_EMPTY			0x14
#define		FF_ERR_ALL_TMPL_EMPTY			0x15
#define		FF_ERR_EMPTY_ID_NOEXIST			0x16
#define		FF_ERR_BROKEN_ID_NOEXIST		0x17
#define		FF_ERR_INVALID_TMPL_DATA		0x18
#define		FF_ERR_DUPLICATION_ID			0x19
#define		FF_ERR_TOO_FAST					0x1A
#define		FF_ERR_BAD_QUALITY				0x1B
#define		FF_ERR_SMALL_LINES				0x1C
#define		FF_ERR_GENERALIZE				0x1D
#define		FF_ERR_INTERNAL					0x1E
#define		FF_ERR_MEMORY					0x1F
#define		FF_ERR_EXCEPTION				0x20
#define		FF_ERR_INVALID_TMPL_NO			0x21
#define		FF_ERR_INVALID_PARAM			0x25
#define 	FF_ERR_EMPTY_FP_IMAGE          	0x26
#define 	FF_ERR_GEN_COUNT				0x27
#define 	FF_ERR_INVALID_DETECT_THRESOLD	0x28
#define		FF_ERR_INVALID_BUFFER_ID		0x29
#define		FF_ERR_INVALID_STORE_TYPE		0x2A
#define		FF_ERR_FP_TIMEOUT				0x2B //Only used in YN020S
#define		FF_ERR_FP_CANCEL				0x2C //Only used in YN020S
#define		FF_ERR_INIT_CMOS				0x30
#define		FF_ERR_NO_DETECT_FINGER			0x31
#define		FF_ERR_CAPTURE_FAIL				0x32 //Only used in YN020S
#define		FF_ERR_RESIDUAL_IMAGE			0x33
#define		FF_ERR_FAIL_INIT_ENGINE			0x34
#define 	FF_ERR_INCORRECT_CODE       	0xFF
/////////////////////////////////////////////////////////////////////


#define  GD_USER_RECORD_SIZE				(512)
#ifdef __cplusplus
extern "C"
{
#endif

    //1.初始化
	JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_initialize(JNIEnv *env,
			jclass obj) {

		int ret = CommandProc(FF_INITIALIZE_CODE, 0, 0, 0);
		LOGE("ret = %x", ret);
		return ret;
	}

    //2.验证指纹
	JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_identify(JNIEnv *env,
			jclass obj, jintArray p_IdBuff) {

		int Id_tmp;
		int ret = CommandProc(FF_IDENTIFY_CODE, &Id_tmp, 0, 0);
		LOGE("ret = %x", ret);
		if (ret ==FF_SUCCESS) {
			jint *tempdata = (*env)->GetIntArrayElements(env, p_IdBuff, NULL);
			tempdata[0] = Id_tmp;
			(*env)->ReleaseIntArrayElements(env, p_IdBuff, tempdata, 0);
		}
		return ret;
	}

    //3.注册指纹
	JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_enroll(JNIEnv *env,
			jclass obj, jint nFingerNumber, jint nTmplNo, jintArray out) {

		int tmp_out;
		int ret = CommandProc(FF_ENROLL_CODE, nFingerNumber, nTmplNo, &tmp_out);
		LOGE("ret = %x", ret);
		if (ret == FF_SUCCESS) {
			jint *tempdata = (*env)->GetIntArrayElements(env, out, NULL);
			tempdata[0] = tmp_out;
			(*env)->ReleaseIntArrayElements(env, out, tempdata, 0);
		}
		return ret;
	}

    //4.清除指定id指纹模板
	JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_clear_1template(JNIEnv *env,
			jclass obj, jint p_nTmplNo, jint Tmpl_Count) {
		int tmp_out;
		int ret = CommandProc(FF_CLEAR_TEMPLATE_CODE, p_nTmplNo, Tmpl_Count, &tmp_out);
		LOGE("ret = %x", ret);
		return ret;

	}

    //5.清除所有指纹模板
	JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_clear_1alltemplate(
			JNIEnv *env, jclass obj) {

		int ret = CommandProc(FF_CLEAR_ALLTEMPLATE_CODE, 0, 0, 0);
		LOGE("ret = %x", ret);
		return ret;
	}

    //6.检索没被利用的可使用的id号
	JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_get_1empty_1id(JNIEnv *env,
			jclass obj, jintArray out) {

		int tmp_out;
		int ret = CommandProc(FF_GET_EMPTY_ID_CODE, &tmp_out, 0, 0);
		LOGE("ret = %x", ret);
		if (ret == FF_SUCCESS) {
			int *tmpdata = (*env)->GetIntArrayElements(env, out, NULL);
			tmpdata[0] = tmp_out;
			(*env)->ReleaseIntArrayElements(env, out, tmpdata, 0);
		}

		return ret;

	}

    //7.获取指纹图像
	JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_get_1image(JNIEnv *env,
			jclass obj, jbyteArray buffer) {
		char TMP_No[250 * 360];

		int ret = CommandProc(FF_GET_IMAGE_CODE, TMP_No, 0, 0);
		LOGE("ret = %x", ret);

		if (ret == FF_SUCCESS) {

			jbyte *temp = (*env)->GetByteArrayElements(env, buffer, NULL);
			int i = 0;
			for (i = 0; i < 250 * 360; i++) {
				temp[i] = TMP_No[i];

			}
			(*env)->ReleaseByteArrayElements(env, buffer, temp, 0);
		}
		return ret;
	}

    //8.读取指定id指纹模板
	JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_read_1template(JNIEnv *env,
			jclass obj, jint id, jbyteArray out) {
		char tmp_out[900];
		int ret = CommandProc(FF_READ_TEMPLATE_CODE, id, tmp_out, 0);
		LOGE("ret = %x", ret);
		if (ret == FF_SUCCESS) {
			jbyte *tmpdata = (*env)->GetByteArrayElements(env, out, NULL);
			int i = 0;
			for (i = 0; i < 512; i++) {
//				LOGE("ret = 0x%02x", tmp_out[i]);
				tmpdata[i] = tmp_out[i];
			}
			(*env)->ReleaseByteArrayElements(env, out, tmpdata, 0);
		}
		return ret;
	}

	//9.写入指定id的指纹模板数据
	JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_write_1template(JNIEnv *env,
			jclass obj, jint id, jbyteArray pBff,jintArray out_id) {
		int tmp_out;
		jbyte *tmpdata = (*env)->GetByteArrayElements(env, pBff, NULL);
		int ret = CommandProc(FF_WRITE_TEMPLATE_CODE, id, tmpdata, &tmp_out);
		LOGE("ret = %x", ret);
		if (ret == FF_SUCCESS) {
			jint *tmpid= (*env)->GetIntArrayElements(env, out_id, NULL);
			tmpid[0]=tmp_out;
			(*env)->ReleaseIntArrayElements(env, out_id, tmpid, 0);
		}
		return ret;
	}

	//10.生成特征模板并写入指定编号的内部缓存。本命令执行前，需先执行图像及采集命令FF_GET_IMAGE_CODE
    JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_store_1ram(JNIEnv *env,
                   			jclass obj,jint RamAddr,jint Type) {

   	   int ret = CommandProc(FF_STORE_FP_RAM_CODE, RamAddr, Type, 0);
       LOGE("ret = %x", ret);
       return ret;
   	}


	//11.从指定编号的内部缓存读出模板数据
    JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_read_1ram(JNIEnv *env,
			jclass obj, jint id, jbyteArray outs) {
		char tmp_out[900];
		int ret = CommandProc(FF_READ_FP_RAM_CODE, id, tmp_out, 0);
		LOGE("ret = %x", ret);
		if (ret == FF_SUCCESS) {
			jbyte *tmpdata = (*env)->GetByteArrayElements(env, outs, NULL);
			int i = 0;
			for (i = 0; i < 512; i++) {
				tmpdata[i] = tmp_out[i];
			}
			(*env)->ReleaseByteArrayElements(env, outs, tmpdata, 0);
		}
		return ret;
	}


    //12.使用多个内部缓存中的模板合成一个新模板，并将其写入指定编号的内部缓存中
    //    。例如，如果GenCount=3，RamAddr=0，则使用0，1，2号3个缓存模板，合成一个
    //     新模板，并将其写入第0号缓存，如果GenCount=2，RamAddr=0，则使用0，1号2个
    //     缓存模板，合成一个新模板，并将其写入第0号缓存。合成后的模板可以通过
    //     FF_READ_FP_RAM_CODE命令读出。
  //  JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_generate_1ram(JNIEnv *env,
   //                			jclass obj,jint RamAddr,jint GenCount) {

   	 //  int ret = CommandProc(FF_GENERATE_FP_RAM_CODE, RamAddr, GenCount, 0);
     //  LOGE("ret = %x", ret);
    //   return ret;
   	//}



	//13.对上次识别成功的模板进行一次更新。必须在FF_IDENTIFY_CODE命令后立即执行，否则无效
    JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_update_1tmpl(JNIEnv *env,
			jclass obj,  jbyteArray result) {
		int tmp_out;
  		int ret = CommandProc(FF_UPDATE_TMPL_CODE, &tmp_out, 0, 0);
        LOGE("ret = %x", ret);
       	if (ret == FF_SUCCESS) {
        	jint *tmpid= (*env)->GetIntArrayElements(env, result, NULL);
        	tmpid[0] = tmp_out;
        	(*env)->ReleaseIntArrayElements(env, result, tmpid, 0);
        }
       	return ret;
	}


	//14.将Iso 19794-2-2005模板转换为Yoyon指纹模板
	 JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_convert_1ISO_1to_1FPT(JNIEnv *env,
    			jclass obj, jbyteArray out, jbyteArray in, jint length) {
    		char tmp_out[900];
    		jbyte *tmpdata = (*env)->GetByteArrayElements(env, in, NULL);
    		int ret = CommandProc(FF_CONVERT_FMR2005_TO_FPT_CODE, tmp_out, tmpdata, length);
    		LOGE("ret = %x", ret);
    		if (ret == FF_SUCCESS) {
    			jbyte *data = (*env)->GetByteArrayElements(env, out, NULL);
    			int i = 0;
    			for (i = 0; i < 810; i++) {
    //				LOGE("ret = 0x%02x", tmp_out[i]);
    				data[i] = tmp_out[i];
    			}
    			(*env)->ReleaseByteArrayElements(env, out, data, 0);
    		}
    		return ret;
    	}

    //15.将Iso 19794-2-2005模板转换为Yoyon指纹模板, 本命令在主机执行,不与USB模块发生通讯.
    JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_convert_1ISO_1to_1FPT_1local(JNIEnv *env,
    			jclass obj, jbyteArray out, jbyteArray in, jint length) {
    		char tmp_out[900];
    		jbyte *tmpdata = (*env)->GetByteArrayElements(env, in, NULL);
    		int ret = CommandProc(FF_CONVERT_FMR2005_TO_FPT_CODE_LOCAL, tmp_out, tmpdata, length);
    		LOGE("ret = %x", ret);
    		if (ret == FF_SUCCESS) {
    			jbyte *data = (*env)->GetByteArrayElements(env, out, NULL);
    			int i = 0;
    			for (i = 0; i < 810; i++) {
    				data[i] = tmp_out[i];
    			}
    			(*env)->ReleaseByteArrayElements(env, out, data, 0);
    		}
    		return ret;
    	}



    //16.将Yoyon指纹模板转换为Iso 19794-2-2005模板,且含有Yoyon扩展数据
	 JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_convert_1FPT_1to_1ISO(JNIEnv *env,
    			jclass obj, jbyteArray in, jbyteArray out, jintArray iso_length) {
    		char tmp_out[900];
    		int tmp_outs;
    		jbyte *tmpdata = (*env)->GetByteArrayElements(env, in, NULL);
    		int ret = CommandProc(FF_CONVERT_FPT_TO_FMR2005_CODE,  tmpdata, tmp_out,&tmp_outs);
    		LOGE("ret = %x", ret);
    		if (ret == FF_SUCCESS) {
    			jbyte *data = (*env)->GetByteArrayElements(env, out, NULL);
    			int i = 0;
    			for (i = 0; i < 810; i++) {
    //				LOGE("ret = 0x%02x", tmp_out[i]);
    				data[i] = tmp_out[i];
    			}
    			(*env)->ReleaseByteArrayElements(env, out, data, 0);
    			jint *tmpid= (*env)->GetIntArrayElements(env, iso_length, NULL);
                tmpid[0]=tmp_outs;
                (*env)->ReleaseIntArrayElements(env, iso_length, tmpid, 0);
    		}
    		return ret;
    	}

	//17.将Yoyon指纹模板转换为Iso 19794-2-2011模板,且含有Yoyon扩展数据
     JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_convert_1FPT_1to_1ISO2011(JNIEnv *env,
    			jclass obj, jbyteArray in, jbyteArray out, jintArray iso_length) {
    		char tmp_out[900];
    		int tmp_outs;
    		jbyte *tmpdata = (*env)->GetByteArrayElements(env, in, NULL);
    		int ret = CommandProc(FF_CONVERT_FPT_TO_FMR2011_CODE,  tmpdata, tmp_out,&tmp_outs);
    		LOGE("ret = %x", ret);
    		if (ret == FF_SUCCESS) {
    			jbyte *data = (*env)->GetByteArrayElements(env, out, NULL);
    			int i = 0;
    			for (i = 0; i < 810; i++) {
    //				LOGE("ret = 0x%02x", tmp_out[i]);
    				data[i] = tmp_out[i];
    			}
    			(*env)->ReleaseByteArrayElements(env, out, data, 0);
    			jint *tmpid= (*env)->GetIntArrayElements(env, iso_length, NULL);
                tmpid[0]=tmp_outs;
                (*env)->ReleaseIntArrayElements(env, iso_length, tmpid, 0);
    		}
    		return ret;
    	}



	//18.将原始图像数据转化为wsq压缩格式
    JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_convert_1IMG_1to_1WSQ(JNIEnv *env,
    			jclass obj, jbyteArray img, jbyteArray wsq, jintArray img_length) {
    		char wsq_out[256*360];
    		jbyte *tmpdata = (*env)->GetByteArrayElements(env, img, NULL);

    		int ret = CommandProc(FF_CONVERT_IMG_TO_WSQ_CODE,  tmpdata, wsq_out, img_length);
    		LOGE("ret = %x", ret);
    		if (ret == FF_SUCCESS) {

    			jbyte *data = (*env)->GetByteArrayElements(env, wsq, NULL);
    			for (int i = 0; i < 250 * 360; i++) {
    				data[i] = wsq_out[i];
    			}
    			(*env)->ReleaseByteArrayElements(env, wsq, data, 0);

    		}
    		return ret;
    	}



	//19.将wsq压缩格式图像数据转化为原始图像数据
    JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_convert_1WSQ_1to_1IMG(JNIEnv *env,
    			jclass obj, jbyteArray img, jbyteArray wsq, jintArray img_length) {
    		char wsq_out[256*360];
    		int tmp_outs;
    		jbyte *tmpdata = (*env)->GetByteArrayElements(env, wsq, NULL);

    		int ret = CommandProc(FF_CONVERT_IMG_TO_WSQ_CODE,  tmpdata, wsq_out, &tmp_outs);
    		LOGE("ret = %x", ret);
    		if (ret == FF_SUCCESS) {

    			jbyte *data = (*env)->GetByteArrayElements(env, img, NULL);
    			for (int i = 0; i < 250 * 360; i++) {
    				data[i] = wsq_out[i];
    			}
    			(*env)->ReleaseByteArrayElements(env, img, data, 0);

    			jint *tmpid= (*env)->GetIntArrayElements(env, img_length, NULL);
                tmpid[0] = tmp_outs;
                (*env)->ReleaseIntArrayElements(env, img_length, tmpid, 0);
    		}
    		return ret;
    	}





	//20.采集256*360的图像（仅K236T指纹头可用）
	JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_capture_1image(JNIEnv *env,
			jclass obj, jbyteArray buffer) {

		char TMP_No[256 * 360];

		int ret = CommandProc(FF_GET_IMAGE_CODE, (unsigned int) TMP_No, 0, 0);
		LOGE("ret = %x", ret);
		if (ret == FF_SUCCESS) {

			jbyte *temp = (*env)->GetByteArrayElements(env, buffer, NULL);
			int i = 0;
			for (i = 0; i < 256 * 360; i++) {
				temp[i] = TMP_No[i];
			}
			(*env)->ReleaseByteArrayElements(env, buffer, temp, 0);
		}
		return ret;
	}


    //21.用FF_GET_IMAGE_CODE命令采集图像后，可调用此命令与身份证指纹模板进行匹配验证




    //22.验证输入的两个ISO2005模板是否为同一手指
    JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_verify_1ISO_1tmpl(JNIEnv *env,
    			jclass obj,  jbyteArray myiso,jbyteArray yoiso) {

    		jbyte *mytmpdata = (*env)->GetByteArrayElements(env, myiso, NULL);
    		jbyte *yotmpdata = (*env)->GetByteArrayElements(env, yoiso, NULL);
    		int ret = CommandProc(FF_VERIFY_2005_TMPLS_LOCAL, mytmpdata, yotmpdata, 0);
    		LOGE("ret = %x", ret);
    		return ret;
    	}




    //23.验证输入的两个ISO2011模板是否为同一手指
    JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_verify_1ISO2011_1tmpl(JNIEnv *env,
    			jclass obj,  jbyteArray myiso2011,jbyteArray yoiso2011) {

    		jbyte *mytmpdata1 = (*env)->GetByteArrayElements(env, myiso2011, NULL);
    		jbyte *yotmpdata2 = (*env)->GetByteArrayElements(env, yoiso2011, NULL);
    		int ret = CommandProc(FF_VERIFY_2011_TMPLS, mytmpdata1, yotmpdata2, 0);
    		LOGE("ret = %x", ret);
    		return ret;
    	}




#ifdef __cplusplus
}
#endif
#endif
