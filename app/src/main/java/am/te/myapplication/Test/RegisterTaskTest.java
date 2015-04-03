package am.te.myapplication.Test;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;
import android.test.ActivityUnitTestCase;
import android.test.UiThreadTest;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.CountDownLatch;

import am.te.myapplication.FriendList;
import am.te.myapplication.R;
import am.te.myapplication.Register;
import am.te.myapplication.Service.RegisterTask;

/**
 * Created by Collin C-C-C-Caldwell on 4/2/15.
 * Used to test the Register Task
 */
public class RegisterTaskTest extends ActivityInstrumentationTestCase2 {
    private final String email = "m@m.com";
    private final String username = "m";
    private final String name = "m";
    private final String pass = "mmmm";
    private final String pass2 = "mmmm";
    private AutoCompleteTextView emailView;
    private EditText usernameView;
    private EditText nameView;
    private EditText passView;
    private EditText pass2View;
    private Activity activity;
    private Button regButton;

    public RegisterTaskTest() {
        super(Register.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        setActivityInitialTouchMode(false);

        activity = getActivity();

        emailView = (AutoCompleteTextView) activity.findViewById(R.id.reg_email);
        usernameView = (EditText) activity.findViewById(R.id.reg_username);
        nameView = (EditText) activity.findViewById(R.id.reg_name);
        passView = (EditText) activity.findViewById(R.id.reg_pass1);
        pass2View = (EditText) activity.findViewById(R.id.reg_pass2);
        regButton = (Button) activity.findViewById(R.id.reg_register);
    }

    @Override
    protected void tearDown() throws Exception {
        emailView.setText(email);
        usernameView.setText(username);
        nameView.setText(name);
        passView.setText(pass);
        pass2View.setText(pass);
        emailView = (AutoCompleteTextView) activity.findViewById(R.id.reg_email);
        passView = (EditText) activity.findViewById(R.id.reg_pass1);
        regButton = (Button) activity.findViewById(R.id.reg_register);

    }

    @UiThreadTest
     public void testAAShortSamePassword() {
        emailView.setText("an@stuff.com");
        usernameView.setText(username);
        nameView.setText(name);
        passView.setText("a");
        pass2View.setText("a");

        final boolean success = regButton.performClick();

//        assertEquals("An error appeared",passView.getError());

    }

    @UiThreadTest
    public void testNoEmail() {
        emailView.setText("");
        usernameView.setText(username);
        nameView.setText(name);
        passView.setText(pass);
        pass2View.setText(pass);
        final boolean success = regButton.performClick();

        assertNull("An error appeared", emailView.getError());
    }

    @UiThreadTest
    public void testZeGoodRegister() {
        emailView.setText(email);
        usernameView.setText(username);
        nameView.setText(name);
        passView.setText(pass);
        pass2View.setText(pass2);

        final boolean success = regButton.performClick();
        assertNull("This email address is invalid", emailView.getError());
        assertNull("This email address is invalid", passView.getError());

    }

    // Check for a valid password, if the user entered one.
    // Check for a valid email address.
    // Check for same passwords


}
