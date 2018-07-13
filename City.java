package com.mplus.uvish.tourist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mplus.uvish.tourist.db.SQLConstants;
import com.mplus.uvish.tourist.db.SQLManager;

import java.util.ArrayList;

public class City extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    SQLManager sm;
    SQLiteDatabase sb;
    CityBean cityBean;
    ArrayList<CityBean> citylist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        lv=(ListView)findViewById(R.id.lv);
        sm=new SQLManager(this);
        sb=sm.openDB();
        citylist=new ArrayList<>();
        filllist();
        ArrayAdapter<CityBean> ad=new ArrayAdapter<CityBean>(this,android.R.layout.simple_list_item_1,citylist);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(this);
    }
    public void filllist()
    {
        Cursor c=sb.query(SQLConstants.TBL_NAMEC,null,null,null,null,null,null);
        if((c!=null)&&(c.moveToFirst()))
        {
            for(c.moveToFirst();!c.isAfterLast();c.moveToNext())      //i.e while table has records (NBT 120)
            {
                String cityid=c.getString(c.getColumnIndex(SQLConstants.COL_ID));
                String cityname=c.getString(c.getColumnIndex(SQLConstants.COL_NAME));
                cityBean=new CityBean();
                cityBean.setCityid(cityid);
                cityBean.setCity(cityname);

                citylist.add(cityBean);

            }
        }
        c.close();

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    String cb=citylist.get(position).getCityid(); //CityBean ct=citylist.get(position);
        Intent places=new Intent(this,Place.class);
        places.putExtra("id",cb);
        startActivity(places);
    }
}

