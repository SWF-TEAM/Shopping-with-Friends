package am.te.myapplication.Service;

import android.os.AsyncTask;

/**
 * Created by Collin on 3/22/15.
 */
public abstract class PopulateTask extends AsyncTask<Void, Void, Boolean> {
    protected static final String server_url = "http://artineer.com/sandbox";
}
