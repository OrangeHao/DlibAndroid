package com.orange.dlibandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import kotlinx.android.synthetic.main.activity_camera.*
import android.app.Activity
import android.view.Surface


class CameraActivity : AppCompatActivity(),SurfaceHolder.Callback {


    var camera:android.hardware.Camera?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        initView()
    }


    private fun initView(){
        val surfaceHolder=surfaceView.holder
        surfaceHolder.addCallback(this)
    }

    override fun surfaceCreated(holder: SurfaceHolder?) {
        camera=android.hardware.Camera.open()

        setCameraDisplayOrientation(this,0,camera!!)
        camera?.setAutoFocusMoveCallback { start, camera ->

        }
        camera?.setPreviewDisplay(holder)
        camera?.startPreview()

    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        camera?.stopPreview()
        camera?.release()
    }


    /**
     * 设置 摄像头的角度
     *
     * @param activity 上下文
     * @param cameraId 摄像头ID（假如手机有N个摄像头，cameraId 的值 就是 0 ~ N-1）
     * @param camera   摄像头对象
     */
    fun setCameraDisplayOrientation(activity: Activity,
                                    cameraId: Int, camera: android.hardware.Camera) {

        val info = android.hardware.Camera.CameraInfo()
        //获取摄像头信息
        android.hardware.Camera.getCameraInfo(cameraId, info)
        val rotation = activity.windowManager.defaultDisplay.rotation
        //获取摄像头当前的角度
        var degrees = 0
        when (rotation) {
            Surface.ROTATION_0 -> degrees = 0
            Surface.ROTATION_90 -> degrees = 90
            Surface.ROTATION_180 -> degrees = 180
            Surface.ROTATION_270 -> degrees = 270
        }

        var result: Int
        if (info.facing === android.hardware.Camera.CameraInfo.CAMERA_FACING_FRONT) {
            //前置摄像头
            result = (info.orientation + degrees) % 360
            result = (360 - result) % 360 // compensate the mirror
        } else {
            // back-facing  后置摄像头
            result = (info.orientation - degrees + 360) % 360
        }
        camera.setDisplayOrientation(result)
    }




}
