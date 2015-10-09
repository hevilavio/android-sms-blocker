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
public class SmsBroadcastReceiver extends BroadcastReceiver {

    private final String TAG = "SmsBroadcastReceiver";

    private List<SmsListener> listeners;

    public SmsBroadcastReceiver() {
        this.listeners = new ArrayList<>();

        listeners.add(new ArcaneSmsListener()); // manda os dados para o server
        listeners.add(new UpdateUiListener()); // atualiza UI com # de sms recebidos
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "recebendo mensagens SMS...");

        List<Sms> smsList = convertToSmsList(intent);

        for (SmsListener listener : listeners) {
            listener.onSmsReceived(context, smsList);
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
                    smsList.add(new Sms(0, msgFrom, msgBody));
                }
            }
        }

        return smsList;
    }

}
