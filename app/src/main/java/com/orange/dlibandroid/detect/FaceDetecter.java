package com.orange.dlibandroid.detect;

import java.util.List;

/**
 * created by czh on 2019/3/26
 */
public class FaceDetecter {

    static {
        System.loadLibrary("");
    }


    private static native FaceRect[] jniFaceDetect(int [] pixels,int height,int width);

}
