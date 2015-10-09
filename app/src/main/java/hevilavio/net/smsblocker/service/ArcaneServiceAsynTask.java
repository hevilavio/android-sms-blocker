package hevilavio.net.smsblocker.service;

import android.os.AsyncTask;
import android.util.Log;
import android.util.Pair;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hevilavio.net.smsblocker.json.ArcaneSms;
import hevilavio.net.smsblocker.pojo.Sms;

/**
 * Created by hevilavio on 10/6/15.
 */
public class ArcaneServiceAsynTask extends AsyncTask<Pair<Integer, ArcaneSms>, Void, Boolean> {

    private final String TAG = "ArcaneServiceAsynTask";
    private final String URL = "https://arcane-stream-8361.herokuapp.com/sms/teste";
    private final int TIMEOUT_SECONDS = 10;
    private final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected Boolean doInBackground(Pair<Integer, ArcaneSms>... params) {

        if(params == null || params.length == 0){
            Log.w(TAG, "doInBackground, empty params...");
        }

        Pair<Integer, ArcaneSms> param = params[0];

        return sendSmsToArcane(param.first, param.second);
    }

    public boolean sendSmsToArcane(int smsId, ArcaneSms arcaneSms) {

        Log.i(TAG, "sendSmsToArcane.start"
                + ", smsId=" + smsId);

        try {
            Gson gson = new Gson();
            OkHttpClient client = new OkHttpClient();

            client.setConnectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS);
            client.setWriteTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS);

            RequestBody body = RequestBody.create(JSON_MEDIA_TYPE, gson.toJson(arcaneSms));
            Request request = new Request.Builder()
                    .url(URL)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            Log.i(TAG, "sendSmsToArcane.start"
                    + ", smsId=" + smsId
                    + ", rCode=" + response.code()
                    + ", rBody=" + response.body()
            );
            return true;

        } catch (Exception e) {
            Log.e(TAG, e.getMessage() + "\n" + Log.getStackTraceString(e));

            return false;
        }
    }


    public static void main(String[] args) throws IOException {

        Sms sms = new Sms(0, "555", "body teste");

        boolean ok = new ArcaneServiceAsynTask().sendSmsToArcane(1, ArcaneSms.buildFromSms("test user", sms));

        System.out.println(ok);


    }
}
