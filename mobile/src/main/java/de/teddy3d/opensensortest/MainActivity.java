package de.teddy3d.opensensortest;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.location.ActivityRecognition;
import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements ConnectionCallbacks, OnConnectionFailedListener, SensorEventListener {

    public static String TAG = MainActivity.class.getSimpleName();

    private RecyclerView.Adapter sensorRecyclerViewAdapter;
    private final List<SensorInfo> sensorInfoList = new ArrayList<>();

    // Activity recognition member variables
    private static final String KEY_ACTIVITY_RECOGNITION_UPDATE = "key_activity_recognition_update";
    private BroadcastReceiver activityRecognitionReceiver;
    private PendingIntent activityRecognitionIntent;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_main);

        buildSensorRecyclerView();
        buildGoogleApiClient();
        buildActivityRecognitionBroadcast();
    }

    /**
     * Called after onCreate(Bundle) — or after onRestart() when the activity had been stopped, but
     * is now again being displayed to the user. It will be followed by onResume().
     */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");

        googleApiClient.connect();
    }

    /**
     * Called after onRestoreInstanceState(Bundle), onRestart(), or onPause(), for your activity to
     * start interacting with the user. This is a good place to begin animations, open
     * exclusive-access devices (such as the camera), etc.
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    /**
     * Called when you are no longer visible to the user. You will next receive either onRestart(),
     * onDestroy(), or nothing, depending on later user activity.
     */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");

        stopSensors();
    }

    /**
     * Perform any final cleanup before an activity is destroyed. This can happen either because the
     * activity is finishing (someone called finish() on it, or because the system is temporarily
     * destroying this instance of the activity to save space. You can distinguish between these two
     * scenarios with the isFinishing() method.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");

        stopSensors();
    }

    /**
     * Build the recycler view for all sensor.
     */
    private void buildSensorRecyclerView() {
        Log.d(TAG, "buildSensorRecyclerView");

        // Get recycler view
        final RecyclerView sensorRecyclerView = (RecyclerView) findViewById(R.id.sensor_recycler_view);

        // Optimize recycler view
        sensorRecyclerView.setHasFixedSize(true);

        // Set recycler view layout manager
        sensorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set names and images for each sensor
        SensorEnum.ACTIVITY_RECOGNITION.setNameResId(R.string.activity_recognition);
        SensorEnum.ACTIVITY_RECOGNITION.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.ACTIVITY_RECOGNITION.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.ACTIVITY_RECOGNITION.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.ACCELERATION.setNameResId(R.string.acceleration);
        SensorEnum.ACCELERATION.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.ACCELERATION.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.ACCELERATION.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.ACCELERATION_LINEAR.setNameResId(R.string.acceleration_linear);
        SensorEnum.ACCELERATION_LINEAR.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.ACCELERATION_LINEAR.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.ACCELERATION_LINEAR.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.CELL.setNameResId(R.string.cell);
        SensorEnum.CELL.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.CELL.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.CELL.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.GPS.setNameResId(R.string.gps);
        SensorEnum.GPS.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.GPS.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.GPS.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.GPS_ASSISTED.setNameResId(R.string.gps_assisted);
        SensorEnum.GPS_ASSISTED.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.GPS_ASSISTED.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.GPS_ASSISTED.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.GRAVITY.setNameResId(R.string.gravity);
        SensorEnum.GRAVITY.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.GRAVITY.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.GRAVITY.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.GYROSCOPE.setNameResId(R.string.gyroscope);
        SensorEnum.GYROSCOPE.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.GYROSCOPE.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.GYROSCOPE.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.GYROSCOPE_UNCALIBRATED.setNameResId(R.string.gyroscope_uncalibrated);
        SensorEnum.GYROSCOPE_UNCALIBRATED.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.GYROSCOPE_UNCALIBRATED.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.GYROSCOPE_UNCALIBRATED.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.HEART_RATE.setNameResId(R.string.heart_rate);
        SensorEnum.HEART_RATE.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.HEART_RATE.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.HEART_RATE.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.LIGHT.setNameResId(R.string.light);
        SensorEnum.LIGHT.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.LIGHT.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.LIGHT.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.MAGNETIC_FIELD.setNameResId(R.string.magnetic_field);
        SensorEnum.MAGNETIC_FIELD.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.MAGNETIC_FIELD.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.MAGNETIC_FIELD.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.MAGNETIC_FIELD_UNCALIBRATED.setNameResId(R.string.magnetic_field_uncalibrated);
        SensorEnum.MAGNETIC_FIELD_UNCALIBRATED.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.MAGNETIC_FIELD_UNCALIBRATED.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.MAGNETIC_FIELD_UNCALIBRATED.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.ORIENTATION.setNameResId(R.string.orientation);
        SensorEnum.ORIENTATION.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.ORIENTATION.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.ORIENTATION.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.PRESSURE	.setNameResId(R.string.pressure);
        SensorEnum.PRESSURE	.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.PRESSURE.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.PRESSURE.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.PROXIMITY.setNameResId(R.string.proximity);
        SensorEnum.PROXIMITY.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.PROXIMITY.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.PROXIMITY.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.RELATIVE_HUMIDITY.setNameResId(R.string.relative_humidity);
        SensorEnum.RELATIVE_HUMIDITY.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.RELATIVE_HUMIDITY.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.RELATIVE_HUMIDITY.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.ROTATION_VECTOR.setNameResId(R.string.rotation_vector);
        SensorEnum.ROTATION_VECTOR.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.ROTATION_VECTOR.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.ROTATION_VECTOR.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.ROTATION_VECTOR_GAME.setNameResId(R.string.rotation_vector_game);
        SensorEnum.ROTATION_VECTOR_GAME.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.ROTATION_VECTOR_GAME.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.ROTATION_VECTOR_GAME.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.ROTATION_VECTOR_GEOMAGNETIC.setNameResId(R.string.rotation_vector_geomagnetic);
        SensorEnum.ROTATION_VECTOR_GEOMAGNETIC.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.ROTATION_VECTOR_GEOMAGNETIC.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.ROTATION_VECTOR_GEOMAGNETIC.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.SIGNIFICANT_MOTION.setNameResId(R.string.significant_motion);
        SensorEnum.SIGNIFICANT_MOTION.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.SIGNIFICANT_MOTION.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.SIGNIFICANT_MOTION.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.STEP_COUNTER.setNameResId(R.string.step_counter);
        SensorEnum.STEP_COUNTER.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.STEP_COUNTER.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.STEP_COUNTER.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.STEP_DETECTOR.setNameResId(R.string.step_detector);
        SensorEnum.STEP_DETECTOR.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.STEP_DETECTOR.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.STEP_DETECTOR.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.TEMPERATURE.setNameResId(R.string.temperature);
        SensorEnum.TEMPERATURE.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.TEMPERATURE.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.TEMPERATURE.setInfoTwoTextResId(R.string.activity_confidence);
        SensorEnum.TEMPERATURE_AMBIENT.setNameResId(R.string.temperature_ambient);
        SensorEnum.TEMPERATURE_AMBIENT.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.TEMPERATURE_AMBIENT.setInfoOneTextResId(R.string.activity_result);
        SensorEnum.TEMPERATURE_AMBIENT.setInfoTwoTextResId(R.string.activity_confidence);

        // Create a list of sensors from SensorInfo.SensorEnum
        for (final SensorEnum sensorEnum : SensorEnum.values()) {
            sensorInfoList.add(new SensorInfo(sensorEnum.getNameResId(), sensorEnum.getImageResId(), sensorEnum.getInfoOneTextResId(), sensorEnum.getInfoTwoTextResId()));
        }

        // Set recycler view adapter
        sensorRecyclerViewAdapter = new SensorRecyclerViewAdapter(sensorInfoList);
        sensorRecyclerView.setAdapter(sensorRecyclerViewAdapter);
    }

    /**
     * Build the Google API client.
     */
    private void buildGoogleApiClient() {
        Log.d(TAG, "buildGoogleApiClient");

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(ActivityRecognition.API)
                .build();
    }

    /**
     * Build the activity recognition broadcast.
     */
    private void buildActivityRecognitionBroadcast() {
        activityRecognitionReceiver = new BroadcastReceiver() {
            /**
             * This method is called when the BroadcastReceiver is receiving an Intent broadcast.
             *
             * @param context Interface to global information about an application environment.
             * @param intent  An intent is an abstract description of an operation to be performed.
             */
            @Override
            public void onReceive(final Context context, final Intent intent) {
                Log.d(TAG, "onReceive activityRecognitionReceiver");

                final ActivityRecognitionResult activityRecognitionResult = ActivityRecognitionResult.extractResult(intent);

                // If no activity recognition result is available skip it
                if (activityRecognitionResult != null) {
                    final SensorInfo sensorInfo = sensorInfoList.get(SensorEnum.ACTIVITY_RECOGNITION.ordinal());
                    final Map<String, String> valueMap = sensorInfo.getValueMap();

                    final DetectedActivity detectedActivity = activityRecognitionResult.getMostProbableActivity();
                    switch (detectedActivity.getType()) {
                        case DetectedActivity.IN_VEHICLE:
                            Log.d(TAG, "onReceive activityRecognitionReceiver: IN_VEHICLE");
                            valueMap.put("activity_result", "IN_VEHICLE");
                            valueMap.put("activity_confidence", Integer.toString(detectedActivity.getConfidence()));
                            break;
                        case DetectedActivity.ON_BICYCLE:
                            Log.d(TAG, "onReceive activityRecognitionReceiver: ON_BICYCLE");
                            valueMap.put("activity_result", "ON_BICYCLE");
                            valueMap.put("activity_confidence", Integer.toString(detectedActivity.getConfidence()));
                            break;
                        case DetectedActivity.ON_FOOT:
                            Log.d(TAG, "onReceive activityRecognitionReceiver: ON_FOOT");
                            valueMap.put("activity_result", "ON_FOOT");
                            valueMap.put("activity_confidence", Integer.toString(detectedActivity.getConfidence()));
                            break;
                        case DetectedActivity.RUNNING:
                            Log.d(TAG, "onReceive activityRecognitionReceiver: RUNNING");
                            valueMap.put("activity_result", "RUNNING");
                            valueMap.put("activity_confidence", Integer.toString(detectedActivity.getConfidence()));
                            break;
                        case DetectedActivity.STILL:
                            Log.d(TAG, "onReceive activityRecognitionReceiver: STILL");
                            valueMap.put("activity_result", "STILL");
                            valueMap.put("activity_confidence", Integer.toString(detectedActivity.getConfidence()));
                            break;
                        case DetectedActivity.TILTING:
                            Log.d(TAG, "onReceive activityRecognitionReceiver: TILTING");
                            valueMap.put("activity_result", "TILTING");
                            valueMap.put("activity_confidence", Integer.toString(detectedActivity.getConfidence()));
                            break;
                        case DetectedActivity.UNKNOWN:
                            Log.d(TAG, "onReceive activityRecognitionReceiver: UNKNOWN");
                            valueMap.put("activity_result", "UNKNOWN");
                            valueMap.put("activity_confidence", Integer.toString(detectedActivity.getConfidence()));
                            break;
                        case DetectedActivity.WALKING:
                            Log.d(TAG, "onReceive activityRecognitionReceiver: WALKING");
                            valueMap.put("activity_result", "WALKING");
                            valueMap.put("activity_confidence", Integer.toString(detectedActivity.getConfidence()));
                            break;
                        default:
                            Log.w(TAG, "onReceive activityRecognitionReceiver: Unhandled detected activity! " +
                                    "DetectedActivity type: " + detectedActivity.getType());
                            valueMap.put("activity_result", "UNKNOWN");
                            valueMap.put("activity_confidence", Integer.toString(detectedActivity.getConfidence()));
                            break;
                    }

                    final int vehicleConfidence = activityRecognitionResult.getActivityConfidence(DetectedActivity.IN_VEHICLE);
                    final int bicycleConfidence = activityRecognitionResult.getActivityConfidence(DetectedActivity.ON_BICYCLE);
                    final int footConfidence = activityRecognitionResult.getActivityConfidence(DetectedActivity.ON_FOOT);
                    final int runningConfidence = activityRecognitionResult.getActivityConfidence(DetectedActivity.RUNNING);
                    final int stillConfidence = activityRecognitionResult.getActivityConfidence(DetectedActivity.STILL);
                    final int tiltingConfidence = activityRecognitionResult.getActivityConfidence(DetectedActivity.TILTING);
                    final int unknownConfidence = activityRecognitionResult.getActivityConfidence(DetectedActivity.UNKNOWN);
                    final int walkingConfidence = activityRecognitionResult.getActivityConfidence(DetectedActivity.WALKING);

                    valueMap.put("vehicle_confidence", Integer.toString(vehicleConfidence));
                    valueMap.put("bicycle_confidence", Integer.toString(bicycleConfidence));
                    valueMap.put("foot_confidence", Integer.toString(footConfidence));
                    valueMap.put("running_confidence", Integer.toString(runningConfidence));
                    valueMap.put("still_confidence", Integer.toString(stillConfidence));
                    valueMap.put("tilting_confidence", Integer.toString(tiltingConfidence));
                    valueMap.put("unknown_confidence", Integer.toString(unknownConfidence));
                    valueMap.put("walking_confidence", Integer.toString(walkingConfidence));

                    sensorRecyclerViewAdapter.notifyDataSetChanged();
                }
            }
        };
    }

    /**
     * Start all sensors.
     */
    private void startSensors() {
        startActivityRecognitionUpdates();
    }

    /**
     * Stop all sensors.
     */
    private void stopSensors() {
        stopActivityRecognitionUpdates();
        googleApiClient.disconnect();
    }

    /**
     * Method of {@link ConnectionCallbacks} interface.
     * After calling connect(), this method will be invoked asynchronously when the connect request
     * has successfully completed.
     *
     * @param connectionHint Bundle of data provided to clients by Google Play services. May be null
     *                       if no content is provided by the service.
     */
    @Override
    public void onConnected(final Bundle connectionHint) {
        Log.d(TAG, "onConnected");

        startSensors();
    }

    /**
     * Method of {@link ConnectionCallbacks} interface.
     * Called when the client is temporarily in a disconnected state.
     *
     * @param cause The reason for the disconnection. Defined by constants CAUSE_*.
     */
    @Override
    public void onConnectionSuspended(final int cause) {
        Log.d(TAG, "onConnectionSuspended");

        switch (cause) {
            case CAUSE_NETWORK_LOST:
                Log.w(TAG, "onConnectionSuspended: " + "Cause: Network lost (Cause ID: " + cause + ")");
                break;
            case CAUSE_SERVICE_DISCONNECTED:
                Log.w(TAG, "onConnectionSuspended: " + "Cause: Service disconnected (Cause ID: " + cause + ")");
                break;
            default:
                Log.w(TAG, "onConnectionSuspended: " + "Cause: unknown (Cause ID: " + cause + ")");
                break;
        }
    }

    /**
     * Method of {@link OnConnectionFailedListener} interface.
     *
     * @param connectionResult The result of the Google API client connection.
     */
    @Override
    public void onConnectionFailed(final ConnectionResult connectionResult) {
        // TODO: Implement behaviour for a failed  connection
    }

    /**
     * Start activity recognition updates.
     */
    private void startActivityRecognitionUpdates() {
        Log.d(TAG, "startActivityRecognitionUpdates");

        this.registerReceiver(activityRecognitionReceiver, new IntentFilter(KEY_ACTIVITY_RECOGNITION_UPDATE));
        final Intent intent = new Intent(KEY_ACTIVITY_RECOGNITION_UPDATE);
        activityRecognitionIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        ActivityRecognition.ActivityRecognitionApi.requestActivityUpdates(googleApiClient, 0, activityRecognitionIntent);
    }

    /**
     * Stop activity recognition updates.
     */
    private void stopActivityRecognitionUpdates() {
        if (googleApiClient.isConnected()) {
            ActivityRecognition.ActivityRecognitionApi.removeActivityUpdates(googleApiClient, activityRecognitionIntent);
            this.unregisterReceiver(activityRecognitionReceiver);
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

    }
}
