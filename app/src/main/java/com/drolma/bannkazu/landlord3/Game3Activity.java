package com.drolma.bannkazu.landlord3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.drolma.bannkazu.R;
import com.drolma.bannkazu.SettingsActivity;
import com.drolma.bannkazu.landlord5.ManualSet5Dialog;
import com.drolma.bannkazu.landlord5.TimeMachine5Dialog;
import com.drolma.bannkazu.transwarp.modelTool;
import com.drolma.bannkazu.transwarp.ormTool;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Drolma on 16/5/23.
 */
public class Game3Activity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Game3Activity";
    private static final String DATABASE_SP = "spDatabase";
    private static final String REC_SNAPSHOT_NOW = "bannkazu_recovery_3";
    private static final int SET_MODEL_MT = 1;
    private static final int SET_MODEL_DBAT = 2;
    private static final int SQL_MODE = 3;
    private static final int RUN_MODE = 3;
    private static int appId;
    private TextView pNameView1;
    private TextView pNameView2;
    private TextView pNameView3;
    private TextView pScoreView1;
    private TextView pScoreView2;
    private TextView pScoreView3;
    private ToggleButton ldlCert1;
    private ToggleButton ldlCert2;
    private ToggleButton ldlCert3;
    private CheckBox spCheck;
    private Button landlordWinBtn;
    private Button farmerWinBtn;
    private Button subBtn;
    private Button addBtn;
    private Button bpsubBtn;
    private Button bpaddBtn;
    private EditText inputBannkazu;
    private EditText inputBasePoint;
    private static int pScore1;
    private static int pScore2;
    private static int pScore3;
    private static int currScore1;
    private static int currScore2;
    private static int currScore3;
    private static int llTimes1 = 0;
    private static int llTimes2 = 0;
    private static int llTimes3 = 0;
    private static int winTimes1 = 0;
    private static int winTimes2 = 0;
    private static int winTimes3 = 0;
    private static int ldlAmount = 0;
    private static int[] llTimesList = {0, 0, 0};
    private static int[] winTimesList = {0, 0, 0};
    private static boolean isSpring = false;
    private static long exitTime = 0;
    private static String[] pNameArr;
    private static String pName1;
    private static String pName2;
    private static String pName3;
    private int bannkazu = 1;
    private int basePoint = 1;
    private ormTool ormt;

    /**
     * -----------------------------------------------------------
     * 视图初始化及按键响应
     * -----------------------------------------------------------
     */

    // 关联主视图
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getPlayerNames();
        setContentView(R.layout.activity_game3);
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
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
                ManualSet3Dialog msa = new ManualSet3Dialog(this, pNameArr, new ManualSet3Dialog.interfaceMSD3BackCallListener() {
                    @Override
                    public void MSD3Event(String[] listIWantToSendBackToTheActivity) {
                        setDataFromBackCall(listIWantToSendBackToTheActivity, SET_MODEL_MT);
                        calcCurrScores();
                        saveSnapshot();
                        saveDataToDB(Integer.valueOf(listIWantToSendBackToTheActivity[0]), Integer.valueOf(listIWantToSendBackToTheActivity[1]), Integer.valueOf(listIWantToSendBackToTheActivity[2]), 2); // 取消手动改分的数据库存储
                    }
                });
                msa.show();
                break;

            case R.id.act_time_machine:
                Log.w(TAG, "触发历史拾取对话框");
                appId = getApplication().MODE_PRIVATE;
                TimeMachine3Dialog tma = new TimeMachine3Dialog(this, appId, ormt, new TimeMachine3Dialog.interfaceTMD3BackCall() {
                    @Override
                    public void TMD3Event(String[] listIWantToSendBackToTheActivity) {
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
                aldBuilder2.setPositiveButton("嗯哪", new DialogInterface.OnClickListener() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.game3_landlordWinBtn: // 地主赢
                cleanAllParams();
                calcParams();
                Log.w(TAG, "本局地主个数 >>> " + ldlAmount);
                Log.w(TAG, "本局当前番数 >>> " + bannkazu);
                if (ifParamsIsOK()) {
                    pScoreView1.setText(String.valueOf(calcThePointsWhenLLWin(pScore1, ldlCert1.isChecked())));
                    pScoreView2.setText(String.valueOf(calcThePointsWhenLLWin(pScore2, ldlCert2.isChecked())));
                    pScoreView3.setText(String.valueOf(calcThePointsWhenLLWin(pScore3, ldlCert3.isChecked())));
                    calcCurrScores();
                    calcAnalysisTimes(true);
                    saveSnapshot();
                    saveDataToDB(1);
                    cleanAllParams();
                    resetFlagBtn();
                    Toast.makeText(this, "地主胜!!本局计分统计完毕", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.game3_farmerWinBtn:  // 农民赢
                cleanAllParams();
                calcParams();
                Log.w(TAG, "本局地主个数 >>> " + ldlAmount);
                Log.w(TAG, "本局当前番数 >>> " + bannkazu);
                if (ifParamsIsOK()) {
                    pScoreView1.setText(String.valueOf(calcThePointsFarmerWin(pScore1, ldlCert1.isChecked())));
                    pScoreView2.setText(String.valueOf(calcThePointsFarmerWin(pScore2, ldlCert2.isChecked())));
                    pScoreView3.setText(String.valueOf(calcThePointsFarmerWin(pScore3, ldlCert3.isChecked())));
                    calcCurrScores();
                    calcAnalysisTimes(false);
                    saveSnapshot();
                    saveDataToDB(0);
                    cleanAllParams();
                    resetFlagBtn();
                    Toast.makeText(this, "农民赢~~本局计分统计完毕", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.game3_btn_sub:  // 番数自减
                if (bannkazu <= 1) {
                    bannkazu = 1;
                } else if (bannkazu > 1)
                    bannkazu /= 2;
                inputBannkazu.setText(String.valueOf(bannkazu));
                break;

            case R.id.game3_btn_add:  // 番数自加
                if (bannkazu < 1) {
                    bannkazu = 1;
                } else if (bannkazu == 1) {
                    bannkazu = 2;
                } else if (bannkazu > 1) {
                    bannkazu *= 2;
                }
                inputBannkazu.setText(String.valueOf(bannkazu));
                break;

            case R.id.game3_btn_bps_add:  // 底数自加
                if (basePoint < 1) {
                    basePoint = 1;
                } else if (basePoint < 3){
                    basePoint++;
                } else if (basePoint == 3) {
                    basePoint = 3;
                }
                inputBasePoint.setText(String.valueOf(basePoint));
                break;

            case R.id.game3_btn_bps_sub:  // 底数自减
                if (basePoint <= 3 && basePoint > 1) {
                    basePoint--;
                } else {
                    basePoint = 1;
                }
                inputBasePoint.setText(String.valueOf(basePoint));
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
        pNameView1 = (TextView) findViewById(R.id.game3_sn1);
        pNameView2 = (TextView) findViewById(R.id.game3_sn2);
        pNameView3 = (TextView) findViewById(R.id.game3_sn3);
        pScoreView1 = (TextView) findViewById(R.id.game3_bannkazu1);
        pScoreView2 = (TextView) findViewById(R.id.game3_bannkazu2);
        pScoreView3 = (TextView) findViewById(R.id.game3_bannkazu3);
        ldlCert1 = (ToggleButton) findViewById(R.id.game3_dzBtn1);
        ldlCert2 = (ToggleButton) findViewById(R.id.game3_dzBtn2);
        ldlCert3 = (ToggleButton) findViewById(R.id.game3_dzBtn3);
        spCheck = (CheckBox) findViewById(R.id.game3_ckb);
        landlordWinBtn = (Button) findViewById(R.id.game3_landlordWinBtn);
        farmerWinBtn = (Button) findViewById(R.id.game3_farmerWinBtn);
        subBtn = (Button) findViewById(R.id.game3_btn_sub);
        addBtn = (Button) findViewById(R.id.game3_btn_add);
        bpaddBtn = (Button) findViewById(R.id.game3_btn_bps_add);
        bpsubBtn = (Button) findViewById(R.id.game3_btn_bps_sub);
        inputBannkazu = (EditText) findViewById(R.id.game3_bannkazuInput);
        inputBasePoint = (EditText) findViewById(R.id.game3_basePointInput);
        landlordWinBtn.setOnClickListener(this);
        farmerWinBtn.setOnClickListener(this);
        bpaddBtn.setOnClickListener(this);
        bpsubBtn.setOnClickListener(this);
        subBtn.setOnClickListener(this);
        addBtn.setOnClickListener(this);
        pNameView1.setText(pName1);
        pNameView2.setText(pName2);
        pNameView3.setText(pName3);
    }

    /**
     * -----------------------------------------------------------
     * 数据初始化与设置
     * -----------------------------------------------------------
     */

    // 1 获取从MainActivity传来的玩家姓名数据
    private void getPlayerNames() {
        if (pNameArr == null) {
            pNameArr = this.getIntent().getExtras().getStringArray("playsNameArray3");
            if (pNameArr == null) throw new AssertionError();
            Log.w(TAG, pNameArr[0] + pNameArr[1] + pNameArr[2]);
            for (int i = 0; i <= 2; i++)
                if (pNameArr[i].toString().trim().equals(""))
                    pNameArr[i] = "玩" + String.valueOf(i);
            pName1 = pNameArr[0];
            pName2 = pNameArr[1];
            pName3 = pNameArr[2];
        }
    }

    // 2 获取本局游戏产生的番数
    private int getInputBBPValue(EditText et) {
        String str_data = et.getText().toString();
        if ("".equals(str_data))
            str_data = "1";
        return Integer.parseInt(str_data);
    }

    // 3 统计当前全局参数(被关玩家数、地主个数、本局游戏番数、各玩家得分)
    private void calcParams() {
        if (spCheck.isChecked())
            isSpring = true;
        Log.i(TAG, "当前春天么>>> " + isSpring);

        if (ldlCert1.isChecked())
            ldlAmount++;
        if (ldlCert2.isChecked())
            ldlAmount++;
        if (ldlCert3.isChecked())
            ldlAmount++;
        Log.i(TAG, "当前地主个数>>> " + ldlAmount);

        bannkazu = getInputBBPValue(inputBannkazu);
        basePoint = getInputBBPValue(inputBasePoint);

        Log.i(TAG, "当局基数分>>> " + basePoint);
        Log.i(TAG, "当局游戏番数>>> " + bannkazu);

        int i1 = Integer.parseInt(pScoreView1.getText().toString());
        int i2 = Integer.parseInt(pScoreView2.getText().toString());
        int i3 = Integer.parseInt(pScoreView3.getText().toString());
        pScore1 = i1;
        pScore2 = i2;
        pScore3 = i3;
        Log.w(TAG, "上局分数 === " +
                String.valueOf(pScore1) + " " +
                String.valueOf(pScore2) + " " +
                String.valueOf(pScore3) + " " );
    }

    // 4 将控件显示分数存为当前分数currScore
    private void calcCurrScores() {
        currScore1 = Integer.parseInt(pScoreView1.getText().toString());
        currScore2 = Integer.parseInt(pScoreView2.getText().toString());
        currScore3 = Integer.parseInt(pScoreView3.getText().toString());
    }

    // 5 计算当前技术统计数据
    private void calcAnalysisTimes(boolean isLLwin) {
        llTimes1 = landlordTimesCount(llTimes1, ldlCert1.isChecked());
        llTimes2 = landlordTimesCount(llTimes2, ldlCert2.isChecked());
        llTimes3 = landlordTimesCount(llTimes3, ldlCert3.isChecked());
        winTimes1 = winTimesCount(winTimes1, ldlCert1.isChecked(), isLLwin);
        winTimes2 = winTimesCount(winTimes2, ldlCert2.isChecked(), isLLwin);
        winTimes3 = winTimesCount(winTimes3, ldlCert3.isChecked(), isLLwin);
        llTimesList[0] = llTimes1;
        llTimesList[1] = llTimes2;
        llTimesList[2] = llTimes3;
        winTimesList[0] = winTimes1;
        winTimesList[1] = winTimes2;
        winTimesList[2] = winTimes3;
    }

    // 5-1 统计当地主次数
    private int landlordTimesCount(int landlordTimes, boolean isLandlord) {
        if (isLandlord)
            landlordTimes++;
        return landlordTimes;
    }

    // 5-2 统计胜次
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

    // 6 对地主个数做判断
    private boolean ifParamsIsOK() {
        if (ldlAmount != 1) {
            new AlertDialog.Builder(this)
                    .setTitle("最高指示")
                    .setMessage("三人局只能有1个地主喔~")
                    .setNegativeButton("哦", null).show();
            return false;
        } else if (!(bannkazu > 0 && (bannkazu == 1 || bannkazu % 2 == 0))) {
            new AlertDialog.Builder(this)
                    .setTitle("最高指示")
                    .setMessage("番数只能是1或2的整数倍正整数~~~")
                    .setNegativeButton("哦", null).show();
            return false;
        } else if (basePoint < 1 || basePoint > 3) {
            new AlertDialog.Builder(this)
                    .setTitle("最高指示")
                    .setMessage("底分只能是1~3之间")
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
        basePoint = 1;
        isSpring = false;
    }

    // 1-2 清空有on/off功能的控件状态
    private void resetFlagBtn() {
        ldlCert1.setChecked(false);
        ldlCert2.setChecked(false);
        ldlCert3.setChecked(false);
        spCheck.setChecked(false);
        inputBannkazu.setText("1");
        inputBasePoint.setText("1");
    }

    // 1-3 重开游戏
    private void resetAll() {
        cleanAllParams();
        resetFlagBtn();
        pScoreView1.setText("0");
        pScoreView2.setText("0");
        pScoreView3.setText("0");
        pScore1 = 0;
        pScore2 = 0;
        pScore3 = 0;
        winTimes1 = 0;
        winTimes2 = 0;
        winTimes3 = 0;
        llTimes1 = 0;
        llTimes2 = 0;
        llTimes3 = 0;
    }

    // 1-4 根据回调函数传回的List重设比分和显示
    private void setDataFromBackCall(String[] strList, int setModel) {
        if (setModel == SET_MODEL_DBAT) {
            llTimes1 = Integer.valueOf(strList[3]).intValue();
            llTimes2 = Integer.valueOf(strList[4]).intValue();
            llTimes3 = Integer.valueOf(strList[5]).intValue();
            winTimes1 = Integer.valueOf(strList[6]).intValue();
            winTimes2 = Integer.valueOf(strList[7]).intValue();
            winTimes3 = Integer.valueOf(strList[8]).intValue();
        }
        pScore1 = Integer.valueOf(strList[0]).intValue();
        pScore2 = Integer.valueOf(strList[1]).intValue();
        pScore3 = Integer.valueOf(strList[2]).intValue();
        pScoreView1.setText(strList[0]);
        pScoreView2.setText(strList[1]);
        pScoreView3.setText(strList[2]);
    }

    // 2-1 保存当前分数快照至 SharedPreference
    private void saveSnapshot() {
        SharedPreferences msp = getSharedPreferences(DATABASE_SP, getApplication().MODE_PRIVATE);
        SharedPreferences.Editor mEditor = msp.edit();
        String snapDateTime = showMeTheTime();
        String scoreSnapStr = snapDateTime +","+ String.valueOf(currScore1) +","+
                String.valueOf(currScore2) +","+ String.valueOf(currScore3) +","+
                llTimes1 +","+ llTimes2 +","+ llTimes3 +","+
                winTimes1 +","+ winTimes2 +","+ winTimes3;
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
            llTimes1 = nowArr[4];
            llTimes2 = nowArr[5];
            llTimes3 = nowArr[6];
            winTimes1 = nowArr[7];
            winTimes2 = nowArr[8];
            winTimes3 = nowArr[9];
            displaySnapshot();
            Log.w(TAG, "Today Arr Str ========>" + str);
            Toast.makeText(this, "已恢复至" + stringArr[0] + "的分数", Toast.LENGTH_SHORT).show();
        } else {
            Log.w(TAG, "Today Arr Str Set Error !!!");
        }
    }

    // 3-1 比分插入DB 自动
    private void saveDataToDB(int isLW) {
        ArrayList<modelTool> mtAL = new ArrayList<modelTool>();
        String time = showMeTheTime();
        modelTool mt = new modelTool(time, String.valueOf(currScore1), String.valueOf(currScore2), String.valueOf(currScore3), isLW, 0, llTimesList, winTimesList);
        mtAL.add(mt);
        ormt.addRec(mtAL, SQL_MODE);
    }

    // 3-1-2 比分插入DB 人工
    private void saveDataToDB(int sp1, int sp2, int sp3, int isLW) {
        ArrayList<modelTool> mtAL = new ArrayList<modelTool>();
        String time = showMeTheTime();
        modelTool mt = new modelTool(time, String.valueOf(sp1), String.valueOf(sp2), String.valueOf(sp3), isLW, 1, llTimesList, winTimesList);
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
     * @param isLandlord 玩家是否地主
     * @return 返回计算后的 int 分数
     */
    private int calcThePointsWhenLLWin(int currScore, Boolean isLandlord) {

        if (isLandlord)
            if (isSpring)
                currScore += 4 * basePoint * bannkazu;
            else
                currScore += 2 * basePoint * bannkazu;
        else {
            if (isSpring)
                currScore -= 2 * basePoint * bannkazu;
            else
                currScore -= basePoint * bannkazu;
        }

        return currScore;

    }

    /**
     * 农民赢 - 代入当前分数, 是否地主, 是否春天的信息
     *
     * @param currScore 代入获取到的当前分数
     * @param isLandlord 玩家是否地主
     * @return 返回计算后的 int 分数
     */
    private int calcThePointsFarmerWin(int currScore, Boolean isLandlord) {

        if (isLandlord) {
            if (isSpring)
                currScore -= 4 * basePoint * bannkazu;
            else
                currScore -= 2 * basePoint * bannkazu;
        } else {
            if (isSpring)
                currScore += 2 * basePoint * bannkazu;
            else
                currScore += basePoint * bannkazu;
        }

        return currScore;

    }


}
