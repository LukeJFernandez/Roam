package fantasticfour.roam;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int category;
    private int distance_miles;
    private SeekBar seekBar;
    private TextView textView;

    private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.willingDist);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);


        Button outdoors_btn = (Button) findViewById(R.id.outdoorsBtn);
        Button romance_btn = (Button) findViewById(R.id.romanceBtn);
        Button hungry_btn = (Button) findViewById(R.id.hungryBtn);
        Button dessert_btn = (Button) findViewById(R.id.dessertBtn);
        Button tired_btn = (Button) findViewById(R.id.tiredBtn);
        Button rand_btn = (Button) findViewById(R.id.randBtn);

        outdoors_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveCategory(5);
            }
        });
        romance_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveCategory(4);
            }
        });

        hungry_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveCategory(3);
            }
        });

        dessert_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveCategory(2);
            }
        });

        tired_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Random rand = new Random();
                int num = rand.nextInt(5) + 1;
                saveCategory(num);
            }
        });

        rand_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveCategory(1);
            }
        });
    }


    public void saveCategory(int input) {
        category = input;
        setContentView(R.layout.activity_distance);
        Button go_btn = (Button) findViewById(R.id.goBtn);
        Button back_btn = (Button) findViewById(R.id.backBtn);
        go_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                saveCategory(2);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.activity_category);
            }
        });
        initializeVariables();

        textView.setText("1.0 mi");

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
                textView.setText(numFormat.format((progress / 100.0) * 5.0) + " mi");

            }
        });
    }

    protected void saveDistance(int input) {
        distance_miles = input;

    }


}