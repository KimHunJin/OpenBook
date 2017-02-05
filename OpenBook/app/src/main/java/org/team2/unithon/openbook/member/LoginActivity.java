package org.team2.unithon.openbook.member;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.team2.unithon.openbook.MainActivity;
import org.team2.unithon.openbook.R;
import org.team2.unithon.openbook.model.GPSPost;
import org.team2.unithon.openbook.network.RestAPI;
import org.team2.unithon.openbook.utils.NaverKey;
import org.team2.unithon.openbook.utils.StaticServerUrl;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private OAuthLogin mOAuthLoginModule;
    private TextView txt;


    private String OAUTH_CLIENT_ID = NaverKey.CLIENT_ID;
    private String OAUTH_CLIENT_SECRET = NaverKey.OAUTH_CLIENT_SECRET;
    private String OAUTH_CLIENT_NAME = NaverKey.OAUTH_CLIENT_NAME;

    private Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = this;


        mOAuthLoginModule = OAuthLogin.getInstance();
        mOAuthLoginModule.init(
                LoginActivity.this
                , OAUTH_CLIENT_ID
                , OAUTH_CLIENT_SECRET
                , OAUTH_CLIENT_NAME
        );

        if (OAuthLogin.getInstance().getAccessToken(getApplicationContext()) != null) {
            mOAuthLoginModule.startOauthLoginActivity(this, mOAuthLoginHandler);
        } else {
            setContentView(R.layout.activity_login);
            OAuthLoginButton mOAuthLoginButton = (OAuthLoginButton) findViewById(R.id.buttonOAuthLoginImg);
            mOAuthLoginButton.setOAuthLoginHandler(mOAuthLoginHandler);
        }

        windowBar();

        txt = (TextView) findViewById(R.id.textView);

    }
    void windowBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorToolbar));
        }
    }

    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            Log.e(TAG, success + "");
            if (success) {
                //로그인 성공시 처리해야 할것들
//                network(OAuthLogin.getInstance().getAccessToken(getApplicationContext()));
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else {
            }
        }
    };

    void network(String token) {
        Map map = new HashMap();
        map.put("token", token);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StaticServerUrl.URL2)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RestAPI connectService = retrofit.create(RestAPI.class);
        Call<GPSPost> call = connectService.setToken(map);
        call.enqueue(new Callback<GPSPost>() {
            @Override
            public void onResponse(Call<GPSPost> call, Response<GPSPost> response) {
                GPSPost gpsPost = response.body();
                Log.e(TAG, gpsPost.getMessage());

            }

            @Override
            public void onFailure(Call<GPSPost> call, Throwable t) {

            }
        });
    }


}
