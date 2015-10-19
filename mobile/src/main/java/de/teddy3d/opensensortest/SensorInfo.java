package de.teddy3d.opensensortest;

import android.util.Log;

import java.util.Map;
import java.util.LinkedHashMap;

/**
 * Contains every information of a sensor card view.
 */
public class SensorInfo {
    // SensorInfo static member values
    protected static String TAG = SensorInfo.class.getSimpleName();

    // SensorInfo information
    private final int nameResId;
    private final int imageResId;
    private final Map<String, String> valueMap;

    /**
     * Create a new SensorInfo object.
     * @param nameResId The name of the sensor as resource ID.
     * @param imageResId The image of the sensor as resource ID.
     */
    protected SensorInfo(final int nameResId, final int imageResId) {
        Log.d(TAG, "SensorInfo constructor");

        this.nameResId = nameResId;
        this.imageResId = imageResId;
        this.valueMap = new LinkedHashMap<>();
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
     * Get a map of all values for the sensor.
     * @return A value map for the sensor.
     */
    protected Map<String, String> getValueMap() {
        Log.d(TAG, "getValueMap");
        return this.valueMap;
    }
}
