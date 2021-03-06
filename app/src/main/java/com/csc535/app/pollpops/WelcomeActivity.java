package com.csc535.app.pollpops;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.MenuItem;
import android.content.Intent;
import android.os.StrictMode;
import android.widget.EditText;

public class WelcomeActivity extends AppCompatActivity {

    public void enterPerformance(View v) {

        PollPopsDB pdb = PollHelper.getPollPopsDB();
        EditText mEdit = (EditText)findViewById(R.id.performanceId);
        EditText userEdit = (EditText)findViewById(R.id.username);
        pdb.performance_id = mEdit.getText().toString().toLowerCase();
        pdb.username = userEdit.getText().toString().toLowerCase();
        if( pdb.userExists() ) {
            Intent intent = new Intent(getApplicationContext(), UserLoginActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), UserCreateLogin.class);
            startActivity(intent);
        }

    }

    public void enterPerformanceAsPerformer( View v ) {
        PollPopsDB pdb = PollHelper.getPollPopsDB();
        EditText mEdit = (EditText)findViewById(R.id.performanceId);
        EditText userEdit = (EditText)findViewById(R.id.username);
        pdb.performance_id = mEdit.getText().toString().toLowerCase();
        pdb.username = userEdit.getText().toString().toLowerCase();
        if( pdb.performanceExists()) {
            Intent intent = new Intent(getApplicationContext(), performer_login.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), PerformanceCreate.class);
            startActivity(intent);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(0xffffffff);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_welcome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
