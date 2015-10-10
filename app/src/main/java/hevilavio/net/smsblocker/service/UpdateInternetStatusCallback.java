package hevilavio.net.smsblocker.service;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import hevilavio.net.smsblocker.RegisteredUserActivity;

import static hevilavio.net.smsblocker.constants.ExtraConstants.INTERNET_CONN;
import static hevilavio.net.smsblocker.constants.ExtraConstants.INTERNET_CONN_STATUS;
import static hevilavio.net.smsblocker.constants.ExtraConstants.WHO_IS;

/**
 *
 * Responsavel por atualizar 'Conexao como a internet' da tela principal
 * Created by hevilavio on 10/9/15.
 */
public class UpdateInternetStatusCallback implements Callback<Boolean> {
    private final String TAG = "UpdInternetStatusCall";

    private final Context applicationContext;

    public UpdateInternetStatusCallback(Context applicationContext) {
        this.applicationContext = applicationContext;
    }


    @Override
    public void execute(Boolean hasInternet) {
        Intent toOpen = new Intent(applicationContext, RegisteredUserActivity.class);
        toOpen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        toOpen.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);

        toOpen.putExtra(INTERNET_CONN_STATUS, hasInternet ? "SIM" : "NAO");
        toOpen.putExtra(WHO_IS, INTERNET_CONN);

        Log.i(TAG, "enviando INTERNET_CONN_STATUS para RegisteredUserActivity" +
                ", hasInternet=" + hasInternet);

        applicationContext.startActivity(toOpen);
    }
}
