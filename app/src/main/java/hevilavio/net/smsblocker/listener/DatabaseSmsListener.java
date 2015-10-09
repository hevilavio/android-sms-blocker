package hevilavio.net.smsblocker.listener;

import android.content.Context;
import android.util.Log;

import java.util.List;

import hevilavio.net.smsblocker.database.SmsDatabase;
import hevilavio.net.smsblocker.database.UserDatabase;
import hevilavio.net.smsblocker.pojo.Sms;

/**
 * Created by hevilavio on 10/5/15.
 */
public class DatabaseSmsListener implements SmsListener{

    private final String TAG = "DatabaseSmsListener";

    public void onSmsReceived(Context context, List<Sms> smsList){

        Log.i(TAG, "onSmsReceived.start, smsListSize=" + smsList.size());

        SmsDatabase smsDatabase = new SmsDatabase(context);

        String activeUser = new UserDatabase(context).getActiveUser();

        for (Sms sms : smsList) {
            smsDatabase.insertSms(activeUser, sms);
        }
        Log.i(TAG, "onSmsReceived.finish");
    }
}
