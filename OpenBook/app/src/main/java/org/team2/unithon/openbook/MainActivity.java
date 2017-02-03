package org.team2.unithon.openbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.team2.unithon.openbook.map.Main2Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(getApplicationContext(), Main2Activity.class));
    }
}
