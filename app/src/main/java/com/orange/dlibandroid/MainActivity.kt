package com.orange.dlibandroid

import android.Manifest
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import com.orange.dlibandroid.camera2.Camera2Activity
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Toast.makeText(this, stringFromJni, Toast.LENGTH_LONG).show()

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
    }

    companion object {

        init {
            System.loadLibrary("test-lib")
        }

        val stringFromJni: String
            external get

        external fun getTransteString(originStr: String): String

        external fun getTransteArray(originArray: IntArray): String
    }


}
