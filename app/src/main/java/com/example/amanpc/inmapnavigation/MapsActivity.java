package com.example.amanpc.inmapnavigation;

import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.maps.GeoPoint;

import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        MapView mapView = (MapView) findViewById(R.id.map); //or you can declare it directly with the API key
        Route route = directions(new GeoPoint((int)(26.2*1E6),(int)(50.6*1E6)), new GeoPoint((int)(26.3*1E6),(int)(50.7*1E6)));
        RouteOverlay routeOverlay = new RouteOverlay(route, Color.BLUE);
        mapView.getOverlays().add(routeOverlay);
        mapView.invalidate();

        AsyncTask(directions(final GeoPoint start, final GeoPoint dest));
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng pu = new LatLng(30.757965, 76.768412);
        LatLng gate1 = new LatLng(30.76106437814946, 76.77405971371081);
        LatLng gate2 = new LatLng(30.754953658351678, 76.76877892511561);

        mMap.addMarker(new MarkerOptions().position(pu).title("Panjab University"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(pu));
    }

    private Route directions(final GeoPoint start, final GeoPoint dest) {
        Parser parser;
        //https://developers.google.com/maps/documentation/directions/#JSON <- get api
        String jsonURL = "http://maps.googleapis.com/maps/api/directions/json?";
        final StringBuffer sBuf = new StringBuffer(jsonURL);
        sBuf.append("origin=");
        sBuf.append(start.getLatitudeE6()/1E6);
        sBuf.append(',');
        sBuf.append(start.getLongitudeE6()/1E6);
        sBuf.append("&destination=");
        sBuf.append(dest.getLatitudeE6()/1E6);
        sBuf.append(',');
        sBuf.append(dest.getLongitudeE6()/1E6);
        sBuf.append("&sensor=true&mode=driving");
        parser = new GoogleParser(sBuf.toString());
        Route r = parser.parse();
        return r;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }
}
