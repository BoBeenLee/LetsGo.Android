package com.example.googlemap.app;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.googlemap.app.adapter.NameListAdapter;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button findRoad = (Button)findViewById(R.id.find_road_btn);
        Button currentMap = (Button)findViewById(R.id.current_map_btn);
        Button test = (Button)findViewById(R.id.tour_board_btn);

        findRoad.setOnClickListener(new IntentClickListener(GoogleMapActivity.class));
        currentMap.setOnClickListener(new IntentClickListener(CurrentMapActivity.class));
        test.setOnClickListener(new IntentClickListener(TourBoardActivity.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }


    class IntentClickListener implements View.OnClickListener {
        Class activityClass;

        public IntentClickListener( Class activityClass){
            this.activityClass = activityClass;
        }

        @Override
        public void onClick(View view) {
            Bundle bundle = new Bundle();
            Intent intent = new Intent(getApplicationContext(), activityClass);

            intent.putExtra("lat1", 37.5546671);
            intent.putExtra("lng1", 126.9374995);
            startActivity(intent);
        }
    }
}
