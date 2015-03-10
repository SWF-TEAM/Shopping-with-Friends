package am.te.myapplication;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;


public class Welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }


    public void openLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openRegister(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }
}
