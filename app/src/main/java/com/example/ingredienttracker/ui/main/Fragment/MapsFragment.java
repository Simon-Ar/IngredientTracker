package com.example.ingredienttracker.ui.main.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.ingredienttracker.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.internal.OnConnectionFailedListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsFragment extends Fragment
        implements OnMapReadyCallback, OnConnectionFailedListener {

    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private final String TAG = "Maps Fragment";
    public boolean mLocationPermissionGranted;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private static final float DEFAULT_ZOOM = 15f;
    private Location userLocation;

    @Override
    public void onResume() {
        super.onResume();
    }

    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation called");
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        if (mLocationPermissionGranted) {
            if (ActivityCompat.checkSelfPermission(
                    getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Task location = mFusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "onComplete: Found Location!");
                        userLocation = (Location) task.getResult();
                        moveCamera(new LatLng(userLocation.getLatitude(), userLocation.getLongitude()),
                                DEFAULT_ZOOM);
                    }
                }
            });
        }
    }

    private void moveCamera(LatLng latLng, float zoom) {
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = (MapView) view.findViewById(R.id.map);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }
        getLocationPermission();
        Log.d(TAG, "onCreateView Permissions granted: " + mLocationPermissionGranted);
        if (mLocationPermissionGranted) {
            mMapView.getMapAsync(this);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getContext(), "Map is ready", Toast.LENGTH_SHORT).show();
        if (mLocationPermissionGranted) {
            getDeviceLocation();
            mGoogleMap = googleMap;
            if (ActivityCompat.checkSelfPermission(
                    getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mGoogleMap.setMyLocationEnabled(true);
            mGoogleMap.getUiSettings().setRotateGesturesEnabled(true);
            mGoogleMap.getUiSettings().setMyLocationButtonEnabled(true);
            mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
            mGoogleMap.getUiSettings().setZoomGesturesEnabled(true);
        }
    }

    private void getLocationPermission() {
        Log.d(TAG,"getLocationPermission: called");
        String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (ActivityCompat.checkSelfPermission(getActivity()
                , FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                mLocationPermissionGranted = true;
                return;
            } else {
                requestPermissions(permission,
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
        } else {
            requestPermissions(permission,
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d(TAG,"onRequestPermissionsResult: called");
        Log.d(TAG,"onRequestPermissionsResult: "+ permissions[0]+permissions[1]);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0) {
                    for (int i =0; i<grantResults.length;i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            Log.d(TAG,"Permissions not granted");
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    Log.d(TAG,"Permissions granted");
                    mLocationPermissionGranted = true;
                }
            }
        }
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
