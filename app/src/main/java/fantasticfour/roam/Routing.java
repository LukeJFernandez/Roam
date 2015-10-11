package fantasticfour.roam;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;

import com.esri.android.map.LocationDisplayManager;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.tasks.na.NAFeaturesAsFeature;
import com.esri.core.tasks.na.Route;
import com.esri.core.tasks.na.RouteParameters;
import com.esri.core.tasks.na.RouteResult;
import com.esri.core.tasks.na.RouteTask;
import com.esri.core.tasks.na.StopGraphic;

import java.util.ArrayList;

/**
 * Created by Vamsi on 10/10/15.
 */
public class Routing {
    // Current route, route summary, and gps location
    public static ArrayList<String> curDirections = null;
    Route curRoute = null;
    String routeSummary = null;
    public static Point mLocation = null;
    // Global results variable for calculating route on separate thread
    RouteTask mRouteTask = null;
    RouteResult mResults = null;
    // Variable to hold server exception to show to user
    Exception mException = null;
    final SpatialReference wm = SpatialReference.create(102100);
    final SpatialReference egs = SpatialReference.create(4326);
    // Handler for processing the results
    final Handler mHandler = new Handler();
    LocationDisplayManager ldm;
//    final Runnable mUpdateResults = new Runnable() {
//        public void run() {
//            updateUI();
//        }
//    };

    public void locations(final Point mLocation, final Point p){
        try {
            mRouteTask = RouteTask
                    .createOnlineRouteTask(
                                "http://sampleserver3.arcgisonline.com/ArcGIS/rest/services/Network/USA/NAServer/Route",
                            null);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        QueryDirections(mLocation,p);
    }

    private void QueryDirections(final Point mLocation, final Point p) {

//        // Show that the route is calculating
//        dialog = ProgressDialog.show(RoutingSample.this, "Routing Sample",
//                "Calculating route...", true);
        // Spawn the request off in a new thread to keep UI responsive
        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    // Start building up routing parameters
                    RouteParameters rp = mRouteTask
                            .retrieveDefaultRouteTaskParameters();
                    NAFeaturesAsFeature rfaf = new NAFeaturesAsFeature();
                    // Convert point to EGS (decimal degrees)
                    // Create the stop points (start at our location, go
                    // to pressed location)
                    StopGraphic point1 = new StopGraphic(mLocation);
                    StopGraphic point2 = new StopGraphic(p);
                    rfaf.setFeatures(new Graphic[] { point1, point2 });
                    rfaf.setCompressedRequest(true);
                    rp.setStops(rfaf);
                    // Set the routing service output SR to our map
                    // service's SR
                    rp.setOutSpatialReference(wm);

                    // Solve the route and use the results to update UI
                    // when received
                    mResults = mRouteTask.solve(rp);
//                    mHandler.post(mUpdateResults);
                } catch (Exception e) {
                    mException = e;
//                    mHandler.post(mUpdateResults);
                }
            }
        };
        // Start the operation
        t.start();

    }

    private class MyLocationListener implements LocationListener {

        public MyLocationListener() {
            super();
        }

        /**
         * If location changes, update our current location. If being found for
         * the first time, zoom to our current position with a resolution of 20
         */
        public void onLocationChanged(Location loc) {
            if (loc == null)
                return;
            mLocation = new Point(loc.getLongitude(), loc.getLatitude());
        }

        public void onProviderDisabled(String provider) {
//            Toast.makeText(getApplicationContext(), "GPS Disabled",
//                    Toast.LENGTH_SHORT).show();
        }

        public void onProviderEnabled(String provider) {
//            Toast.makeText(getApplicationContext(), "GPS Enabled",
//                    Toast.LENGTH_SHORT).show();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

    }

}
