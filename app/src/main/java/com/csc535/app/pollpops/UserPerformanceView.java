package com.csc535.app.pollpops;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.Timer;
import java.util.TimerTask;

public class UserPerformanceView extends AppCompatActivity {

    public void sendChatMessage(View v) {
        Intent intent = new Intent(getApplicationContext(), UserPerformanceView.class);
        PollPopsDB pdb = PollHelper.getPollPopsDB();
        EditText mEdit = (EditText)findViewById(R.id.chatSend);
        pdb.sendChatMessage(mEdit.getText().toString());
        mEdit.setText( "" );
        mEdit.setEnabled( false );
        mEdit.setEnabled( true );
        this.updateChats();
    }

    private void processFeedback( int level ) {
        PollPopsDB pdb = PollHelper.getPollPopsDB();
        pdb.recordFeedback( level );
    }


    public void likeAction( View v ) {
        this.processFeedback( 1 );
    }

    public void dislikeAction( View v ) {
        this.processFeedback( -1 );
    }

    public void updateNowPlaying() {
        PollPopsDB pdb = PollHelper.getPollPopsDB();
        TextView txt = (TextView)findViewById(R.id.nowPlaying);
        String msg = pdb.getNowPlaying();
        txt.setText( "Now Playing: " + msg );
    }

    public void updateChats() {
        PollPopsDB pdb = PollHelper.getPollPopsDB();
        TextView txt = (TextView)findViewById(R.id.chatDisplay);
        String msg = "";
        String[] msgs = pdb.getChatMessages( 100 );
        for( String _msg : msgs ) {
            if( _msg != null ) {
                msg = msg + _msg + "\n";
            }
        }
        txt.setText(msg);
    }


    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        this.updateChats();
        this.updateNowPlaying();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_performance_view);
        View view2 = this.getWindow().getDecorView();
        view2.setBackgroundColor(0xffffffff);
        this.updateChats();
        final UserPerformanceView view = this;
        this.updateNowPlaying();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.updateChats();
                        view.updateNowPlaying();
                    }
                });
            }
        }, 0, 4000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_user_performance_view, menu);
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

    public void viewSetlist(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), AudienceSetlist.class);
        startActivity(intent);
    }
}
