package com.drolma.bannkazu.landlord3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.drolma.bannkazu.R;
import com.drolma.bannkazu.transwarp.modelTool;
import com.drolma.bannkazu.transwarp.ormTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Drolma on 16/5/23.
 */
public class TimeMachine3Dialog  extends Dialog implements View.OnClickListener  {

    private static final String TAG = "TimeMachine3Dialog";
    private static final int SQL_MODE = 3;
    private int appIdIns;
    private View view;
    private Context ctx;
    private ormTool ormt;
    private TextView lltTV1;
    private TextView lltTV2;
    private TextView lltTV3;
    private TextView witTV1;
    private TextView witTV2;
    private TextView witTV3;
    private ListView lv;
    private Button cfmBtn;
    private Button cclBtn;
    private String dtime;
    private String sp1;
    private String sp2;
    private String sp3;
    private String llt1;
    private String llt2;
    private String llt3;
    private String wit1;
    private String wit2;
    private String wit3;
    private ArrayList<Map<String, String>> mlist;
    private interfaceTMD3BackCall onTMD3BackCall;


    public TimeMachine3Dialog(Context context, int appId, ormTool orm, interfaceTMD3BackCall TMD3BackCallListener) {
        super(context);
        this.ctx = context;
        this.setTitle("历史成绩拾取");
        appIdIns = appId;
        ormt = orm;
        onTMD3BackCall = TMD3BackCallListener;
    }

    // 定义接口 Callback ,包含回调方法 callback()
    public interface interfaceTMD3BackCall {
        public void TMD3Event(String[] listIWantToSendBackToTheActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAllViews();
    }

    private void initAllViews() {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        view = inflater.inflate(R.layout.dialog_time_machine3, null);
        setContentView(view);   // 主界面的初始化一定要最先完成
        lltTV1 = (TextView) findViewById(R.id.dialog_da3_llt_p1);
        lltTV2 = (TextView) findViewById(R.id.dialog_da3_llt_p2);
        lltTV3 = (TextView) findViewById(R.id.dialog_da3_llt_p3);
        witTV1 = (TextView) findViewById(R.id.dialog_da3_wt_p1);
        witTV2 = (TextView) findViewById(R.id.dialog_da3_wt_p2);
        witTV3 = (TextView) findViewById(R.id.dialog_da3_wt_p3);
        cclBtn = (Button) findViewById(R.id.dialog_tm3_ccbtn);
        cfmBtn = (Button) findViewById(R.id.dialog_tm3_cbtn);
        cclBtn.setOnClickListener(this);
        cfmBtn.setOnClickListener(this);
        lv = (ListView) findViewById(R.id.dialog_tm3_lv);

        mlist = getListData();
        SimpleAdapter adapter = new SimpleAdapter(
                ctx,
                mlist,
                R.layout.cell_history_back3,
                new String[]{"dateTime", "ps1", "ps2", "ps3", "isLLWin", "isAutoInsert"},
                new int[]{ R.id.cell3_tv1, R.id.cell3_tv2, R.id.cell3_tv3, R.id.cell3_tv4, R.id.cell3_tv7, R.id.cell3_tv8}
        );
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView dtimeView = (TextView) view.findViewById(R.id.cell3_tv1);
                TextView sopView1 = (TextView) view.findViewById(R.id.cell3_tv2);
                TextView sopView2 = (TextView) view.findViewById(R.id.cell3_tv3);
                TextView sopView3 = (TextView) view.findViewById(R.id.cell3_tv4);
                dtime = dtimeView.getText().toString();
                sp1 = sopView1.getText().toString();
                sp2 = sopView2.getText().toString();
                sp3 = sopView3.getText().toString();
                Map<String, String> mMap = new HashMap<String, String>();
                mMap = mlist.get(position);
                llt1 = mMap.get("pllt1");
                llt2 = mMap.get("pllt2");
                llt3 = mMap.get("pllt3");
                wit1 = mMap.get("pwit1");
                wit2 = mMap.get("pwit2");
                wit3 = mMap.get("pwit3");
                lltTV1.setText(llt1);
                lltTV2.setText(llt2);
                lltTV3.setText(llt3);
                witTV1.setText(wit1);
                witTV2.setText(wit2);
                witTV3.setText(wit3);
                Log.w(TAG, "当前选中内容 >>> " + dtime +","+ sp1 +","+ sp2 +","+ sp3);
            }
        });
    }

    public ArrayList<Map<String,String>> getListData() {
        List<modelTool> mtList = ormt.queryRec(SQL_MODE);
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (modelTool mt : mtList) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("dateTime", mt.dateTime);
            map.put("ps1", mt.pScore1);
            map.put("ps2", mt.pScore2);
            map.put("ps3", mt.pScore3);
            map.put("pllt1", String.valueOf(mt.llt1));
            map.put("pllt2", String.valueOf(mt.llt2));
            map.put("pllt3", String.valueOf(mt.llt3));
            map.put("pwit1", String.valueOf(mt.wit1));
            map.put("pwit2", String.valueOf(mt.wit2));
            map.put("pwit3", String.valueOf(mt.wit3));
            map.put("isLLWin", String.valueOf(mt.isLLWin));
            map.put("isAutoInsert", String.valueOf(mt.isAutoInsert));
            list.add(map);
            Log.w(TAG, "A map list is ready");
        }
        return list;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.dialog_tm3_cbtn:
                AlertDialog.Builder aldBuilder1 = new AlertDialog.Builder(ctx);
                aldBuilder1.setTitle("_(:з」∠)_");
                aldBuilder1.setMessage("确定要用选中数据替代现有分数吗");
                aldBuilder1.setNegativeButton("不了", null);
                aldBuilder1.setPositiveButton("对啊", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        onTMD3BackCall.TMD3Event(arrangeTheList());
                        dismiss();
                    }
                });
                aldBuilder1.create().show();
                break;
            case R.id.dialog_tm3_ccbtn:
                cancel();
                break;
        }

    }

    private String[] arrangeTheList() {
        String[] list = {sp1, sp2, sp3, llt1, llt2, llt3, wit1, wit2, wit3};
        return list;
    }

}
