package com.csc535.app.pollpops;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.LineData;
import com.mongodb.client.MongoCursor;
import com.github.mikephil.charting.data.Entry;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import org.bson.Document;

public class PerformerActivity extends AppCompatActivity {

    public void updateNowPlaying(View v ) {
        PollPopsDB pdb = PollHelper.getPollPopsDB();
        EditText mEdit = (EditText)findViewById(R.id.nowPlayingEdit);
        pdb.setNowPlaying(mEdit.getText().toString());
        mEdit.setEnabled(false);
        mEdit.setEnabled(true);
    }

    public void updateChart( MenuItem i ) {
        this.updateChartInternal();
    }

    public void updateChartInternal() {
        PollPopsDB pdb = PollHelper.getPollPopsDB();
        MongoCursor<Document> cursor = pdb.getFeedbackCursor();
        LineChart chart = (LineChart) findViewById(R.id.feedbackChart);
        int index = 0;
        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> feed = new ArrayList<Entry>();
        while( cursor.hasNext() ) {
            int val = cursor.next().getInteger( "feedback" );
            if( index > 0 ) {
                val = (int) (feed.get(index - 1).getVal() + val);
            }
            feed.add( new Entry( val, index ) );
            xVals.add(index, Integer.toString( index ));
            index++;
        }
        LineDataSet lineDataSet = new LineDataSet( feed, "Audience Feedback" );
        LineData data = new LineData(xVals, lineDataSet);
        chart.setData(data);
        chart.invalidate();
    }

    public void viewChats(MenuItem i) {
        Intent intent = new Intent(getApplicationContext(), PerformerChats.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_performer);
        View view2 = this.getWindow().getDecorView();
        view2.setBackgroundColor(0xffffffff);
        this.updateChartInternal();
        final PerformerActivity view = this;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        view.updateChartInternal();
                    }
                });
            }
        }, 0, 5000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_performer, menu);
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

    public void recordPerformance(MenuItem item) {
        Intent intent = new Intent(getApplicationContext(), PerformanceRecord.class);
        startActivity(intent);
    }
}
