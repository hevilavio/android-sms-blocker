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
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hevilavio.net.smsblocker.database.SmsDatabase;
import hevilavio.net.smsblocker.json.ArcaneSms;

/**
 * Created by hevilavio on 10/6/15.
 */
public class ArcaneSmsAsynTask extends AsyncTask<Pair<Integer, ArcaneSms>, Void, Pair<Integer, Boolean>> {

    private final String TAG = "ArcaneSmsAsynTask";
    private final String URL = "https://arcane-stream-8361.herokuapp.com/sms/teste";
    private final int TIMEOUT_SECONDS = 10;
    private final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    private final SmsDatabase smsDatabase;

    public ArcaneSmsAsynTask(SmsDatabase smsDatabase) {
        this.smsDatabase = smsDatabase;
    }

    /**
     * @param params - Id do Sms | ArcaneSms
     * @return  - Id do Sms | sentWithSuccess
     * */
    @Override
    protected Pair<Integer, Boolean> doInBackground(Pair<Integer, ArcaneSms>... params) {

        if(params == null || params.length == 0){
            Log.w(TAG, "doInBackground, empty params...");
        }

        Pair<Integer, ArcaneSms> param = params[0];

        return sendSmsToArcane(param.first, param.second);
    }

    @Override
    protected void onPostExecute(Pair<Integer, Boolean> result) {
        new UpdateSentToServerCallback(smsDatabase).execute(result);
    }

    public Pair<Integer, Boolean> sendSmsToArcane(int smsId, ArcaneSms arcaneSms) {

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
                    + ", rBody=" + getResponseBody(response.body())
            );

            return new Pair<>(smsId, true);

        } catch (Exception e) {
            logError(e.getMessage() + "\n" + Log.getStackTraceString(e));

            return new Pair<>(smsId, false);
        }
    }

    private String getResponseBody(ResponseBody responseBody) {
        if(responseBody == null){
            return "null";
        }

        try {
            return responseBody.string();
        } catch (IOException e) {
            logError(e.getMessage() + "\n" + Log.getStackTraceString(e));
            return "null";
        }
    }

    private void logError(String msg) {
        Log.e(TAG, msg);
    }

/*
    public static void main(String[] args) throws IOException {

        Sms sms = new Sms(0, "555", "body teste");

        boolean ok = new ArcaneSmsAsynTask().sendSmsToArcane(1, ArcaneSms.buildFromSms("test user", sms));

        System.out.println(ok);
    }*/
}
