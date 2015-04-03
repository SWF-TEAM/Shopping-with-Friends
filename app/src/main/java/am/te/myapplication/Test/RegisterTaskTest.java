package am.te.myapplication.Test;

import android.app.Activity;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.AutoCompleteTextView;

import java.util.concurrent.CountDownLatch;

import am.te.myapplication.FriendList;
import am.te.myapplication.R;
import am.te.myapplication.Register;
import am.te.myapplication.Service.RegisterTask;

/**
 * Created by Collin C-C-C-Caldwell on 4/2/15.
 * Used to test the Register Task
 */
public class RegisterTaskTest extends ActivityUnitTestCase<Register> {
    private Intent intent;
    private RegisterTask task;
    private Register newRegister;

    public RegisterTaskTest() {
        super(Register.class);
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        intent = new Intent(Intent.ACTION_MAIN);
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    public void testRegisterUserUnique() throws Throwable {
        String username, name, email, password;
        username = "m";
        name = "m";
        email = "m@m.m";
        password = "mmmm";
        newRegister = getActivity();
        task = new RegisterTask(username, name, email, password, newRegister , new AutoCompleteTextView(newRegister));
        task.execute((Void) null);
        Thread.sleep(10000);
        assertFalse(task.get());
    }

}
