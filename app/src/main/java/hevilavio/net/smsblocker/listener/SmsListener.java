package hevilavio.net.smsblocker.listener;

import android.content.Context;

import java.util.List;

import hevilavio.net.smsblocker.pojo.Sms;

/**
 * Created by hevilavio on 10/5/15.
 */
public interface SmsListener {

    void onSmsReceived(Context context, List<Sms> smsList);
}
