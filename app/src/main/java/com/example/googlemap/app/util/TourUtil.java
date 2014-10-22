package com.example.googlemap.app.util;

import android.util.Log;

import com.example.googlemap.app.model.TourLocation;
import com.example.googlemap.app.model.TourSet;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by BoBinLee on 2014-07-09.
 */
public class TourUtil {
    public static final int TOUR_COURSE = 25;
    public static final int TOUR_CULTURE = 14;
    public static final int TOUR_TRAVEL = 12;
    public static final int TOUR_FOOD = 39;

    public static List<TourLocation> getTourGson(URI param) {
        Gson gson = new Gson();
        TourSet result = null;

        JSONObject jsonObject = getJson(param, "response");
        try {
            JSONObject jsonChk = jsonObject.getJSONObject("body");

            if(jsonChk.getString("items").equals(""))
                return new ArrayList<TourLocation>();
        } catch(Exception e){
            e.printStackTrace();
        }
        result = gson.fromJson(jsonObject.toString(), TourSet.class);

        return result.body.items.item;
    }

    public static JSONObject getJson(URI param, String name) {
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet(param);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            String entity = EntityUtils.toString(httpResponse.getEntity());
            JSONObject jsonObject = new JSONObject(entity).getJSONObject(name);
            return jsonObject;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static URI getParam(double pMapX, double pMapY, int pRadius, int pContentTypeId) {
//		LocationBaseList 기반 Param
        String addr = "";
        String mapX = "mapX=" + pMapX;
        String mapY = "mapY=" + pMapY;
        String radius = "radius=" + pRadius;
        String contentTypeId = "contentTypeId=" + pContentTypeId;
        //인증키(서비스키) url인코딩
        try {
            addr = TourLocation.ADDR;
        } catch (Exception e) {
        }
        addr = addr + TourLocation.SERVICE_KEY + "&" + mapX + "&" + mapY + "&" + radius + "&" + contentTypeId + "&" + TourLocation.MOBILE_APP + "&" + TourLocation.MOBILE_TYPE + "&" + TourLocation.REST_TYPE + "&" + TourLocation.TOUR_ETC;
        Log.v("url", addr);
        return URI.create(addr);
    }

    public static String decodeStr(String param){
        String result = null;

        try {
            result = URLDecoder.decode(URLEncoder.encode(param, "8859_1"), "UTF-8");
        } catch (Exception e){ e.printStackTrace(); }
        return result;
    }
}
