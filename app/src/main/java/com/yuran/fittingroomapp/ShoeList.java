package com.yuran.fittingroomapp;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class ShoeList extends Activity{

    ListView list;
    String[] itemname ={
            "Shoes 35",
            "Shoes 36",
            "Shoes 37",
            "Shoes 38",
            "Shoes 39",
            "Shoes 40",
            "Shoes 41",
            "Shoes 42",
            "Shoes 43",
            "Shoes 44",
            "Shoes 45",
            "Shoes 46",
            "Shoes 47",
            "Shoes 48"
    };

    Integer[] imgid={
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6,
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
            R.drawable.pic6,
            R.drawable.pic1,
            R.drawable.pic2
    };

    Boolean[] checked={
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false,
            false
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoe_list);

        int index = (int) Math.round(ShoeSize.getSize(Detection.length, ShoeSize.EU, ShoeSize.MALE));
        checked[index-35] = true;

        CustomListAdapter adapter=new CustomListAdapter(this, itemname, imgid,checked);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                String Slecteditem = itemname[+position];
                Toast.makeText(getApplicationContext(), Slecteditem, Toast.LENGTH_SHORT).show();

            }
        });


    }
}