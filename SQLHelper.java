package com.mplus.uvish.tourist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by uvish on 7/1/2017.
 */

public class SQLHelper extends SQLiteOpenHelper
{
    Context context;

    public SQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(SQLConstants.CREATEQUERY_PLACE);

        Toast.makeText(context,"PLACE Table Created",Toast.LENGTH_LONG).show();
        db.execSQL(SQLConstants.CREATEQUERY_CITY);

        Toast.makeText(context,"CITY Table Created",Toast.LENGTH_LONG).show();



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}