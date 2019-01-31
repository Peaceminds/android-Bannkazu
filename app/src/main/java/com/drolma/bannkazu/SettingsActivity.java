package com.drolma.bannkazu;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.drolma.bannkazu.transwarp.ormTool;

import java.io.File;

/**
 * Created by Drolma on 16/5/24.
 */
public class SettingsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SettingsActivity";
    private static final String DATABASE_SP = "spDatabase";
    private Context ctx;
    private Button cleanDBBtn;
    private Button cleanSPBtn;
    private Button specificBtn;
    private Button financingBtn;
    private int int3or5;
    private static SharedPreferences mySharedPreferences;
    private static ormTool orm;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ctx = this;
        setContentView(R.layout.activity_settings);
        initActionBar();
        initViews();
        int3or5 = getIntent().getExtras().getInt("3or5");
        orm = new ormTool(this);
    }

    public void initActionBar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        cleanDBBtn = (Button) findViewById(R.id.settings_clean_db);
        cleanSPBtn = (Button) findViewById(R.id.settings_clean_sp);
        specificBtn = (Button) findViewById(R.id.settings_specification);
        financingBtn = (Button) findViewById(R.id.settings_financing);
        cleanDBBtn.setOnClickListener(this);
        cleanSPBtn.setOnClickListener(this);
        specificBtn.setOnClickListener(this);
        financingBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.settings_clean_db:
                Log.w(TAG, "数据库删除操作");
                AlertDialog.Builder builder1 = new AlertDialog.Builder(ctx);
                builder1.setTitle("清空历史数据库");
                builder1.setMessage("清空后将无法查看和恢复历史比分，真要这么做嘛？（再次游戏前请将分数手动清零）");
                builder1.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        cleanDatabases(getApplicationContext(), int3or5);
                        Toast.makeText(ctx, "历史数据已清空", Toast.LENGTH_SHORT).show();
                    }
                });
                builder1.setNegativeButton("取消", null);
                builder1.create().show();
                break;

            case R.id.settings_clean_sp:
                Log.w(TAG, "缓存清空操作");
                AlertDialog.Builder builder2 = new AlertDialog.Builder(ctx);
                builder2.setTitle("清空缓存数据");
                builder2.setMessage("清空后将无法恢复比分，真要这么做嘛？（用户数据只占用少量存储资源。再次游戏前请将分数手动清零）");
                builder2.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        cleanSharedPreference(getApplicationContext());
                        Toast.makeText(ctx, "缓存已清空", Toast.LENGTH_SHORT).show();
                    }
                });
                builder2.setNegativeButton("取消", null);
                builder2.create().show();
                break;

            case R.id.settings_specification:
                Log.w(TAG, "启动手册界面");
                Intent intent1 = new Intent();
                intent1.setAction("android.intent.action.Intro5");
                ComponentName cp1 = new ComponentName(ctx, SpecificActivity.class);
                intent1.setComponent(cp1);
                startActivity(intent1);
                break;

            case R.id.settings_financing:
                Toast.makeText(ctx, "功能开发中_(:з」∠)_", Toast.LENGTH_SHORT).show();
                break;

        }

    }

    public static void cleanDatabases(Context context, int i) {
        try {
            orm.cleanDBTable(i);
            Log.w(TAG, "本应用Databases已清空");
        } catch (Exception e) {
            e.printStackTrace();
            Log.w(TAG, "Databases清空失败！");
        }
    }


    public static void cleanSharedPreference(Context context) {
        try {
            mySharedPreferences = context.getSharedPreferences(DATABASE_SP, context.MODE_PRIVATE);
            mySharedPreferences.edit().clear().commit();
            Log.w(TAG, "SharedPreference已清空");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void deleteFilesByDirectory(File directory) {
        if (directory != null && directory.exists() && directory.isDirectory()) {
            for (File item : directory.listFiles()) {
                item.delete();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //应用的最后一个Activity关闭时应释放DB
        orm.closeDB();
    }


}
