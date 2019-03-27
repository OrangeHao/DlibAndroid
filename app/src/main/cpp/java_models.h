//
// Created by chenzihao on 2019/3/27.
//

#ifndef DLIBANDROID_JAVA_MODELS_H
#define DLIBANDROID_JAVA_MODELS_H


#include <jni.h>

#define CLASSNAME_RESULT_FACERECT "com/orange/dlibandroid/detect/FaceRect"

class J_Model_FaceRect {

private:
    jfieldID jID_left;
    jfieldID jID_top;
    jfieldID jID_right;
    jfieldID jID_bottom;

public:

    J_Model_FaceRect(JNIEnv *env) {
        jclass faceRectClass = env->FindClass(CLASSNAME_RESULT_FACERECT);
        jID_left = env->GetFieldID(faceRectClass,"left","I");
        jID_top = env->GetFieldID(faceRectClass,"top","I");
        jID_right = env->GetFieldID(faceRectClass,"right","I");
        jID_bottom = env->GetFieldID(faceRectClass,"bottom","I");
    }

    void setValues(JNIEnv *env,jobject &jFaceRect,
            const int &left,const int &top,const int &right,const int &bottom){
        env->SetIntField(jFaceRect,jID_left,left);
        env->SetIntField(jFaceRect,jID_top,top);
        env->SetIntField(jFaceRect,jID_right,right);
        env->SetIntField(jFaceRect,jID_bottom,bottom);
    }

    static jobject createJobject(JNIEnv *env){
        jclass faceRectClass=env->FindClass(CLASSNAME_RESULT_FACERECT);
        jmethodID mId=env->GetMethodID(faceRectClass,"<init>","()V");
        return env->NewObject(faceRectClass,mId);
    }

    static jobjectArray createJObjcetArray(JNIEnv *env, const int &size){
        jclass faceRectClass=env->FindClass(CLASSNAME_RESULT_FACERECT);
        return (jobjectArray)env->NewObjectArray(size,faceRectClass,NULL);
    }

};


#endif //DLIBANDROID_JAVA_MODELS_H
