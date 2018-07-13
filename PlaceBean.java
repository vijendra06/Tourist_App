package com.mplus.uvish.tourist;

/**
 * Created by uvish on 7/16/2017.
 */

public class PlaceBean {
    private String cityid,placeid,placename;

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getPlaceid() {
        return placeid;
    }

    public void setPlaceid(String placeid) {
        this.placeid = placeid;
    }

    public String getPlacename() {
        return placename;
    }

    public void setPlacename(String placename) {
        this.placename = placename;
    }
    public String toString()
    {
        return placeid+" "+cityid+" "+placename;
    }
}
