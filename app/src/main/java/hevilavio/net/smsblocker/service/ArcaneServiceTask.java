package hevilavio.net.smsblocker.service;

import android.os.AsyncTask;
import android.util.Log;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

import hevilavio.net.smsblocker.json.ArcaneApiResult;
import hevilavio.net.smsblocker.json.ArcaneSms;
import hevilavio.net.smsblocker.pojo.Sms;

/**
 * Created by hevilavio on 10/6/15.
 */
public class ArcaneServiceTask extends AsyncTask<ArcaneSms, String, String> {

    private final String TAG = "ArcaneServiceTask";
    private String url = "https://arcane-stream-8361.herokuapp.com//sms/teste";

    @Override
    protected String doInBackground(ArcaneSms... params) {

        if(params == null){
            return "params is null";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

        for (ArcaneSms arcaneSms : params) {
            Log.i(TAG, "enviando SMS para server...");

            HttpEntity<ArcaneSms> entity = new HttpEntity<>(arcaneSms, headers);

            ResponseEntity<ArcaneApiResult> exchange =
                    new RestTemplate().exchange(url, HttpMethod.POST, entity, ArcaneApiResult.class);

            Log.i(TAG, "SMS enviado, statusCode={" + exchange.getStatusCode()
                    + "}, resposta={" + exchange.getBody() + "}");
        }

        return "OK";
    }

    public static void main(String[] args) {

        Sms sms = new Sms("555", "body teste");
        ArcaneSms params = ArcaneSms.buildFromSms("test user", sms);

        String response = new ArcaneServiceTask().doInBackground(params);

        if(response != "OK"){
            throw new RuntimeException("failed!");
        }

    }
}
