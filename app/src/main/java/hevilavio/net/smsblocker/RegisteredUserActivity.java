package hevilavio.net.smsblocker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;


public class RegisteredUserActivity extends AppCompatActivity {

    private final String TAG = "RegisteredUserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "inciando activity...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.registered_user_activity);

        Intent intent = getIntent();
        String firstName = intent.getStringExtra("firstName");

        // TODO: Registar o usuario aqui:

        // TODO: Salvar no db o usuario registrado

        // TODO: Iniciar Listener de SMS

        TextView textView = (TextView) findViewById(R.id.registered_for_name);
        Log.i(TAG, "textView encontrado, textView=" + textView);



        textView.setText(firstName);

    }
}
