package am.te.myapplication.Service;

import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;

/**
 * The parent of all tasks that the current user may perform.
 *
 * @author Mitchell Manguno
 * @version 1.0
 * @since 2015 March 22
 */
public abstract class UserTask extends AsyncTask<Void, Void, Boolean> {

    protected static final String server_url = "http://artineer.com/sandbox";

    /**
     * Encodes a String, so that it may be used in a URL.
     *
     * @param toEncode the string to encode
     * @return the encoded string
     * @throws UnsupportedEncodingException
     */
    public static String encode(String toEncode) throws
                                                  UnsupportedEncodingException {
        return java.net.URLEncoder.encode(toEncode, "UTF-8");
    }
}
