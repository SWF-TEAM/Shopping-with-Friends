package am.te.myapplication.Test;


import android.content.Intent;
import android.test.ActivityUnitTestCase;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;

import am.te.myapplication.presenter.FriendList;
import am.te.myapplication.model.Agent;
import am.te.myapplication.model.User;
import am.te.myapplication.service.PopulateFriendsTask;

public class PopulateFriendsTaskTest extends ActivityUnitTestCase<FriendList> {
    private Intent intent;
    private PopulateFriendsTask task;

    public PopulateFriendsTaskTest() {
        super(FriendList.class);
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

    public void testPopulateFriendsTaskOneFriend() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        List<User> toPopulate = new ArrayList<>();
        String userIDtoTest = "U5518ba4a"; //coolcat0 user
        int numFriends = 1; //number of friends which coolcat0 has
        Agent.setUniqueIDofCurrentlyLoggedIn(userIDtoTest);

        task = new PopulateFriendsTask(toPopulate);

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                task.execute();
            }
        });
        Thread.sleep(10000);
        assertTrue(toPopulate.size() == numFriends);
    }

    public void testPopulateFriendsTaskNoFriends() throws Throwable {
        final CountDownLatch signal = new CountDownLatch(1);

        List<User> toPopulate = new ArrayList<>();
        String userIDtoTest = "U551b0931"; //sam user
        int numFriends = 0; //number of friends which sam has
        Agent.setUniqueIDofCurrentlyLoggedIn(userIDtoTest);

        task = new PopulateFriendsTask(toPopulate);

        runTestOnUiThread(new Runnable() {
            @Override
            public void run() {
                task.execute();
            }
        });
        Thread.sleep(10000);
        assertTrue(toPopulate.size() == numFriends);
    }
}