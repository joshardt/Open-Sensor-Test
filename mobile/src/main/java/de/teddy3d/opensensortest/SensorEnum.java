package de.teddy3d.opensensortest;

public enum SensorEnum {
    ACTIVITY_RECOGNITION,
    ACCELERATION_SENSOR;

    private int name;
    private int imageResId;

    /**
     * Set the name resource ID of the sensor.
     * @param name The name of the sensor as resource ID.
     */
    protected void setNameResId(final int name) {
        this.name = name;
    }

    /**
     * Get the name of the sensor as resource ID.
     * @return The name of the sensor as resource ID.
     */
    protected int getNameResId() {
        return this.name;
    }

    /**
     * Set the image resource ID of the sensor.
     * @param imageResId The image of the sensor as resource ID.
     */
    protected void setImageResId(final int imageResId) {
        this.imageResId = imageResId;
    }

    /**
     * Get the image of the sensor as resource ID.
     * @return The image of the sensor as resource ID.
     */
    protected int getImageResId() {
        return this.imageResId;
    }
}
