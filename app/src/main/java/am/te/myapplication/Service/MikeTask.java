package am.te.myapplication.Service;

import android.app.Activity;
import android.media.MediaPlayer;
import android.view.View;

import am.te.myapplication.R;

/**
 * Created by Collin on 3/28/15.
 */
public class MikeTask {
    static MediaPlayer player;

    public static void ahh(Activity activity){
        player=MediaPlayer.create(activity, R.raw.ahhhh);
        player.start();
    }
}
