package fantasticfour.roam;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.net.InetAddress;

/**
 * Created by Vamsi on 6/4/15.
 */
public class NetCheck extends AsyncTask<Void, Void, Boolean> {

    private Context context;
    private boolean connected;
    private AsyncTask task;

    public NetCheck(Context context, AsyncTask task) {
        this.context = context;
        this.task = task;

    }


    public static void checkNetworkConnection(Context c, AsyncTask task) throws Exception{
        NetCheck check = new NetCheck(c, task);
        check.execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com"); //You can replace it with your name

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(Boolean connected) {
        this.connected = connected;
        if(!connected)
            Toast.makeText(context,"Error in Network Connection",Toast.LENGTH_SHORT).show();
        else
            task.execute();

    }
}
