package aula6.com.br.gps;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private static final int REQUEST_CODE_GPS_PERMISSION = 1001;

    private Location location;
    private ArrayList<Location> locations = new ArrayList<>();

    private EditText searchEditText;
    private TextView locationTextView;

    private double latitude;
    private double longitude;
    private String coodenate;

    private LocationDAO locationDAO = new LocationDAO(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(android.location.Location location) {
                System.out.println("\nEntrou no onLocationChanged\n");
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                latitude = lat;
                longitude = lon;
                coodenate = String.format("Lat: %f, Long: %f", lat, lon);
                System.out.println("\nCoordenada: " + coodenate + "\n");

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };

        setContentView(R.layout.activity_main);

    }

    /*
    public void showList (View view) {
        System.out.println("\n Coordenada2: " + coodenate + "\n");
        location = new Location();
        if(historyPos.size() >= 5) {
            historyPos.remove(0);
        }
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        //historyPos.add(location);
        //String coodenate = String.format("Lat: %f,\nLong: %f", location.getLatitude(), location.getLongitude());
        //historyPos.add(String.format("Lat: %f, Long: %f", location.getLatitude(), location.getLongitude()));
        historyPos.add(String.format("Lat: %f, Long: %f", location.getLatitude(), location.getLongitude()));
        System.out.println("\nArray na tela inicial: " + historyPos);
        String lat = String.valueOf(location.getLatitude());
        String lon = String.valueOf(location.getLongitude());
        Intent intent = new Intent (this, LocationListActivity.class);
        intent.putExtra("lat", lat);
        intent.putExtra("lon", lon);
        intent.putExtra("historyPos", historyPos);
        intent.putExtra("coodenate", coodenate);
        startActivity(intent);
    }
    */

    public void showList(List<Location> lo) {
        System.out.println("\nEntrou no ShowList, coordenada recebida: " + coodenate + "\n");
        location = new Location();
        if(lo.size() >= 50) {
            lo.remove(0);
        }
        location.setLatitude(latitude);
        location.setLongitude(longitude);
        lo.add(location);
        System.out.println("\nArray na tela inicial: " + lo);

        locationDAO.insertLocation(location);
/*
        LocationDBHelper dbHelper = new LocationDBHelper(this);

        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(LocalContract.LocationContract.COLUMN_NAME_LATITUDE, latitude);
        values.put(LocalContract.LocationContract.COLUMN_NAME_LONGITUDE, longitude);

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(LocalContract.LocationContract.TABLE_NAME, null, values);
        System.out.println("Insert Na Main: "+ values + "\n" + newRowId);
*/

    }


    public void openList(View view) {
        System.out.println("\nApertou o botão\n" + locations);
        showList(locations);
        System.out.println("\nopenList depois do select: " + locations + "\n");
        Intent intent = new Intent(this, LocationListActivity.class);
        intent.putExtra("locations", locations);
        MainActivity.this.startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        //a permissão já foi dada?
        if (ActivityCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED){
            //somente ativa
            // a localização é obtida via hardware, intervalo de 0 segundos e 0 metros entre atualizações
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                    0, 0, locationListener);
        }
        else{
            //permissão ainda não foi nada, solicita ao usuário
            //quando o usuário responder, o método onRequestPermissionsResult vai ser chamado
            ActivityCompat.requestPermissions(this,
                    new String []{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_GPS_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull
            String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CODE_GPS_PERMISSION){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //permissão concedida, ativamos o GPS
                if (ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                            0, 0, locationListener);
                }
            }
            else{
                //usuário negou, não ativamos
                Toast.makeText(this,
                        getString(R.string.no_gps), Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }
}
