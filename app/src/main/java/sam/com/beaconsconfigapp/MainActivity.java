package sam.com.beaconsconfigapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    private Button myBeaconsButton;
    private Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        this.myBeaconsButton = (Button) findViewById(R.id.main_my_beacons_button);
        this.myBeaconsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToMyBeaconsActivity();
            }
        });
        this.addButton = (Button) findViewById(R.id.main_add_button);
        this.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToNewBeaconActivity();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    private void goToMyBeaconsActivity() {
        Intent intent = new Intent(this, MyBeaconsActivity.class);
        startActivity(intent);
    }

    private void goToNewBeaconActivity() {
        Intent intent = new Intent(this, NewBeaconActivity.class);
        startActivity(intent);
    }
}
