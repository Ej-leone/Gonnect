#include "android/log.h"
//static const char *TAG= __FUNCTION__;
#define LOGI(fmt, args...) __android_log_print(ANDROID_LOG_INFO,  __FUNCTION__, fmt, ##args)
#define LOGD(fmt, args...) __android_log_print(ANDROID_LOG_DEBUG, __FUNCTION__, fmt, ##args)
#define LOGW(fmt, args...) __android_log_print(ANDROID_LOG_WARN, __FUNCTION__, fmt, ##args)
#define LOGE(fmt, args...) __android_log_print(ANDROID_LOG_ERROR, __FUNCTION__, fmt, ##args)
