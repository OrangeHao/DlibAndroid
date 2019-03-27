package com.orange.dlibandroid;

import android.util.Log;

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
}
