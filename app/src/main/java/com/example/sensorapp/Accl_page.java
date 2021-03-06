package com.example.sensorapp;

import android.annotation.SuppressLint;
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

public class Accl_page extends Fragment implements SensorEventListener {
    private SensorManager mSensorManager;
    private TextView xvalues,yvalues,zvalues,text;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSensorManager = (SensorManager)

                this.getActivity().getSystemService(Activity.SENSOR_SERVICE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.accelerometer_page, container, false);
        xvalues = (TextView) rootView.findViewById(R.id.xaccl_value);
        yvalues = (TextView) rootView.findViewById(R.id.yaccl_value);
        zvalues = (TextView) rootView.findViewById(R.id.zaccl_value);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        text = view.findViewById(R.id.accl_label);

        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Sensor tsensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

                if (tsensor != null){
                    Toast.makeText(v.getContext(),"Name : "+tsensor.getName()+"\nVendor : "+tsensor.getVendor()
                            +"\nVersion :"+tsensor.getVersion()+"\nPower : "+tsensor.getPower()
                            +" mA\nResolution : "+tsensor.getResolution()+" m/s2\nMax. Range : "
                            +tsensor.getMaximumRange()+" m/s2", Toast.LENGTH_SHORT).show();
                } else {
                    text.setClickable(false);
                }

            }
        });
        //graph.addSeries(xseries);


    }

    @SuppressLint("StringFormatInvalid")
    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0], y = event.values[1], z = event.values[2];
        xvalues.setText(getResources().getString(R.string.xaccl_value,x));
        //xvalues.setText("X : "+String.format("%.2f",x));
        yvalues.setText(getResources().getString(R.string.yaccl_value, y));
        zvalues.setText(getResources().getString(R.string.zaccl_value, z));

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
        if (mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) == null){
            Toast.makeText(getActivity(), "Sensor not found", Toast.LENGTH_SHORT).show();
            xvalues.setText("");
            yvalues.setText("oops!");
            zvalues.setText("");
        } else {
        mSensorManager.registerListener(this,
                mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0),
                SensorManager.SENSOR_DELAY_NORMAL);}
    }

    private void unregisterSensorListener() {
        mSensorManager.unregisterListener(this);
    }
}
