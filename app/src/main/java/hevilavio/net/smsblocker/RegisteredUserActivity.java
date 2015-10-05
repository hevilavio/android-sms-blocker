package hevilavio.net.smsblocker;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import hevilavio.net.smsblocker.database.UserDatabase;


public class RegisteredUserActivity extends AppCompatActivity {

    private final String TAG = "RegisteredUserActivity";

    private UserDatabase  userDatabase;

    public RegisteredUserActivity() {
        this.userDatabase = new UserDatabase(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "inciando activity...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered_user_activity);

        Intent intent = getIntent();
        String userName = intent.getStringExtra("userName");
        boolean alreadyRegistered  = intent.getBooleanExtra("alreadyRegistered", false);

        registerUserIfDoesnotRegistered(userName, alreadyRegistered);

        // TODO: HC no servidor para mostrar na tela

        ((TextView) findViewById(R.id.registered_for_name)).setText(userName);
        ((TextView) findViewById(R.id.server_connection_text)).setText("N/A");
        ((TextView) findViewById(R.id.sqlite_connection_text)).setText(userDatabase.hc());
        ((TextView) findViewById(R.id.sms_count_text)).setText("0");

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        Log.i(TAG, "recebendo intent");
        int amountOfSms = intent.getIntExtra("amountOfSms", 42);
        ((TextView) findViewById(R.id.sms_count_text)).setText(String.valueOf(amountOfSms));
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
