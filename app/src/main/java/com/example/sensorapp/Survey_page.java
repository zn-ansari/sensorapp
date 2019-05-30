package com.example.sensorapp;

import android.app.Activity;
import android.graphics.Color;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class Survey_page extends Fragment implements SensorEventListener {
    private SensorManager mSensorManager;
    private TextView xvalues,yvalues,zvalues;
    List<Sensor> sL;
    int i = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager)

                this.getActivity().getSystemService(Activity.SENSOR_SERVICE);
        sL = mSensorManager.getSensorList(Sensor.TYPE_ALL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.survey_page, container, false);
        LinearLayout myLayout = (LinearLayout) rootView.findViewById(R.id.my_layout);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout myLayout = (LinearLayout) view.findViewById(R.id.my_layout);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        StringBuilder sensorText = new StringBuilder();
        for (final Sensor currentSensor : sL){
            if  ((currentSensor.getName().contains("Uncalibrated")) ||
                    (currentSensor.getName().contains("Secondary"))) {
            } else {
                Button btn = new Button(getContext());
                btn.setId(i);
                btn.setText(""+currentSensor.getName());
                btn.setBackgroundColor(Color.parseColor("#FFFFFF"));
                myLayout.addView(btn, params);
                btn = ((Button) view.findViewById(i));
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(),
                                "The sensor " + currentSensor.getName() + " is working fine!", Toast.LENGTH_SHORT)
                                .show();
                    }
                });

            }
            i++;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

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
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ALL) == null){
            Toast.makeText(getActivity(), "Sensor not found", Toast.LENGTH_SHORT).show();
        } else {
        mSensorManager.registerListener(this,
                mSensorManager.getSensorList(Sensor.TYPE_ALL).get(0),
                SensorManager.SENSOR_DELAY_NORMAL);
        }



    }


    private void unregisterSensorListener() {
        mSensorManager.unregisterListener(this);
    }
}