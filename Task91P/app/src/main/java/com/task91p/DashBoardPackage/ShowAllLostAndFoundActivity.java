package com.task91p.DashBoardPackage;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.task91p.BaseActivity;
import com.task91p.DashBoardPackage.AdaptersPackage.DisplayAdapter;
import com.task91p.DashBoardPackage.AdaptersPackage.OnClickListenerInterface;
import com.task91p.DataBaseHelperPackage.DataBaseHelper;
import com.task91p.DataModelPackage.DataModel;
import com.task91p.MainActivity;
import com.task91p.databinding.ActivityShowAllLostAndFoundBinding;

import java.util.List;

public class ShowAllLostAndFoundActivity extends BaseActivity<ActivityShowAllLostAndFoundBinding>
        implements OnClickListenerInterface {

    private ActivityShowAllLostAndFoundBinding binding;
    private DisplayAdapter displayAdapter;
    private List<DataModel> dataList;


    @Override
    protected ActivityShowAllLostAndFoundBinding getViewBinding() {
        binding = ActivityShowAllLostAndFoundBinding.inflate(getLayoutInflater());
        return binding;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.displayRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        dataList = getDataFromDatabase();
        if(dataList.isEmpty())
        {
            binding.noDataFoundTxtView.setVisibility(View.VISIBLE);
        }else
        {
            binding.displayRecyclerView.setVisibility(View.VISIBLE);
        }
        displayAdapter = new DisplayAdapter(dataList,this);
        binding.displayRecyclerView.setAdapter(displayAdapter);

    }

    private List<DataModel> getDataFromDatabase() {
        // Implement your database retrieval logic here and return the list of data
        // For simplicity, let's assume you have a DatabaseHelper instance called dbHelper
        DataBaseHelper dbHelper = new DataBaseHelper(this);
        return dbHelper.getAllData();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(ShowAllLostAndFoundActivity.this, MainActivity.class));
        finish();
    }


    @Override
    public void onItemClick(DataModel dataModel) {
        Intent intent = new Intent(ShowAllLostAndFoundActivity.this,DisplayAndRemoveAcitivty.class);
        intent.putExtra("dataModel", dataModel);
        startActivity(intent);

    }
}