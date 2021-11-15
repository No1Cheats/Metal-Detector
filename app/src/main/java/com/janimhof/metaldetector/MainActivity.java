package com.janimhof.metaldetector;

import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements SensorEventListener  {

    private ProgressBar progressBar;
    private TextView textView;

    private Sensor magnetometer;
    private SensorManager mSensorManager;
    private SeekBar seekBar;

    private int maxVal = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        textView = findViewById(R.id.textView);
        seekBar = findViewById(R.id.seekBar);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        magnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        seekBar.setMin(100);
        seekBar.setMax(1000);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChangedValue = 100;

            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressChangedValue = progress;
                maxVal = progressChangedValue;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // bruh
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                // bruh
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float[] mag = event.values;
        double magnitude = Math.sqrt(mag[0] * mag[0] + mag[1] * mag[1] + mag[2] * mag[2]);
        progressBar.setMax(maxVal);
        progressBar.setProgress((int) magnitude);
        textView.setText("Magnitude: " + magnitude);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        // N/A
    }

}