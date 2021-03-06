package timerclock.agcp.com.timerclock;

import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static android.R.attr.handle;

public class RunTime extends AppCompatActivity {

    private int seconds = 0;
    private boolean running;
    private boolean wasRunning;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run_time);
        if(savedInstanceState!=null){
            seconds=savedInstanceState.getInt("seconds");
            running=savedInstanceState.getBoolean("running");
            wasRunning=savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }


    protected void onPause(){
        super.onPause();
        wasRunning=running;
        running=false;
    }

    protected void onResume(){
        super.onResume();
        if(wasRunning)
            running=true;
    }

    public void onSaveInstanceState(Bundle saveInstanceState){
        saveInstanceState.putInt("seconds",seconds);
        saveInstanceState.putBoolean("running",running);
        saveInstanceState.putBoolean("wasRunning",wasRunning);
    }
    private void runTimer(){
        final TextView textView = (TextView) findViewById(R.id.time);
        final Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds / 3600;
                int mins = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format("%d:%02d:%02d", hours, mins, secs);
                System.out.println(time);
                textView.setText(time);
                if (running)
                    seconds++;
                handler.postDelayed(this,1000);
            }
        });
    }



    public void onClickStart(View view){
        running = true;
    }

    public void onClickStop(View view){
        running = false;
    }

    public void onClickReset(View view){
        running = false;
        seconds = 0;
    }
}
