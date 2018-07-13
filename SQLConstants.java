package com.mplus.uvish.tourist.db;

/**
 * Created by uvish on 7/1/2017.
 */

public class SQLConstants
{
    public static final String DB_NAME="Tourist";
    public static final int DB_VER=1;

    public static final String TBL_NAMEC="CITY";
    public static final String COL_NAME="CITYNAME";
    public static final String COL_ID="CITYID";
    //CREATE TABLE "City" ("CITY_ID" TEXT, "CITY_NAME" TEXT)
   // public static  final String CREATECITYQUERY="create table "+TBL_NAME1+"("+COL_ID+" text,"+COL_NAME+" text)";
    public static final String CREATEQUERY_CITY="create table "+TBL_NAMEC+"("+COL_ID+" text,"+COL_NAME+" text)";


    public static final String TBL_NAMEP="Places";
    public static final String PLACE_ID="PlaceID";
    public static final String PLACE_NAME="PlaceName";
    public static final String CITY_ID="CityID";
    public static final String DESCRIPTION="Description";
    public static final String CREATOR="Creator";
    public static final String PICTURE="Picture";
    public static final String CREATEQUERY_PLACE="create table "+TBL_NAMEP+" ("+PLACE_ID+" text,"+PLACE_NAME+" text,"+CITY_ID+" text,"+DESCRIPTION+" text,"+CREATOR+" text,"+PICTURE+" blob)";





}
