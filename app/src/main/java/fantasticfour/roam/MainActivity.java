package fantasticfour.roam;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.Random;

import fantasticfour.roam.database.DatabaseConnection;

public class MainActivity extends AppCompatActivity {

    private int category;
    private int distance_miles;
    private SeekBar seekBar;
    private TextView textView;
    LocationManager mLocationManager = null;

    private void initializeVariables() {
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        textView = (TextView) findViewById(R.id.willingDist);
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500,
                0, mLocationListener);

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
                saveCategory(1);
            }
        });

        rand_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Random rand = new Random();
                int num = rand.nextInt(5) + 1;
                saveCategory(num);
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
                Location loc1 = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                try {
                    NetCheck.checkNetworkConnection(getApplicationContext(),new LocationTask(getApplicationContext(), category, loc1.getLatitude(),loc1.getLongitude(),distance_miles));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent9 = new Intent(MainActivity.this, NavigationActivity.class);
                MainActivity.this.startActivity(intent9);
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
                        saveCategory(1);
                    }
                });

                rand_btn.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        Random rand = new Random();
                        int num = rand.nextInt(5) + 1;
                        saveCategory(num);
                    }
                });
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
                saveDistance(progress);
            }
        });
    }

    protected void saveDistance(int input) {
        distance_miles = input;
    }


    private class LocationTask extends AsyncTask<Object, Void, JSONObject> {
        Context c; int category; double latitude; double longitude; int distance;

        public LocationTask(Context c, int category, double latitude, double longitude, int distance) {
            this.c = c;
            this.category = category;
            this.latitude = latitude;
            this.longitude = longitude;
            this.distance = distance;
        }

        @Override
        protected JSONObject doInBackground(Object... params) {
            return DatabaseConnection.getLocation(category, latitude, longitude, distance);
        }
    }

    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
}