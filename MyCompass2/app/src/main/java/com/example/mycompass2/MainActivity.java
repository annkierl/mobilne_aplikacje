package com.example.mycompass2;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView tv_mdegree;
    private static SensorManager mSensorManager;
    Camera camera;
    FrameLayout frameLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_mdegree = findViewById(R.id.degree);
        frameLayout = findViewById(R.id.camera);

        camera = Camera.open();
        ShowCamera showCamera;
        showCamera = new ShowCamera(this, camera);
        frameLayout.addView(showCamera);


        mSensorManager =(SensorManager)getSystemService(Context.SENSOR_SERVICE);

        mSensorManager.registerListener(this, mSensorManager
                        .getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD),
                SensorManager.SENSOR_DELAY_UI);

        mSensorManager.registerListener(this, mSensorManager
                        .getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(mSensor!=null){
            mSensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
        else{
            Toast.makeText(MainActivity.this,"Sensor not supported!",Toast.LENGTH_SHORT);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    float [] mGravity;
    float [] mGeomagnetic;

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
            mGravity = event.values;
        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD)
            mGeomagnetic = event.values;
        if (mGravity != null && mGeomagnetic != null) {
            float R[] = new float[9];
            float I[] = new float[9];
            if (SensorManager.getRotationMatrix(R, I,
                    mGravity, mGeomagnetic)) {
                float orientation[] = new float[3];
                SensorManager.getOrientation(R, orientation);
                float degree = (float) Math.toDegrees(orientation[0]);

                tv_mdegree.setText(Float.toString(degree));

            }
        }
    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
