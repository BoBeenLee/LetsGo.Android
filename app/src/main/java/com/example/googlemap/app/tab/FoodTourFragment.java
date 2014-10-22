package com.example.googlemap.app.tab;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.googlemap.app.R;
import com.example.googlemap.app.adapter.TourListAdapter;
import com.example.googlemap.app.model.TourLocation;
import com.example.googlemap.app.util.TourUtil;
import com.origamilabs.library.views.StaggeredGridView;

import java.net.URI;
import java.util.List;

public class FoodTourFragment extends Fragment {
    private List<TourLocation> tourList = null;
    private URI tourURL = null;

    private TourListAdapter adapter;
    private StaggeredGridView gridView;

    private double mapX, mapY;
    private int radius;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        StrictMode.enableDefaults();
        View rootView = inflater.inflate(R.layout.fragment_food_tour, container, false);
        final int contentTypeId = TourUtil.TOUR_FOOD;
        Bundle arguments = getArguments();

        mapX = arguments.getDouble("mapX"); mapY = arguments.getDouble("mapY");
        radius = 20000;

        gridView = (StaggeredGridView) rootView.findViewById(R.id.staggeredGridView);

        int margin = getResources().getDimensionPixelSize(R.dimen.tour_item_margin);
        gridView.setItemMargin(margin); // set the GridView margin
        gridView.setPadding(margin, 0, margin, 0); // have the margin on the sides as well

        tourURL = TourUtil.getParam(mapX, mapY, radius, contentTypeId);
        tourList = TourUtil.getTourGson(tourURL);
        adapter = new TourListAdapter(rootView.getContext(), tourList);

        gridView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        return rootView;
    }
}
