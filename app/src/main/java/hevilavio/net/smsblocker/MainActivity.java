package hevilavio.net.smsblocker;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.util.Calendar;

import static hevilavio.net.smsblocker.constants.ExtraConstants.USERNAME;
import static hevilavio.net.smsblocker.constants.ExtraConstants.ALREADY_REGISTERED;
import hevilavio.net.smsblocker.database.UserDatabase;
import hevilavio.net.smsblocker.service.SendDataToServerService;

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

        startSendDataToServerSchedule();

        if(registeredUser != null){
            sendToRegisteredUser(registeredUser, true);
        }
        else {
            setContentView(R.layout.activity_main);
        }
    }

    private void startSendDataToServerSchedule() {
        Log.i(TAG, "startSendDataToServerSchedule.start");

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 10);

        Intent intent = new Intent(this, SendDataToServerService.class);

        PendingIntent pendingIntent = PendingIntent.getService(this, 0, intent, 0);

        AlarmManager alarm = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // every 10s
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                10*1000, pendingIntent);

        Log.i(TAG, "startSendDataToServerSchedule.finish");
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
