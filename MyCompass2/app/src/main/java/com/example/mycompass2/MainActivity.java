package com.example.mycompass2;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView tv_mdegree;
    ImageView iv_marrow;
    private static SensorManager msensorManager;
    private Sensor mSensor;
    private float currentDegree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv_mdegree = findViewById(R.id.degree);
        iv_marrow =findViewById(R.id.iv_arrow);
        msensorManager =(SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensor=msensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
    }



    @Override
    protected void onResume() {
        super.onResume();
        if(mSensor!=null){
            msensorManager.registerListener(this,mSensor,SensorManager.SENSOR_DELAY_FASTEST);
        }
        else{
            Toast.makeText(MainActivity.this,"Sensor not supported!",Toast.LENGTH_SHORT);

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        msensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
    int degree=Math.round(event.values[0]);
    tv_mdegree.setText(Integer.toString(degree)+(char)0*0030);
        RotateAnimation rotateAnimation= new RotateAnimation(currentDegree,-degree, Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        rotateAnimation.setDuration(500);
        rotateAnimation.setFillAfter(true);
        iv_marrow.setAnimation(rotateAnimation);
        currentDegree=-degree;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
