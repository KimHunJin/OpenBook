package org.team2.unithon.openbook.member;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.nhn.android.naverlogin.OAuthLogin;
import com.nhn.android.naverlogin.OAuthLoginHandler;
import com.nhn.android.naverlogin.ui.view.OAuthLoginButton;

import org.team2.unithon.openbook.MainActivity;
import org.team2.unithon.openbook.R;
import org.team2.unithon.openbook.utils.NaverKey;

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


        txt = (TextView) findViewById(R.id.textView);

    }

    private OAuthLoginHandler mOAuthLoginHandler = new OAuthLoginHandler() {
        @Override
        public void run(boolean success) {
            Log.e(TAG, success + "");
            if (success) {
                //로그인 성공시 처리해야 할것들
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            } else {
            }
        }
    };


}
