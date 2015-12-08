package de.teddy3d.opensensortest;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.HashMap;

public class SensorDataReceiver implements SensorEventListener {

    protected static String TAG = SensorInfo.class.getSimpleName();

    private final HashMap<Integer, SensorInfo> sensorInfoMap;
    private final RecyclerView.Adapter sensorRecyclerViewAdapter;
    private final SensorManager sensorManager;

    private Sensor sensorAcceleration;
    private Sensor sensorAccelerationLinear;
    private Sensor sensorGravity;
    private Sensor sensorGyroscope;
    private Sensor sensorGyroscopeUncalibrated;
    private Sensor sensorLight;
    private Sensor sensorMagneticField;
    private Sensor sensorMagneticFieldUncalibrated;
    private Sensor sensorPressure;
    private Sensor sensorProximity;
    private Sensor sensorRelativeHumidity;
    private Sensor sensorRotationVector;
    private Sensor sensorRotationVectorGame;
    private Sensor sensorRotationVectorGeomagnetic;
    private Sensor sensorSignificantMotion;
    private Sensor sensorStepCounter;
    private Sensor sensorStepDetector;
    private Sensor sensorAmbientTemperature;

    protected  SensorDataReceiver(@NonNull final HashMap<Integer, SensorInfo> sensorInfoMap, @NonNull final RecyclerView.Adapter sensorRecyclerViewAdapter, @NonNull final SensorManager sensorManager) {
        this.sensorInfoMap = sensorInfoMap;
        this.sensorRecyclerViewAdapter = sensorRecyclerViewAdapter;
        this.sensorManager = sensorManager;
        buildSensors();
    }

    private void buildSensors() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            sensorAcceleration = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER, true);
            sensorAccelerationLinear = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION, true);
            sensorGravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY, true);
            sensorGyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE, true);
            sensorGyroscopeUncalibrated = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE_UNCALIBRATED, true);
            sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT, true);
            sensorMagneticField = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD, true);
            sensorMagneticFieldUncalibrated = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED, true);
            sensorPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE, true);
            sensorProximity = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY, true);
            sensorRelativeHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY, true);
            sensorRotationVector = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR, true);
            sensorRotationVectorGame = sensorManager.getDefaultSensor(Sensor.TYPE_GAME_ROTATION_VECTOR, true);
            sensorRotationVectorGeomagnetic = sensorManager.getDefaultSensor(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR, true);
            sensorSignificantMotion = sensorManager.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION, true);
            sensorStepCounter = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER, true);
            sensorStepDetector = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR, true);
            sensorAmbientTemperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE, true);
        }
        else {
            sensorAcceleration = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER).get(0);
            sensorAccelerationLinear = sensorManager.getSensorList(Sensor.TYPE_LINEAR_ACCELERATION).get(0);
            sensorGravity = sensorManager.getSensorList(Sensor.TYPE_GRAVITY).get(0);
            sensorGyroscope = sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE).get(0);
            sensorLight = sensorManager.getSensorList(Sensor.TYPE_LIGHT).get(0);
            sensorMagneticField = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD).get(0);
            sensorPressure = sensorManager.getSensorList(Sensor.TYPE_PRESSURE).get(0);
            sensorProximity = sensorManager.getSensorList(Sensor.TYPE_PROXIMITY).get(0);
            sensorRotationVector = sensorManager.getSensorList(Sensor.TYPE_ROTATION_VECTOR).get(0);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                sensorRelativeHumidity = sensorManager.getSensorList(Sensor.TYPE_RELATIVE_HUMIDITY).get(0);
                sensorAmbientTemperature = sensorManager.getSensorList(Sensor.TYPE_AMBIENT_TEMPERATURE).get(0);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                sensorGyroscopeUncalibrated = sensorManager.getSensorList(Sensor.TYPE_GYROSCOPE_UNCALIBRATED).get(0);
                sensorMagneticFieldUncalibrated = sensorManager.getSensorList(Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED).get(0);
                sensorRotationVectorGame = sensorManager.getSensorList(Sensor.TYPE_GAME_ROTATION_VECTOR).get(0);
                sensorSignificantMotion = sensorManager.getSensorList(Sensor.TYPE_SIGNIFICANT_MOTION).get(0);
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                sensorRotationVectorGeomagnetic = sensorManager.getSensorList(Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR).get(0);
                sensorStepCounter = sensorManager.getSensorList(Sensor.TYPE_STEP_COUNTER).get(0);
                sensorStepDetector = sensorManager.getSensorList(Sensor.TYPE_STEP_DETECTOR).get(0);
            }
        }
    }

    /**
     * Start all sensors from {@link SensorManager}.
     */
    protected void startSensors() {
        if (sensorAcceleration != null)
            sensorManager.registerListener(this, sensorAcceleration, 0);

        if (sensorAccelerationLinear != null)
            sensorManager.registerListener(this, sensorAccelerationLinear, 0);
    }

    /**
     * Stop all sensors from {@link SensorManager}
     */
    protected void stopSensors() {
        try {
            sensorManager.unregisterListener(this);
        }
        catch (final Exception exception) {
            // The sensor manager was not registered.
            // There is nothing to do in that case.
            // Everything is fine.
        }
    }

    /**
     * Method of {@link SensorEventListener}.<br>
     * Called when sensor values have changed.
     *
     * @param sensorEvent This class represents a {@link Sensor} event and holds information such as the
     *                    sensor's type, the time-stamp, accuracy and of course the sensor's data.
     */
    @Override
    public void onSensorChanged(final SensorEvent sensorEvent) {
        Log.d(TAG, "onSensorChanged: " + sensorEvent.sensor.getName());

        // Check for right sensor
        if (sensorEvent.sensor == sensorAcceleration) {
            final SensorInfo sensorInfo = sensorInfoMap.get(SensorInfo.ACCELERATION);
            //sensorInfo.addOrUpdateValue(R.string.accelerationValue...);
            // TODO: Update UI
        }
        else {
            Log.w(TAG, "onSensorChanged: Unhandled sensor " + sensorEvent.sensor.getName());
        }
    }

    /**
     * Method of {@link SensorEventListener}.<br>
     * Called when the accuracy of the registered sensor has changed.
     *
     * @param sensor The corresponding sensor.
     * @param accuracy The new accuracy of this sensor, one of SensorManager.SENSOR_STATUS_*
     */
    @Override
    public void onAccuracyChanged(final Sensor sensor, final int accuracy) {
        Log.d(TAG, "onAccuracyChanged");

        switch (accuracy) {
            case SensorManager.SENSOR_STATUS_ACCURACY_HIGH:
                Log.d(TAG, "onAccuracyChanged: " + sensor.getName() + " sensor accuracy is high.");
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_MEDIUM:
                Log.d(TAG, "onAccuracyChanged: " + sensor.getName() + " sensor accuracy is medium.");
                break;
            case SensorManager.SENSOR_STATUS_ACCURACY_LOW:
                Log.d(TAG, "onAccuracyChanged: " + sensor.getName() + " sensor accuracy is low.");
                break;
            case SensorManager.SENSOR_STATUS_UNRELIABLE:
                Log.d(TAG, "onAccuracyChanged: " + sensor.getName() + " sensor is unreliable.");
                break;
            default:
                Log.w(TAG, "onAccuracyChanged: " + sensor.getName() + " sensor accuracy status unknown.");
                break;
        }
    }
}
