package hevilavio.net.smsblocker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hevilavio on 10/5/15.
 */
public class UserDatabase extends SQLiteOpenHelper{

    private final String TAG = "UserDatabase";

    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "smsblocker.db";

    private final String tableName = "users";


    private final String SQL_CREATE_TABLE = "create table " + tableName + " (" +
            " id integer primary key AUTOINCREMENT NOT NULL" +
            ", name text" +
            ", active integer" +
            ")";
    private final String SQL_DELETE_TABLE = "drop table if exists " + tableName;


    public UserDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "criando database...");
        db.execSQL(SQL_CREATE_TABLE);
        Log.i(TAG, "database criada.");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE);
        onCreate(db);
    }

    public long createUser(String userName){
        Log.i(TAG, "criando usuario...");

        ContentValues values = new ContentValues();
        values.put("name", userName);
        values.put("active", 1);

        return getWritableDatabase() .insert(tableName, null, values);
    }

    public String getActiveUser(){

        Cursor cursor = getWritableDatabase().rawQuery("select name from " + tableName + " where" +
                " active = 1", null);

        boolean hasData = cursor.moveToFirst();
        Log.i(TAG, "hasData=" + hasData);

        if(!hasData){
            return null;
        }
        return cursor.getString(cursor.getColumnIndex("name"));
    }

    public void inactiveUser() {
        getWritableDatabase().execSQL("update " + tableName + " set active = 0 where active = 1");
    }
}
