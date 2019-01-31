package com.drolma.bannkazu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import com.drolma.bannkazu.landlord3.Intro3Activity;
import com.drolma.bannkazu.landlord5.Intro5Activity;
import com.drolma.bannkazu.transwarp.ormTool;

/**
 * Created by Drolma on 16/5/23.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private static final String TAG = "MainActivity";
    private static final String DATABASE_SP = "spDatabase";
    private static final int SQL_MODE_3 = 3;
    private static final int SQL_MODE_5 = 5;
    private static SharedPreferences mySharedPreferences;
    private static ormTool orm;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initActionBar();
        initAllViews();
        orm = new ormTool(this);
        mySharedPreferences = getSharedPreferences(DATABASE_SP, this.MODE_PRIVATE);
    }

    private void initActionBar() {
        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().hide();
    }

    private void initAllViews() {
        Button ll3Btn = (Button) findViewById(R.id.main_btn_ll3);
        Button ll5Btn = (Button) findViewById(R.id.main_btn_ll5);
        Button mjBtn = (Button) findViewById(R.id.main_btn_mj);
        ll3Btn.setOnClickListener(this);
        ll5Btn.setOnClickListener(this);
        mjBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.main_btn_ll3:
                Log.w(TAG, "User chose the ll3 ~");
                jump(1);
                break;

            case R.id.main_btn_ll5:
                Log.w(TAG, "User chose the ll5 ~");
                jump(2);
                break;

            case R.id.main_btn_mj:
                Log.w(TAG, "User chose the mj ~");
                jump(3);
                break;
        }
    }

    private void jump(int i) {
        Intent jumpIntent = new Intent();
        switch (i) {
            case 1:
                cleanDatabases(SQL_MODE_3);
//                cleanSharedPreference();
                jumpIntent.setAction("android.intent.action.Intro3");
                jumpIntent.setClass(this, Intro3Activity.class);
                this.startActivity(jumpIntent);
                Toast.makeText(this, "三人鬥地主始動！", Toast.LENGTH_SHORT).show();
                break;

            case 2:
                cleanDatabases(SQL_MODE_5);
//                cleanSharedPreference();
                jumpIntent.setAction("android.intent.action.Intro5");
                jumpIntent.setClass(this, Intro5Activity.class);
                this.startActivity(jumpIntent);
                Toast.makeText(this, "五人鬥地主始動！", Toast.LENGTH_SHORT).show();
                break;

            case 3:
                Toast.makeText(this, "開發中，敬請期待！", Toast.LENGTH_SHORT).show();
                break;
        }

    }


    /**
     * 开始游戏前清空DB
     * @param i 游戏模式
     */
    public static void cleanDatabases(int i) {
        try {
            orm.cleanDBTable(i);
            Log.w(TAG, "本应用Databases已清空");
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "Databases清空失败！");
        }
    }


    /**
     * 开始游戏前清空SP
     */
    public static void cleanSharedPreference() {
        try {
            mySharedPreferences.edit().clear().commit();
            Log.w(TAG, "SharedPreference已清空");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
