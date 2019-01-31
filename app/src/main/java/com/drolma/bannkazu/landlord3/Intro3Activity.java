package com.drolma.bannkazu.landlord3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.drolma.bannkazu.R;
import com.drolma.bannkazu.landlord5.Game5Activity;

/**
 * Created by Drolma on 16/5/23.
 */
public class Intro3Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Intro3Activity";
    private EditText name1;
    private EditText name2;
    private EditText name3;
    private String nameStr1;
    private String nameStr2;
    private String nameStr3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro3);
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
        name1 = (EditText) findViewById(R.id.intro3_pName1);
        name2 = (EditText) findViewById(R.id.intro3_pName2);
        name3 = (EditText) findViewById(R.id.intro3_pName3);
        Button startBtn = (Button) findViewById(R.id.intro3_start_button);
        startBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.intro3_start_button:
                getInput();
                Intent jumpIntent = new Intent();
                String[] nameArr = getPlayerNames();
                jumpIntent.setAction("android.intent.action.Game3");
                jumpIntent.setClass(this, Game3Activity.class);
                jumpIntent.putExtra("playsNameArray3", nameArr);
                this.startActivity(jumpIntent);
                Toast.makeText(this, "開始遊戲！", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void getInput() {
        nameStr1 = name1.getText().toString();
        nameStr2 = name2.getText().toString();
        nameStr3 = name3.getText().toString();
    }

    private String[] getPlayerNames() {
        String[] nameArr = { nameStr1, nameStr2, nameStr3 };
        return nameArr;
    }

}
