package com.orange.dlibandroid;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.util.Log;

import com.orange.dlibandroid.detect.FaceDetecter;
import com.orange.dlibandroid.detect.FaceRect;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * created by czh on 2019/3/21
 */
public class Test {


    public static void TestInteger(){
        Integer i1 = 33;
        Integer i2 = 33;
        if (i1==i2){
            Log.d("czh","true");
        }else {
            Log.d("czh","false");
        }
        Integer i11 = 333;
        Integer i22 = 333;
        if (i11==i22){
            Log.d("czh","true");
        }else {
            Log.d("czh","false");
        }

        try {
            int a1=12345678;
            byte[] array=new byte[4];
            ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
            ObjectOutputStream objectOutputStream=new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeInt(a1);
            array=byteArrayOutputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int[] face_detection(Bitmap origin_image){
        float scale = 240.f/ Math.max(origin_image.getHeight(), origin_image.getWidth());
        int width = (int)(origin_image.getWidth()*scale);
        int height = (int)(origin_image.getHeight()*scale);
        Bitmap resize_image=Bitmap.createScaledBitmap(origin_image,width,height , false);

        // 保存所有的像素的数组，图片宽×高
        int[] pixels = new int[width * height];

        resize_image.getPixels(pixels, 0, width, 0, 0, width, height);
        FaceRect[] rect= FaceDetecter.jniFaceDetect(pixels,height,width);
        Log.d("czh","faces:"+rect.length);
        if (rect.length==0){
            return null;
        }
        FaceRect rect1=rect[0];
        int[] result_rect=new int[4];
        result_rect[0]=(int)(rect1.getLeft()/scale);
        result_rect[1]=(int)(rect1.getTop()/scale);
        result_rect[2]=(int)(rect1.getRight()/scale);
        result_rect[3]=(int)(rect1.getBottom()/scale);
        result_rect[2]=result_rect[2]+result_rect[0];
        result_rect[3]=result_rect[3]+result_rect[1];
        return  result_rect;
    }

    public static int[] face_detection2(Bitmap origin_image){
        int width = origin_image.getWidth();
        int height = origin_image.getHeight();
        int[] pixels = new int[width * height];
        origin_image.getPixels(pixels, 0, width, 0, 0, width, height);

        long startTime=System.currentTimeMillis();
        FaceRect[] rect= FaceDetecter.jniFaceDetect(pixels,height,width);
        long costTime=(System.currentTimeMillis()-startTime);
        Log.d("czh","back from jni cost time:"+costTime+" ms");
        Log.d("czh","faces:"+rect.length);
        if (rect.length==0){
            return null;
        }
        FaceRect rect1=rect[0];
        int[] result_rect=new int[4];
        result_rect[0]=(rect1.getLeft());
        result_rect[1]=(rect1.getTop());
        result_rect[2]=(rect1.getRight());
        result_rect[3]=(rect1.getBottom());
        return  result_rect;
    }

    public static void setBitmap(Bitmap origin){
        int[] rect=face_detection2(origin);
        if (rect==null){
            return;
        }
        Canvas canvas=new Canvas(origin);
        Paint p=new Paint();
        p.setColor(Color.RED);
        p.setStrokeWidth(3.0f);
        canvas.drawLine(rect[0], rect[1], rect[2], rect[1], p);//up
        canvas.drawLine(rect[0], rect[1], rect[0], rect[3], p);//left
        canvas.drawLine(rect[0], rect[3], rect[2], rect[3], p);//down
        canvas.drawLine(rect[2], rect[1], rect[2], rect[3], p);
    }



}
