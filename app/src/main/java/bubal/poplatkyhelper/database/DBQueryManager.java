package bubal.poplatkyhelper.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bubal.poplatkyhelper.model.ModelMeasure;

public class DBQueryManager {

    private SQLiteDatabase database;

    public DBQueryManager(SQLiteDatabase database) {
        this.database = database;
    }

    public ModelMeasure getMeasure(long timeStamp) {
        ModelMeasure modelMeasure = null;
        Cursor cursor = database.query(DBHelper.MEASURES_TABLE, null, DBHelper.SELECTION_TIME_STAMP,
                new String[]{Long.toString(timeStamp)}, null, null, null);

        if (cursor.moveToFirst()) {
            float value = cursor.getFloat(cursor.getColumnIndex(DBHelper.MEASURE_VALUE_COLUMN));
            long date = cursor.getLong(cursor.getColumnIndex(DBHelper.MEASURE_DATE_COLUMN));
            int type = cursor.getInt(cursor.getColumnIndex(DBHelper.MEASURE_TYPE_COLUMN));

            modelMeasure = new ModelMeasure(value, date, type, timeStamp);
        }
        cursor.close();

        return modelMeasure;
    }

    public List<ModelMeasure> getMeasures(String selection, String[] selectionArgs, String orderBy) {
        List<ModelMeasure> measures = new ArrayList<>();

        Cursor cursor = database.query(DBHelper.MEASURES_TABLE, null, selection, selectionArgs, null, null, orderBy);

        if (cursor.moveToFirst()) {
            do {
                float value = cursor.getFloat(cursor.getColumnIndex(DBHelper.MEASURE_VALUE_COLUMN));
                long date = cursor.getLong(cursor.getColumnIndex(DBHelper.MEASURE_DATE_COLUMN));
                int type = cursor.getInt(cursor.getColumnIndex(DBHelper.MEASURE_TYPE_COLUMN));
                long timeStamp = cursor.getLong(cursor.getColumnIndex(DBHelper.MEASURE_TIME_STAMP_COLUMN));

                ModelMeasure modelMeasure = new ModelMeasure(value, date, type, timeStamp);
                measures.add(modelMeasure);
            } while (cursor.moveToNext());
        }
        cursor.close();

        return measures;
    }
}
