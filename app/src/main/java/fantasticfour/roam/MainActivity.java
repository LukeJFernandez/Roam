package fantasticfour.roam;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private int category;
    private int distance_miles;
    private SeekBar seekBar;
    private TextView textView;

    private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.willingDist);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        final Handler handler3 = new Handler();
        handler3.postDelayed(new Runnable() {
            @Override
            public void run() {
                setContentView(R.layout.activity_distance);
                initializeVariables();

                textView.setText("<= 1.0 mi");

                final DecimalFormat numFormat = new DecimalFormat("#.0");

                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    int progress = 0;

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progressValue, boolean fromUser) {
                        progress = progressValue;

                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {

                    }

                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                        textView.setText("<= " + numFormat.format((progress / 100.0) * 5.0) + " mi");

                    }
                });
            }
        }, 5000);

        final Handler handler2 = new Handler();
        handler2.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent2 = new Intent(MainActivity.this, NavigationActivity.class);
                MainActivity.this.startActivity(intent2);
            }
        }, 5000);
    }

    protected void saveCategory(int input) {
        category = input;
    }

    protected void saveDistance(int input) {
        distance_miles = input;

    }


}