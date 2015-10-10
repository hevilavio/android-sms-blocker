package hevilavio.net.smsblocker.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.Pair;

import java.util.List;

import hevilavio.net.smsblocker.database.SmsDatabase;
import hevilavio.net.smsblocker.database.UserDatabase;
import hevilavio.net.smsblocker.json.ArcaneSms;
import hevilavio.net.smsblocker.pojo.Sms;

/**
 * Created by hevilavio on 10/9/15.
 */
public class SendDataToServerService extends Service {

    private final String TAG = "SendDataToServerService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("SendDataToServerService can't bind!");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "SendDataToServerService.onStartCommand.start");
        int superResponse = super.onStartCommand(intent, flags, startId);

        SmsDatabase smsDatabase = new SmsDatabase(getApplicationContext());
        UserDatabase userDatabase = new UserDatabase(getApplicationContext());

        List<Sms> pendingToSendList = smsDatabase.getSmsPendingToSend();
        String activeUser = userDatabase.getActiveUser();

        Log.i(TAG, "pendingToSendList.size=" + pendingToSendList.size());
        for (Sms pendingToSend : pendingToSendList) {
            ArcaneSms arcaneSms = ArcaneSms.buildFromSms(activeUser, pendingToSend);

            Pair<Integer, ArcaneSms> params = new Pair<>(pendingToSend.getId(), arcaneSms);

            new ArcaneSmsAsynTask(smsDatabase).execute(params);

            /**
             * ArcaneSmsAsynTask é responsável por atualizar o status
             * @see {@link ArcaneSmsAsynTask#onPostExecute(Pair)}
             * */
        }

        Log.i(TAG, "SendDataToServerService.onStartCommand.finish");
        return superResponse;
    }
}
