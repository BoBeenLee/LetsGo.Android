package com.example.googlemap.app.route;

import java.util.List;

public interface RoutingListener {
    public void onRoutingFailure();

    public void onRoutingStart();

    public void onRoutingSuccess(List<Route> result);
}