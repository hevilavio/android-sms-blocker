package hevilavio.net.smsblocker.service;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by hevilavio on 10/6/15.
 */
public class ArcaneHcTask extends AsyncTask<Void, Void, Boolean> {

    private final String TAG = "ArcaneHcTask";

    private final Context applicationContext;

    public ArcaneHcTask(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    protected Boolean doInBackground(Void... v) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://arcane-stream-8361.herokuapp.com")
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.i(TAG, "hc on arcane, responseStatusCode=" + response.code());

            return response.code() == 200;
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean hasInternet) {
        new UpdateInternetStatusCallback(applicationContext).execute(hasInternet);
    }
}
