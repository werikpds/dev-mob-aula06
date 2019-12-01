package aula6.com.br.gps;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LocationDAO {
    private Context context;

    public LocationDAO (Context context) {
        this.context = context;
    }

    public List<Location> search() {
        LocationDBHelper dbHelper = new LocationDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Location> locations = new ArrayList<>();
        String command = String.format(
                Locale.getDefault(),
                "SELECT * FROM %s",
                LocalContract.LocationContract.TABLE_NAME
        );
        Cursor cursor = db.rawQuery(command, null);
        while (cursor.moveToNext()) {
            int idLocation = cursor.getInt(
                    cursor.getColumnIndex(
//                            String.format(
//                                    Locale.getDefault(),
//                                    "%s.%s",
//                                    LocalContract.LocationContract.TABLE_NAME,
                            LocalContract.LocationContract.COLUMN_NAME_ID
                    )
            );
//            );
            double latitude = cursor.getDouble(
                    cursor.getColumnIndex(
//                            String.format(
//                                    Locale.getDefault(),
//                                    "%s.%s",
//                                    LocalContract.LocationContract.TABLE_NAME,
                            LocalContract.LocationContract.COLUMN_NAME_LATITUDE
                    )
            );
//            );
            double longitude = cursor.getDouble(
                    cursor.getColumnIndex(
//                            String.format(
//                                    Locale.getDefault(),
//                                    "%s.%s",
//                                    LocalContract.LocationContract.TABLE_NAME,
                            LocalContract.LocationContract.COLUMN_NAME_LONGITUDE
                    )
            );
//            );
            Location location = new Location(idLocation, latitude, longitude);
            System.out.println("Entrou em search: " + locations);
            locations.add(location);
            System.out.println("Search: " + locations);
        }
        cursor.close();
        db.close();
        dbHelper.close();
        return locations;
    }

    public List<Location> searchLimit() {
        System.out.println("Entrou no searchLimit");
        LocationDBHelper dbHelper = new LocationDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<Location> locations = new ArrayList<>();
        String command = String.format(
                Locale.getDefault(),
                "SELECT * FROM %s ORDER BY id_location DESC LIMIT 50",
                LocalContract.LocationContract.TABLE_NAME
        );
        Cursor cursor = db.rawQuery(command, null);
        while (cursor.moveToNext()) {
            int idLocation = cursor.getInt(
                    cursor.getColumnIndex(
//                            String.format(
//                                    Locale.getDefault(),
//                                    "%s.%s",
//                                    LocalContract.LocationContract.TABLE_NAME,
                            LocalContract.LocationContract.COLUMN_NAME_ID
                    )
            );
//            );
            double latitude = cursor.getDouble(
                    cursor.getColumnIndex(
//                            String.format(
//                                    Locale.getDefault(),
//                                    "%s.%s",
//                                    LocalContract.LocationContract.TABLE_NAME,
                            LocalContract.LocationContract.COLUMN_NAME_LATITUDE
                    )
            );
//            );
            double longitude = cursor.getDouble(
                    cursor.getColumnIndex(
//                            String.format(
//                                    Locale.getDefault(),
//                                    "%s.%s",
//                                    LocalContract.LocationContract.TABLE_NAME,
                            LocalContract.LocationContract.COLUMN_NAME_LONGITUDE
                    )
            );
//            );
            Location location = new Location(idLocation, latitude, longitude);
            System.out.println("Locations no SearchLimit antes: " + locations);
            locations.add(location);
            System.out.println("Locations no SearchLimit depois: " + locations);
        }
        cursor.close();
        db.close();
        dbHelper.close();
        return locations;
    }

    public Location insertLocation(Location location) {
        System.out.println("Entrou em insert: ");
        LocationDBHelper dbHelper = new LocationDBHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        //Location location = new Location();
        String template = "INSERT INTO %s (%s, %s) VALUES (%f, %f);";
        String command = String.format(
                Locale.getDefault(),
                template,
                LocalContract.LocationContract.TABLE_NAME,
                LocalContract.LocationContract.COLUMN_NAME_LATITUDE,
                LocalContract.LocationContract.COLUMN_NAME_LONGITUDE,
                location.getLatitude(),
                location.getLongitude()
        );

        // Gets the data repository in write mode
        db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(LocalContract.LocationContract.COLUMN_NAME_LATITUDE, location.getLatitude());
        values.put(LocalContract.LocationContract.COLUMN_NAME_LONGITUDE, location.getLongitude());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(LocalContract.LocationContract.TABLE_NAME, null, values);
        System.out.println("\nInsert values: " + values +
                            "\nInsert location" + location +
                            "\nId da newRow" + newRowId);
        db.close();
        dbHelper.close();
        return location;
    }

}
