package hevilavio.net.smsblocker.listener;

import android.content.Context;
import android.util.Log;

import java.util.List;

import hevilavio.net.smsblocker.json.ArcaneSms;
import hevilavio.net.smsblocker.pojo.Sms;
import hevilavio.net.smsblocker.service.ArcaneServiceTask;

/**
 * Created by hevilavio on 10/5/15.
 */
public class ArcaneSmsListener implements SmsListener{

    private final String TAG = "ArcaneSmsListener";

    public void onSmsReceived(Context context, List<Sms> smsList){

        Log.i(TAG, "Preparando task para envio de sms para Arcane, size=" + smsList.size());
        ArcaneSms[] params = new ArcaneSms[smsList.size()];

        int i = 0;
        for (Sms sms : smsList) {
            ArcaneSms arcaneSms = ArcaneSms.buildFromSms("for test", sms);
            params[i++] = arcaneSms;
        }

        new ArcaneServiceTask().execute(params);

        Log.i(TAG, "Task de envio criada");


        /*
        * TODO:
        *   Persistir sms com flg_sent = 0
        *   Enviar sms para server
        *   Se ok, atualizar flg_sent = 1
        *   Se nok, fazer nada (posteriormente uma thread irá reenviar os sms's "perdidos")
        * **/

    }
}
