package hevilavio.net.smsblocker.listener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import hevilavio.net.smsblocker.RegisteredUserActivity;
import hevilavio.net.smsblocker.pojo.Sms;

/**
 * Created by hevilavio on 10/5/15.
 */
public class UpdateUiListener implements SmsListener {

    private final String TAG = "UpdateUiListener";

    @Override
    public void onSmsReceived(Context context, List<Sms> smsList) {

        Intent toOpen = new Intent(context, RegisteredUserActivity.class);
        toOpen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        toOpen.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        int amountOfReceivedSms = getAmountOfReceivedSms(smsList);
        toOpen.putExtra("amountOfSms", amountOfReceivedSms);

        Log.i(TAG, "enviando amountOfSms para RegisteredUserActivity" +
                ", amountOfSms=" + amountOfReceivedSms);

        context.startActivity(toOpen);
    }

    private static int count = 0;
    private int getAmountOfReceivedSms(List<Sms> smsList) {
        return (count++) + smsList.size();
    }
}