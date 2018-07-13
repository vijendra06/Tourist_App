package com.mplus.uvish.tourist;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Build;
import android.speech.tts.TextToSpeech;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mplus.uvish.tourist.db.SQLConstants;
import com.mplus.uvish.tourist.db.SQLManager;

import java.io.ByteArrayInputStream;
import java.util.Locale;

public class Details extends AppCompatActivity implements TextToSpeech.OnInitListener {
    TextView tv,tvname;
    ImageView iv;
    SQLManager sm;
    SQLiteDatabase sb;
    public byte[] ib;
    String placename,desc;
    TextToSpeech tts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent details=getIntent();
       String pid=details.getStringExtra("placeid");
        tv=(TextView)findViewById(R.id.tv);
        tvname=(TextView)findViewById(R.id.txtname);
        iv=(ImageView)findViewById(R.id.iv);
        sm=new SQLManager(this);
        sb=sm.openDB();
        fetchdetails(pid);
        tts=new TextToSpeech(this,this);

    }
    public void fetchdetails(String pid)
    {
        String[]args={pid};
        Cursor c=sb.query(SQLConstants.TBL_NAMEP,null,SQLConstants.PLACE_ID+"=?",args,null,null,null);
                if(c!=null && c.moveToFirst())
                {


                    placename=c.getString(c.getColumnIndex(SQLConstants.PLACE_NAME));
                    desc=c.getString(c.getColumnIndex(SQLConstants.DESCRIPTION));
                    ib=c.getBlob(c.getColumnIndex(SQLConstants.PICTURE));



                    c.close();

        }
        ByteArrayInputStream ba=new ByteArrayInputStream(ib);
        Bitmap b= BitmapFactory.decodeStream(ba);
        iv.setImageBitmap(b);
        tvname.setText(placename);
        tv.setMovementMethod(new ScrollingMovementMethod());
        tv.setText(desc);
    }
    @RequiresApi(api= Build.VERSION_CODES.LOLLIPOP)
    public void totts(View v)
    {
        if(tts!=null) tts.stop();
        else {
           tts.speak(desc, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    @Override
    public void onInit(int status) {
        int res=tts.setLanguage(Locale.ENGLISH);
        if(res==TextToSpeech.LANG_NOT_SUPPORTED || res==TextToSpeech.LANG_MISSING_DATA);
        Toast.makeText(this,"Language Not Supported", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.shutdown();
    }
}
