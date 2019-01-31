package com.drolma.bannkazu.landlord5;

/* Coding by Peaceminds, powered by Mr.Xing */

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.drolma.bannkazu.R;
import com.drolma.bannkazu.SettingsActivity;
import com.drolma.bannkazu.landlord3.Intro3Activity;
import com.drolma.bannkazu.transwarp.modelTool;
import com.drolma.bannkazu.transwarp.ormTool;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static android.content.SharedPreferences.*;

public class Game5Activity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = "Game5Activity";
    private static final String DATABASE_SP = "spDatabase";
    private static final String REC_SNAPSHOT_NOW = "bannkazu_recovery_5";
    private static final int SET_MODEL_MT = 1;
    private static final int SET_MODEL_DBAT = 2;
    private static final int SQL_MODE = 5;
    private static final int RUN_MODE = 5;
    private static int appId;
    private TextView pNameView1;
    private TextView pNameView2;
    private TextView pNameView3;
    private TextView pNameView4;
    private TextView pNameView5;
    private TextView pScoreView1;
    private TextView pScoreView2;
    private TextView pScoreView3;
    private TextView pScoreView4;
    private TextView pScoreView5;
    private ToggleButton ldlCert1;
    private ToggleButton ldlCert2;
    private ToggleButton ldlCert3;
    private ToggleButton ldlCert4;
    private ToggleButton ldlCert5;
    private CheckBox spCheck1;
    private CheckBox spCheck2;
    private CheckBox spCheck3;
    private CheckBox spCheck4;
    private CheckBox spCheck5;
    private Button landlordWinBtn;
    private Button farmerWinBtn;
    private Button subBtn;
    private Button addBtn;
    private EditText inputBannkazu;
    private static int pScore1;
    private static int pScore2;
    private static int pScore3;
    private static int pScore4;
    private static int pScore5;
    private static int currScore1;
    private static int currScore2;
    private static int currScore3;
    private static int currScore4;
    private static int currScore5;
    private static int llTimes1 = 0;
    private static int llTimes2 = 0;
    private static int llTimes3 = 0;
    private static int llTimes4 = 0;
    private static int llTimes5 = 0;
    private static int winTimes1 = 0;
    private static int winTimes2 = 0;
    private static int winTimes3 = 0;
    private static int winTimes4 = 0;
    private static int winTimes5 = 0;
    private static int[] llTimesList = {0, 0, 0, 0, 0};
    private static int[] winTimesList = {0, 0, 0, 0, 0};
    private static String pName1;
    private static String pName2;
    private static String pName3;
    private static String pName4;
    private static String pName5;
    private int bannkazu = 0;
    private static int ldlAmount = 0;
    private static int spAmount = 0;
    private static String[] pNameArr;
    private static long exitTime = 0;
    private ormTool ormt;

    /**
     * -----------------------------------------------------------
     * 视图初始化部分
     * -----------------------------------------------------------
     */

    // 关联主视图
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPlayerNames();
        setContentView(R.layout.activity_game5);
        initOverflowMenu();
        initActionBar();
        initViews();
        ormt = new ormTool(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //应用的最后一个Activity关闭时应释放DB
        ormt.closeDB();
    }

    // 关联菜单视图
    @Override
    public boolean onCreateOptionsMenu(Menu myMenu) {
        getMenuInflater().inflate(R.menu.menu_main, myMenu);
        return true;
    }

    // force to show overflow menu in actionbar for android 4.4 below
    private void initOverflowMenu() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if (menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 初始化ActionBar
    public void initActionBar() {
//        getSupportActionBar().setLogo(R.drawable.logo1);
//        getSupportActionBar().setDisplayUseLogoEnabled(true);
//        getSupportActionBar().setHomeButtonEnabled(true);      //不显示箭头符号，允许通过onOptionsItemSelected()进行箭头，但一般的情况下，还是应该给个箭头提示给用户。
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);   //显示返回的箭头，并可通过onOptionsItemSelected()进行监听，其资源ID为android.R.id.home。
    }

    // 菜单按钮响应
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                Toast.makeText(this, "┗|｀O′|┛ 嗷~~", Toast.LENGTH_SHORT).show();
                break;

            case R.id.act_settings:
                Log.w(TAG, "触发跳转设定界面");
                Intent jumpIntent = new Intent();
                jumpIntent.setAction("android.intent.action.Settings");
                jumpIntent.putExtra("3or5", RUN_MODE);
                jumpIntent.setClass(this, SettingsActivity.class);
                this.startActivity(jumpIntent);
                break;

            case R.id.act_finish_game:
                Log.w(TAG, "触发清零对话框");
                AlertDialog.Builder aldBuilder1 = new AlertDialog.Builder(this);
                aldBuilder1.setTitle("_(:з」∠)_");
                aldBuilder1.setMessage("确定要清空当前数据吗？");
                aldBuilder1.setNegativeButton("不了", null);
                aldBuilder1.setPositiveButton("对啊", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        cleanAllParams();
                        resetAll();
                        toastYeah(1);
                    }
                });
                aldBuilder1.create().show();
                break;

            case R.id.act_manual_calc:
                Log.w(TAG, "触发手工改分对话框");
                ManualSet5Dialog msa = new ManualSet5Dialog(this, pNameArr, new ManualSet5Dialog.interfaceMSDBackCallListener() {
                    @Override
                    public void MSDEvent(String[] mInputScoresList) {
                        setDataFromBackCall(mInputScoresList, SET_MODEL_MT);
                        calcCurrScores();
                        saveSnapshot();
                        saveDataToDB(Integer.valueOf(mInputScoresList[0]), Integer.valueOf(mInputScoresList[1]), Integer.valueOf(mInputScoresList[2]), Integer.valueOf(mInputScoresList[3]), Integer.valueOf(mInputScoresList[4]), 2); // 取消手动改分的数据库存储
                    }
                });
                msa.show();
                break;

            case R.id.act_time_machine:
                Log.w(TAG, "触发历史拾取对话框");
                appId = getApplication().MODE_PRIVATE;
                TimeMachine5Dialog tma = new TimeMachine5Dialog(this, appId, ormt, new TimeMachine5Dialog.interfaceTMDBackCall() {
                    @Override
                    public void TMDEvent(String[] listIWantToSendBackToTheActivity) {
                        setDataFromBackCall(listIWantToSendBackToTheActivity, SET_MODEL_DBAT);
                        calcCurrScores();
                        saveSnapshot();
                        toastYeah(2);
                    }
                });
                tma.show();
                break;

            case R.id.act_recovery:
                Log.w(TAG, "触发恢复Dialog");
                AlertDialog.Builder aldBuilder2 = new AlertDialog.Builder(this);
                aldBuilder2.setTitle("数据恢复");
                aldBuilder2.setMessage("恢复到最后一次数据吗？（意外退出可恢复最后状态）");
                aldBuilder2.setNegativeButton("算了", null);
                aldBuilder2.setPositiveButton("最后", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        cleanAllParams();
                        resetAll();
                        backToTheFuture();
                    }
                });
                aldBuilder2.create().show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // 返回
    private void toastYeah(int i) {
        if (i == 1) {
            Toast.makeText(this, "分数手动修改完成！", Toast.LENGTH_SHORT).show();
        } else if (i == 2) {
            Toast.makeText(this, "历史分数修改完成！", Toast.LENGTH_SHORT).show();
        }
    }

    // 主视图控件响应事件
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.game_landlordWinBtn: // 地主赢
                cleanAllParams();
                calcParams();
                Log.w(TAG, "本局地主个数 >>> " + ldlAmount);
                Log.w(TAG, "本局当前番数 >>> " + bannkazu);
                if (ifParamsIsOK()) {
                    pScoreView1.setText(String.valueOf(calcThePointsWhenLLWin(pScore1, spAmount, ldlCert1.isChecked(), spCheck1.isChecked())));
                    pScoreView2.setText(String.valueOf(calcThePointsWhenLLWin(pScore2, spAmount, ldlCert2.isChecked(), spCheck2.isChecked())));
                    pScoreView3.setText(String.valueOf(calcThePointsWhenLLWin(pScore3, spAmount, ldlCert3.isChecked(), spCheck3.isChecked())));
                    pScoreView4.setText(String.valueOf(calcThePointsWhenLLWin(pScore4, spAmount, ldlCert4.isChecked(), spCheck4.isChecked())));
                    pScoreView5.setText(String.valueOf(calcThePointsWhenLLWin(pScore5, spAmount, ldlCert5.isChecked(), spCheck5.isChecked())));
                    calcCurrScores();
                    calcAnalysisTimes(true);
                    saveSnapshot();
                    saveDataToDB(1);
                    cleanAllParams();
                    resetFlagBtn();
                    Toast.makeText(this, "地主胜!!本局计分统计完毕", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.game_farmerWinBtn:  // 农民赢
                cleanAllParams();
                calcParams();
                Log.w(TAG, "本局地主个数 >>> " + ldlAmount);
                Log.w(TAG, "本局当前番数 >>> " + bannkazu);
                if (ifParamsIsOK()) {
                    pScoreView1.setText(String.valueOf(calcThePointsFarmerWin(pScore1, spAmount, ldlCert1.isChecked(), spCheck1.isChecked())));
                    pScoreView2.setText(String.valueOf(calcThePointsFarmerWin(pScore2, spAmount, ldlCert2.isChecked(), spCheck2.isChecked())));
                    pScoreView3.setText(String.valueOf(calcThePointsFarmerWin(pScore3, spAmount, ldlCert3.isChecked(), spCheck3.isChecked())));
                    pScoreView4.setText(String.valueOf(calcThePointsFarmerWin(pScore4, spAmount, ldlCert4.isChecked(), spCheck4.isChecked())));
                    pScoreView5.setText(String.valueOf(calcThePointsFarmerWin(pScore5, spAmount, ldlCert5.isChecked(), spCheck5.isChecked())));
                    calcCurrScores();
                    calcAnalysisTimes(false);
                    saveSnapshot();
                    saveDataToDB(0);
                    cleanAllParams();
                    resetFlagBtn();
                    Toast.makeText(this, "农民赢~~本局计分统计完毕", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.game_btn_sub:  // 番数自减
                if (bannkazu <= 1) {
                    bannkazu = 1;
                } else if (bannkazu > 1)
                    bannkazu /= 2;
                inputBannkazu.setText(String.valueOf(bannkazu));
                break;

            case R.id.game_btn_add:  // 番数自加
                if (bannkazu < 1) {
                    bannkazu = 1;
                } else if (bannkazu == 1) {
                    bannkazu = 2;
                } else if (bannkazu > 1) {
                    bannkazu *= 2;
                }
                inputBannkazu.setText(String.valueOf(bannkazu));
                break;
        }
    }

    // 手机物理按键 返回按钮的响应
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 3000) {
                Toast.makeText(getApplicationContext(), "再按一次整盘游戏将终止喔！", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                this.finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    // 关联视图控件, 设置监听器
    private void initViews() {
        pNameView1 = (TextView) findViewById(R.id.game_sn1);
        pNameView2 = (TextView) findViewById(R.id.game_sn2);
        pNameView3 = (TextView) findViewById(R.id.game_sn3);
        pNameView4 = (TextView) findViewById(R.id.game_sn4);
        pNameView5 = (TextView) findViewById(R.id.game_sn5);
        pScoreView1 = (TextView) findViewById(R.id.game_bannkazu1);
        pScoreView2 = (TextView) findViewById(R.id.game_bannkazu2);
        pScoreView3 = (TextView) findViewById(R.id.game_bannkazu3);
        pScoreView4 = (TextView) findViewById(R.id.game_bannkazu4);
        pScoreView5 = (TextView) findViewById(R.id.game_bannkazu5);
        ldlCert1 = (ToggleButton) findViewById(R.id.game_dzBtn1);
        ldlCert2 = (ToggleButton) findViewById(R.id.game_dzBtn2);
        ldlCert3 = (ToggleButton) findViewById(R.id.game_dzBtn3);
        ldlCert4 = (ToggleButton) findViewById(R.id.game_dzBtn4);
        ldlCert5 = (ToggleButton) findViewById(R.id.game_dzBtn5);
        spCheck1 = (CheckBox) findViewById(R.id.game_ckb1);
        spCheck2 = (CheckBox) findViewById(R.id.game_ckb2);
        spCheck3 = (CheckBox) findViewById(R.id.game_ckb3);
        spCheck4 = (CheckBox) findViewById(R.id.game_ckb4);
        spCheck5 = (CheckBox) findViewById(R.id.game_ckb5);
        landlordWinBtn = (Button) findViewById(R.id.game_landlordWinBtn);
        farmerWinBtn = (Button) findViewById(R.id.game_farmerWinBtn);
        subBtn = (Button) findViewById(R.id.game_btn_sub);
        addBtn = (Button) findViewById(R.id.game_btn_add);
        inputBannkazu = (EditText) findViewById(R.id.game_inputBannkazu);
        landlordWinBtn.setOnClickListener(this);
        farmerWinBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        pNameView1.setText(pName1);
        pNameView2.setText(pName2);
        pNameView3.setText(pName3);
        pNameView4.setText(pName4);
        pNameView5.setText(pName5);
    }

    /**
     * -----------------------------------------------------------
     * 数据初始化部分
     * -----------------------------------------------------------
     */

    // 获取从MainActivity传来的玩家姓名数据
    private void getPlayerNames() {
        if (pNameArr == null) {
            pNameArr = this.getIntent().getExtras().getStringArray("playsNameArray");
            Log.w(TAG, pNameArr[0] + pNameArr[1] + pNameArr[2] + pNameArr[3] + pNameArr[4]);
            for (int i = 0; i <= 4; i++)
                if (pNameArr[i].toString().trim().equals(""))
                    pNameArr[i] = "玩" + String.valueOf(i);
            pName1 = pNameArr[0];
            pName2 = pNameArr[1];
            pName3 = pNameArr[2];
            pName4 = pNameArr[3];
            pName5 = pNameArr[4];
        }
    }

    // 获取本局游戏产生的番数
    private int getBannkazuValue(EditText et) {
        String str_data = et.getText().toString();
        if ("".equals(str_data))
            str_data = "1";
        return Integer.parseInt(str_data);
    }

    // 统计当前全局参数(被关玩家数、地主个数、本局游戏番数、各玩家得分)
    private void calcParams() {
        if (spCheck1.isChecked())
            spAmount++;
        if (spCheck2.isChecked())
            spAmount++;
        if (spCheck3.isChecked())
            spAmount++;
        if (spCheck4.isChecked())
            spAmount++;
        if (spCheck5.isChecked())
            spAmount++;
        Log.i(TAG, "当前春天人数>>> " + spAmount);

        if (ldlCert1.isChecked())
            ldlAmount++;
        if (ldlCert2.isChecked())
            ldlAmount++;
        if (ldlCert3.isChecked())
            ldlAmount++;
        if (ldlCert4.isChecked())
            ldlAmount++;
        if (ldlCert5.isChecked())
            ldlAmount++;
        Log.i(TAG, "当前地主个数>>> " + ldlAmount);

        bannkazu = getBannkazuValue(inputBannkazu);
        Log.i(TAG, "当局游戏番数>>> " + bannkazu);

        int i1 = Integer.parseInt(pScoreView1.getText().toString());
        int i2 = Integer.parseInt(pScoreView2.getText().toString());
        int i3 = Integer.parseInt(pScoreView3.getText().toString());
        int i4 = Integer.parseInt(pScoreView4.getText().toString());
        int i5 = Integer.parseInt(pScoreView5.getText().toString());
        pScore1 = i1;
        pScore2 = i2;
        pScore3 = i3;
        pScore4 = i4;
        pScore5 = i5;
        Log.w(TAG, "上局分数 === " +
                String.valueOf(pScore1) + " " +
                String.valueOf(pScore2) + " " +
                String.valueOf(pScore3) + " " +
                String.valueOf(pScore4) + " " +
                String.valueOf(pScore5) + " ");
    }

    // 将控件显示分数存为当前分数currScore
    private void calcCurrScores() {
        currScore1 = Integer.parseInt(pScoreView1.getText().toString());
        currScore2 = Integer.parseInt(pScoreView2.getText().toString());
        currScore3 = Integer.parseInt(pScoreView3.getText().toString());
        currScore4 = Integer.parseInt(pScoreView4.getText().toString());
        currScore5 = Integer.parseInt(pScoreView5.getText().toString());
    }

    //
    private void calcAnalysisTimes(boolean isLLwin) {
        llTimes1 = landlordTimesCount(llTimes1, ldlCert1.isChecked());
        llTimes2 = landlordTimesCount(llTimes2, ldlCert2.isChecked());
        llTimes3 = landlordTimesCount(llTimes3, ldlCert3.isChecked());
        llTimes4 = landlordTimesCount(llTimes4, ldlCert4.isChecked());
        llTimes5 = landlordTimesCount(llTimes5, ldlCert5.isChecked());
        winTimes1 = winTimesCount(winTimes1, ldlCert1.isChecked(), isLLwin);
        winTimes2 = winTimesCount(winTimes2, ldlCert2.isChecked(), isLLwin);
        winTimes3 = winTimesCount(winTimes3, ldlCert3.isChecked(), isLLwin);
        winTimes4 = winTimesCount(winTimes4, ldlCert4.isChecked(), isLLwin);
        winTimes5 = winTimesCount(winTimes5, ldlCert5.isChecked(), isLLwin);
        llTimesList[0] = llTimes1;
        llTimesList[1] = llTimes2;
        llTimesList[2] = llTimes3;
        llTimesList[3] = llTimes4;
        llTimesList[4] = llTimes5;
        winTimesList[0] = winTimes1;
        winTimesList[1] = winTimes2;
        winTimesList[2] = winTimes3;
        winTimesList[3] = winTimes4;
        winTimesList[4] = winTimes5;
    }

    //
    private int landlordTimesCount(int landlordTimes, boolean isLandlord) {
        if (isLandlord)
            landlordTimes++;
        return landlordTimes;
    }

    //
    private int winTimesCount(int winTimesNow, boolean isLandlord, boolean isLandlordWin) {
        if (isLandlord) {
            if (isLandlordWin)
                winTimesNow++;
        }
        else {
            if (!isLandlordWin)
                winTimesNow++;
        }
        return winTimesNow;
    }

    // 对地主个数做判断
    private boolean ifParamsIsOK() {
        if (ldlAmount < 1 || ldlAmount > 2) {
            new AlertDialog.Builder(this)
                    .setTitle("最高指示")
                    .setMessage("春春酱说一局只有1~2个地主喔~")
                    .setNegativeButton("哦", null).show();
            return false;
        } else if (!(bannkazu > 0 && (bannkazu == 1 || bannkazu % 2 == 0))) {
            new AlertDialog.Builder(this)
                    .setTitle("最高指示")
                    .setMessage("番数只能是1或2的整数倍正整数~~~")
                    .setNegativeButton("哦", null).show();
            return false;
        } else
            return true;
    }

    /**
     * -----------------------------------------------------------
     * 1. 数据清空
     * 2. 状态保存/读取/显示
     * 3. SQLite DB 保存当前分数
     * -----------------------------------------------------------
     */
    // 1-1 清空各个变量 初始化控件显示
    private void cleanAllParams() {
        ldlAmount = 0;
        bannkazu = 1;
        spAmount = 0;
    }

    // 1-2 清空有on/off功能的控件状态
    private void resetFlagBtn() {
        ldlCert1.setChecked(false);
        ldlCert2.setChecked(false);
        ldlCert3.setChecked(false);
        ldlCert4.setChecked(false);
        ldlCert5.setChecked(false);
        spCheck1.setChecked(false);
        spCheck2.setChecked(false);
        spCheck3.setChecked(false);
        spCheck4.setChecked(false);
        spCheck5.setChecked(false);
        inputBannkazu.setText("1");
    }

    // 1-3 重开游戏
    private void resetAll() {
        cleanAllParams();
        resetFlagBtn();
        pScoreView1.setText("0");
        pScoreView2.setText("0");
        pScoreView3.setText("0");
        pScoreView4.setText("0");
        pScoreView5.setText("0");
        pScore1 = 0;
        pScore2 = 0;
        pScore3 = 0;
        pScore4 = 0;
        pScore5 = 0;
        llTimes1 = 0;
        llTimes2 = 0;
        llTimes3 = 0;
        llTimes4 = 0;
        llTimes5 = 0;
        winTimes1 = 0;
        winTimes2 = 0;
        winTimes3 = 0;
        winTimes4 = 0;
        winTimes5 = 0;
    }

    // 1-4 根据回调函数传回的List重设比分和显示
    private void setDataFromBackCall(String[] strList, int setModel) {
        if (setModel == SET_MODEL_DBAT) {
            llTimes1 = Integer.valueOf(strList[5]).intValue();
            llTimes2 = Integer.valueOf(strList[6]).intValue();
            llTimes3 = Integer.valueOf(strList[7]).intValue();
            llTimes4 = Integer.valueOf(strList[8]).intValue();
            llTimes5 = Integer.valueOf(strList[9]).intValue();
            winTimes1 = Integer.valueOf(strList[10]).intValue();
            winTimes2 = Integer.valueOf(strList[11]).intValue();
            winTimes3 = Integer.valueOf(strList[12]).intValue();
            winTimes4 = Integer.valueOf(strList[13]).intValue();
            winTimes5 = Integer.valueOf(strList[14]).intValue();
        }
        pScore1 = Integer.valueOf(strList[0]).intValue();
        pScore2 = Integer.valueOf(strList[1]).intValue();
        pScore3 = Integer.valueOf(strList[2]).intValue();
        pScore4 = Integer.valueOf(strList[3]).intValue();
        pScore5 = Integer.valueOf(strList[4]).intValue();
        pScoreView1.setText(strList[0]);
        pScoreView2.setText(strList[1]);
        pScoreView3.setText(strList[2]);
        pScoreView4.setText(strList[3]);
        pScoreView5.setText(strList[4]);
    }

    // 2-1 保存当前分数快照至 SharedPreference
    private void saveSnapshot() {
        SharedPreferences msp = getSharedPreferences(DATABASE_SP, getApplication().MODE_PRIVATE);
        Editor mEditor = msp.edit();
        String snapDateTime = showMeTheTime();
        String scoreSnapStr = snapDateTime +","+ String.valueOf(currScore1) +","+
                String.valueOf(currScore2) +","+ String.valueOf(currScore3) +","+
                String.valueOf(currScore4) +","+ String.valueOf(currScore5) +","+
                llTimes1 +","+ llTimes2 +","+ llTimes3 +","+ llTimes4 +","+ llTimes5 +","+
                winTimes1 +","+ winTimes2 +","+ winTimes3 +","+ winTimes4 +","+ winTimes5;
        Log.w(TAG, "快照分数 === " + scoreSnapStr);

        mEditor.putString(REC_SNAPSHOT_NOW, scoreSnapStr).commit();
    }

    // 2-2 从 SharedPreference 读取快照
    private String loadSnapshot(String theSnapshotStrIndex) {
        SharedPreferences msp = getSharedPreferences(DATABASE_SP, getApplication().MODE_PRIVATE);
        String sst = msp.getString(theSnapshotStrIndex, "err");
        Log.w(TAG, "Snapshot is loaded ===> " + sst);
        return sst;
    }

    // 2-3 根据玩家分数变量显示分数
    private void displaySnapshot() {
        pScoreView1.setText(String.valueOf(pScore1));
        pScoreView2.setText(String.valueOf(pScore2));
        pScoreView3.setText(String.valueOf(pScore3));
        pScoreView4.setText(String.valueOf(pScore4));
        pScoreView5.setText(String.valueOf(pScore5));
    }


    // 2-4 返回最后一次/不慎退出前的数据
    private void backToTheFuture() {
        int[] nowArr;
        String str = loadSnapshot(REC_SNAPSHOT_NOW);
        if (!str.equals("err")) {
            String[] stringArr = str.split(",");
            nowArr = new int[stringArr.length];
            for (int i = 1; i < stringArr.length; i++) {
                nowArr[i] = Integer.parseInt(stringArr[i]);
            }
            pScore1 = nowArr[1];
            pScore2 = nowArr[2];
            pScore3 = nowArr[3];
            pScore4 = nowArr[4];
            pScore5 = nowArr[5];
            llTimes1 = nowArr[6];
            llTimes2 = nowArr[7];
            llTimes3 = nowArr[8];
            llTimes4 = nowArr[9];
            llTimes5 = nowArr[10];
            winTimes1 = nowArr[11];
            winTimes2 = nowArr[12];
            winTimes3 = nowArr[13];
            winTimes4 = nowArr[14];
            winTimes5 = nowArr[15];
            displaySnapshot();
            Log.w(TAG, "Today Arr Str ========>" + str);
            Toast.makeText(this, "已恢复至" + stringArr[0] + "的分数", Toast.LENGTH_SHORT).show();
        } else {
            Log.w(TAG, "Today Arr Str Set Error !!!");
        }
    }

    // 3-1 将比分插入DB 自动
    private void saveDataToDB(int isLW) {
        ArrayList<modelTool> mtAL = new ArrayList<modelTool>();
        String time = showMeTheTime();
        modelTool mt = new modelTool(time, String.valueOf(currScore1), String.valueOf(currScore2), String.valueOf(currScore3), String.valueOf(currScore4), String.valueOf(currScore5), isLW, 0, llTimesList, winTimesList);
        mtAL.add(mt);
        ormt.addRec(mtAL, SQL_MODE);
    }

    // 3-1-2 将比分插入DB 人工
    private void saveDataToDB(int sp1, int sp2, int sp3, int sp4, int sp5, int isLW) {
        ArrayList<modelTool> mtAL = new ArrayList<modelTool>();
        String time = showMeTheTime();
        modelTool mt = new modelTool(time, String.valueOf(sp1), String.valueOf(sp2), String.valueOf(sp3), String.valueOf(sp4), String.valueOf(sp5), isLW, 1, llTimesList, winTimesList);
        mtAL.add(mt);
        ormt.addRec(mtAL, SQL_MODE);
    }

    // 4-1 获取时间
    private String showMeTheTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yy-MM-dd hh:mm:ss");
        String sdTime = sDateFormat.format(new java.util.Date());
        return sdTime;
    }

    /**
     * 地主赢 - 代入当前分数, 是否地主, 是否春天的信息
     *
     * @param currScore 代入获取到的当前分数
     * @param springNum 代入春天人数
     * @param isLandlord 玩家是否地主
     * @param isSpring 玩家是否春天
     * @return 返回计算后的 int 分数
     */
    private int calcThePointsWhenLLWin(int currScore, int springNum, Boolean isLandlord, Boolean isSpring) {
        switch (ldlAmount) {
            case 1:
                if (isLandlord)
                    currScore += 12 * bannkazu + 3 * springNum * bannkazu;
                else {
                    if (isSpring)
                        currScore -= 6 * bannkazu;
                    else
                        currScore -= 3 * bannkazu;
                }
                break;
            case 2:
                if (isLandlord)
                    currScore += 9 * bannkazu + 3 * springNum * bannkazu;
                else {
                    if (isSpring)
                        currScore -= 12 * bannkazu;
                    else
                        currScore -= 6 * bannkazu;
                }
                break;
        }
        return currScore;
    }

    /**
     * 农民赢 - 代入当前分数, 是否地主, 是否春天的信息
     *
     * @param currScore 代入获取到的当前分数
     * @param springNum 代入春天人数
     * @param isLandlord 玩家是否地主
     * @param isSpring 玩家是否春天
     * @return 返回计算后的 int 分数
     */
    private int calcThePointsFarmerWin(int currScore, int springNum, Boolean isLandlord, Boolean isSpring) {
        switch (ldlAmount) {
            case 1:
                if (isLandlord) {
                    if (isSpring)
                        currScore -= 24 * bannkazu;
                    else
                        currScore -= 12 * bannkazu;
                } else
                    currScore += 3 * bannkazu + 3 * springNum * bannkazu;
                break;
            case 2:
                if (isLandlord) {
                    if (isSpring)
                        currScore -= 18 * bannkazu;
                    else
                        currScore -= 9 * bannkazu;
                } else
                    currScore += 6 * bannkazu + 3 * springNum * bannkazu;
                break;
        }
        return currScore;
    }


}
