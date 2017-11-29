package com.riggsandrew.ariggs.birdlisttest;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = fragmentManager.findFragmentById(R.id.main_frag_container);

        if(fragment == null) {
            fragment = new MainFragment();
            fragmentManager.beginTransaction().add(R.id.main_frag_container, fragment).commit();
        }
    }
}
