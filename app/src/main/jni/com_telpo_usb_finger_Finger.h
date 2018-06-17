/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_telpo_usb_finger_Finger */

#ifndef _Included_com_telpo_usb_finger_Finger
#define _Included_com_telpo_usb_finger_Finger
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    initialize
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_initialize
  (JNIEnv *, jclass);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    identify
 * Signature: ([I)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_identify
  (JNIEnv *, jclass, jintArray);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    enroll
 * Signature: (II[I)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_enroll
  (JNIEnv *, jclass, jint, jint, jintArray);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    clear_template
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_clear_1template
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    clear_alltemplate
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_clear_1alltemplate
  (JNIEnv *, jclass);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    get_empty_id
 * Signature: ([I)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_get_1empty_1id
  (JNIEnv *, jclass, jintArray);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    get_image
 * Signature: ([B)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_get_1image
  (JNIEnv *, jclass, jbyteArray);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    read_template
 * Signature: (I[B)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_read_1template
  (JNIEnv *, jclass, jint, jbyteArray);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    write_template
 * Signature: (I[B[I)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_write_1template
  (JNIEnv *, jclass, jint, jbyteArray, jintArray);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    store_ram
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_store_1ram
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    read_ram
 * Signature: (I[B)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_read_1ram
  (JNIEnv *, jclass, jint, jbyteArray);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    generate_ram
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_generate_1ram
  (JNIEnv *, jclass, jint, jint);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    update_tmpl
 * Signature: ([I)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_update_1tmpl
  (JNIEnv *, jclass, jintArray);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    verify_ISO_tmpl
 * Signature: ([B[B)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_verify_1ISO_1tmpl
  (JNIEnv *, jclass, jbyteArray, jbyteArray);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    verify_ISO2011_tmpl
 * Signature: ([B[B)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_verify_1ISO2011_1tmpl
  (JNIEnv *, jclass, jbyteArray, jbyteArray);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    convert_ISO_to_FPT
 * Signature: ([B[BI)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_convert_1ISO_1to_1FPT
  (JNIEnv *, jclass, jbyteArray, jbyteArray, jint);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    convert_ISO_to_FPT_local
 * Signature: ([B[BI)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_convert_1ISO_1to_1FPT_1local
  (JNIEnv *, jclass, jbyteArray, jbyteArray, jint);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    convert_FPT_to_ISO
 * Signature: ([B[B[I)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_convert_1FPT_1to_1ISO
  (JNIEnv *, jclass, jbyteArray, jbyteArray, jintArray);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    convert_FPT_to_ISO2011
 * Signature: ([B[B[I)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_convert_1FPT_1to_1ISO2011
  (JNIEnv *, jclass, jbyteArray, jbyteArray, jintArray);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    convert_IMG_to_WSQ
 * Signature: ([B[B[I)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_convert_1IMG_1to_1WSQ
  (JNIEnv *, jclass, jbyteArray, jbyteArray, jintArray);

/*
 * Class:     com_telpo_usb_finger_Finger
 * Method:    convert_WSQ_to_IMG
 * Signature: ([B[B[I)I
 */
JNIEXPORT jint JNICALL Java_com_telpo_usb_finger_Finger_convert_1WSQ_1to_1IMG
  (JNIEnv *, jclass, jbyteArray, jbyteArray, jintArray);

#ifdef __cplusplus
}
#endif
#endif
