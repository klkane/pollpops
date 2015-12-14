package com.csc535.app.pollpops;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AudienceSetlist extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audience_setlist);
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(0xffffffff);
        PollPopsDB pdb = PollHelper.getPollPopsDB();
        TextView txt = (TextView)findViewById(R.id.setlistView);
        String msg = "";
        String[] msgs = pdb.getSetlist();
        for( String _msg : msgs ) {
            if( _msg != null ) {
                msg = msg + _msg + "\n";
            }
        }
        txt.setText(msg);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_audience_setlist, menu);
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
