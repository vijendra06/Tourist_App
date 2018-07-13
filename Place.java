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
import android.widget.Toast;

import com.mplus.uvish.tourist.db.SQLConstants;
import com.mplus.uvish.tourist.db.SQLManager;

import java.util.ArrayList;

public class Place extends AppCompatActivity implements AdapterView.OnItemClickListener {
    ListView lv;
    SQLManager sm;
    SQLiteDatabase sb;
    PlaceBean pb;
    ArrayList<PlaceBean> placelist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);
        lv=(ListView)findViewById(R.id.lv);
        sm=new SQLManager(this);
        sb=sm.openDB();
        placelist=new ArrayList<>();
        ArrayAdapter<PlaceBean> ad=new ArrayAdapter<PlaceBean>(this,android.R.layout.simple_list_item_1,placelist);
        lv.setAdapter(ad);
        lv.setOnItemClickListener(this);
        Intent i=getIntent();
       String cid=i.getStringExtra("id");
       filllist(cid);
    }
    public void filllist(String cid)
    {
        Cursor cp = sb.query(SQLConstants.TBL_NAMEP,null,null,null,null,null,null);
            if ((cp != null) && (cp.moveToFirst()))
            {
                for (cp.moveToFirst(); !cp.isAfterLast(); cp.moveToNext())      //i.e while table has records (NBT 120)
                {
                    String cityid = cp.getString(cp.getColumnIndex(SQLConstants.CITY_ID));
                    String placename = cp.getString(cp.getColumnIndex(SQLConstants.PLACE_NAME));
                    String placeid = cp.getString(cp.getColumnIndex(SQLConstants.PLACE_ID));
                    pb = new PlaceBean();
                    pb.setCityid(cityid);
                    pb.setPlacename(placename);
                    pb.setPlaceid(placeid);
                    if (cityid.equals(cid))
                    {
                        placelist.add(pb);
                    }

                }
            }
            cp.close();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String cb=placelist.get(position).getPlaceid();
        Intent details=new Intent(this,Details.class);
        details.putExtra("placeid",cb);
        startActivity(details);
    }
}

