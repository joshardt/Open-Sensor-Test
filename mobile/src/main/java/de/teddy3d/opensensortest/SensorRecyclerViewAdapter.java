package de.teddy3d.opensensortest;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Map;
import java.util.HashMap;

public class SensorRecyclerViewAdapter extends RecyclerView.Adapter<SensorRecyclerViewAdapter.ViewHolder>
    implements View.OnClickListener {

    public static String TAG = SensorRecyclerViewAdapter.class.getSimpleName();

    private final Context context;
    private final RecyclerView sensorRecyclerView;
    private final Map<Integer, SensorInfo> dataSet;

    // Provide a suitable constructor (depends on the kind of dataSet)
    protected SensorRecyclerViewAdapter(final Context context, final RecyclerView sensorRecyclerView, final HashMap<Integer, SensorInfo> sensorInfo) {
        Log.d(TAG, "SensorRecyclerViewAdapter constructor");

        this.context = context;
        this.sensorRecyclerView = sensorRecyclerView;
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
        cardView.setOnClickListener(this);
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
        final HashMap<Integer, String> values = sensorInfo.getValues();
        byte counter = 0;
        for (Map.Entry<Integer, String> entry : values.entrySet()) {
            if (counter == 0) {
                viewHolder.sensorInfoOneText.setText(context.getText(entry.getKey()));
                viewHolder.sensorInfoOneValue.setText(entry.getValue());
                counter++;
            }
            else if (counter == 1) {
                viewHolder.sensorInfoTwoText.setText(context.getText(entry.getKey()));
                viewHolder.sensorInfoTwoValue.setText(entry.getValue());
                counter++;
            }
            else {
                break;
            }
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
     * Called when a view has been clicked.
     *
     * @param view The view that was clicked.
     */
    @Override
    public void onClick(final View view) {
        int position = sensorRecyclerView.indexOfChild(view);
        Log.d(TAG, "Clicked on position " + position);

        // TODO: Start new fragment and pass (every value of) sensorInfo
        // SensorInfo sensorInfo = dataSet.get(position);
    }

    /**
     * View Holder.
     */
    protected static class ViewHolder extends RecyclerView.ViewHolder {
        private final ImageView sensorImage;
        private final TextView sensorName;
        private final TextView sensorInfoOneText;
        private final TextView sensorInfoOneValue;
        private final TextView sensorInfoTwoText;
        private final TextView sensorInfoTwoValue;

        private ViewHolder(final CardView cardView) {
            super(cardView);
            this.sensorImage = (ImageView) cardView.findViewById(R.id.sensor_image);
            this.sensorName = (TextView) cardView.findViewById(R.id.sensor_name);
            this.sensorInfoOneText = (TextView) cardView.findViewById(R.id.sensor_info_one_text);
            this.sensorInfoOneValue = (TextView) cardView.findViewById(R.id.sensor_info_one_value);
            this.sensorInfoTwoText = (TextView) cardView.findViewById(R.id.sensor_info_two_text);
            this.sensorInfoTwoValue = (TextView) cardView.findViewById(R.id.sensor_info_two_value);
        }
    }
}
