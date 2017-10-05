package com.manojbhadane.sqlcipher.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.manojbhadane.sqlcipher.model.DataModel;

import net.sqlcipher.database.SQLiteDatabase;
import net.sqlcipher.database.SQLiteOpenHelper;

import java.util.ArrayList;

/**
 * Created by manoj.bhadane on 05-10-2017.
 */
public class DbHelper extends SQLiteOpenHelper
{
    private static final String KEY = "pass123";
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "TestDB";

    private static final String COL_ID = "Id";
    private static final String COL_NAME = "Name";
    private static final String TABLE_TEST = "Test";

    private Context mContext;
    private SQLiteDatabase mDb;
    private static DbHelper mInstance = null;

    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    public static synchronized DbHelper getInstance(Context context)
    {
        if (mInstance == null)
        {
            mInstance = new DbHelper(context);
        }
        return mInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase)
    {
        this.mDb = sqLiteDatabase;

        String CREATE_TABLE_TEST = "CREATE TABLE " + TABLE_TEST + " ("
                + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL , "
                + COL_NAME + " TEXT )";

        sqLiteDatabase.execSQL(CREATE_TABLE_TEST);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion)
    {
        for (int i = oldVersion; i < newVersion; i++)
        {
            switch (i)
            {
                case 1:
                    break;
                default:
                    break;
            }
        }
    }

    public SQLiteDatabase getWriteable(Context context)
    {
        mContext = context;
        try
        {
            mDb = this.getWritableDatabase(KEY);
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return mDb;
    }

    public void save(DataModel model)
    {
        mDb = getWriteable(mContext);
        ContentValues values = new ContentValues();
        values.put(COL_NAME, model.getName());

        if (CheckIsDataAlreadyInDBorNot(TABLE_TEST, COL_NAME, "" + model.getName()))
        {
            mDb.updateWithOnConflict(TABLE_TEST, values,
                    COL_NAME + " = '" + model.getName() + "'", null, SQLiteDatabase.CONFLICT_REPLACE);
        } else
        {
            mDb.insert(TABLE_TEST, null, values);
        }
    }

    public ArrayList<DataModel> listAll(DataModel dataModel)
    {
        ArrayList<DataModel> arrayList = new ArrayList<>();
        String query = " SELECT * FROM " + TABLE_TEST + " ORDER BY " + COL_ID;

        Cursor cursor = mDb.rawQuery(query, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            DataModel lModel = new DataModel();
            lModel.setId(cursor.getInt(cursor.getColumnIndex(COL_ID)));
            lModel.setName(cursor.getString(cursor.getColumnIndex(COL_NAME)));
            arrayList.add(lModel);
            cursor.moveToNext();
        }
        cursor.close();
        return arrayList;
    }

    public boolean CheckIsDataAlreadyInDBorNot(String TableName, String dbfield, String fieldValue)
    {
        String Query = "SELECT * FROM " + TableName + " WHERE " + dbfield + " = '" + fieldValue + "'";
        Cursor cursor = mDb.rawQuery(Query, null);
        if (cursor.getCount() <= 0)
        {
            cursor.close();
            return false;
        }
        cursor.close();
        return true;
    }
}
