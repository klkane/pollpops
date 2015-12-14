package com.csc535.app.pollpops;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class UserCreateLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_create_login, menu);
        return true;
    }

    public void createUser(View v) {
        EditText mEdit = (EditText)findViewById(R.id.passwordTxt1);
        EditText mEdit2 = (EditText)findViewById(R.id.passwordTxt2);
        PollPopsDB pdb = PollHelper.getPollPopsDB();

        if( mEdit.getText().toString().equals(mEdit2.getText().toString()) ) {
            pdb.createUser( mEdit.getText().toString() );
            Intent intent = new Intent(getApplicationContext(), UserPerformanceView.class);
            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "The passwords you entered did not match";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
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
