package hevilavio.net.smsblocker.listener;

import android.content.Context;
import android.util.Log;

import java.util.List;

import hevilavio.net.smsblocker.pojo.Sms;

/**
 * Created by hevilavio on 10/5/15.
 */
public class ExportSmsListener implements SmsListener{

    private final String TAG = "ExportSmsListener";

    public void onSmsReceived(Context context, List<Sms> smsList){

        for (Sms sms : smsList) {
            Log.i(TAG, sms.toString());
        }

        /*
        * TODO:
        *   Persistir sms com flg_sent = 0
        *   Enviar sms para server
        *   Se ok, atualizar flg_sent = 1
        *   Se nok, fazer nada (posteriormente uma thread irá reenviar os sms's "perdidos")
        * **/

    }
}
