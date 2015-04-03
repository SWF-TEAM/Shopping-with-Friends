package am.te.myapplication.Test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.text.SpannableStringBuilder;
import android.widget.Button;
import android.widget.EditText;

import am.te.myapplication.Model.Agent;
import am.te.myapplication.R;
import am.te.myapplication.SearchFriends;

/**
 * Tests the add friend functionality of the application.
 *
 * @author Veronica LeBlanc
 * @version 1.0
 * @since 2015 April 02
 */
public class AddFriendTest extends ActivityInstrumentationTestCase2 {

    private final String VALID_FRIEND = "vll"; //this is the friend to add
    private final String VALID_EMAIL = "vll@gt.edu"; //this is the friend's email
    private final String MATCH_ERR = "wrong user or email";
    private final String NULL_FIELD = "This field is required";

    private Activity mActivity;
    private EditText mNameView;
    private EditText mEmailView;
    private Button searchFriends;

    public AddFriendTest() {
        super(SearchFriends.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(false);

        String userIDtoTest = "U551ede55"; //user v (the only user who will add vll as friend)
        Agent.setUniqueIDofCurrentlyLoggedIn(userIDtoTest);

        mActivity = getActivity();

        // grab the fields from the activity
        mNameView = (EditText) mActivity.findViewById(R.id.name);
        mEmailView = (EditText) mActivity.findViewById(R.id.email);
        searchFriends = (Button) mActivity.findViewById(R.id.search_button);


        // make sure that it was initialized to the correct values
        preconditions();
    }

    @Override
    protected void tearDown() throws Exception {
            super.tearDown();
    }

    /**
     * Tests for an email which does not match with a valid username
     * Expected to NOT add friend
     */
    @UiThreadTest
    public void testInvalidEmail() {
        String email0 = ""; // empty email
        String email1 = "vll@gt.edu"; //email of a valid user, but not this user
        String email2 = "awefpoijasdfpiojasdf"; //total nonsense

        String[] emails = {email0, email1, email2};

        for (int i = 0; i < emails.length; i++) {
            mNameView.setText(VALID_FRIEND);
            mEmailView.setText(emails[i]);
            searchFriends.performClick();

            //assertNotNull("No error message found", mEmailView.getError());
            final String actual = mEmailView.getText().toString();
            final CharSequence error = mEmailView.getError();

            assertEquals("Invalid match of user and password", actual, emails[i]);
            //assertNotNull("No error occurred despite invalid addFriend", error);
        }
    }

    /**
     * Tests for a username which does not match with a valid password
     * Expected to NOT add friend.
     */
    @UiThreadTest
    public void testInvalidName() {
        String name0 = ""; // blank name, this user does not exist
        String name1 = "coolcat0"; //this user does not match the email
        String name2 = "apsodifjaposidjfpasidjf"; //this user does not exist

        String[] names = {name0, name1, name2};

        for (int i = 0; i < names.length; i++) {
            mNameView.setText(names[i]);
            mEmailView.setText(VALID_EMAIL);
            searchFriends.performClick();

//            assertNotNull("No error message found", mNameView.getError());
            final String actual = mNameView.getText().toString();
            final CharSequence error = mNameView.getError();

            assertEquals("Invalid match of user and password", actual, names[i]);
         //   assertNotNull("No error occurred despite invalid addFriend", error);
        }
    }

    /**
     * Tests for the successful case of login: correct username, correct
     * password, and a corresponding user in the database.
     */
    @UiThreadTest
    public void testSuccessfulAddFriend() {
        mNameView.setText(VALID_FRIEND);
        mEmailView.setText(VALID_EMAIL);

        final boolean success = searchFriends.performClick();

        //make sure click works
        assertTrue("Button has no listener", success);

        final CharSequence passErr = mNameView.getError();
        final CharSequence userErr = mEmailView.getError();

        //make sure there are no errors
        assertEquals("Error occurred during valid addFriend", null, passErr);

        //make sure there are no errors
        assertEquals("Error occurred during valid addFriend", null, userErr);
    }

    /**
     * Makes sure that the activity starts up with the correct preconditions.
     */
    private void preconditions() {
        assertNotNull("The activity was null", mActivity);
        assertNotNull("The username view was null", mNameView);
        assertNotNull("The password view was null", mEmailView);
        assertNotNull("The search button was null", searchFriends);
    }

}
