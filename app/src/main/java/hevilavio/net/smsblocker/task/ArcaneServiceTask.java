package hevilavio.net.smsblocker.task;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import hevilavio.net.smsblocker.json.ArcaneSms;

/**
 * Created by hevilavio on 10/6/15.
 */
public class ArcaneServiceTask extends AsyncTask<ArcaneSms[], String, String> {

    private final String TAG = "ArcaneServiceTask";
    private String url = "https://arcane-stream-8361.herokuapp.com/sms/teste";

    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected String doInBackground(ArcaneSms[]... params) {

        Log.i(TAG, "iniciando tarefa...");

        if(params == null || params.length == 0){
            return "params is null or has no size";
        }

        try {
            return sendToArcane(params[0]);


        } catch (IOException e) {
            Log.e(TAG, e.getMessage() + "\n" + Log.getStackTraceString(e));

            return null;
        }
    }

    @NonNull
    public String sendToArcane(ArcaneSms[] param) throws IOException {

        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        client.setConnectTimeout(10, TimeUnit.SECONDS);
        client.setWriteTimeout(10, TimeUnit.SECONDS);

        for (ArcaneSms arcaneSms : param) {
            Log.i(TAG, "enviando SMS para server...");


            RequestBody body = RequestBody.create(JSON, gson.toJson(arcaneSms));
            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .build();
            Response response = client.newCall(request).execute();

            System.out.println(response.code());

            Log.i(TAG, "SMS enviado, statusCode={" + response.code()
                    + "}, responseBody={" + response.body().string() + "}");
        }

        return "OK";
    }


//    public static void main(String[] args) throws IOException {
//
//        Sms sms = new Sms("555", "body teste");
//        ArcaneSms[] params = new ArcaneSms[] { ArcaneSms.buildFromSms("test user", sms) };
//
//        new ArcaneServiceTask().sendToArcane(params);
//
//        if("P" != "OK"){
//            throw new RuntimeException("failed!");
//        }
//
//    }
}
