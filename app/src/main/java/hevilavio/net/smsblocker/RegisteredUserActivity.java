package hevilavio.net.smsblocker;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class RegisteredUserActivity extends AppCompatActivity {

    private final String TAG = "RegisteredUserActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "inciando activity...");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_message_activity);

        Intent intent = getIntent();
        String firstName = intent.getStringExtra("firstName");

//        TextView textView = new TextView(this);
        TextView textView = (TextView) findViewById(R.id.registered_for_name);
        Log.i(TAG, "textView encontrado, textView=" + textView);

        textView.setText(firstName);

    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView textView = (TextView) findViewById(R.id.registered_for_name);
        Log.i(TAG, "ONRESUME textView encontrado, textView=" + textView);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() { }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.display_message_content,
                    container, false);
            return rootView;
        }
    }
}
