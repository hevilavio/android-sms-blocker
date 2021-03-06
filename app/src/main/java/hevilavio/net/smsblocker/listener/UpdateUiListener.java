package hevilavio.net.smsblocker.listener;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.List;

import hevilavio.net.smsblocker.RegisteredUserActivity;
import hevilavio.net.smsblocker.pojo.Sms;

import static hevilavio.net.smsblocker.constants.ExtraConstants.AMOUNT_OF_SMS;
import static hevilavio.net.smsblocker.constants.ExtraConstants.WHO_IS;
import static hevilavio.net.smsblocker.constants.ExtraConstants.SMS_COUNT;

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

        int amountOfReceivedSms = smsList.size();
        toOpen.putExtra(AMOUNT_OF_SMS, amountOfReceivedSms);
        toOpen.putExtra(WHO_IS, SMS_COUNT);

        Log.i(TAG, "enviando amountOfSms para RegisteredUserActivity" +
                ", amountOfSms=" + amountOfReceivedSms);

        // Nao subir activity automaticamente
//        context.startActivity(toOpen);
    }
}
