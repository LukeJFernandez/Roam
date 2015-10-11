package fantasticfour.roam;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.location.LocationManager;
import android.location.LocationListener;
import android.location.Location;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import java.util.UUID;

public class NavigationActivity extends AppCompatActivity {

    UUID pebbleAppId = UUID.fromString("43181d1a-de09-40a7-8307-8bd1b34adde3");
    float finishDistance = 20; //In meters
    double destLat = 35.918519;
    double destLong = -79.038193;
    boolean arrived = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            double latitude = Double.parseDouble(extras.getString("latitude"));
            double longitude = Double.parseDouble(extras.getString("longitude"));
            setDestination(latitude, longitude);
        }
        setContentView(R.layout.activity_navigation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pebbleSetup();
        initNavigation();
    }

    protected void initNavigation() {
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                if(!arrived) {
                    computeDirection(location);
                }
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 0, locationListener);
        } catch (SecurityException ex) {}
    }

    void computeDirection(Location loc) {
        Location destination = new Location(loc);
        destination.setLatitude(destLat);
        destination.setLongitude(destLong);
        if(loc.distanceTo(destination) <= finishDistance) {
            showFinishScreen();
            arrived = true;
            showFinishApp();
        } else {
            float bearingTo = loc.bearingTo(destination) - loc.getBearing();
            setAngle((int) bearingTo);
        }
    }

    void setDestination(double destLat, double destLong) {
        this.destLat = destLat;
        this.destLong = destLong;
    }

    void pebbleSetup() {
        boolean isConnected = PebbleKit.isWatchConnected(this);
        boolean appMessagesSupported = PebbleKit.areAppMessagesSupported(this);
        PebbleKit.startAppOnPebble(this, pebbleAppId);
        PebbleKit.registerReceivedAckHandler(getApplicationContext(), new PebbleKit.PebbleAckReceiver(pebbleAppId) {
            @Override
            public void receiveAck(Context context, int transactionId) {}
        });
        PebbleKit.registerReceivedNackHandler(getApplicationContext(), new PebbleKit.PebbleNackReceiver(pebbleAppId) {
            @Override
            public void receiveNack(Context context, int transactionId) {}
        });
    }

    void sendToPebble(PebbleDictionary data) {
        PebbleKit.sendDataToPebble(this, pebbleAppId, data);
    }

    void setAngle(int angle) {
        PebbleDictionary angleData = new PebbleDictionary();
        angleData.addInt32(1, angle);
        if(!arrived) {
            sendToPebble(angleData);
        }
    }

    void showFinishScreen() {
        if(!arrived) {
            PebbleDictionary finishData = new PebbleDictionary();
            finishData.addInt32(2, 0);
            sendToPebble(finishData);
        }
    }

    void showFinishApp() {
        setContentView(R.layout.activity_end);
    }

}
