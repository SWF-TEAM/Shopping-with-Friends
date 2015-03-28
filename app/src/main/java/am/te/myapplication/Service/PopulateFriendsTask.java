package am.te.myapplication.Service;

import android.app.Activity;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.User;

public class PopulateFriendsTask extends UserTask {

    private List<User> toPopulate;
    private ArrayAdapter adapter;
    private Activity activity;

    public PopulateFriendsTask(List<User> toPopulate) {
        this.toPopulate = toPopulate;
    }

    public PopulateFriendsTask(List<User> toPopulate, ArrayAdapter adapter, Activity activity) {
        this.toPopulate = toPopulate;
        this.adapter = adapter;
        this.activity = activity;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        System.out.println("beginning to populate friends");
        //DATABASE SHIT (get a list of possible friends from database)
        ArrayList<User> theFriends = new ArrayList<>();
        String TAG = PopulateFriendsTask.class.getSimpleName();

        String link = server_url + "/listfriends.php?userID=" + Agent.getUniqueIDofCurrentlyLoggedIn();
        String result = fetchHTTPResponseAsStr(TAG, link);
        if (result.equals("0 results")) {
            Log.d(TAG, result);
            return false;
        }
        Log.d(TAG, result);
        JSONArray jsonArray = null;
        try {
            jsonArray = new JSONArray(result);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        for (int i = 0; i < jsonArray.length() - 1; i++) {
            try {
                JSONObject lineOfArray = jsonArray.getJSONObject(i);
                String id = lineOfArray.getString("friendID");
                String email = lineOfArray.getString("email");
                String name = lineOfArray.getString("name");
                String description = lineOfArray.getString("description");
                String username = lineOfArray.getString("username");
                User friend = new User(username, "", email, id, description, name);
                theFriends.add(friend);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }
        System.out.println("all went well with getting friend data");
        System.out.println("the number of friends to be populated into list is: " + theFriends.size());
        toPopulate.clear();
        toPopulate.addAll(theFriends);
        if (adapter != null) {
            activity.runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    adapter.notifyDataSetChanged();
                }
            });
        }
        return true;
    }

    /*@Override
    protected void onCancelled() {
        mUserPopulateFriendsTask = null;
    }*/
}