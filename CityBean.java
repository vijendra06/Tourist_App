package com.mplus.uvish.tourist;

/**
 * Created by uvish on 7/13/2017.
 */

public class CityBean {
    private String cityid,city;

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
    public String toString()
    {
        return cityid+" "+city;
    }
}
