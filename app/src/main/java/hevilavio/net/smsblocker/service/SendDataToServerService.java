package hevilavio.net.smsblocker.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

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
        Log.i(TAG, "Startando SendDataToServerService...");
        return super.onStartCommand(intent, flags, startId);
    }
}
