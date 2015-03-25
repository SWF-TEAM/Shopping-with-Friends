package am.te.myapplication.Service;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import am.te.myapplication.FriendList;
import am.te.myapplication.Model.Agent;
import am.te.myapplication.Model.User;

public class PopulateFriendsTask extends UserTask {
    List<User> toPopulate;

    public PopulateFriendsTask(List<User> toPopulate) {
        this.toPopulate = toPopulate;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        //DATABASE SHIT (get a list of possible friends from database)
        ArrayList<User> theFriends = new ArrayList<>();
        String TAG = FriendList.class.getSimpleName();

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
        toPopulate.clear();
        toPopulate.addAll(theFriends);
        return true;
    }

    /*@Override
    protected void onCancelled() {
        mUserPopulateFriendsTask = null;
    }*/
}