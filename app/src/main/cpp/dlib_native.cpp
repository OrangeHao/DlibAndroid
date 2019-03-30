
//
// Created by chenzihao on 2019/3/25.
//

#include <jni.h>
#include <string>
#include <vector>
#include <iostream>
#include <android/log.h>


#include "java_models.h"
#include "dlib/image_io.h"
#include "dlib/image_processing.h"
#include "dlib/image_processing/frontal_face_detector.h"

#include <ctime>


#define TAG    "czh" // 这个是自定义的LOG的标识
#define LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__) // 定义LOGD类型

J_Model_FaceRect *model_faceRect;


JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved){
    JNIEnv *env;
    vm->GetEnv((void **)&env,JNI_VERSION_1_6);
    model_faceRect=new J_Model_FaceRect(env);
    return JNI_VERSION_1_6;
}

void JNI_OnUnload(JavaVM *vm, void *reserved){
    delete model_faceRect;
}



jobjectArray getFaceModels(JNIEnv *env, std::vector<dlib::rectangle> faceDetector, const int &size) {
    jobjectArray jDetRetArray = J_Model_FaceRect::createJObjcetArray(env, size);
    for (int i = 0; i < size; i++) {
        jobject jDetRet = J_Model_FaceRect::createJobject(env);
        env->SetObjectArrayElement(jDetRetArray, i, jDetRet);

        model_faceRect->setValues(env, jDetRet, faceDetector[i].left(), faceDetector[i].top(),
                                  faceDetector[i].right(), faceDetector[i].bottom());
    }
    return jDetRetArray;
}




dlib::frontal_face_detector m_facedetector=dlib::get_frontal_face_detector();
//获取人脸框
jobjectArray getFaceResult(JNIEnv *env, const std::vector<int>img,int height,int width)
{
    dlib::array2d<unsigned char>image;
    image.set_size(height,width);

    clock_t t1 = clock();
    for (int i = 0; i < height; i++)
    {
        for(int j=0;j<width;j++)
        {
            int clr = img[i*width+j];
            int red = (clr & 0x00ff0000) >> 16; // 取高两位
            int green = (clr & 0x0000ff00) >> 8; // 取中两位
            int blue = clr & 0x000000ff; // 取低两位
            unsigned char gray=red*0.299+green*0.587+blue*0.114;
            //dlib::rgb_pixel pt(red,green,blue);
            image[i][j]=gray;
        }
    }
    clock_t t2 = clock();
    LOGD("bitmap to gray cost time: %d ms",(t2-t1)*1000/CLOCKS_PER_SEC);

    clock_t begin = clock();
    std::vector<dlib::rectangle> dets= m_facedetector(image);
    clock_t end = clock();
    LOGD("dlib dectet  cost time: %d ms",(end-begin)*1000/CLOCKS_PER_SEC);

    LOGD("face detaect result: %d",dets.size());

    return getFaceModels(env,dets,dets.size());

}


extern "C"
JNIEXPORT jobjectArray JNICALL
Java_com_orange_dlibandroid_detect_FaceDetecter_jniFaceDetect(JNIEnv *env, jclass type,
                                                              jintArray pixels_, jint height,
                                                              jint width) {
    clock_t begin = clock();

    jsize len = env->GetArrayLength(pixels_);
    jint *body = env->GetIntArrayElements(pixels_, 0);
    std::vector<int> image_datacpp(height * width);
    for (jsize i = 0; i < len; i++) {
        image_datacpp[i] = (int) body[i];
    }

    clock_t end = clock();
    LOGD("jni start cost time: %d ms",(end-begin)*1000/CLOCKS_PER_SEC);

    return getFaceResult(env,image_datacpp, height, width);
}