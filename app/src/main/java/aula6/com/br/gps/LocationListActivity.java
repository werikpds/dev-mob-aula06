package aula6.com.br.gps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LocationListActivity extends AppCompatActivity {

    private ListView locationsLV;
    private String coordenate, lat, lon;
    private List<Location> list = new ArrayList<>();
    private ArrayList<String> historyPos;

    private Location teste;
    private List<Location> locations;

    private LocationDAO locationDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_locations_list);
        locationDAO = new LocationDAO(this);
        locationsLV = findViewById(R.id.locationsListView);
        Intent origemIntent = getIntent();
//        lat = origemIntent.getStringExtra("lat");
//        lon = origemIntent.getStringExtra("lon");
//        coordenate = origemIntent.getStringExtra("coordenate");
//        historyPos = origemIntent.getStringArrayListExtra("historyPos");

        //teste = getIntent().getExtras().getParcelable("local");
        locations = (ArrayList) origemIntent.getSerializableExtra("locations");

//        final List<Location> locations = this.locations;
        List<Location> locations = locationDAO.searchLimit();
        LocationArrayAdapter adapter =
                new LocationArrayAdapter(this, locations);
        System.out.println("Ver o Array no ListActivity: " + locations);
        locationsLV.setAdapter(adapter);
        locationsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String locaisList = locationsLV.getItemAtPosition(position).toString();
                String lat = locaisList.split(",")[0].split(":")[1];
                String lon = locaisList.split(",")[1].split(":")[1];

                Uri gmmIntentUri = Uri.parse(String.format("geo:%s,%s", lat, lon));
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
            }
        });

        Toast.makeText(this, "Salvo", Toast.LENGTH_LONG).show();
    }

    public void sair(View view) {
        finish();
    }

}