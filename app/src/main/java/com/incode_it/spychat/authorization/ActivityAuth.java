package com.incode_it.spychat.authorization;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.incode_it.spychat.contacts.ActivityMain;
import com.incode_it.spychat.C;
import com.incode_it.spychat.R;
import com.incode_it.spychat.interfaces.OnFragmentsAuthorizationListener;

import java.util.ArrayList;

public class ActivityAuth extends AppCompatActivity implements OnFragmentsAuthorizationListener {
    private CoordinatorLayout coordinatorLayout;
    private static final String TAG = "myhttp";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    public static String myPhoneNumber = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPlayServices();
        if (checkIsLoggedIn())
        {
            Intent intent = new Intent(this, ActivityMain.class);
            intent.putExtra(C.REQUEST_PIN, true);
            startActivityForResult(intent, C.REQUEST_CODE_SKIP_AUTH);
            return;
        }
        else
        {
            setContentView(R.layout.activity_auth);
        }

        getPhoneNumber();

        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        assert toolbar != null;
        toolbar.setTitle("SPYchat");
        setSupportActionBar(toolbar);

        ViewPager viewPager = (ViewPager) findViewById(R.id.fragment_view_pager);
        AuthFragmentPagerAdapter adapter = new AuthFragmentPagerAdapter(getSupportFragmentManager());
        assert viewPager != null;
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        assert tabLayout != null;
        tabLayout.setupWithViewPager(viewPager);
    }

    private boolean checkIsLoggedIn()
    {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String accessToken = sharedPreferences.getString(C.SHARED_ACCESS_TOKEN, null);
        return accessToken != null;
    }

    @Override
    public void onLogIn()
    {
        Log.i(TAG, "onLogIn");
        hideKeyBoard();
    }

    @Override
    public void onSignUp() {
        Log.i(TAG, "onSignUp");
        hideKeyBoard();
    }

    @Override
    public void onAuthorizationSuccess(String accessToken, String refreshToken, String myPhoneNumber) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.edit()
                .putString(C.SHARED_ACCESS_TOKEN, accessToken)
                .putString(C.SHARED_REFRESH_TOKEN, refreshToken)
                .putString(C.SHARED_MY_PHONE_NUMBER, myPhoneNumber)
                .apply();
        Intent intent = new Intent(this, ActivityMain.class);
        intent.putExtra(C.REQUEST_PIN, false);
        startActivityForResult(intent, C.REQUEST_CODE_SKIP_AUTH);
    }

    @Override
    public void onError(String error) {
        Snackbar snackbar = Snackbar.make(coordinatorLayout, "Connection error", Snackbar.LENGTH_INDEFINITE)
                .setActionTextColor(Color.RED)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

        TextView textView = (TextView) snackbar.getView().findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    private boolean hasConnection() {
        ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();
    }

    public void getPhoneNumber()
    {
        Log.d("myPerm", "AA getPhoneNumber ");
        TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
        myPhoneNumber = tm.getLine1Number();
        /*if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.READ_SMS)
                        != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(new String[]{Manifest.permission.READ_SMS},
                    C.READ_SMS_CODE);
        }
        else
        {
            TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
            myPhoneNumber = tm.getLine1Number();
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d("myPerm", "AA onActivityResult resultCode "+resultCode);
        if (resultCode == C.REQUEST_CODE_SKIP_AUTH) finish();
    }

    /*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("myPerm", "AA onRequestPermissionsResult " + requestCode);
        if (requestCode == C.READ_SMS_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
                myPhoneNumber = tm.getLine1Number();
                if (myPhoneNumber == null || myPhoneNumber.length() == 0)
                {
                    myPhoneNumber = "";
                    Toast.makeText(this, "Phone number is unavailable", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "Sorry!!! Permission Denied",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }*/

    public class AuthFragmentPagerAdapter extends FragmentPagerAdapter {
        ArrayList<Fragment> fragmentArrayList;

        public AuthFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentArrayList = new ArrayList<>();
            fragmentArrayList.add(new FragmentLogIn());
            fragmentArrayList.add(new FragmentSingUp());
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentArrayList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentArrayList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) return getString(R.string.log_in);
            else return getString(R.string.sign_up);
        }
    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST)
                        .show();
            } else {
                Log.i(TAG, "This device is not supported.");
                finish();
            }
            return false;
        }
        return true;
    }

    public void hideKeyBoard() {
        View view = getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
