package de.teddy3d.opensensortest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_main);

        buildSensorRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu");

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        Log.d(TAG, "onOptionsItemSelected");

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Build the recycler view for all sensor.
     */
    private void buildSensorRecyclerView() {
        Log.d(TAG, "buildSensorRecyclerView");

        // Get recycler view
        RecyclerView sensorRecyclerView = (RecyclerView) findViewById(R.id.sensor_recycler_view);

        // Optimize recycler view
        sensorRecyclerView.setHasFixedSize(true);

        // Set recycler view layout manager
        sensorRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set names and images for each sensor
        SensorEnum.ACTIVITY_RECOGNITION.setNameResId(R.string.activity_recognition);
        SensorEnum.ACTIVITY_RECOGNITION.setImageResId(R.mipmap.ic_launcher);
        SensorEnum.ACCELERATION_SENSOR.setNameResId(R.string.acceleration_sensor);
        SensorEnum.ACCELERATION_SENSOR.setImageResId(R.mipmap.ic_launcher);

        // Create a list of sensors from SensorInfo.SensorEnum
        final List<SensorInfo> sensorInfoList = new ArrayList<>();
        for (SensorEnum sensorEnum : SensorEnum.values()) {
            sensorInfoList.add(new SensorInfo(sensorEnum.getNameResId(), sensorEnum.getImageResId()));
        }

        /*
        // Get a sensor entry at a specific position
        final SensorInfo activityRecognition = sensorInfoList.get(
                SensorInfo.SensorEnum.ACTIVITY_RECOGNITION.ordinal()
        );
        // Change values in value map
        Map valueMap = activityRecognition.getValueMap();
        valueMap.put("Key", "Value"); // put will add or update value
        */

        // Set recycler view adapter
        RecyclerView.Adapter sensorRecyclerViewAdapter = new SensorRecyclerViewAdapter(sensorInfoList);
        sensorRecyclerView.setAdapter(sensorRecyclerViewAdapter);
    }
}
