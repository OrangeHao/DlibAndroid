package com.orange.dlibandroid

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.Toast
import com.orange.dlibandroid.camera2.Camera2Activity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dlib_act_btn.setOnClickListener {
            var rxPermissions = RxPermissions(this)
            rxPermissions
                    .request(Manifest.permission.CAMERA)
                    .subscribe { granted ->
                        if (granted) {
                            startActivity(Intent(this,CameraActivity::class.java))
                        }
                    }
        }

        camera2_act.setOnClickListener {
            var rxPermissions = RxPermissions(this)
            rxPermissions
                    .request(Manifest.permission.CAMERA)
                    .subscribe { granted ->
                        if (granted) {
                            startActivity(Intent(this, Camera2Activity::class.java))
                        }
                    }
        }

        Test.TestInteger()


        test()
    }


    private fun test(){
        val bitmap=BitmapFactory.decodeResource(resources,R.drawable.gyy).copy(Bitmap.Config.ARGB_8888, true)
        Test.setBitmap(bitmap)
        faceImg.setImageBitmap(bitmap)
    }





}
