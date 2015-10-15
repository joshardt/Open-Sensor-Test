package de.teddy3d.opensensortest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView sensorList = null;
    private RecyclerView.Adapter sensorListAdapter = null;
    private RecyclerView.LayoutManager sensorListLayoutManager = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorList = (RecyclerView) findViewById(R.id.sensor_list);
        sensorList.setHasFixedSize(true);
        sensorListLayoutManager = new LinearLayoutManager(this);
        sensorList.setLayoutManager(sensorListLayoutManager);

        // Sensor information
        final List<SensorInfo> sensorInfo = new ArrayList<>();
        sensorInfo.add(new SensorInfo("Hello Sensor!"));

        sensorListAdapter = new SensorListAdapter(sensorInfo);
        sensorList.setAdapter(sensorListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
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
}
