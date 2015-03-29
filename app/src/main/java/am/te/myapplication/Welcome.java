package am.te.myapplication;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.content.Intent;


public class Welcome extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome);
    }


    public void openLogin() {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void openRegister() {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }

    public void ahh(){
        MediaPlayer player = MediaPlayer.create(this, R.raw.ahhhh);
        player.start();
    }
}
