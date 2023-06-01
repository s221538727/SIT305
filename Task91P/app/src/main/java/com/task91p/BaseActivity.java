package com.task91p;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity<T extends ViewBinding> extends AppCompatActivity
{
    private T binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

/*
        requestWindowFeature(Window.FEATURE_NO_TITLE); // hide the title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); // enable full screen mode
*/

         binding = getViewBinding(); // get the view binding instance
        setContentView(binding.getRoot());
    }


    @Override
    public void onBackPressed() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            // If there are Fragments on the back stack, pop the back stack
            fragmentManager.popBackStack();
        } else {
            // Otherwise, navigate back to the parent Activity
            super.onBackPressed();
        }
    }

    protected abstract T getViewBinding();

}
