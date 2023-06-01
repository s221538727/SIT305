package com.task91p.DashBoardPackage;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.task91p.BaseActivity;
import com.task91p.DataBaseHelperPackage.DataBaseHelper;
import com.task91p.MainActivity;
import com.task91p.R;
import com.task91p.databinding.ActivityDashBoardBinding;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class NewAdvert extends BaseActivity<ActivityDashBoardBinding>
{

    private static final int AUTOCOMPLETE_REQUEST_CODE = 123;
    ActivityDashBoardBinding binding;
    DataBaseHelper databaseHelper;
    private FusedLocationProviderClient fusedLocationClient;
    private Geocoder geocoder;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 100;



    @Override
    protected ActivityDashBoardBinding getViewBinding() {
        binding = ActivityDashBoardBinding.inflate(getLayoutInflater());
        return binding;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        databaseHelper = new DataBaseHelper(this);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        geocoder = new Geocoder(this);

        binding.editLocation.setFocusable(false);
        binding.editLocation.setClickable(true);
        binding.editLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                searchPlace();
            }
        });

        binding.getCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getCurrentLocations();
                if (ActivityCompat.checkSelfPermission(NewAdvert.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // Location permission has not been granted, so request it
                    ActivityCompat.requestPermissions(NewAdvert.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
                } else {
                    // Location permission has already been granted, proceed with location operations
                    getCurrentLocations();
                }

            }
        });
        binding.saveAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String postType = "";

                int selectedRadioButtonId = binding.radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != View.NO_ID) {
                    RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
                    postType = selectedRadioButton.getText().toString();
                }


                String name = binding.editName.getText().toString().trim();
                String phone = binding.editPhone.getText().toString().trim();
                String description = binding.editDescription.getText().toString().trim();
                String date = binding.editDate.getText().toString().trim();
                String location = binding.editLocation.getText().toString().trim();

                if (postType.isEmpty() || name.isEmpty() || phone.isEmpty() || description.isEmpty() || date.isEmpty() || location.isEmpty()) {
                    Toast.makeText(NewAdvert.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    // All fields are valid, insert the data into the database
                    boolean isInserted =  databaseHelper.insertData(postType, name, phone, description, date, location,location);
                    if (isInserted) {
                        Toast.makeText(NewAdvert.this, "Requested Added successfully", Toast.LENGTH_SHORT).show();
                        onBackPressed();
                    } else {
                        Toast.makeText(NewAdvert.this, "Failure!!", Toast.LENGTH_SHORT).show();
                    }

                }


                }
        });


    }

    private void getCurrentLocations() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Handle the case where location permission is not granted
            return;
        }

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    try {
                        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                        if (addresses != null && !addresses.isEmpty()) {
                            Address address = addresses.get(0);
                            String locationName = address.getAddressLine(0);
                            // Use the location name as needed

                            binding.editLocation.setText(locationName);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }




    private void searchPlace() {
        if (!Places.isInitialized()) {
            Places.initialize(this, getApplication().getString(R.string.google_maps_key));
        }
// Set the fields to specify which types of place data to
// return after the user has made a selection.
        List<Place.Field> fields = Arrays.asList(Place.Field.LAT_LNG, Place.Field.ADDRESS);
        // Start the autocomplete intent.
        Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).build(this);
        startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);
    }

        @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NewAdvert.this, MainActivity.class));
        finish();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);
                String destinationAddress = place.getAddress();
                binding.editLocation.setText(destinationAddress);

            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(this, "Some went wrong. Search again", Toast.LENGTH_SHORT).show();
                Log.i("TAG", status.getStatusMessage());
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Location permission has been granted, proceed with location operations
                getCurrentLocations();
            } else {
                // Location permission has been denied, handle accordingly
                // For example, display a message or disable location functionality
            }
        }
    }
}