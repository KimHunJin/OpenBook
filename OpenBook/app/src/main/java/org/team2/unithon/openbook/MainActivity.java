package org.team2.unithon.openbook;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nhn.android.naverlogin.OAuthLogin;

import org.team2.unithon.openbook.main.MainFragment;
import org.team2.unithon.openbook.main.MainMapFragment;
import org.team2.unithon.openbook.member.LoginActivity;
import org.team2.unithon.openbook.model.GPSPost;
import org.team2.unithon.openbook.model.Test;
import org.team2.unithon.openbook.network.NetworkRequest;
import org.team2.unithon.openbook.network.RestAPI;
import org.team2.unithon.openbook.network.RestApiBuilder;
import org.team2.unithon.openbook.utils.BackPressCloseHandler;
import org.team2.unithon.openbook.utils.StaticServerUrl;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Subscription;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private boolean isGPS = false;
    private FragmentManager fm;
    private FragmentTransaction fragmentTransaction;
    private Toolbar toolbar;
    ImageView imgSwitch, imgNav;
    private LinearLayout linMyPage, linRegistShop, linLogout;
    private BackPressCloseHandler backPressCloseHandler;
    private DrawerLayout drawerLayout;

    private Subscription mGetPostSubscription;

    private boolean navOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initialize();
        click();
        windowBar();
//        network();
        getThumbnail();
    }

    void getThumbnail() {

    }



    void windowBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorToolbar));
        }
    }

    void initialize() {

        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setContentInsetsAbsolute(0, 0);
        setSupportActionBar(toolbar);
        imgSwitch = (ImageView) findViewById(R.id.img_toolber_gps);
        imgNav = (ImageView) findViewById(R.id.img_toolbar_nav);

        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);

        linLogout = (LinearLayout)findViewById(R.id.nav_logout);
        backPressCloseHandler = new BackPressCloseHandler(this);
        swapFrame();
    }

    void click() {
        Log.e(TAG,"image switch");
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
                if(navOpen==false) {
                    drawerLayout.openDrawer(Gravity.LEFT);
                    navOpen = true;
                } else {
                    drawerLayout.closeDrawer(Gravity.LEFT);
                    navOpen = false;
                }
            }
        });

        linLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, OAuthLogin.getInstance().getAccessToken(getApplicationContext()) + "");
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
        Log.e(TAG, isGPS + "");
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

    /**
     * 뒤로가기 키를 눌렀을 때
     */
    @Override
    public void onBackPressed() {
        // 네비게이션이 열려있으면
        //핸들러 작동
        backPressCloseHandler.onBackPressed();
        if(isGPS == true) {

        } else {
            Toast.makeText(getApplicationContext(), "한 번 더 누르면 앱이 종료됩니다", Toast.LENGTH_SHORT).show();
        }
    }
}
