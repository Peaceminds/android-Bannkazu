package com.drolma.bannkazu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Drolma on 16/5/24.
 */
public class SpecificActivity extends Activity {

    private static final String TAG = "SpecificActivity";
    private TextView tv01;
    private TextView tv02;
    private TextView tv03;
    private TextView tv04;
    private TextView tv05;
    private TextView tv06;
    private TextView tv07;
    private TextView tv08;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific);
        tv01 = (TextView) findViewById(R.id.specific_h1);
        tv02 = (TextView) findViewById(R.id.specific_c1);
        tv03 = (TextView) findViewById(R.id.specific_h2);
        tv04 = (TextView) findViewById(R.id.specific_c2);
        tv05 = (TextView) findViewById(R.id.specific_h3);
        tv06 = (TextView) findViewById(R.id.specific_c3);
        tv07 = (TextView) findViewById(R.id.specific_h4);
        tv08 = (TextView) findViewById(R.id.specific_c4);
    }
}
