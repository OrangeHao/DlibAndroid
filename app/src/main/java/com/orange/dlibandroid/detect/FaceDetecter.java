package com.orange.dlibandroid.detect;

import android.graphics.Bitmap;
import android.util.Log;

import java.util.List;

/**
 * created by czh on 2019/3/26
 */
public class FaceDetecter {

    static {
        System.loadLibrary("dlib_native");
    }


    public static native FaceRect[] jniFaceDetect(int [] pixels,int height,int width);

    public static FaceRect[] face_detection(Bitmap origin_image){
        int width = origin_image.getWidth();
        int height = origin_image.getHeight();
        int[] pixels = new int[width * height];
        origin_image.getPixels(pixels, 0, width, 0, 0, width, height);

        long startTime=System.currentTimeMillis();
        FaceRect[] rect= FaceDetecter.jniFaceDetect(pixels,height,width);
        long costTime=(System.currentTimeMillis()-startTime);
        Log.d("czh","back from jni cost time:"+costTime+" ms");
        Log.d("czh","faces:"+rect.length);
        return rect;
    }

}
