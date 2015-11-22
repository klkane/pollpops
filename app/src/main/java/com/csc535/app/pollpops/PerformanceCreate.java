package com.csc535.app.pollpops;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class PerformanceCreate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performance_create);
    }

    public void createPerformance( View v ) {
        PollPopsDB pdb = PollHelper.getPollPopsDB();
        EditText performerEdit = (EditText) findViewById(R.id.performerText);
        EditText locationEdit = (EditText) findViewById(R.id.locationText);
        EditText dateEdit = (EditText) findViewById(R.id.dateText);
        EditText timeEdit = (EditText) findViewById(R.id.timeText);
        EditText passwordEdit = (EditText) findViewById(R.id.passwordEdit);
        pdb.createPerformance( performerEdit.getText().toString(),
                locationEdit.getText().toString(),
                dateEdit.getText().toString(),
                timeEdit.getText().toString(),
                passwordEdit.getText().toString());
        Intent intent = new Intent(getApplicationContext(), PerformerActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_performance_create, menu);
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
