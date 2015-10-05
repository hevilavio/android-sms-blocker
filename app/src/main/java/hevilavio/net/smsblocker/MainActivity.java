package hevilavio.net.smsblocker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import hevilavio.net.smsblocker.database.UserDatabase;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String registeredUser = new UserDatabase(getBaseContext()).getActiveUser();
        Log.i(TAG, "registeredUser=" + registeredUser);

        if(registeredUser != null){
            sendToRegisteredUser(registeredUser, true);
        }
        else {
            setContentView(R.layout.activity_main);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
        }



//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    /** Called when the user clicks in the 'Enviar' button */
    public void sendName(View view) {
        EditText firstName = (EditText) findViewById(R.id.first_name);
        String firstNameContent = firstName.getText().toString();

        sendToRegisteredUser(firstNameContent, false);
    }


    private void sendToRegisteredUser(String userName, boolean alreadyRegistered) {
        Intent registeredUserActivity = new Intent(this, RegisteredUserActivity.class);

        registeredUserActivity.putExtra("userName", userName);
        registeredUserActivity.putExtra("alreadyRegistered", alreadyRegistered);

        startActivity(registeredUserActivity);
    }
}
