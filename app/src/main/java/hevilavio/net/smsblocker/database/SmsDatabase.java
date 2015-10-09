package hevilavio.net.smsblocker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.List;

import hevilavio.net.smsblocker.pojo.Sms;

/**
 * Created by hevilavio on 10/7/15.
 */
public class SmsDatabase extends CustomSQLiteOpenHelper {

    // private final String TAG = "SmsDatabase";

    public static final String TABLE_NAME = "sms";
    public static final String SQL_CREATE_TABLE = "create table " + TABLE_NAME + " (" +
            " id integer primary key AUTOINCREMENT NOT NULL" +
            ", activeUser text" +
            ", fromNumber text" +
            ", body text" +
            ", sentToServer integer" +
            ")";
    public static final String SQL_DELETE_TABLE = "drop table if exists " + TABLE_NAME;

    public SmsDatabase(Context context) {
        super(context);
    }

    @Override
    protected String getTableName() {
        return TABLE_NAME;
    }


    /**
     * @param activeUser - Usuario ativo no momento.
     * */
    public long insertSms(String activeUser, Sms sms){

        if(activeUser == null || sms == null){
            throw new IllegalArgumentException("activeUser=[" + activeUser + "], sms=[" + sms + "]");
        }

        ContentValues values = new ContentValues();
        values.put("activeUser", activeUser);
        values.put("fromNumber", sms.getForm());
        values.put("body", sms.getBody());
        values.put("sentToServer", 0);

        return insert(values);
    }

    public int count() {
        Cursor cursor = getWritableDatabase()
                .rawQuery("select count(1) as count from " + getTableName(), null);


        boolean hasData = cursor.moveToFirst();

        if(!hasData){
            return 0;
        }
        return cursor.getInt(cursor.getColumnIndex("count"));
    }


    public List<Sms> getSmsPendingToSend() {

        List<Sms> list = new ArrayList<>();
        Cursor cursor = getWritableDatabase().query(false,
                getTableName(),
                null,
                "sentToServer = 0",
                null,
                null,
                null,
                null,
                null);

        while (cursor.moveToNext()){
            Sms sms = new Sms(
                    cursor.getInt(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("fromNumber")),
                    cursor.getString(cursor.getColumnIndex("body"))
            );

            list.add(sms);
        }
        return list;
    }
}
