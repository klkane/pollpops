package com.csc535.app.pollpops;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class performer_login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performer_login);
    }

    public void loginAttempt( View v ) {
        EditText passwordEdit = (EditText)findViewById(R.id.passwordEdit);
        PollPopsDB pdb = PollHelper.getPollPopsDB();

        if( passwordEdit.getText().toString().equals( pdb.getPassword() ) ) {
            Intent intent = new Intent(getApplicationContext(), PerformerActivity.class);
            startActivity(intent);
        } else {
            TextView txt = (TextView)findViewById(R.id.errorText);
            txt.setText( "Password did not match, enter the correct password to become the performer.\n" );
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_performer_login, menu);
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
