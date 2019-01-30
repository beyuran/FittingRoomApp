package com.yuran.fittingroomapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.app.Activity;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FootSide extends Activity {

    static String image_file = "temp_side.jpg";
    ImageView viewImage;
    FloatingActionButton b;
    Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foot_side);
        b=(FloatingActionButton)findViewById(R.id.btnSelectPhoto);
        viewImage=(ImageView)findViewById(R.id.viewImage);
        FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.btnSideDone);
        if(ShoeSize.isValidLength(Detection.length)) {
            fab.show();
            viewImage.setImageResource(R.drawable.foot_side_checked);
        }else {
            fab.hide();
            viewImage.setImageResource(R.drawable.foot_side);
        }
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds options to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void selectImage() {

        Intent intent = new Intent(this,DetectCamera.class);
        intent.putExtra(DetectCamera.EXTRA_VIEW_TYPE, Detection.VIEW_SIDE);
        startActivityForResult(intent, 1);

        /* Build dialog to choose options: "Take Photo", "Choose from Gallery" , "Cancel"

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(FootSide.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), image_file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
                    startActivityForResult(intent,1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);

                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                if(data != null) {
                    viewImage.setImageResource(R.drawable.foot_side_checked);
                    FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.btnSideDone);
                    if(ShoeSize.isValidLength(Detection.length)) {
                        fab.show();
                        viewImage.setImageResource(R.drawable.foot_side_checked);
                    }else {
                        fab.hide();
                        viewImage.setImageResource(R.drawable.foot_side);
                        Toast.makeText(getApplicationContext(),"Please, try again to take a photo", Toast.LENGTH_LONG).show();
                    }
                }
            } else if (requestCode == 2) {

                Uri selectedImage = data.getData();
                String[] filePath = { MediaStore.Images.Media.DATA };
                Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                String picturePath = c.getString(columnIndex);
                c.close();

                BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
                bitmapOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
                Bitmap bitmap = BitmapFactory.decodeFile(picturePath,bitmapOptions);
                //Detection detection = new Detection();
                //viewImage.setImageBitmap(detection.footDetect(bitmap,0));
                viewImage.setImageResource(R.drawable.foot_side_checked);
            }
        }
    }

    public void sideDone(View view) {
        Intent intent = new Intent(this, FootTop.class);
        startActivity(intent);
    }

    public void sideBack(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}