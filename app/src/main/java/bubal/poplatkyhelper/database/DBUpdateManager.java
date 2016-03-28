package bubal.poplatkyhelper.database;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import bubal.poplatkyhelper.model.ModelMeasure;

public class DBUpdateManager {

    SQLiteDatabase database;

    public DBUpdateManager(SQLiteDatabase database) {
        this.database = database;
    }


    //The next few methods are needed for updating columns separately
    public void value(long timeStamp, float value) {
        update(DBHelper.MEASURE_VALUE_COLUMN, timeStamp, value);
    }

    public void date(long timeStamp, long date) {
        update(DBHelper.MEASURE_DATE_COLUMN, timeStamp, date);
    }

    public void type(long timeStamp, int type) {
        update(DBHelper.MEASURE_TYPE_COLUMN, timeStamp, type);
    }

    //And this one is for updating the whole measures table
    public void measure(ModelMeasure measure) {
        value(measure.getTimeStamp(), measure.getValue());
        date(measure.getTimeStamp(), measure.getDate());
        type(measure.getTimeStamp(), measure.getMeasureType());
    }


    private void update(String column, long key, float value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(column, value);
        database.update(DBHelper.MEASURES_TABLE, contentValues, DBHelper.MEASURE_TIME_STAMP_COLUMN + " = " + key, null);
    }

    private void update(String column, long key, long value) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(column, value);
        database.update(DBHelper.MEASURES_TABLE, contentValues, DBHelper.MEASURE_TIME_STAMP_COLUMN + " = " + key, null);
    }
}
