package sam.com.beaconsconfigapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import sam.com.beaconsconfigapp.storage.KinveyWebStorage;
import sam.com.beaconsconfigapp.storage.WebStorage;
import sam.com.beaconsconfigapp.storage.WebStorageCallback;


public class LoginActivity extends Activity {

    private Button cancelButton;
    private BeaconConfigApplication application;

    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();

        this.application = (BeaconConfigApplication) getApplication();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.login, menu);
        return true;
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

    private void initViews() {
        this.usernameEditText = (EditText) findViewById(R.id.login_username_edittext);
        this.passwordEditText = (EditText) findViewById(R.id.login_password_edittext);
        this.loginButton = (Button) findViewById(R.id.login_login_button);

        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    private void login() {
        String username = this.usernameEditText.getText().toString();
        String password = this.passwordEditText.getText().toString();

        this.application.getWebStorage().login(username, password, new WebStorageCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
                Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(Void response) {
                Toast.makeText(LoginActivity.this, "User logged in", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
