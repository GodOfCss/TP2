package ca.csfoy.tp2.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class GoDatabaseFactory extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MyFirstDatabase";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_TABLE_SQL =
            "CREATE TABLE goMove (" +
                    "NO_PLAY INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "COLOR TEXT," +
                    "STONE TEXT)";
    public static final String DROP_TABLE_SQL =
            "DROP TABLE IF EXISTS goMove";

    public static final String SELECT_ALL_OF_SQL = "" +
            "SELECT" +
            " NO_PLAY," +
            " COLOR," +
            " STONE" +
            " FROM" +
            " goMove;";

    public GoDatabaseFactory(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
//Faire ceci pour chaque table que vous avez.
        database.execSQL(CREATE_TABLE_SQL);
    }
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
//Faire ceci pour chaque table que vous avez.
        database.execSQL(DROP_TABLE_SQL);
        onCreate(database);
    }

}
