package hevilavio.net.smsblocker.listener;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import hevilavio.net.smsblocker.pojo.Sms;

/**
 * Created by hevilavio on 10/5/15.
 */
public class SmsListener extends BroadcastReceiver {

    private final String TAG = "SmsListener";
    private UserContentListener userContentListener;

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i(TAG, "recebendo mensagens SMS...");

        List<Sms> smsList = convertToSmsList(intent);

        for (Sms sms : smsList) {
            getUserContentListener(context).onSmsReceived(sms);
        }
    }

    public List<Sms> convertToSmsList(Intent intent){
        List<Sms> smsList = new ArrayList<>();

        if(intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")){
            Bundle bundle = intent.getExtras();
            if (bundle != null){
                Object[] pdus = (Object[]) bundle.get("pdus");

                SmsMessage[] msgs = new SmsMessage[pdus.length];

                for(int i=0; i<msgs.length; i++){
                    msgs[i] = SmsMessage.createFromPdu((byte[]) pdus[i]);
                    String msgFrom = msgs[i].getOriginatingAddress();
                    String msgBody = msgs[i].getMessageBody();

                    Log.i(TAG, String.format("SMS: msgFrom=%s, body=%s", msgFrom, msgBody));
                    smsList.add(new Sms(msgFrom, msgBody));
                }
            }
        }

        return smsList;
    }

    private UserContentListener getUserContentListener(Context context) {
        if(userContentListener == null){
            userContentListener = new UserContentListener(context);
        }

        return userContentListener;
    }
}
