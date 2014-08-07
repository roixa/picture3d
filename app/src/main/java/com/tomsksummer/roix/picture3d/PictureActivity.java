package com.tomsksummer.roix.picture3d;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.tomsksummer.roix.picture3d.GLEngine.PictureSurfaceView;
import com.tomsksummer.roix.picture3d.GLEngine.mSurfaceView;
import com.tomsksummer.roix.picture3d.R;

public class PictureActivity extends Activity {

    private PictureSurfaceView mGLSurfaceView;
    private SensorManager sensorManager;
    private Sensor sensorAccel;//accelerator sensor

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        sensorAccel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        mGLSurfaceView = new PictureSurfaceView(this);

        setContentView(mGLSurfaceView);
    }


    @Override
    protected void onResume()
    {
        // Вызывается GL surface view's onResume() когда активити переходит onResume().
        super.onResume();
        sensorManager.registerListener(listener, sensorAccel,
                SensorManager.SENSOR_DELAY_NORMAL);

        mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause()
    {
        // Вызывается GL surface view's onPause() когда наше активити onPause().
        super.onPause();
        mGLSurfaceView.onPause();
    }

    float[] valuesAccel = new float[3];


    SensorEventListener listener = new SensorEventListener() {

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            int type=event.sensor.getType();
            int pref=Sensor.TYPE_ACCELEROMETER;
            if(type==pref){

                for (int i = 0; i < 3; i++)    valuesAccel[i] = event.values[i];

                mGLSurfaceView.renderer.base.setAcc(valuesAccel[0],valuesAccel[1],valuesAccel[2]);
                mGLSurfaceView.requestRender();
            }
        }

    };
}
