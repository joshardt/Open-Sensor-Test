package de.teddy3d.opensensortest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.android.gms.location.ActivityRecognitionResult;
import com.google.android.gms.location.DetectedActivity;

public class ActivityRecognitionReceiver extends BroadcastReceiver {

    public static String TAG = ActivityRecognitionReceiver.class.getSimpleName();

    private final SensorInfo sensorInfo;
    private final RecyclerView.Adapter sensorRecyclerViewAdapter;

    public ActivityRecognitionReceiver(@NonNull final SensorInfo sensorInfo, @NonNull final RecyclerView.Adapter sensorRecyclerViewAdapter) {
        this.sensorInfo = sensorInfo;
        this.sensorRecyclerViewAdapter = sensorRecyclerViewAdapter;
    }

    /**
     * This method is called when the BroadcastReceiver is receiving an Intent broadcast.
     *
     * @param context The Context in which the receiver is running.
     * @param intent  The Intent being received.
     */
    @Override
    public void onReceive(final Context context, final Intent intent) {
        Log.d(TAG, "onReceive activityRecognitionReceiver");

        final ActivityRecognitionResult activityRecognitionResult = ActivityRecognitionResult.extractResult(intent);

        // If no activity recognition result is available skip it
        if (activityRecognitionResult != null) {
            final DetectedActivity detectedActivity = activityRecognitionResult.getMostProbableActivity();
            final int confidence = detectedActivity.getConfidence();

            switch (detectedActivity.getType()) {
                case DetectedActivity.IN_VEHICLE:
                    Log.d(TAG, "onReceive activityRecognitionReceiver: IN_VEHICLE");
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_recognized_activity, context.getString(R.string.activity_recognition_in_vehicle));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_confidence, Integer.toString(confidence));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_in_vehicle, Integer.toString(confidence));
                    break;
                case DetectedActivity.ON_BICYCLE:
                    Log.d(TAG, "onReceive activityRecognitionReceiver: ON_BICYCLE");
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_recognized_activity, context.getString(R.string.activity_recognition_on_bicycle));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_confidence, Integer.toString(confidence));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_on_bicycle, Integer.toString(confidence));
                    break;
                case DetectedActivity.ON_FOOT:
                    Log.d(TAG, "onReceive activityRecognitionReceiver: ON_FOOT");
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_recognized_activity, context.getString(R.string.activity_recognition_on_foot));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_confidence, Integer.toString(confidence));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_on_foot, Integer.toString(confidence));
                    break;
                case DetectedActivity.RUNNING:
                    Log.d(TAG, "onReceive activityRecognitionReceiver: RUNNING");
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_recognized_activity, context.getString(R.string.activity_recognition_running));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_confidence, Integer.toString(confidence));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_running, Integer.toString(confidence));
                    break;
                case DetectedActivity.STILL:
                    Log.d(TAG, "onReceive activityRecognitionReceiver: STILL");
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_recognized_activity, context.getString(R.string.activity_recognition_still));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_confidence, Integer.toString(confidence));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_still, Integer.toString(confidence));
                    break;
                case DetectedActivity.TILTING:
                    Log.d(TAG, "onReceive activityRecognitionReceiver: TILTING");
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_recognized_activity, context.getString(R.string.activity_recognition_tilting));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_confidence, Integer.toString(confidence));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_tilting, Integer.toString(confidence));
                    break;
                case DetectedActivity.UNKNOWN:
                    Log.d(TAG, "onReceive activityRecognitionReceiver: UNKNOWN");
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_recognized_activity, context.getString(R.string.activity_recognition_unknown));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_confidence, Integer.toString(confidence));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_unknown, Integer.toString(confidence));
                    break;
                case DetectedActivity.WALKING:
                    Log.d(TAG, "onReceive activityRecognitionReceiver: WALKING");
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_recognized_activity, context.getString(R.string.activity_recognition_walking));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_confidence, Integer.toString(confidence));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_walking, Integer.toString(confidence));
                    break;
                default:
                    Log.w(TAG, "onReceive activityRecognitionReceiver: Unhandled detected activity! " +
                            "DetectedActivity type: " + detectedActivity.getType());
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_recognized_activity, context.getString(R.string.activity_recognition_unknown));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_confidence, Integer.toString(confidence));
                    sensorInfo.addOrUpdateValue(R.string.activity_recognition_unknown, Integer.toString(confidence));
                    break;
            }

            sensorRecyclerViewAdapter.notifyItemChanged(SensorInfo.ACTIVITY_RECOGNITION);
        }
    }
}
