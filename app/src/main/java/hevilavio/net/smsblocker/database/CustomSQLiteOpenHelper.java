package hevilavio.net.smsblocker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by hevilavio on 10/7/15.
 */
public abstract class CustomSQLiteOpenHelper extends SQLiteOpenHelper {

    private final String TAG = "CustomSQLiteOpenHelper";
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "smsblocker.db";

    public CustomSQLiteOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db, UserDatabase.TABLE_NAME, UserDatabase.SQL_CREATE_TABLE);
        createTable(db, SmsDatabase.TABLE_NAME, SmsDatabase.SQL_CREATE_TABLE);
    }

    private void createTable(SQLiteDatabase db, String tableName, String sqlCreate) {
        Log.i(TAG, "criando tabela [" + tableName + "]...");
        db.execSQL(sqlCreate);
        Log.i(TAG, "tabela [" + tableName + "] criada");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "upgrade [" + getTableName() + "]...");

        db.execSQL(UserDatabase.SQL_DELETE_TABLE);
        db.execSQL(SmsDatabase.SQL_DELETE_TABLE);

        onCreate(db);
    }

    public String hc(){
        return getWritableDatabase().isDatabaseIntegrityOk() ? "OK" : "ERRO";
    }

    public long insert(ContentValues values) {
        long id = getWritableDatabase().insert(getTableName(), null, values);
        Log.i(TAG, "insert em [" + getTableName() + "], id=[" + id + "]");

        return id;
    }

    protected abstract String getTableName();
}
