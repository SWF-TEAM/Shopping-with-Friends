package am.te.myapplication.presenter;

import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import java.util.logging.Level;
import java.util.logging.Logger;

import am.te.myapplication.R;


public class Welcome extends Activity {

    private static final Logger log = Logger.getLogger("Welcome");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }


    public void openLogin(View v) {

        log.log(Level.INFO, "Logging-in, view is " + v.toString());

        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openRegister(View v) {

        log.log(Level.INFO, "Registering, view is " + v.toString());

        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void ahh(){
        MediaPlayer player = MediaPlayer.create(this, R.raw.ahhhh);
        player.start();
    }
}
