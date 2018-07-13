package com.mplus.uvish.tourist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by uvish on 7/1/2017.
 */

public class SQLManager {
    Context context;
    SQLHelper sqlHelper;
    SQLiteDatabase sb;

    public SQLManager(Context context)
    {

        this.context=context;
        sqlHelper=new SQLHelper
                (context,SQLConstants.DB_NAME,null,SQLConstants.DB_VER);
    }

    public SQLiteDatabase openDB()
    {
        sb= sqlHelper.getWritableDatabase();
        return  sb;

    }


    public  void closeDB()
    {
        sqlHelper.close();
    }


}
