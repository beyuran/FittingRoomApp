package com.yuran.fittingroomapp;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewFrame;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.android.CameraBridgeViewBase;
import org.opencv.android.CameraBridgeViewBase.CvCameraViewListener2;
import org.opencv.imgproc.Imgproc;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URLEncoder;

public class DetectCamera extends Activity implements CvCameraViewListener2 {

    private static double ADD_SCALE = 1.117;

    private static Boolean DEBUG_MODE = false;
    private static Boolean SHOW_FRAME = true;

    public final static String EXTRA_VIEW_TYPE = "com.yuran.fittingroomapp.EXTRA_OUTPUT";

    private static final Scalar    SHEET_RECT_COLOR     = new Scalar(0, 255, 0, 255);
    private static final Scalar    FOOT_RECT_COLOR     = new Scalar(255, 0, 0, 255);

    private Detection mDetection = new Detection();

    private int view_type = Detection.VIEW_SIDE;

    private Mat                    mRgba;
    private Mat                    mGray;

    Rect foot;
    Rect sheet;

    private boolean freeze = false;

    private CameraBridgeViewBase   mOpenCvCameraView;

    private BaseLoaderCallback  mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                case LoaderCallbackInterface.SUCCESS:
                {
                    mOpenCvCameraView.enableView();
                } break;
                default:
                {
                    super.onManagerConnected(status);
                } break;
            }
        }
    };

    public DetectCamera() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.detect_camera_view);

        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.detect_surface_view);
        mOpenCvCameraView.setCvCameraViewListener(this);

        Intent intent = getIntent();
        view_type = intent.getIntExtra(EXTRA_VIEW_TYPE, Detection.VIEW_NONE);
    }

    @Override
    public void onPause()
    {
        super.onPause();
        if (mOpenCvCameraView != null)
            mOpenCvCameraView.disableView();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            /* Internal OpenCV library not found. Using OpenCV Manager for initialization */
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            /* OpenCV library found inside package. Using it! */
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        mOpenCvCameraView.disableView();
    }

    public void onCameraViewStarted(int width, int height) {
        mGray = new Mat();
        mRgba = new Mat();
    }

    public void onCameraViewStopped() {
        mGray.release();
        mRgba.release();
    }

    public Mat onCameraFrame(CvCameraViewFrame inputFrame) {
        if(!freeze) mRgba = inputFrame.rgba();
        mGray = inputFrame.gray();

        Rect frame = mDetection.drawSheet(mRgba,view_type);
        foot = mDetection.footDetect(mRgba,view_type);
        sheet = mDetection.sheetDetect(mRgba, view_type);

        if(DEBUG_MODE && freeze){
            Scalar fontColor = new Scalar(0, 255, 0);
            Point fontPoint = new Point();
            fontPoint.x = 15;
            fontPoint.y = 200;

            if(foot!=null && sheet != null)
                if(sheet.width!=0.0)
                {
                    Detection.length = 297.0/sheet.width  * foot.width * ADD_SCALE;
                    Detection.width = 297.0/sheet.width  * foot.height;
                }

            Imgproc.putText(mRgba,String.valueOf(Detection.length),
                    fontPoint, Core.FONT_HERSHEY_PLAIN, 5, fontColor,
                    2, Core.LINE_AA, false);
            Imgproc.putText(mRgba,String.valueOf(Detection.width),
                    new Point(15,250), Core.FONT_HERSHEY_PLAIN, 5, fontColor,
                    2, Core.LINE_AA, false);

            return mRgba;
        }

        if(DEBUG_MODE  || SHOW_FRAME){//DEBUG_MODE
            if(sheet!=null)
                Imgproc.rectangle(mRgba, sheet.tl(), sheet.br(), SHEET_RECT_COLOR, 3);
            if(foot != null)
                Imgproc.rectangle(mRgba, foot.tl(), foot.br(), FOOT_RECT_COLOR, 3);
        }

        Imgproc.rectangle(mRgba, frame.tl(), frame.br(), new Scalar(0, 0, 0), 3);

        //return mDetection.primaryProcessing(mRgba);

        return mRgba;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return true;
    }

    public void takeSnapshot(View view) {
        freeze = !freeze;
        if(!DEBUG_MODE)
        {
            if(foot!=null && sheet != null)
                if(sheet.width!=0.0)
                {
                    if(view_type == Detection.VIEW_SIDE)
                        Detection.length = 297.0/sheet.width  * foot.width * ADD_SCALE;
                    else if (view_type == Detection.VIEW_TOP)
                        Detection.width = 297.0/sheet.width  * foot.height;
                }

            Intent intent = new Intent();
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
