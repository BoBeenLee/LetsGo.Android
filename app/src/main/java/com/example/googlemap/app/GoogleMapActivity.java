package com.example.googlemap.app;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.List;

import com.example.googlemap.app.adapter.NameListAdapter;
import com.example.googlemap.app.route.Route;

public class GoogleMapActivity extends ActionBarActivity {
    protected GoogleMap map;
    protected LatLng start;
    protected LatLng end;
    //    Target
    protected Marker targetMaker;

    //    Main
    protected Button searchOriginBtn, searchDestinationBtn;
    protected TextView originText, destinationText;
    protected Button searchBtn;
    protected Button sendBtn;

    //    Popup
    protected PopupWindow popupWindow;
    protected Button inputSearchBtn, closeBtn;
    protected TextView inputSearchText;
    protected ListView listView;
    //
    //    Location
    protected Location location;
    protected LatLng myLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);

        setUpMapIfNeeded();
        map.setMyLocationEnabled(true);

//       setView
        searchOriginBtn = (Button) findViewById(R.id.search_origin_btn);
        searchDestinationBtn = (Button) findViewById(R.id.search_destination_btn);
        originText = (TextView) findViewById(R.id.origin_text);
        destinationText = (TextView) findViewById(R.id.destination_text);
        searchBtn = (Button) findViewById(R.id.search_btn);
        sendBtn = (Button) findViewById(R.id.send_btn);

//        리스너 설정
        sendBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(targetMaker == null){
                   Toast.makeText(getApplicationContext(), "출발점과 도착점을 입력하세요", Toast.LENGTH_SHORT).show();
                   return ;
                }
                Intent intent =  new Intent(getApplicationContext(), TourBoardActivity.class);
                intent.putExtra("lat", targetMaker.getPosition().latitude);
                intent.putExtra("lng", targetMaker.getPosition().longitude);
//                Log.v("test2", "" + curLatLng.latitude); Log.v("test2", "" + curLatLng.longitude);
                startActivity(intent);
            }
        });
        searchOriginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAddress(originText);
            }
        });
        searchDestinationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchAddress(destinationText);
            }
        });
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.clear();

                Address sAddress, lAddress;
                sAddress = (Address)originText.getTag();
                lAddress = (Address)destinationText.getTag();

//               값을 입력 받지 못했을 경우
                if(sAddress == null || lAddress == null){
                    Toast.makeText(getApplicationContext(), "출발점과 도착점을 입력하세요", Toast.LENGTH_SHORT).show();
                    return ;
                }

                start = new LatLng(sAddress.getLatitude(), sAddress.getLongitude());
                end = new LatLng(lAddress.getLatitude(), lAddress.getLongitude());

                // Start marker
                MarkerOptions options = new MarkerOptions();
                options.position(start);
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
                Marker sMarker = map.addMarker(options);
                sMarker.setTitle("출발점");

                // End marker
                options = new MarkerOptions();
                options.position(end);
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
                Marker lMarker = map.addMarker(options);
                lMarker.setTitle("도착점");

                // set Target
                LatLng target = new LatLng((sAddress.getLatitude() + lAddress.getLatitude())/2, (sAddress.getLongitude() + lAddress.getLongitude())/2);

                options = new MarkerOptions();
                options.position(target);
                options.icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_launcher));
                targetMaker = map.addMarker(options);

                targetMaker.setTitle("선택");
                targetMaker.setDraggable(true);

                map.setMyLocationEnabled(true);

                LatLngBounds.Builder builder = new LatLngBounds.Builder();
                builder.include(start);
                builder.include(end);

                // animate and zoom
                map.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 100));
            }
        });
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
            map = fm.getMap();
        }
    }

    public void searchAddress(TextView textView) {
        final TextView targetTextView = textView;

        View popupView = getLayoutInflater().inflate(R.layout.popup_map_name_search, null);

        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

//        setView
        closeBtn = (Button) popupView.findViewById(R.id.close_btn);
        inputSearchBtn = (Button) popupView.findViewById(R.id.popup_search_btn);
        inputSearchText = (TextView) popupView.findViewById(R.id.popup_search_text);
        listView = (ListView) popupView.findViewById(R.id.list_view);

        //       팝업 리스너
        inputSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String locationName = inputSearchText.getText().toString();
                Geocoder geocoder = new Geocoder(getBaseContext());

                List<Address> addressList = null;
                try {
                    addressList = geocoder.getFromLocationName(locationName, 10);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //        List Adapter 에게 전달.
                NameListAdapter nameListAdapter = new NameListAdapter(getApplicationContext(), popupWindow, addressList, targetTextView);
                listView.setAdapter(nameListAdapter);
                listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
            }
        });

        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

//        popup setting
        //팝업 뷰 터치 되도록
        popupWindow.setTouchable(true);
        //팝업 뷰 포커스도 주고
        popupWindow.setFocusable(true);
        //팝업 뷰 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
        popupWindow.setOutsideTouchable(true);

        popupWindow.setAnimationStyle(0); // 애니메이션 설정(-1:설정, 0:설정안함)
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, -100);
    }


    void drawPolyLine(Route route){
        PolylineOptions polyoptions = getPolyOptions(route);
        polyoptions.color(Color.BLUE);
        polyoptions.width(10);
        //  polyoptions.addAll(mPolyOptions.getPoints());
        // 길 그려주는 곳
        map.addPolyline(polyoptions);

        // Start marker
        MarkerOptions options = new MarkerOptions();
        options.position(start);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.start_blue));
        map.addMarker(options);

        // End marker
        options = new MarkerOptions();
        options.position(end);
        options.icon(BitmapDescriptorFactory.fromResource(R.drawable.end_green));
        map.addMarker(options);
    }

    PolylineOptions getPolyOptions(Route route) {
        PolylineOptions mOptions = new PolylineOptions();

        for (LatLng point : route.getPoints()) {
            mOptions.add(point);
        }
        return mOptions;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.google_map, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
