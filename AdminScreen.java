package com.mplus.uvish.tourist;

import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mplus.uvish.tourist.db.SQLConstants;
import com.mplus.uvish.tourist.db.SQLManager;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;

import javax.xml.transform.URIResolver;

public class AdminScreen extends AppCompatActivity {
    SQLManager sm;
    SQLiteDatabase sb;
    CityBean cb;
    PlaceBean pb;
    ArrayList<CityBean> citylist;
    ArrayList<PlaceBean> placelist;
    ImageView iv;
    Bitmap bm;
    LinearLayout layout;
    ImageButton ib;
    byte[] imgarr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_screen);

        sm = new SQLManager(this);
        sb = sm.openDB();

        citylist = new ArrayList<>();
        placelist =new ArrayList<>();



    }

    public void filllist() {

        Cursor c = sb.query(SQLConstants.TBL_NAMEC, null, null, null, null, null, null);
        if ((c != null) && (c.moveToFirst())) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())      //i.e while table has records (NBT 120)
            {
                String id = c.getString(c.getColumnIndex(SQLConstants.COL_ID));
                String name = c.getString(c.getColumnIndex(SQLConstants.COL_NAME));
                cb = new CityBean();
                cb.setCityid(id);
                cb.setCity(name);
                citylist.add(cb);

//                HashSet hs=new HashSet();
//                hs.addAll(citylist);
//                citylist.clear();
//                citylist.addAll(hs);



            }
        }
        c.close();

    }
    public void fillplacelist() {

        Cursor c = sb.query(SQLConstants.TBL_NAMEP, null, null, null, null, null, null);
        if ((c != null) && (c.moveToFirst())) {
            for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())      //i.e while table has records (NBT 120)
            {
                String id = c.getString(c.getColumnIndex(SQLConstants.PLACE_ID));
                String name = c.getString(c.getColumnIndex(SQLConstants.PLACE_NAME));
                String ctid=c.getString(c.getColumnIndex(SQLConstants.CITY_ID));
                pb = new PlaceBean();
                pb.setPlaceid(id);
                pb.setPlacename(name);
                pb.setCityid(ctid);
                placelist.add(pb);

//                HashSet hs=new HashSet();
//                hs.addAll(citylist);
//                citylist.clear();
//                citylist.addAll(hs);



            }
        }
        c.close();

    }

    public void addcity(View v) {
        AlertDialog.Builder adb = new AlertDialog.Builder(this);  //default style alert Dialog Box
        adb.setTitle("ADD CITY");
        adb.setIcon(R.drawable.addp);

        //custom layout in dialog
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        final EditText ct = new EditText(this);
        ct.setHint("City Name");
        final EditText ctid = new EditText(this);
        ctid.setHint("City ID");
        layout.addView(ct);
        layout.addView(ctid);
        adb.setView(layout);
        //custom layout

        adb.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String city = ct.getText().toString();
                String cityid = ctid.getText().toString();
                ContentValues cv = new ContentValues();
                cv.put(SQLConstants.COL_NAME, city);
                cv.put(SQLConstants.COL_ID, cityid);
                long l = sb.insert(SQLConstants.TBL_NAMEC, null, cv);
                if (l > 0) {
                    Toast.makeText(AdminScreen.this, "City Added !", Toast.LENGTH_SHORT).show();
                    ct.setText("");
                    ctid.setText("");
                }
            }
        }); //making local anonymous inner class for handling button click events
        adb.setNeutralButton("CANCEL", null);
        AlertDialog add = adb.create();
        add.show();
    }

    public void addplace(View v) {
        filllist();
        AlertDialog.Builder adb = new AlertDialog.Builder(this);  //default style alert Dialog Box
        adb.setTitle("ADD PLACE");
        adb.setIcon(R.drawable.addp);

        //custom layout in dialog
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setVerticalScrollBarEnabled(true);
        layout.setScrollContainer(true);


        final AutoCompleteTextView actv = new AutoCompleteTextView(this);
        actv.setHint("Select City");
        actv.setMaxLines(1);
        actv.setSingleLine(true);
        final EditText ct = new EditText(this);
        ct.setHint("Place Name");
        final EditText ctid = new EditText(this);
        ctid.setHint("Place ID");
        final EditText desc = new EditText(this);
        desc.setHint("Descrption");
        desc.setMaxLines(20);
        final EditText creator = new EditText(this);
        creator.setHint("Creator");

        LinearLayout innerLayout = new LinearLayout(this);
        innerLayout.setOrientation(LinearLayout.HORIZONTAL);
        innerLayout.setGravity(Gravity.CENTER);
        final TextView tv = new TextView(this);
        tv.setText("Pick Photo");
        ib = new ImageButton(this);
        ib.setImageResource(R.drawable.pickphoto);

       ib.setOnClickListener(new View.OnClickListener() {
                                  @Override
                                  public void onClick(View v) {

                                      Intent pickPhoto = new Intent(Intent.ACTION_PICK);
                                         pickPhoto.setType("image/*");
                                      startActivityForResult(pickPhoto,1);//one can be replaced with any action code

                                  }

                              }


        );

        innerLayout.addView(tv);
        innerLayout.addView(ib);


        ArrayAdapter<CityBean> ad = new ArrayAdapter<CityBean>(this, android.R.layout.simple_list_item_1, citylist);
        actv.setAdapter(ad);
        actv.setInputType(0);
        actv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) actv.showDropDown();
            }
        });


        layout.addView(actv);
        layout.addView(ct);
        layout.addView(ctid);
        layout.addView(creator);
        layout.addView(innerLayout);
        layout.addView(desc);


        adb.setView(layout);
        //custom layout

        adb.setPositiveButton("OKAY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


             //bm=Bitmap.createBitmap(ib.getWidth(),ib.getHeight(),Bitmap.Config.ARGB_8888);   // converting ib image button to bitmap
//                ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                bm.compress(Bitmap.CompressFormat.PNG, 100, bos);
//                byte[] imgarr = bos.toByteArray();



                String cityinfo = actv.getText().toString();
                int n = cityinfo.indexOf(" ");
                String cityid = cityinfo.substring(0, n);
                String place = ct.getText().toString();
                String placeid = ctid.getText().toString();
                String description = desc.getText().toString();
                String cretr=creator.getText().toString();



                ContentValues cv = new ContentValues();
                cv.put(SQLConstants.PLACE_NAME, place);
                cv.put(SQLConstants.PLACE_ID, placeid);
                cv.put(SQLConstants.CITY_ID, cityid);
                cv.put(SQLConstants.DESCRIPTION, description);
                cv.put(SQLConstants.CREATOR,cretr);
                cv.put(SQLConstants.PICTURE,imgarr);


                long l = sb.insert(SQLConstants.TBL_NAMEP, null, cv);
                if (l > 0) {
                    Toast.makeText(AdminScreen.this, "Place Added !", Toast.LENGTH_SHORT).show();
                    ct.setText("");
                    ctid.setText("");
                    actv.setText("");
                    desc.setText("");
                    creator.setText("");
                }
            }
        }); //making local anonymous inner class for handling button click events
        adb.setNeutralButton("CANCEL", null);
        AlertDialog acd = adb.create();
        acd.show();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

              if(requestCode == 1)
              {
                  Uri image=data.getData();
                  String[] filepathColumn={MediaStore.Images.Media.DATA};
                  Cursor cursor=getContentResolver().query(image,filepathColumn,null,null,null);
                  cursor.moveToFirst();

                  int columnIndex=cursor.getColumnIndex(filepathColumn[0]);
                  String filepath=cursor.getString(columnIndex);
                  cursor.close();

                  Bitmap selectedImage=BitmapFactory.decodeFile(filepath);
                  ib.setImageBitmap(selectedImage);
                  ByteArrayOutputStream bos=new ByteArrayOutputStream();
                  selectedImage.compress(Bitmap.CompressFormat.JPEG,100,bos);
                  imgarr=bos.toByteArray();

              }

          }

    public void deletecity(View v)
    {   filllist();
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);  //default style alert Dialog Box
        adb.setTitle("City To Delete");
        adb.setIcon(R.drawable.delp);
        adb.setMessage("*Places in the city will also be deleted..Are you sure ??");

        //custom layout in dialog
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);


        final AutoCompleteTextView actv = new AutoCompleteTextView(this);
        actv.setHint("Select City");
        actv.setMaxLines(1);
        actv.setSingleLine(true);
        ArrayAdapter<CityBean> ad = new ArrayAdapter<CityBean>(this, android.R.layout.simple_list_item_1, citylist);
        actv.setAdapter(ad);
        actv.setInputType(0);
        actv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) actv.showDropDown();
            }
        });
        layout.addView(actv);
        adb.setView(layout);




        adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                                String cityinfo = actv.getText().toString();
                                final int n=cityinfo.indexOf(" ");
                                String cityname=cityinfo.substring(0,n);
                                final String[]argss={cityname};

                            int rw = sb.delete(SQLConstants.TBL_NAMEC, SQLConstants.COL_ID + "=?",argss);
                                int rww=sb.delete(SQLConstants.TBL_NAMEP, SQLConstants.CITY_ID + "=?",argss);
                                if ((rw > 0)  &&  (rww > 0))
                                    Toast.makeText(AdminScreen.this, "Record Deleted", Toast.LENGTH_SHORT).show();
                            }
                        });


        adb.setNeutralButton("CANCEL",null);
        AlertDialog delcity=adb.create();
        delcity.show();
    }


    public void deleteplace(View v)
    {   fillplacelist();
        final AlertDialog.Builder adb = new AlertDialog.Builder(this);  //default style alert Dialog Box
        adb.setTitle("Place To Delete");
        adb.setIcon(R.drawable.delp);
        adb.setMessage("*Places will be deleted..Are you sure ??");

        //custom layout in dialog
        layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);


        final AutoCompleteTextView actv = new AutoCompleteTextView(this);
        actv.setHint("Select Place");
        actv.setMaxLines(1);
        actv.setSingleLine(true);
        ArrayAdapter<PlaceBean> ad = new ArrayAdapter<PlaceBean>(this, android.R.layout.simple_list_item_1, placelist);
        actv.setAdapter(ad);
        actv.setInputType(0);
        actv.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) actv.showDropDown();
            }
        });
        layout.addView(actv);
        adb.setView(layout);




        adb.setPositiveButton("YES", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                String placeinfo = actv.getText().toString();
                final int n=placeinfo.indexOf(" ");
                String placeid=placeinfo.substring(0,n);
                final String[]argss={placeid};

                int rw = sb.delete(SQLConstants.TBL_NAMEP, SQLConstants.PLACE_ID + "=?",argss);
                if (rw > 0)
                    Toast.makeText(AdminScreen.this, "Record Deleted", Toast.LENGTH_SHORT).show();
            }
        });


        adb.setNeutralButton("CANCEL",null);
        AlertDialog delcity=adb.create();
        delcity.show();
    }
}