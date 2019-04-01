package com.orange.dlibandroid

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.SurfaceHolder
import kotlinx.android.synthetic.main.activity_camera.*
import android.app.Activity
import android.graphics.*
import android.hardware.Camera
import android.os.AsyncTask
import android.util.Log
import android.view.Surface
import com.orange.dlibandroid.detect.FaceDetecter
import com.orange.dlibandroid.detect.FaceRect
import java.io.ByteArrayOutputStream


class CameraActivity : AppCompatActivity(), SurfaceHolder.Callback, Camera.PreviewCallback {


    var camera: android.hardware.Camera? = null
    var mIsDetecting = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)

        initView()
    }


    private fun initView() {
        val surfaceHolder = surfaceView.holder
        surfaceHolder.addCallback(this)
    }


    override fun surfaceCreated(holder: SurfaceHolder?) {
        camera = android.hardware.Camera.open()

        setCameraDisplayOrientation(this, 0, camera!!)
        camera?.setAutoFocusMoveCallback { start, camera ->

        }
        camera?.setPreviewCallback(this)
        camera?.setPreviewDisplay(holder)
        camera?.startPreview()

    }

    override fun surfaceChanged(holder: SurfaceHolder?, format: Int, width: Int, height: Int) {
    }


    override fun surfaceDestroyed(holder: SurfaceHolder?) {
        holder?.removeCallback(this)
        camera?.setPreviewCallback(null)
        camera?.lock()
        camera?.stopPreview()
        camera?.release()
        camera=null
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

    override fun onPreviewFrame(data: ByteArray?, camera: Camera?) {
        if (!mIsDetecting){
            var size = camera?.getParameters()?.getPreviewSize() //获取预览大小
            val w = size?.width //宽度
            val h = size?.height
            val image = YuvImage(data, ImageFormat.NV21, w!!, h!!, null)
            var os = ByteArrayOutputStream()
            if(!image.compressToJpeg(Rect(0, 0, w, h), 100, os)){
                return
            }
            var tem=os.toByteArray()
            var bmp = BitmapFactory.decodeByteArray(tem, 0, tem.size)
            if (bmp!=null){
                bmp=rotateBitmap(bmp,90f)
                DetectTask().execute(bmp)
            }
        }
    }

    private inner class DetectTask : AsyncTask<Bitmap, Void, List<FaceRect>>() {

        override fun onPreExecute() {
            mIsDetecting = true
            super.onPreExecute()
        }

        override fun doInBackground(vararg bp: Bitmap): List<FaceRect> {
            val results: List<FaceRect>
            results = FaceDetecter.face_detection(bp[0]).asList()
            return results
        }

        override fun onPostExecute(results: List<FaceRect>) {
            coverView.setResults(results)
            mIsDetecting = false
        }
    }


    /**
     * 选择变换
     *
     * @param origin 原图
     * @param alpha  旋转角度，可正可负
     * @return 旋转后的图片
     */
    fun rotateBitmap(origin: Bitmap?, alpha: Float): Bitmap? {
        if (origin == null) {
            return null
        }
        val width = origin.width
        val height = origin.height
        val matrix = Matrix()
        matrix.setRotate(alpha)
        // 围绕原地进行旋转
        val newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false)
        if (newBM == origin) {
            return newBM
        }
        origin.recycle()
        return newBM
    }


}
