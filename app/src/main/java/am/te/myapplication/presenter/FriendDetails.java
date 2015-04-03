package am.te.myapplication.presenter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.logging.Level;
import java.util.logging.Logger;

import am.te.myapplication.model.User;

import am.te.myapplication.R;
import am.te.myapplication.service.RemoveFriendTask;
import am.te.myapplication.service.UserTask;


/**
 * This displays the details of your friend that you clicked on
 *
 * @author Collin Caldwell
 * @version 1.0
 * @since 2015 - February - 26
 */
public class FriendDetails extends Activity {
    private User currentUser;
    private static final Logger log = Logger.getLogger("FriendDetails");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_details);

        Bundle extras = getIntent().getExtras();
        currentUser = FriendList.selectedFriend;
        // Put the username onto the details screen
        TextView usernameText = (TextView) findViewById(R.id.Username);
        usernameText.setText(currentUser.getUsername());

        // Put the email onto the details screen
        TextView emailText = (TextView) findViewById(R.id.Email);
        emailText.setText(currentUser.getEmail());

        // Put the rating onto the details screen
        TextView ratingText = (TextView) findViewById(R.id.Rating);
        ratingText.setText("Rating: "+ String.valueOf(currentUser.getRating()));

        // Put the email onto the details screen
        TextView salesText = (TextView) findViewById(R.id.salesReports);
        salesText.setText("");
    }

    /**
     * Removes your friends while in details view
     *
     */
    public void removeFriend(View v) {

        log.log(Level.INFO, "Removing friend, view is " + v.toString());

        UserTask mRemoveFriendTask =
                            new RemoveFriendTask(currentUser.getId(), this);
        mRemoveFriendTask.execute();
        mRemoveFriendTask = null;
    }
}
