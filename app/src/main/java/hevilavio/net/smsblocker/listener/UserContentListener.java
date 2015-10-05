package hevilavio.net.smsblocker.listener;

import android.content.Context;
import android.util.Log;

import hevilavio.net.smsblocker.database.UserDatabase;
import hevilavio.net.smsblocker.pojo.Sms;

/**
 * Created by hevilavio on 10/5/15.
 */
public class UserContentListener {

    private final String TAG = "UserContentListener";

    private final UserDatabase userDatabase;

    public UserContentListener(Context context) {
        this.userDatabase = new UserDatabase(context);
    }

    public void onSmsReceived(Sms sms){
        Log.i(TAG, sms.toString());

        /*
        * TODO:
        *   Persistir sms com flg_sent = 0
        *   Enviar sms para server
        *   Se ok, atualizar flg_sent = 1
        *   Se nok, fazer nada (posteriormente uma thread irá reenviar os sms's "perdidos")
        * **/

    }
}
