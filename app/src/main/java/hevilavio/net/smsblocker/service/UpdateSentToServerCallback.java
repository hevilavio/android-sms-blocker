package hevilavio.net.smsblocker.service;

import android.util.Log;
import android.util.Pair;

import hevilavio.net.smsblocker.database.SmsDatabase;

/**
 * Responsavel por atualizar a flag 'sentToServer=1' de um Sms específico.
 *
 * Created by hevilavio on 10/9/15.
 */
public class UpdateSentToServerCallback implements Callback<Pair<Integer, Boolean>> {

    private final String TAG = "UpdateSentToServerAct";
    private final SmsDatabase smsDatabase;

    public UpdateSentToServerCallback(SmsDatabase smsDatabase) {
        this.smsDatabase = smsDatabase;
    }

    @Override
    public void execute(Pair<Integer, Boolean> result) {
        int smsId = result.first;
        Boolean sentWithSuccess = result.second;

        if(sentWithSuccess){
            Log.i(TAG, "onPostExecute.update, smsId=" + smsId);
            smsDatabase.updateSentToServerWithSuccess(smsId);
        }
    }
}
