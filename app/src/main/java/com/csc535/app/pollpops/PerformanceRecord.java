package com.csc535.app.pollpops;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.media.MediaRecorder;
import android.media.MediaPlayer;
import android.view.View;
import android.widget.Button;
import android.os.Environment;
import java.io.IOException;

public class PerformanceRecord extends AppCompatActivity {
    private MediaRecorder myAudioRecorder;
    private MediaPlayer myAudioPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = this.getWindow().getDecorView();
        view.setBackgroundColor(0xffffffff);
        setContentView(R.layout.activity_performance_record);
        Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setEnabled( false );
        Button stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setEnabled(false);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_performance_record, menu);
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

    public void playAction( View v ) {
        Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setEnabled( false );
        Button stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setEnabled( true );
        Button recordButton = (Button) findViewById(R.id.recordButton);
        recordButton.setEnabled( false );

        myAudioPlayer = new MediaPlayer();
        String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        try {
            myAudioPlayer.setDataSource(outputFile);
            myAudioPlayer.prepare();
            myAudioPlayer.start();
        }
        catch( IOException e ){
        }
        catch( Exception e ) {
        }
    }

    public void stopAction( View v ) {
        if( myAudioRecorder != null ) {
            try {
                myAudioRecorder.stop();
                myAudioRecorder.reset();
                myAudioRecorder.release();
            }
            catch( Exception e ) {
            }
            myAudioRecorder = null;
        } else if( myAudioPlayer != null ) {
            try {
                myAudioPlayer.stop();
                myAudioPlayer.release();
            } catch ( Exception e ) {
            }
            myAudioPlayer = null;
        }

        Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setEnabled( true );
        Button stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setEnabled( false );
        Button recordButton = (Button) findViewById(R.id.recordButton);
        recordButton.setEnabled( true );
    }

    public void recordAction( View v ) {
        Button playButton = (Button) findViewById(R.id.playButton);
        playButton.setEnabled(false);
        Button stopButton = (Button) findViewById(R.id.stopButton);
        stopButton.setEnabled(true);
        Button recordButton = (Button) findViewById(R.id.recordButton);
        recordButton.setEnabled(false);
        String outputFile = Environment.getExternalStorageDirectory().getAbsolutePath() + "/recording.3gp";

        myAudioRecorder = new MediaRecorder();
        myAudioRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        myAudioRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        myAudioRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
        myAudioRecorder.setOutputFile(outputFile);

        try {
            myAudioRecorder.prepare();
            myAudioRecorder.start();
        }
        catch (IllegalStateException e) {
        }
        catch (IOException e) {
        }
        catch ( Exception e) {
        }
    }
}
