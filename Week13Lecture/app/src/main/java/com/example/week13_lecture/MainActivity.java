package com.example.week13_lecture;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    SensorManager mSensorManager;
    List<Sensor> sensorList;
    List<Sensor> otherList;
    TextView tvSensorList;
    Button button;
    Sensor mSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init_UI();
        init_sensor();
    }

    private void displaySensors(List<Sensor> list) {
        String s = "";
        for(int i = 0; i < list.size(); i++){
            s += list.get(i).getName() + "\n";
        }
        tvSensorList.setText(s);
    }

    private void init_UI() {
        tvSensorList = (TextView) findViewById(R.id.tvSensorList);
        button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, CompassActivity.class);
                startActivity(i);
            }
        });
    }

    private void init_sensor() {
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        displaySensors(sensorList);

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            otherList = mSensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
            displaySensors(otherList);
        }

        if(mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        String data = "";
        data = sensorEvent.sensor.getName();
        data += "\n Accuracy: " + sensorEvent.accuracy;
        data += "\n timestamp: " + sensorEvent.timestamp;
        data += "\n values: "  ;
        for(int i = 0; i<sensorEvent.values.length;i++)
            data += "\n\t" + i + ": " + sensorEvent.values[i];

        tvSensorList.setText(data);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }
}