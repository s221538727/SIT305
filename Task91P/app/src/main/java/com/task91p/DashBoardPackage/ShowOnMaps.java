package com.task91p.DashBoardPackage;

import androidx.fragment.app.FragmentActivity;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.model.Place;
import com.task91p.R;
import com.task91p.databinding.ActivityShowOnMapsBinding;

import java.io.IOException;
import java.util.List;

public class ShowOnMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityShowOnMapsBinding binding;
    List<String> placesList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityShowOnMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        placesList = getIntent().getStringArrayListExtra("placesList");

        for (String place : placesList) {
            Log.d("Place", place);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
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

        Geocoder geocoder = new Geocoder(ShowOnMaps.this);
        for (String place : placesList) {
            try {
                List<Address> addresses = geocoder.getFromLocationName(place, 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    googleMap.addMarker(new MarkerOptions()
                            .position(latLng)
                            .title(place));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Move the camera to the first place in the list
        if (!placesList.isEmpty()) {
            try {
                List<Address> addresses = geocoder.getFromLocationName(placesList.get(0), 1);
                if (addresses != null && !addresses.isEmpty()) {
                    Address address = addresses.get(0);
                    LatLng firstPlaceLatLng = new LatLng(address.getLatitude(), address.getLongitude());
                    googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(firstPlaceLatLng, 10));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}