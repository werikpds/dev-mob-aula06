package aula6.com.br.gps;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class LocationArrayAdapter extends ArrayAdapter<Location> {

    private Context context;
    private List<Location> locations;

    public LocationArrayAdapter(Context context, List<Location> locations) {
        super (context, -1, locations);
        this.context = context;
        this.locations = locations;
    }

    @NonNull
    @Override
    public View getView (int position,
                         @Nullable View convertView,
                         @NonNull ViewGroup parent) {
        Location localAtual = getItem(position);
        LocationViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.list_item, parent, false);
            viewHolder = new LocationViewHolder();
            viewHolder.localImageView = convertView.findViewById(R.id.localImageView);
            viewHolder.latitudeTextView = convertView.findViewById(R.id.latitudeTextView);
            viewHolder.longitudeTextView = convertView .findViewById(R.id.longitudeTextView);
            convertView.setTag(viewHolder);
        }
        viewHolder = (LocationViewHolder) convertView.getTag();

        double latitude = localAtual.getLatitude();
        double longitude = localAtual.getLongitude();


        viewHolder.latitudeTextView.setText("Lat: " + latitude);
        viewHolder.longitudeTextView.setText("Lon: " + longitude);
        viewHolder.localImageView.setImageResource(R.drawable.ic_place_black_24dp);
        return convertView;

        /*
        View view = inflater.inflate(R.layout.list_item, parent, false);

        localIV.setImageResource(R.drawable.ic_place_black_24dp);
//        latitudeTV.setText(String.valueOf(localAtual.getLatitude()));
//        longitudeTV.setText(String.valueOf(localAtual.getLongitude()));

        latitudeTV.setText("Lat: " + String.valueOf(localAtual.getLatitude()));
        longitudeTV.setText("Lon: " + String.valueOf(localAtual.getLongitude()));

        return view;
        */
    }

    @Override
    public int getCount() {
        return locations.size();
    }
}
