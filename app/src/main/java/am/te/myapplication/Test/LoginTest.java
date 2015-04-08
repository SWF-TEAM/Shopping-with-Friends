package am.te.myapplication.Test;

import am.te.myapplication.presenter.Login;
import am.te.myapplication.R;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.test.UiThreadTest;
import android.text.Editable;
import android.text.InputFilter;
import android.text.SpannableStringBuilder;
import android.widget.Button;
import android.widget.EditText;

/**
 * Tests the login functionality of the application.
 *
 * @author Mitchell Manguno <mmanguno3@gatech.edu>
 * @version 1.0
 * @since 2015 April 02
 */
public class LoginTest extends ActivityInstrumentationTestCase2 {

    private final String VALID_USER = "m";
    private final String VALID_PASS = "mmmm";
    private final String PASS_ERR = "This password is incorrect";
    private final String USER_ERR = "invalid username";
    private final String SIGNIN_ERR = "Invalid password or username.";
    private final String NULL_FIELD = "This field is required";
    private Activity mActivity;
    private EditText mUsernameView;
    private EditText mPasswordView;
    private Button signIn;

    public LoginTest() {
        super(Login.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        setActivityInitialTouchMode(false);

        mActivity = getActivity();

        // grab the fields from the activity
        mUsernameView = (EditText) mActivity.findViewById(R.id.login_username);
        mPasswordView = (EditText) mActivity.findViewById(R.id.password);
        signIn = (Button) mActivity.findViewById(R.id.username_sign_in_button);


        // make sure that it was initialized to the correct values
        preconditions();
    }

    @Override
    protected void tearDown() throws Exception {
            super.tearDown();
    }

    /**
     * Tests for a password not being of the form [a-zA-Z0-9]++ of length
     * [4, INF).
     */
    @UiThreadTest
    public void testInvalidPasswordFormat() {
        String pass0 = ""; // empty password
        String pass1 = "1"; // short password
        String pass2 = "1a"; // short password
        String pass3 = "ms1"; // short password
        String pass4 = "@!%(@#&!(#%!Y#%!2"; // invalid chars
        String pass5 = "!%$"; //compound error: short & invalid

        String[] passes = {pass0, pass1, pass2, pass3, pass4, pass5};

        for (int i = 0; i < passes.length; i++) {
            mUsernameView.setText(VALID_USER);
            mPasswordView.setText(passes[i]);
            signIn.performClick();
            assertNotNull("No error message found", mPasswordView.getError());
            final String actual = mPasswordView.getText().toString();
            final String error = mPasswordView.getError().toString();
            assertEquals("Unexpected data passed in", actual, passes[i]);
            assertEquals("Error message not shown", error, PASS_ERR);
        }

    }

    /**
     * Tests for a username not being of the form [a-zA-Z0-9]++.
     */
    @UiThreadTest
    public void testIncorrectUsernameFormat() {
        String name0 = "@!%(@#&!(#%!Y#%!2"; // invalid chars
        String name1 = "!%$"; // invalid chars

        String[] name = {name0, name1};

        for (int i = 0; i < name.length; i++) {
            mUsernameView.setText(name[i]);
            mPasswordView.setText(VALID_PASS);
            signIn.performClick();
            assertNotNull("No error message found", mUsernameView.getError());
            final String actual = mUsernameView.getText().toString();
            final String error = mUsernameView.getError().toString();
            assertEquals("Unexpected data passed in", actual, name[i]);
            assertFalse("Error not recognized by field",
                                    new SpannableStringBuilder().equals(error));
            assertEquals("Error message not shown", error, USER_ERR);
        }
    }

    /**
     * Tests for a username and password that are valid, but no such user
     * exists in the database.
     */
    @UiThreadTest
    public void testCorrectInformationFormatNoSuchUser() {

        // I can guarantee with some certainty that this username and password
        // will not be chosen by a user.
        final String username =  "ptD0sW9lX0mANe2VShihAUfKJuamv5JZi3qQV87Wk" +
                "z0et0nkToI30dt96PsX7aqCSrhlDzV2U1yGeWCt0EswS6lM0do2pgLfBVTW";
        final String password = "Y0nadr2sXmlLOrjeN3EwTjnrv8eOj8Z2Y4K1zflUo5" +
                "BUAFTrkmi517p4qdpT9oz1CJ7XYJ4ytWYzHm8rG8PQr3AuyJnPYNp5tpWx";

        mUsernameView.setText(username);
        mPasswordView.setText(password);

        final boolean success = signIn.performClick();

        assertTrue("Button has no listener", success);

        final CharSequence err = mPasswordView.getError();

        assertNotNull("Error was not shown", err);

        final String errStr = err.toString();

        assertEquals("Error not raised", errStr,
                                               "Invalid password or username.");

    }

    /**
     * Tests for the successful case of login: correct username, correct
     * password, and a corresponding user in the database.
     */
    @UiThreadTest
    public void testXXCorrectLogin() {
        mUsernameView.setText(VALID_USER);
        mPasswordView.setText(VALID_PASS);

        final boolean success = signIn.performClick();

        assertTrue("Button has no listener", success);

        final CharSequence passErr = mPasswordView.getError();
        final CharSequence userErr = mUsernameView.getError();

        assertEquals("Error occurred during normal sign-in", null, passErr);

        assertEquals("Error occurred during normal sign-in", null, userErr);

        assertTrue("Activity is not finished", mActivity.isFinishing());

    }

    /**
     * Makes sure that the activity starts up with the correct preconditions.
     */
    private void preconditions() {
        assertNotNull("The activity was null", mActivity);
        assertNotNull("The username view was null", mUsernameView);
        assertNotNull("The password view was null", mPasswordView);
        assertNotNull("The sign-in button was null", signIn);
    }

}
