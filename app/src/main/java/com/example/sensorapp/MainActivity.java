package com.example.sensorapp;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        appBarLayout = (AppBarLayout) findViewById(R.id.appbarid);
        viewPager = (ViewPager) findViewById(R.id.viewPagerid);
        ViewPagerAdaptor adapter = new ViewPagerAdaptor(getSupportFragmentManager());

        adapter.AddFragment(new Home(), "Home");
        adapter.AddFragment(new Accl_page(), "Accelerometer");
        adapter.AddFragment(new Gyro_page(), "Gyroscope");
        adapter.AddFragment(new Magn_page(), "Magnetometer");
        adapter.AddFragment(new Pedometer_page(), "Pedometer");
        adapter.AddFragment(new Light_page(), "Light");
        adapter.AddFragment(new Temp_page(), "Temperature");
        adapter.AddFragment(new Proximity_page(), "Proximity");
        adapter.AddFragment(new Survey_page(), "Survey");
        adapter.AddFragment(new Reference_page(), "References");
        //

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
}
