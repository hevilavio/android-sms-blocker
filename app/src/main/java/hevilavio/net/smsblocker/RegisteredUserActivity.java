package hevilavio.net.smsblocker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import hevilavio.net.smsblocker.database.UserDatabase;


public class RegisteredUserActivity extends AppCompatActivity {

    private final String TAG = "RegisteredUserActivity";

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
            long userId = new UserDatabase(getBaseContext()).createUser(userName);
            Log.i(TAG, "usuario criado, id=" + userId);
        }
    }
}
