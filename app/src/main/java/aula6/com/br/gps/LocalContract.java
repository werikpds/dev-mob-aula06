package aula6.com.br.gps;

import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public final class LocalContract {

    private static List<Location> locations;
    private static MainActivity mainActivity = new MainActivity();

    static{
        System.out.println("Entrou no static: ");

        locations = new ArrayList<>();
        locations.add (new Location(
                49.241327,
                123.111914
        ));
    }

    public static String createTableLocation () {
        System.out.println("Create table");
        return String.format(
                Locale.getDefault(),
                "CREATE TABLE IF NOT EXISTS %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s DOUBLE, %s DOUBLE);",
                LocationContract.TABLE_NAME,
                LocationContract.COLUMN_NAME_ID,
                LocationContract.COLUMN_NAME_LATITUDE,
                LocationContract.COLUMN_NAME_LONGITUDE
        );
    }

    // evita que seja instanciada acidentalmente
    private LocalContract() {

    }

    /*
    public static String insertLocal() {
        String template = "INSERT INTO %s (%s, %s) VALUES (%f, %f);";
        StringBuilder sb = new StringBuilder("");
        for (Location location : locations){
            sb.append(
                    String.format(
                            Locale.getDefault(),
                            template,
                            LocationContract.TABLE_NAME,
                            LocationContract.COLUMN_NAME_LATITUDE,
                            LocationContract.COLUMN_NAME_LONGITUDE,
                            location.getLatitude(),
                            location.getLongitude()
                    )
            );
        }
        return sb.toString();
    }
    */


    // insert
    public static String insertLocal() {
        System.out.println("Insert Local no Contract: ");
//        LocationDAO locationDAO = new LocationDAO(this);
//        Location location = locationDao
        String template = String.format(
                Locale.getDefault(),
                "INSERT INTO %s (%s, %s) VALUES",
                LocationContract.TABLE_NAME,
                LocationContract.COLUMN_NAME_LATITUDE,
                LocationContract.COLUMN_NAME_LONGITUDE
        );

        StringBuilder sb = new StringBuilder("");
        for (Location location : locations) {
            Log.e("rafa", location.toString());
            sb.append(
                    String.format(
                            Locale.getDefault(),
                            "(%f, %f);",
                            location.getLatitude(),
                            location.getLongitude()
                    )
            );
        }

        String result = template + sb.toString();
        result = result.substring(0, result.length() - 1);
        result = result.concat(";");

        System.out.println("Insert Local No Contract: " + result);

        return result;
    }


    // classe interna para representar a tabela tb_localizacao
    public static class LocationContract implements BaseColumns {
        public static final String TABLE_NAME = "tb_location";
        public static final String COLUMN_NAME_ID = "id_location";
        public static final String COLUMN_NAME_LATITUDE = "latitude";
        public static final String COLUMN_NAME_LONGITUDE = "longitude";

        // drop table
        public static final String DROP_TABLE = String.format(Locale.getDefault(), "DROP TABLE %s", LocationContract.TABLE_NAME);
    }
}
