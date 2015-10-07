package hevilavio.net.smsblocker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import static hevilavio.net.smsblocker.constants.ExtraConstants.USERNAME;
import static hevilavio.net.smsblocker.constants.ExtraConstants.ALREADY_REGISTERED;
import hevilavio.net.smsblocker.database.UserDatabase;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    private final UserDatabase userDatabase;

    public MainActivity() {
        this.userDatabase = new UserDatabase(this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String registeredUser = userDatabase.getActiveUser();
        Log.i(TAG, "registeredUser=" + registeredUser);

        if(registeredUser != null){
            sendToRegisteredUser(registeredUser, true);
        }
        else {
            setContentView(R.layout.activity_main);
        }
    }

    /**
     * Chamado por Button.btn_send
     * */
    public void sendName(View view) {
        EditText userName = (EditText) findViewById(R.id.user_name);
        String userNameContent = userName.getText().toString();

        sendToRegisteredUser(userNameContent, false);
    }


    private void sendToRegisteredUser(String userName, boolean alreadyRegistered) {
        Intent registeredUserActivity = new Intent(this, RegisteredUserActivity.class);

        registeredUserActivity.putExtra(USERNAME, userName);
        registeredUserActivity.putExtra(ALREADY_REGISTERED, alreadyRegistered);

        startActivity(registeredUserActivity);
    }
}
