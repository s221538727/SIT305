package com.task91p;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.task91p.DashBoardPackage.NewAdvert;
import com.task91p.DashBoardPackage.ShowAllLostAndFoundActivity;
import com.task91p.DashBoardPackage.ShowOnMaps;
import com.task91p.DataBaseHelperPackage.DataBaseHelper;
import com.task91p.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding>
{
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.showAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ShowAllLostAndFoundActivity.class));
                finish();
            }
        });


        binding.newAdvertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this, NewAdvert.class));
                finish();
            }
        });


        binding.showOnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataBaseHelper databaseHelper = new DataBaseHelper(MainActivity.this);

                // Call the getPlaces method to retrieve the places data
                List<String> placesList = databaseHelper.getPlaces();

                // Create an Intent to start the other activity
                Intent intent = new Intent(MainActivity.this, ShowOnMaps.class);

                // Pass the placesList as an extra to the Intent
                intent.putStringArrayListExtra("placesList", (ArrayList<String>) placesList);

                // Start the other activity
                startActivity(intent);

            }
        });

    }

    @Override
    protected ActivityMainBinding getViewBinding() {

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        return binding;
    }
}