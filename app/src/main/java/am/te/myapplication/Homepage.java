package am.te.myapplication;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by mitchell on 1/30/15.
 */
public class Homepage extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("test");
        setContentView(R.layout.homepage);
    }
}
