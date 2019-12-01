package aula6.com.br.gps;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocationDBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "locations.db";
    private static final int DB_VERSION = 1;

    LocationDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    /*
    // testando novo metodo
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + LocalContract.LocationContract.TABLE_NAME + " (" +
                    LocalContract.LocationContract._ID + " INTEGER PRIMARY KEY," +
                    LocalContract.LocationContract.COLUMN_NAME_LATITUDE + " DOUBLE," +
                    LocalContract.LocationContract.COLUMN_NAME_LONGITUDE + " DOUBLE)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + LocalContract.LocationContract.TABLE_NAME;
    */

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("DBHelper onCreate");
        db.execSQL(LocalContract.createTableLocation());
        db.execSQL(LocalContract.insertLocal());

        //db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("DBHelper onUpgrade");
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(LocalContract.LocationContract.DROP_TABLE);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("DBHelper onDowngrade");
        onUpgrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        /*
        db.execSQL(LocalContract.LocationContract.DROP_TABLE);
        onCreate(db);
        */
    }
}
