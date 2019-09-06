package com.example.scenichiking.ui.main;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.scenichiking.BR;
import com.example.scenichiking.R;
import com.example.scenichiking.data.MarkerPoint;
import com.example.scenichiking.data.MarkersPointData;
import com.example.scenichiking.databinding.ActivityMainBinding;
import com.example.scenichiking.ui.base.BaseActivity;
import com.example.scenichiking.ui.markers_fragment.MarkersFragment;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineResult;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.geometry.LatLngBounds;
import com.mapbox.mapboxsdk.location.LocationComponent;
import com.mapbox.mapboxsdk.location.LocationComponentActivationOptions;
import com.mapbox.mapboxsdk.location.modes.CameraMode;
import com.mapbox.mapboxsdk.location.modes.RenderMode;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.CircleManager;
import com.mapbox.mapboxsdk.plugins.annotation.CircleOptions;
import com.mapbox.mapboxsdk.plugins.annotation.LineManager;
import com.mapbox.mapboxsdk.plugins.annotation.LineOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel>
        implements PermissionsListener, LocationEngineCallback<LocationEngineResult>, MainNavigator {
    ActivityMainBinding binding;
    PermissionsManager permissionsManager = new PermissionsManager(this);
    MainViewModel viewModel;
    Location lastLocation;
    private MapboxMap mapboxMap;
    LocationEngine locationEngine;
    CircleManager circleManager;
    LineManager lineManager;
    Bundle mySavedInstance;

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public MainViewModel getViewModel() {
        viewModel = new MainViewModel();
        return viewModel;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        mySavedInstance = savedInstanceState;
        Mapbox.getInstance(this, getString(R.string.mapbox_access_token));
        super.onCreate(savedInstanceState);
        binding = getViewDataBinding();
        viewModel.setNavigator(this);
        setUpMap(savedInstanceState);

    }

    private void setUpMap(Bundle savedInstanceState) {
        lastLocation = new Location("");
        //DefaultLocation
        lastLocation.setLatitude(52.517652);
        lastLocation.setLongitude(13.405124);
        locationEngine = LocationEngineProvider.getBestLocationEngine(this);


        binding.mapView.onCreate(savedInstanceState);
        binding.mapView.getMapAsync(mapboxMap -> {
            MainActivity.this.mapboxMap = mapboxMap;
            mapboxMap.addOnMapLongClickListener(MainActivity.this::addCircle);

            mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                @Override
                public void onStyleLoaded(@NonNull Style style) {
                    enableLocationComponent(mapboxMap, style);

                    lineManager = new LineManager(binding.mapView, mapboxMap, style);
                    circleManager = new CircleManager(binding.mapView, mapboxMap, style);

                }
            });
        });
    }

    private boolean addCircle(LatLng point) {
        MarkerPoint markerPoint = new MarkerPoint();
        markerPoint.setPoint(point);
        markerPoint.setFavorite(false);
        viewModel.addMarkerPoint(markerPoint);
        AbstractFactory abstractFactory = FactoryProducer.getFactory(false);
        CircleOptions circleOptions = ((CircleOptions) abstractFactory.getOption("circle"))
                .withLatLng(point);
        circleManager.create(circleOptions);
        return true;
    }


    @SuppressWarnings({"MissingPermission"})
    private void enableLocationComponent(MapboxMap mapboxMap, Style style) {

        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            LocationComponent locationComponent = mapboxMap.getLocationComponent();
            locationComponent.activateLocationComponent(LocationComponentActivationOptions
                    .builder(this, style).build());
            locationComponent.setLocationComponentEnabled(true);
            locationComponent.setCameraMode(CameraMode.TRACKING);
            locationComponent.setRenderMode(RenderMode.COMPASS);
            locationEngine.getLastLocation(this);

        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);

        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        Toast.makeText(this, permissionsToExplain.toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) setUpMap(mySavedInstance);
    }


    @Override
    public void onSuccess(LocationEngineResult result) {
        lastLocation = result.getLastLocation();
    }

    @Override
    public void onFailure(@NonNull Exception exception) {
        Toast.makeText(this, getString(R.string.location_error), Toast.LENGTH_LONG).show();

    }


    public void updateMarkers(MarkerPoint markerPoints) {
        viewModel.updateMarker(markerPoints);
    }

    @Override
    public void updateAllMarkers(@NotNull List<MarkerPoint> markerPoints) {
        circleManager.deleteAll();
        AbstractFactory abstractFactory;
        for (MarkerPoint point : viewModel.getMarkerPoints()) {
            if (point.isFavorite()) {
                abstractFactory = FactoryProducer.getFactory(true);
            } else {
                abstractFactory = FactoryProducer.getFactory(false);
            }
            CircleOptions circleOptions = ((CircleOptions) abstractFactory
                    .getOption("circle")).withLatLng(point.getPoint());
            circleManager.create(circleOptions);
        }

    }

    @Override
    public void onGotoListClick() {
        MarkersPointData data = new MarkersPointData();
        data.setMarkerPoints(viewModel.getMarkerPoints());
        MarkersFragment fragment = MarkersFragment.newInstance(data);

        getSupportFragmentManager()
                .beginTransaction()
                .addToBackStack(MarkersFragment.class.getName())
                .add(R.id.lytMain, fragment, MarkersFragment.TAG)
                .commit();
    }

    @Override
    public void onRouteClick() {
        if (viewModel.getHasRoute()) {
            mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude()), 20));
        } else {
            Toast.makeText(this, getString(R.string.route_error), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onGetRouteClick() {
        if (viewModel.getMarkerPoints().size() > 0) {
            List<LatLng> latLngs = new ArrayList<>();
            LatLng currentPosition = new LatLng();
            currentPosition.setLatitude(lastLocation.getLatitude());
            currentPosition.setLongitude(lastLocation.getLongitude());
            latLngs.add(currentPosition);
            for (MarkerPoint markerPoint : viewModel.getMarkerPoints()) {
                latLngs.add(markerPoint.getPoint());
            }
            AbstractFactory abstractFactory = FactoryProducer.getFactory(false);
            LineOptions lineOptions = ((LineOptions) abstractFactory
                    .getOption("line")).withLatLngs(latLngs);
            lineManager.create(lineOptions);
            viewModel.setHasRoute(true);
        } else {
            Toast.makeText(this, getString(R.string.get_route_error), Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onOverViewClick() {
        if (viewModel.getMarkerPoints().size() > 0) {
            List<LatLng> latLngs = new ArrayList<>();
            LatLng currentPosition = new LatLng();
            currentPosition.setLatitude(lastLocation.getLatitude());
            currentPosition.setLongitude(lastLocation.getLongitude());
            latLngs.add(currentPosition);
            for (MarkerPoint markerPoint : viewModel.getMarkerPoints()) {
                latLngs.add(markerPoint.getPoint());
            }
            LatLngBounds latLngBounds = new LatLngBounds.Builder().includes(latLngs).build();

            mapboxMap.easeCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 50)
                    , 1000);

        } else {
            Toast.makeText(this, getString(R.string.over_view_error), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        binding.mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        binding.mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        binding.mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        binding.mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding.mapView.onDestroy();
        locationEngine.removeLocationUpdates(this);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        binding.mapView.onSaveInstanceState(outState);
    }
}
