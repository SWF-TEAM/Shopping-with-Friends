package am.te.myapplication;

import java.io.UnsupportedEncodingException;

/**
 * Created by elimonent on 3/6/15.
 */
public class Encoder {
    public static String encode(String toEncode) throws UnsupportedEncodingException {
        return java.net.URLEncoder.encode(toEncode, "UTF-8");
    }
}
