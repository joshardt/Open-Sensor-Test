package de.teddy3d.opensensortest;

public enum SensorEnum {
    ACTIVITY_RECOGNITION,
    ACCELERATION,
    ACCELERATION_LINEAR,
    CELL,
    GPS,
    GPS_ASSISTED,
    GRAVITY,
    GYROSCOPE,
    GYROSCOPE_UNCALIBRATED,
    HEART_RATE,
    LIGHT,
    MAGNETIC_FIELD,
    MAGNETIC_FIELD_UNCALIBRATED,
    ORIENTATION,
    PRESSURE,
    PROXIMITY,
    RELATIVE_HUMIDITY,
    ROTATION_VECTOR,
    ROTATION_VECTOR_GAME,
    ROTATION_VECTOR_GEOMAGNETIC,
    SIGNIFICANT_MOTION,
    STEP_COUNTER,
    STEP_DETECTOR,
    TEMPERATURE,
    TEMPERATURE_AMBIENT;

    private int name;
    private int imageResId;
    private int infoOneText;
    private int infoTwoText;

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

    /**
     * Set the info one text resource ID of the sensor.
     * @param infoOneText The name of the sensor as resource ID.
     */
    protected void setInfoOneTextResId(final int infoOneText) {
        this.infoOneText = infoOneText;
    }

    /**
     * Get the info one text of the sensor as resource ID.
     * @return The name of the sensor as resource ID.
     */
    protected int getInfoOneTextResId() {
        return this.infoOneText;
    }

    /**
     * Set the info two text resource ID of the sensor.
     * @param infoTwoText The name of the sensor as resource ID.
     */
    protected void setInfoTwoTextResId(final int infoTwoText) {
        this.infoTwoText = infoTwoText;
    }

    /**
     * Get the info two text of the sensor as resource ID.
     * @return The name of the sensor as resource ID.
     */
    protected int getInfoTwoTextResId() {
        return this.infoTwoText;
    }
}
