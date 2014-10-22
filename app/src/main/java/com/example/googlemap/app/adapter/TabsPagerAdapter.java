package com.example.googlemap.app.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.googlemap.app.model.TourLocation;
import com.example.googlemap.app.tab.CourseTourFragment;
import com.example.googlemap.app.tab.CultureTourFragment;
import com.example.googlemap.app.tab.FoodTourFragment;

import java.net.URI;

/**
 * Created by BoBinLee on 2014-07-09.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    private double mapX, mapY;

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setMapX(double mapX){
        this.mapX = mapX;
    }

    public void setMapY(double mapY){
        this.mapY = mapY;
    }

    @Override
    public Fragment getItem(int index) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putDouble("mapX", mapX);
        bundle.putDouble("mapY", mapY);

        switch (index) {
            case 0:
                fragment = new CourseTourFragment();
                fragment.setArguments(bundle);
                return fragment;
            case 1:
                fragment = new CultureTourFragment();
                fragment.setArguments(bundle);
                return fragment;
            case 2:
                fragment = new FoodTourFragment();
                fragment.setArguments(bundle);
                return fragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
}
