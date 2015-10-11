package fantasticfour.roam;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.getpebble.android.kit.PebbleKit;
import com.getpebble.android.kit.util.PebbleDictionary;

import java.util.UUID;

public class NavigationActivity extends AppCompatActivity {

    UUID pebbleAppId = UUID.fromString("43181d1a-de09-40a7-8307-8bd1b34adde3");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        pebbleSetup();
        try {
            Thread.sleep(5000);
        } catch (Exception ex) {}
        showFinishScreen();
        /*int angle = 45;
        while(true) {
            setAngle(angle);
            angle += 45;
            try {
                Thread.sleep(1000);
            } catch (Exception ex) {ex.printStackTrace();}
        }*/
    }

    void pebbleSetup() {
        boolean isConnected = PebbleKit.isWatchConnected(this);
        boolean appMessagesSupported = PebbleKit.areAppMessagesSupported(this);
        PebbleKit.startAppOnPebble(this, pebbleAppId);
        PebbleKit.registerReceivedAckHandler(getApplicationContext(), new PebbleKit.PebbleAckReceiver(pebbleAppId) {
            @Override
            public void receiveAck(Context context, int transactionId) {
            }
        });
        PebbleKit.registerReceivedNackHandler(getApplicationContext(), new PebbleKit.PebbleNackReceiver(pebbleAppId) {
            @Override
            public void receiveNack(Context context, int transactionId) {
            }
        });
    }

    void setAngle(int angle) {
        PebbleDictionary angleData = new PebbleDictionary();
        angleData.addInt32(1, angle);
        PebbleKit.sendDataToPebble(this, pebbleAppId, angleData);
    }

    void showFinishScreen() {
        PebbleDictionary finishData = new PebbleDictionary();
        finishData.addInt32(2, 0);
        PebbleKit.sendDataToPebble(this, pebbleAppId, finishData);
    }

}
