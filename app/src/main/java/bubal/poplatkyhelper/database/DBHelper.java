package bubal.poplatkyhelper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import bubal.poplatkyhelper.model.ModelMeasure;

public class DBHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "poplatkyhelper_database";

    public static final String MEASURES_TABLE = "measures_table";

    public static final String MEASURE_VALUE_COLUMN = "measure_value";
    public static final String MEASURE_DATE_COLUMN = "measure_date";
    public static final String MEASURE_TYPE_COLUMN = "measure_type";
    public static final String MEASURE_TIME_STAMP_COLUMN = "measure_time_stamp";

    private static final String MEASURES_TABLE_CREATE_SCRIPT = "CREATE TABLE "
            + MEASURES_TABLE + " (" + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MEASURE_VALUE_COLUMN + " FLOAT NOT NULL, " + MEASURE_DATE_COLUMN + " LONG, "
            + MEASURE_TYPE_COLUMN + " INTEGER, " + MEASURE_TIME_STAMP_COLUMN + " LONG);";

    public static final String SELECTION_TIME_STAMP = MEASURE_TIME_STAMP_COLUMN + " = ?";


    private DBQueryManager queryManager;
    private DBUpdateManager updateManager;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        queryManager = new DBQueryManager(getReadableDatabase());
        updateManager = new DBUpdateManager(getWritableDatabase());
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(MEASURES_TABLE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE " + MEASURES_TABLE);
        onCreate(db);
    }

    public void saveMeasure(ModelMeasure measure) {
        ContentValues newValues = new ContentValues();

        newValues.put(MEASURE_VALUE_COLUMN, measure.getValue());
        newValues.put(MEASURE_DATE_COLUMN, measure.getDate());
        newValues.put(MEASURE_TYPE_COLUMN, measure.getMeasureType());
        newValues.put(MEASURE_TIME_STAMP_COLUMN, measure.getTimeStamp());

        getWritableDatabase().insert(MEASURES_TABLE, null, newValues);
    }

    public DBQueryManager query() {
        return queryManager;
    }

    public DBUpdateManager update() {
        return updateManager;
    }

    public void removeMeasure(long timeStamp) {
        getWritableDatabase().delete(MEASURES_TABLE, SELECTION_TIME_STAMP, new String[]{Long.toString(timeStamp)});
    }
}
