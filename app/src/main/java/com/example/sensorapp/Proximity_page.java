package com.example.sensorapp;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Proximity_page extends Fragment implements SensorEventListener {
    private SensorManager mSensorManager;
    private TextView mTextProximity,text;
    private TextView mTextProximityExp;
    private ImageView mDrake;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager)

                this.getActivity().getSystemService(Activity.SENSOR_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.proximity_page, container, false);
        mTextProximity = (TextView) rootView.findViewById(R.id.proximity_value);
        mTextProximityExp = (TextView) rootView.findViewById(R.id.proximity_expression);
        mDrake = (ImageView) rootView.findViewById(R.id.drake_image);
        return rootView;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text = view.findViewById(R.id.proximity_label);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sensor tsensor = mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

                if (tsensor != null){
                    Toast.makeText(v.getContext(),"Name : "+tsensor.getName()+"\nVendor : "+tsensor.getVendor()
                            +"\nVersion :"+tsensor.getVersion()+"\nPower : "+tsensor.getPower()
                            +" mA\nResolution : "+tsensor.getResolution()+" cm\nMax. Range : "
                            +tsensor.getMaximumRange()+" cm", Toast.LENGTH_SHORT).show();
                } else {
                    text.setClickable(false);
                }
            }
        });

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];//, y = event.values[1];
        //mTextProximity.setText("" + x);
        mTextProximity.setText(getResources().getString(R.string.proximity_value, x));
        if (x!=0){
            mTextProximityExp.setText("Just displaying sensor value!");
            mDrake.setImageResource(R.drawable.drake_nooooooo);
        }
           else{
            mTextProximityExp.setText("Using memes instead!!!");
            mDrake.setImageResource(R.drawable.drake_meme);

           }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }

    @Override
    public void setMenuVisibility(boolean menuVisible) {
        super.setMenuVisibility(menuVisible);

        // First starts (gets called before everything else)
        if (mSensorManager == null) {
            return;
        }

        if (menuVisible) {
            this.registerSensorListener();
        } else {
            this.unregisterSensorListener();
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        if (this.getUserVisibleHint()) {
            this.registerSensorListener();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        this.unregisterSensorListener();
    }

    private void registerSensorListener() {
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null){
            Toast.makeText(getActivity(), "Sensor not found", Toast.LENGTH_SHORT).show();
            mTextProximity.setText("oops!");

        } else {
        mSensorManager.registerListener(this,
                mSensorManager.getSensorList(Sensor.TYPE_PROXIMITY).get(0),
                SensorManager.SENSOR_DELAY_NORMAL);}
    }

    private void unregisterSensorListener() {
        mSensorManager.unregisterListener(this);
    }
}