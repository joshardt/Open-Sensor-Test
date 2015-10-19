package de.teddy3d.opensensortest;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class SensorRecyclerViewAdapter extends RecyclerView.Adapter<SensorRecyclerViewAdapter.ViewHolder> {

    public static String TAG = SensorRecyclerViewAdapter.class.getSimpleName();

    private final List<SensorInfo> dataSet;

    // Provide a suitable constructor (depends on the kind of dataSet)
    protected SensorRecyclerViewAdapter(final List<SensorInfo> sensorInfo) {
        Log.d(TAG, "SensorRecyclerViewAdapter constructor");

        this.dataSet = sensorInfo;
    }

    /**
     * Called when RecyclerView needs a new RecyclerView.ViewHolder of the given type to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an
     *               adapter position.
     * @param viewType The view type of the new View.
     * @return A new ViewHolder that holds a View of the given view type.
     */
    @Override
    public SensorRecyclerViewAdapter.ViewHolder onCreateViewHolder(final ViewGroup parent, final int viewType) {
        final CardView cardView = (CardView) LayoutInflater.from(
                parent.getContext()).inflate(R.layout.sensor_card_view, parent, false
        );
        return new ViewHolder(cardView);
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the itemView to reflect the item at the given position.
     *
     * @param viewHolder The ViewHolder which should be updated to represent the contents of the item at
     *               the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {
        final SensorInfo sensorInfo = dataSet.get(position);
        viewHolder.sensorName.setText(sensorInfo.getNameResId());
        viewHolder.sensorImage.setImageResource(sensorInfo.getImageResId());
        final Map<String, String> valueMap = sensorInfo.getValueMap();
        if (valueMap.size() > 1) {
            final String[] values = valueMap.values().toArray(new String[2]);
            viewHolder.sensorInfoOne.setText(values[0]);
            viewHolder.sensorInfoTwo.setText(values[1]);
        }
    }

    /**
     * Returns the total number of items in the data set hold by the adapter.
     *
     * @return The total number of items in this adapter.
     */
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    /**
     * View Holder.
     */
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        private final TextView sensorName;
        private final TextView sensorInfoOne;
        private final TextView sensorInfoTwo;
        private final ImageView sensorImage;

        private ViewHolder(final CardView cardView) {
            super(cardView);
            this.sensorName = (TextView) cardView.findViewById(R.id.sensor_name);
            this.sensorInfoOne = (TextView) cardView.findViewById(R.id.sensor_info_one);
            this.sensorInfoTwo = (TextView) cardView.findViewById(R.id.sensor_info_two);
            this.sensorImage = (ImageView) cardView.findViewById(R.id.sensor_image);
        }
    }
}
