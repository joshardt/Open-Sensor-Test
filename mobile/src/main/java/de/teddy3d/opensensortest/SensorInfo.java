package de.teddy3d.opensensortest;

import android.util.Log;

import java.util.LinkedHashMap;

/**
 * Contains every information of a sensor card view.
 */
public class SensorInfo {

    protected static String TAG = SensorInfo.class.getSimpleName();

    protected static int ACTIVITY_RECOGNITION = 0;
    protected static int ACCELERATION = 1;
    protected static int ACCELERATION_LINEAR = 2;
    protected static int CELL = 3;
    protected static int GPS = 4;
    protected static int GPS_ASSISTED = 5;
    protected static int GRAVITY = 6;
    protected static int GYROSCOPE = 7;
    protected static int GYROSCOPE_UNCALIBRATED = 8;
    protected static int LIGHT = 9;
    protected static int MAGNETIC_FIELD = 10;
    protected static int MAGNETIC_FIELD_UNCALIBRATED = 11;
    protected static int PRESSURE = 12;
    protected static int PROXIMITY = 13;
    protected static int RELATIVE_HUMIDITY = 14;
    protected static int ROTATION_VECTOR = 15;
    protected static int ROTATION_VECTOR_GAME = 16;
    protected static int ROTATION_VECTOR_GEOMAGNETIC = 17;
    protected static int SIGNIFICANT_MOTION = 18;
    protected static int STEP_COUNTER = 19;
    protected static int STEP_DETECTOR = 20;
    protected static int TEMPERATURE_AMBIENT = 21;

    // SensorInfo information
    private final int nameResId;
    private final int imageResId;
    private final LinkedHashMap<Integer, String> values;

    /**
     * Create a new SensorInfo object.
     * @param nameResId The name of the sensor as resource ID.
     * @param imageResId The image of the sensor as resource ID.
     */
    protected SensorInfo(final int nameResId, final int imageResId) {
        Log.d(TAG, "SensorInfo constructor");

        this.nameResId = nameResId;
        this.imageResId = imageResId;
        this.values = new LinkedHashMap<>();
    }

    /**
     * Get the name of the sensor as resource ID.
     * @return The name of the sensor as resource ID.
     */
    protected int getNameResId() {
        Log.d(TAG, "getNameResId");
        return this.nameResId;
    }

    /**
     * Get the image of the sensor as resource ID.
     * @return The image of the sensor as resource ID.
     */
    protected int getImageResId() {
        Log.d(TAG, "getImageResId");
        return this.imageResId;
    }

    /**
     * Add or update a value for this sensor.
     * @param nameResId The name of the value as resource ID.
     * @param value The actual value as String.
     */
    protected void addOrUpdateValue(final int nameResId, final String value) {
        Log.d(TAG, "getValues");
        this.values.put(nameResId, value);
    }

    /**
     * Get a map of all values for this sensor.
     * @return A value map for the sensor.
     */
    protected LinkedHashMap<Integer, String> getValues() {
        Log.d(TAG, "getValues");
        return this.values;
    }
}
