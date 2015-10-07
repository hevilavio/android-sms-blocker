package hevilavio.net.smsblocker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by hevilavio on 10/5/15.
 */
public class UserDatabase extends CustomSQLiteOpenHelper {

//    private final String TAG = "UserDatabase";

    public static final String TABLE_NAME = "user";
    public static final String SQL_CREATE_TABLE = "create table " + TABLE_NAME + " (" +
            " id integer primary key AUTOINCREMENT NOT NULL" +
            ", name text" +
            ", active integer" +
            ")";
    public static final String SQL_DELETE_TABLE = "drop table if exists " + TABLE_NAME;


    public UserDatabase(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }

    public long createUser(String userName){
        ContentValues values = new ContentValues();
        values.put("name", userName);
        values.put("active", 1);

        return insert(values);
    }

    public String getActiveUser(){

        Cursor cursor = getWritableDatabase()
                .rawQuery("select name from " + getTableName() + " where active = 1", null);

        boolean hasData = cursor.moveToFirst();

        if(!hasData){
            return null;
        }
        return cursor.getString(cursor.getColumnIndex("name"));
    }

    public void inactiveUser() {
        getWritableDatabase().execSQL("update " + getTableName() + " set active = 0 where active = 1");
    }
}
