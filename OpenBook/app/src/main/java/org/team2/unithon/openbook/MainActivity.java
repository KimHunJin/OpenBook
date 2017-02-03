package org.team2.unithon.openbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.nhn.android.naverlogin.OAuthLogin;

import org.team2.unithon.openbook.main.MainFragment;
import org.team2.unithon.openbook.main.MainMapFragment;
import org.team2.unithon.openbook.member.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private boolean isGPS = false;
    private FragmentManager fm;
    private FragmentTransaction fragmentTransaction;
    private Toolbar toolbar;
    ImageView imgSwitch, imgNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


//        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

        initialize();
        click();

    }

    void initialize() {

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setContentInsetsAbsolute(0,0);
        setSupportActionBar(toolbar);
        imgSwitch = (ImageView) findViewById(R.id.img_toolber_gps);
        imgNav = (ImageView)findViewById(R.id.img_toolbar_nav);

        swapFrame();
    }

    void click() {
        imgSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isGPS == false) {
                    isGPS = true;
                } else {
                    isGPS = false;
                }
                swapFrame();
            }
        });

        imgNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,OAuthLogin.getInstance().getAccessToken(getApplicationContext())+"");
                OAuthLogin.getInstance().logout(getApplicationContext());
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });
    }


    /**
     * 프래그먼트를 교체하는 메서드
     */
    void swapFrame() {
        Log.e(TAG,isGPS+"");
        fm = getSupportFragmentManager();
        fragmentTransaction = fm.beginTransaction();

        if (isGPS == false) {
            MainFragment mainFragment = new MainFragment();
            mainFragment.setArguments(new Bundle());
            fragmentTransaction.replace(R.id.main_contents, mainFragment);
        } else {
            MainMapFragment mainMapFragment = new MainMapFragment();
            mainMapFragment.setArguments(new Bundle());
            fragmentTransaction.replace(R.id.main_contents, mainMapFragment);
            fragmentTransaction.addToBackStack(null); // back button click

        }
        fragmentTransaction.commit();
    }
}
