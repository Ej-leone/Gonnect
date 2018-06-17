#ifndef _SYMBOL_H_
#define _SYMBOL_H_



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

#define  FF_CONVERT_FPT_TO_FMR2005_CODE			0x0143
#define  FF_CONVERT_FPT_TO_FMR2011_CODE			0x0145

#define 	FF_CONVERT_IMG_TO_WSQ_CODE	    0x0202  //not implented in device
#define 	FF_CONVERT_WSQ_TO_IMG_CODE	    0x0203  //not implented in device


#define  FF_CAPTURE_IMAGE_CODE        0x0300



#define 	FF_VERIFY_GA_TMPL_CODE			0x3001




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



#define		GD_NO_DETECT_FINGER				0x01
#define		GD_DETECT_FINGER				0x00
#define		GD_TEMPLATE_NOT_EMPTY			0x01
#define		GD_TEMPLATE_EMPTY				0x00
/////////////////////////////////////////////////////////////////////

#define 	GD_USER_RECORD_SIZE				(512)

UINT8  CommandProc( UINT16 p_wCmdCode, FULL_UINT p_nParam1, FULL_UINT p_nParam2, FULL_UINT p_nParam3 );


#endif  //_SYMBOL_H_