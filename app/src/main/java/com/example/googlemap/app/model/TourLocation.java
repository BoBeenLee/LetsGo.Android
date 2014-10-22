package com.example.googlemap.app.model;

import com.example.googlemap.app.util.TourUtil;

import java.io.Serializable;
import java.net.URLEncoder;

public class TourLocation implements Serializable {
    public static final String PARAM_TYPE = "locationBasedList";
    public static final String ADDR = "http://api.visitkorea.or.kr/openapi/service/rest/KorService/" + PARAM_TYPE + "?ServiceKey=";
    public static String SERVICE_KEY = null;

    static {
        try {
            SERVICE_KEY = URLEncoder.encode("lKOYPhTvXwcOTxwoTtX7ScJ0MvHKFqJBCjNvCgUofFPFKMK4nUJO4nHsqseMwMwa0YOp0HVYiuaR/EOZgAyRjw==", "UTF-8");
        } catch(Exception e){ }
    }

    //		LocationBaseList 기반 Param
    public static final String MOBILE_APP = "MobileApp=GoogleMap";
    public static final String MOBILE_TYPE = "MobileOS=AND";

    public static final String REST_TYPE = "_type=json";
    public static final String TOUR_ETC = "overviewYN=Y&defaultYN=Y";

    public String addr1;
    public String addr2;
    public String areacode;
    public String booktour;
    public String cat1;
    public String cat2;
    public String cat3;
    public String contentid;
    public String contenttypeid;
    public String createdtime;
    public String dist;
    public String firstimage;
    public String firstimage2;
    public String mapx;
    public String mapy;
    public String mlevel;
    public String modifiedtime;
    public String readcount;
    public String sigungucode;
//
    public String homepage;
    public String title;
    public String overview;


    public String getOverview() {
        return overview;
    }

    public String getAddr1() {
        return TourUtil.decodeStr(addr1);
    }

    public String getAddr2() {
        return addr2;
    }

    public String getAreacode() {
        return areacode;
    }

    public String getBooktour() {
        return booktour;
    }

    public String getCat1() {
        return cat1;
    }

    public String getCat2() {
        return cat2;
    }

    public String getCat3() {
        return cat3;
    }

    public String getContentid() {
        return contentid;
    }

    public String getContenttypeid() {
        return contenttypeid;
    }

    public String getCreatedtime() {
        return createdtime;
    }

    public String getDist() {
        return dist;
    }

    public String getFirstimage() {
        return firstimage;
    }

    public String getFirstimage2() {
        return firstimage2;
    }

    public String getMapx() {
        return mapx;
    }

    public String getMapy() {
        return mapy;
    }

    public String getMlevel() {
        return mlevel;
    }

    public String getModifiedtime() {
        return modifiedtime;
    }

    public String getReadcount() {
        return readcount;
    }

    public String getSigungucode() {
        return sigungucode;
    }

    public String getHomepage() {
        return homepage;
    }

    public String getTitle() {
        return TourUtil.decodeStr(title);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
