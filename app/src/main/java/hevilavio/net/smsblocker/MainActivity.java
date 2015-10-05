package hevilavio.net.smsblocker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Se o usuario já for registrado, enviar para RegisteredUserActivity

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

    /** Called when the user clicks the 'Enviar' button */
    public void sendName(View view) {
        Intent displayFirstNameActivity = new Intent(this, RegisteredUserActivity.class);
        EditText firstName = (EditText) findViewById(R.id.first_name);
        String firstNameContent = firstName.getText().toString();
        displayFirstNameActivity.putExtra("firstName", firstNameContent);

        startActivity(displayFirstNameActivity);

    }
}
