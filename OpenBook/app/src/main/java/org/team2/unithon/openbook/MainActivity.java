package org.team2.unithon.openbook;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import org.team2.unithon.openbook.main.MainFragment;
import org.team2.unithon.openbook.member.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startActivity(new Intent(getApplicationContext(), LoginActivity.class));

//        MainFragment mainFragment = new MainFragment();
//        mainFragment.setArguments(new Bundle());
//        FragmentManager fm = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fm.beginTransaction();
//        fragmentTransaction.add(R.id.main_contents, mainFragment);
//        fragmentTransaction.commit();
    }
}
