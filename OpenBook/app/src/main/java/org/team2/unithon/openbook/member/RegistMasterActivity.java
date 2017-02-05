package org.team2.unithon.openbook.member;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.team2.unithon.openbook.R;

public class RegistMasterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist_master);
        windowBar();
    }
    void windowBar() {
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorToolbar));
        }
    }

}
