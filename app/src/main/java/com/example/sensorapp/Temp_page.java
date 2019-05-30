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
import android.widget.TextView;
import android.widget.Toast;

public class Temp_page extends Fragment implements SensorEventListener {
    private SensorManager mSensorManager;
    private TextView xvalues, text;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager)

                this.getActivity().getSystemService(Activity.SENSOR_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.temp_page,container, false);
        xvalues = (TextView) rootView.findViewById(R.id.temp_value);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text = view.findViewById(R.id.temp_label);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sensor tsensor = mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);

                if (tsensor != null){
                    Toast.makeText(v.getContext(),"Name : "+tsensor.getName()+"\nVendor : "+tsensor.getVendor()
                            +"\nVersion :"+tsensor.getVersion()+"\nPower : "+tsensor.getPower()
                            +" mA\nResolution : "+tsensor.getResolution()+" rad/s", Toast.LENGTH_SHORT).show();
                } else {
                    xvalues.setText("oops!");
                    text.setClickable(false);
                }
            }
        });

    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];

        xvalues.setText(getResources().getString(R.string.temp_value, x));


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
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE) == null){
            Toast.makeText(getActivity(), "Sensor not found", Toast.LENGTH_SHORT).show();
            xvalues.setText("oops!");
        } else {
            mSensorManager.registerListener(this,
                    mSensorManager.getSensorList(Sensor.TYPE_AMBIENT_TEMPERATURE).get(0),
                    SensorManager.SENSOR_DELAY_NORMAL);
        }


    }

    private void unregisterSensorListener() {
        mSensorManager.unregisterListener(this);
    }
}