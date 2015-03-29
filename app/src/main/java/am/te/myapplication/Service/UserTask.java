package am.te.myapplication.Service;
import android.util.Log;

import android.os.AsyncTask;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;

import java.io.UnsupportedEncodingException;

/**
 * The parent of all tasks that the current user may perform.
 *
 * @author Veronica LeBlanc, Mitchell Manguno
 * @version 1.1
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

    /**
     * Makes an HTTP get request, reads the html response, and returns it as a string.
     * Returns an empty string if there's an exception.
     *
     * @param TAG the string used to tag log entries.
     * @param link the link which receives the get request and returns a response.
     * @return a string representing the data in the html response.
     * @since 2015 March 23
     */
    public static String fetchHTTPResponseAsStr(String TAG, String link){
        String parsedResponse = "";

        try {
            //establishes a connection to "link" for a get request
            HttpClient client = new DefaultHttpClient();
            HttpGet request = new HttpGet();
            request.setURI(new URI(link));

            //sends html request and stores response
            HttpResponse response = client.execute(request);

            //reads html content and converts to string
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder("");
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
                break;
            }
            in.close();

            parsedResponse = sb.toString();
            Log.d(TAG, parsedResponse);

        }catch(Exception e){
            Log.e(TAG, "EXCEPTION>>>>", e);
        }

        return parsedResponse;
    }
}
