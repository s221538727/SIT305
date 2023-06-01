package com.task91p.DashBoardPackage;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.task91p.BaseActivity;
import com.task91p.DataBaseHelperPackage.DataBaseHelper;
import com.task91p.DataModelPackage.DataModel;
import com.task91p.MainActivity;
import com.task91p.databinding.ActivityDisplayAndRemoveAcitivtyBinding;

public class DisplayAndRemoveAcitivty extends BaseActivity<ActivityDisplayAndRemoveAcitivtyBinding>
{

    ActivityDisplayAndRemoveAcitivtyBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("dataModel")) {
            DataModel dataModel = (DataModel) intent.getSerializableExtra("dataModel");

            binding.txtPostType.setText(dataModel.getPostType());
            binding.txtName.setText(dataModel.getName());
            binding.txtDescription.setText(dataModel.getDescription());
            binding.txtPhone.setText(dataModel.getPhone());
            binding.txtDate.setText(dataModel.getDate());
            binding.txtLocation.setText(dataModel.getLocation());

        }

        binding.btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DataBaseHelper databaseHelper = new DataBaseHelper(DisplayAndRemoveAcitivty.this);

                // Retrieve the DataModel object from the intent
                DataModel dataModel = (DataModel) getIntent().getSerializableExtra("dataModel");

                // Get the ID of the data model
                int id = dataModel.getId();

                // Remove the item from the database
                boolean isDeleted = databaseHelper.deleteData(id);

                if (isDeleted) {
                    // Item deleted successfully
                    Toast.makeText(DisplayAndRemoveAcitivty.this, dataModel.getName()+" removed", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(DisplayAndRemoveAcitivty.this,ShowAllLostAndFoundActivity.class));
                    finish(); // Finish the activity and go back to the previous screen
                } else {
                    // Failed to delete the item
                    Toast.makeText(DisplayAndRemoveAcitivty.this, "Failed to remove item", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    @Override
    protected ActivityDisplayAndRemoveAcitivtyBinding getViewBinding() {
        binding = ActivityDisplayAndRemoveAcitivtyBinding.inflate(getLayoutInflater());
        return  binding;

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(DisplayAndRemoveAcitivty.this, MainActivity.class));
        finish();
    }

}