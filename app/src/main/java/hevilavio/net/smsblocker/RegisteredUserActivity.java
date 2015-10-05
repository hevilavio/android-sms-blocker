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


        // TODO: HC no servidor

        // TODO: Salvar no db o usuario registrado
        saveUserIfDoesnotRegistered(userName, alreadyRegistered);

        // TODO: Iniciar Listener de SMS

        TextView textView = (TextView) findViewById(R.id.registered_for_name);
        Log.i(TAG, "textView encontrado, textView=" + textView);

        textView.setText(userName);

    }

    private void saveUserIfDoesnotRegistered(String userName, boolean alreadyRegistered) {

        if(alreadyRegistered){
            Log.i(TAG, "usuario ja existe, userName=" + userName);
        }else{
            long userId = userDatabase.createUser(userName);
            Log.i(TAG, "usuario criado, id=" + userId);
        }
    }

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

    private void unregisterUser() {

        Log.i(TAG, "cancelando registro de usuario");
        userDatabase.inactiveUser();
        // TODO: Notificar server do cancelamento de registro

        sendToMainActivity();
    }

    private void sendToMainActivity() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
