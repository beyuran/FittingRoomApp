package com.yuran.fittingroomapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import org.opencv.android.BaseLoaderCallback;
import org.opencv.android.LoaderCallbackInterface;
import org.opencv.android.OpenCVLoader;

public class MainActivity extends AppCompatActivity {

    private static Boolean managerConnceted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);
        //ImageView imageViewSide = (ImageView) findViewById(R.id.imageView);
        //imageViewSide.setImageResource(R.drawable.foot_scetch_side);
        //ImageView imageViewTop = (ImageView) findViewById(R.id.imageView2);
        //imageViewTop.setImageResource(R.drawable.foot_scetch_top);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        if (!OpenCVLoader.initDebug()) {
            //Internal OpenCV library not found. Using OpenCV Manager for initialization;
            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_0_0, this, mLoaderCallback);
        } else {
            //OpenCV library found inside package. Using it!"
            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
        }
    }

    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
        @Override
        public void onManagerConnected(int status) {
            switch (status) {
                //Когда загрузились- запускаем очередь обработки изображений
                case LoaderCallbackInterface.SUCCESS:
                    managerConnceted = true;
                    break;
                default:
                    super.onManagerConnected(status);
                    break;
            }
        }
    };

    public void start(View view) {
        if(managerConnceted) {
            Intent intent = new Intent(this, FootSide.class);
            startActivity(intent);
        }
    }

    public void test(View view) {
        Intent intent = new Intent(this, DetectCamera.class);
        startActivity(intent);
    }

}
