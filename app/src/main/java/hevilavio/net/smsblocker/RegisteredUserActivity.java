package hevilavio.net.smsblocker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import hevilavio.net.smsblocker.database.SmsDatabase;
import hevilavio.net.smsblocker.database.UserDatabase;
import hevilavio.net.smsblocker.service.ArcaneHcTask;

import static hevilavio.net.smsblocker.constants.ExtraConstants.ALREADY_REGISTERED;
import static hevilavio.net.smsblocker.constants.ExtraConstants.AMOUNT_OF_SMS;
import static hevilavio.net.smsblocker.constants.ExtraConstants.INTERNET_CONN;
import static hevilavio.net.smsblocker.constants.ExtraConstants.INTERNET_CONN_STATUS;
import static hevilavio.net.smsblocker.constants.ExtraConstants.SMS_COUNT;
import static hevilavio.net.smsblocker.constants.ExtraConstants.USERNAME;
import static hevilavio.net.smsblocker.constants.ExtraConstants.WHO_IS;


public class RegisteredUserActivity extends AppCompatActivity {

    private final String TAG = "RegisteredUserActivity";

    private UserDatabase  userDatabase;
    private SmsDatabase smsDatabase;

    public RegisteredUserActivity() {
        this.userDatabase = new UserDatabase(this);
        this.smsDatabase = new SmsDatabase(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "inciando activity...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered_user_activity);

        Intent intent = getIntent();
        String userName = intent.getStringExtra(USERNAME);
        boolean alreadyRegistered  = intent.getBooleanExtra(ALREADY_REGISTERED, false);

        registerUserIfDoesnotRegistered(userName, alreadyRegistered);

        ((TextView) findViewById(R.id.registered_for_name)).setText(userName);
        ((TextView) findViewById(R.id.server_connection_text)).setText("?"); //
        ((TextView) findViewById(R.id.sqlite_connection_text)).setText(userDatabase.hc());
        ((TextView) findViewById(R.id.sms_count_text)).setText(String.valueOf(smsDatabase.count()));

        new ArcaneHcTask(getApplicationContext()).execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        new ArcaneHcTask(getApplicationContext()).execute();
        ((TextView) findViewById(R.id.sms_count_text)).setText(String.valueOf(smsDatabase.count()));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.i(TAG, "recebendo intent");

        String whoIs = intent.getStringExtra(WHO_IS);
        Log.i(TAG, "onNewIntent, whoIs=" + whoIs);

        /**
         * TODO: talvez isso possa ser feito de uma maneira melhor
         * */
        if(SMS_COUNT.equals(whoIs)){

            int amountOfSms = intent.getIntExtra(AMOUNT_OF_SMS, 42);
            /**
             * TODO: Tem um bug aqui, tem dados inseridos na tabela por outra thread.
             * */
            int total = smsDatabase.count() + amountOfSms;
            ((TextView) findViewById(R.id.sms_count_text)).setText(String.valueOf(total));
        }
        else if (whoIs.equals(INTERNET_CONN)){

            String connStatus = intent.getStringExtra(INTERNET_CONN_STATUS);
            ((TextView) findViewById(R.id.server_connection_text)).setText(connStatus);
        }
    }

    private void registerUserIfDoesnotRegistered(String userName, boolean alreadyRegistered) {

        if(alreadyRegistered){
            Log.i(TAG, "usuario ja existe, userName=" + userName);
        }
        else {
            long userId = userDatabase.createUser(userName);
            Log.i(TAG, "usuario criado, id=" + userId);
        }
    }

    /**
     * Quando usuario clica em R.id.btn_unregister
     * */
    public void showAlertDialog(View view){
        Log.i(TAG, "showAlertDialog chamado");

        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setTitle(R.string.dialog_unregister_title)
                .setMessage(R.string.dialog_unregister_message)
                .setPositiveButton(R.string.dialog_unregister_positive, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        unregisterUser();
                    }
                })
                .setNegativeButton(R.string.dialog_unregister_negative, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // nothing here
                    }
                });

        builder.show();
    }

    public void unregisterUser() {

        Log.i(TAG, "cancelando registro de usuario");
        userDatabase.inactiveUser();
        // TODO: Notificar server do cancelamento de registro

        sendToMainActivity();
    }

    private void sendToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
