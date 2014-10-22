package com.example.googlemap.app;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


public class CurrentMapActivity extends ActionBarActivity {
    protected GoogleMap map;
    protected LatLng curLatLng;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_map);

        Button currentSendBtn = (Button) findViewById(R.id.current_send_btn);

        currentSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), TourBoardActivity.class);
                intent.putExtra("lat", curLatLng.latitude);
                intent.putExtra("lng", curLatLng.longitude);
//                Log.v("test2", "" + curLatLng.latitude); Log.v("test2", "" + curLatLng.longitude);
                startActivity(intent);
            }
        });

        setUpMapIfNeeded();
        getCurrentLocation();
    }

    private void setUpMapIfNeeded() {
        if (map == null) {
            MapFragment fm = (MapFragment) getFragmentManager().findFragmentById(R.id.current_map);
            map = fm.getMap();
        }
    }

    private void getCurrentLocation() {

        double[] d = getlocation();

//        Log.v("test3", d[0] + ""); Log.v("test3", d[1] + "");
        curLatLng = new LatLng(d[0], d[1]);
        map
                .addMarker(new MarkerOptions()
                        .position(curLatLng)
                        .title("Current Location")
                        .icon(BitmapDescriptorFactory
                                .fromResource(R.drawable.abc_ic_search)));

        map
                .animateCamera(CameraUpdateFactory.newLatLngZoom(
                        curLatLng, 5));
    }

    public double[] getlocation() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);

        Location l = null;
        for (int i = 0; i < providers.size(); i++) {
            l = lm.getLastKnownLocation(providers.get(i));
            if (l != null)
                break;
        }
        double[] gps = new double[2];

        if (l != null) {
            gps[0] = l.getLatitude();
            gps[1] = l.getLongitude();
        }
        return gps;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.current_map, menu);
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
