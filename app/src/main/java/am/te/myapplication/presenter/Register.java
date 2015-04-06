package am.te.myapplication.presenter;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.net.Uri;
import android.provider.ContactsContract;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import am.te.myapplication.R;
import am.te.myapplication.model.User;
import am.te.myapplication.service.RegisterTask;
import am.te.myapplication.service.UserTask;
import am.te.myapplication.util.Validation;

public class Register extends Activity implements LoaderCallbacks<Cursor> {

    private AutoCompleteTextView emailView;
    private EditText usernameView;
    private EditText nameView;
    private EditText passwordView;
    private EditText passwordView2;

    private void populateAutoComplete() {
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up the login form.
        emailView = (AutoCompleteTextView) findViewById(R.id.reg_email);
        usernameView = (EditText) findViewById(R.id.reg_username);
        nameView = (EditText) findViewById(R.id.reg_name);
        populateAutoComplete();

        passwordView = (EditText) findViewById(R.id.reg_pass1);
        passwordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });
        passwordView2 = (EditText) findViewById(R.id.reg_pass2);
        passwordView2.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.reg_register);
        mEmailSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void proceedToShoppingPage() {
        Intent shoppingIntent = new Intent(this, Welcome.class);
        startActivity(shoppingIntent);

    }
    void attemptRegister() {

        // Reset errors.
        emailView.setError(null);
        passwordView.setError(null);
        passwordView2.setError(null);

        // Store values at the time of the login attempt.
        String email = emailView.getText().toString();
        String username = usernameView.getText().toString();
        String name = nameView.getText().toString();
        String password = passwordView.getText().toString();
        String password2 = passwordView2.getText().toString();

        boolean cancel = false;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password)
         && !Validation.isPasswordValid(password)) {
            passwordView.setError(getString(R.string.error_invalid_password));
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            emailView.setError(getString(R.string.error_field_required));
            cancel = true;
        } else if (!Validation.isEmailValid(email)) {
            emailView.setError(getString(R.string.error_invalid_email));
            cancel = true;
        }
        // Check for same passwords
        boolean passSame = Validation.arePasswordsSame(password,password2);
        if (!passSame) {
            Context context = getApplicationContext();
            CharSequence text = "Passwords don't match!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            passwordView.requestFocus();
        } else {
            //String username, String password, String email, String id, String description, String name
            User newUser = new User(username,password,email,"","",name);
            UserTask mAuthTask = new RegisterTask(newUser, this, emailView);
            mAuthTask.execute((Void) null);

        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }
    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(Register.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        emailView.setAdapter(adapter);
    }
    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
    }
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    @Override
    public void finish() {
        proceedToShoppingPage();
        super.finish();
    }

}

