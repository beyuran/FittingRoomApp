package com.yuran.fittingroomapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class Info extends AppCompatActivity {

    Info master;
    int system_type;
    int sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        master = this;

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(master, View3d.class);
                startActivity(intent);
            }
        });

        updateInfo(getCurrentFocus());
    }

    public void infoBack(View view) {
        Intent intent = new Intent(this, FootTop.class);
        startActivity(intent);
    }

    public void updateInfo(View view) {

        TextView textView = (TextView)findViewById(R.id.textView);
        RadioButton radioFemale = (RadioButton)findViewById(R.id.radioFemale);
        RadioButton radioMale = (RadioButton)findViewById(R.id.radioMale);
        RadioButton radioEU = (RadioButton)findViewById(R.id.radioEU);
        RadioButton radioUS = (RadioButton)findViewById(R.id.radioUS);
        RadioButton radioUK = (RadioButton)findViewById(R.id.radioUK);
        RadioButton radioMode1 = (RadioButton)findViewById(R.id.radioMode1);

        if(radioFemale.isChecked())
            sex = ShoeSize.FEMALE;
        else
            sex = ShoeSize.MALE;

        if(radioEU.isChecked())
            system_type = ShoeSize.EU;
        else if(radioUS.isChecked())
            system_type = ShoeSize.US;
        else
            system_type = ShoeSize.UK;

        if(radioMode1.isChecked())
            textView.setText(getString(R.string.info_size) +" "+ ShoeSize.getSize(Detection.length,system_type,sex) + ShoeSize.ToString(system_type));
        else
            textView.setText(getString(R.string.info_size) +" "+ ShoeSize.getSize2(Detection.length,system_type,sex) + ShoeSize.ToString(system_type));
    }
}
