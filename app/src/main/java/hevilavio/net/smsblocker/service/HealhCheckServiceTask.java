package hevilavio.net.smsblocker.service;

import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

/**
 * Created by hevilavio on 10/6/15.
 */
public class HealhCheckServiceTask extends AsyncTask<String, String, Boolean> {

    private final String TAG = "HealhCheckServiceTask";

    @Override
    protected Boolean doInBackground(String... params) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://www.google.com")
                .get()
                .build();

        try {
            Response response = client.newCall(request).execute();
            Log.i(TAG, "hc on google.com, responseStatusCode=" + response.code());

            return response.code() == 200;
        } catch (Exception e) {
            Log.e(TAG, Log.getStackTraceString(e));
            return false;
        }
    }

}
