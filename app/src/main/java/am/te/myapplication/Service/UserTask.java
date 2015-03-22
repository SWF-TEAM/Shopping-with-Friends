package am.te.myapplication.Service;

import android.app.Activity;
import android.os.AsyncTask;

/**
 * The parent of all tasks that a user may perform.
 *
 * @author Mitchell Manguno
 * @version 1.0
 * @since 2015 March 22
 */
public abstract class UserTask extends AsyncTask<Void, Void, Boolean> {

    protected static final String server_url = "http://artineer.com/sandbox";

}
