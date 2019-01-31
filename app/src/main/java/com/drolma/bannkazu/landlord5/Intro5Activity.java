package com.drolma.bannkazu.landlord5;

/**
 * A DouDiZhu(5 player) calculator
 */

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.drolma.bannkazu.R;

public class Intro5Activity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "Intro5Activity";
    private EditText name1;
    private EditText name2;
    private EditText name3;
    private EditText name4;
    private EditText name5;
    private String nameStr1;
    private String nameStr2;
    private String nameStr3;
    private String nameStr4;
    private String nameStr5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro5);
        initActionBar();
        initViews();
    }

    private void initActionBar() {
//        getSupportActionBar().setLogo(R.drawable.logo1);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setDisplayShowHomeEnabled(false);
//        getSupportActionBar().hide();
    }

    private void initViews() {
        name1 = (EditText) findViewById(R.id.intro_pName1);
        name2 = (EditText) findViewById(R.id.intro_pName2);
        name3 = (EditText) findViewById(R.id.intro_pName3);
        name4 = (EditText) findViewById(R.id.intro_pName4);
        name5 = (EditText) findViewById(R.id.intro_pName5);
        Button startBtn = (Button) findViewById(R.id.intro_start_button);
        startBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.intro_start_button:
                getInput();
                Intent jumpIntent = new Intent();
                String[] nameArr = getPlayerNames();
                jumpIntent.setAction("android.intent.action.Game5");
                jumpIntent.setClass(this, Game5Activity.class);
                jumpIntent.putExtra("playsNameArray", nameArr);
                this.startActivity(jumpIntent);
                Toast.makeText(this, "開始遊戲！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void getInput() {
        nameStr1 = name1.getText().toString();
        nameStr2 = name2.getText().toString();
        nameStr3 = name3.getText().toString();
        nameStr4 = name4.getText().toString();
        nameStr5 = name5.getText().toString();
    }

    private String[] getPlayerNames() {
        String[] nameArr = { nameStr1, nameStr2, nameStr3, nameStr4, nameStr5 };
        return nameArr;
    }

}
