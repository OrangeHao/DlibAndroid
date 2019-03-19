//
// Created by chenzihao on 2019/2/27.
//

#include <jni.h>
#include <string>




extern "C"
JNIEXPORT jstring JNICALL
Java_com_orange_dlibandroid_MainActivity_getStringFromJni(JNIEnv *env, jclass type) {

    return env->NewStringUTF("hello world");
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_orange_dlibandroid_MainActivity_getTransteString(JNIEnv *env, jclass type,
                                                          jstring originStr_) {
    const char *originStr = env->GetStringUTFChars(originStr_, 0);

    // TODO

    env->ReleaseStringUTFChars(originStr_, originStr);

    return env->NewStringUTF("");
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_orange_dlibandroid_MainActivity_getTransteArray(JNIEnv *env, jclass type,
                                                         jintArray originArray_) {
    jint *originArray = env->GetIntArrayElements(originArray_, NULL);

    // TODO

    env->ReleaseIntArrayElements(originArray_, originArray, 0);

    return env->NewStringUTF("");
}