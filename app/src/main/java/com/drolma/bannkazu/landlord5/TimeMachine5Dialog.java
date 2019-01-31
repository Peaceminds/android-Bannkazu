package com.drolma.bannkazu.landlord5;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.drolma.bannkazu.R;
import com.drolma.bannkazu.transwarp.modelTool;
import com.drolma.bannkazu.transwarp.ormTool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Drolma on 16/5/12.
 */
public class TimeMachine5Dialog extends Dialog implements View.OnClickListener {

    private static final String TAG = "TimeMachine5Dialog";
    private static final int SQL_MODE = 5;
    private int appIdIns;
    private View view;
    private Context ctx;
    private ormTool ormt;
    private TextView lltTV1;
    private TextView lltTV2;
    private TextView lltTV3;
    private TextView lltTV4;
    private TextView lltTV5;
    private TextView witTV1;
    private TextView witTV2;
    private TextView witTV3;
    private TextView witTV4;
    private TextView witTV5;
    private ListView lv;
    private Button cfmBtn;
    private Button cclBtn;
    private String dtime;
    private String sp1;
    private String sp2;
    private String sp3;
    private String sp4;
    private String sp5;
    private String llt1;
    private String llt2;
    private String llt3;
    private String llt4;
    private String llt5;
    private String wit1;
    private String wit2;
    private String wit3;
    private String wit4;
    private String wit5;
    private ArrayList<Map<String, String>> mlist;
    private interfaceTMDBackCall onTMDBackCall;


    public TimeMachine5Dialog(Context context, int appId, ormTool orm, interfaceTMDBackCall TMDBackCallListener) {
        super(context);
        this.ctx = context;
        this.setTitle("历史成绩拾取");
        appIdIns = appId;
        ormt = orm;
        onTMDBackCall = TMDBackCallListener;
    }

    // 定义接口 Callback ,包含回调方法 callback()
    public interface interfaceTMDBackCall {
        public void TMDEvent(String[] listIWantToSendBackToTheActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAllViews();
    }

    private void initAllViews() {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        view = inflater.inflate(R.layout.dialog_time_machine5, null);
        setContentView(view);   // 主界面的初始化一定要最先完成
        lltTV1 = (TextView) findViewById(R.id.dialog_da_llt_p1);
        lltTV2 = (TextView) findViewById(R.id.dialog_da_llt_p2);
        lltTV3 = (TextView) findViewById(R.id.dialog_da_llt_p3);
        lltTV4 = (TextView) findViewById(R.id.dialog_da_llt_p4);
        lltTV5 = (TextView) findViewById(R.id.dialog_da_llt_p5);
        witTV1 = (TextView) findViewById(R.id.dialog_da_wt_p1);
        witTV2 = (TextView) findViewById(R.id.dialog_da_wt_p2);
        witTV3 = (TextView) findViewById(R.id.dialog_da_wt_p3);
        witTV4 = (TextView) findViewById(R.id.dialog_da_wt_p4);
        witTV5 = (TextView) findViewById(R.id.dialog_da_wt_p5);
        cclBtn = (Button) findViewById(R.id.dialog_tm_ccbtn);
        cfmBtn = (Button) findViewById(R.id.dialog_tm_cbtn);
        cclBtn.setOnClickListener(this);
        cfmBtn.setOnClickListener(this);
        lv = (ListView) findViewById(R.id.dialog_tm_lv);

        mlist = getListData();
        SimpleAdapter adapter = new SimpleAdapter(
                ctx,
                mlist,
                R.layout.cell_history_back5,
                new String[]{"dateTime", "ps1", "ps2", "ps3", "ps4", "ps5", "isLLWin", "isAutoInsert"},
                new int[]{ R.id.cell_tv1, R.id.cell_tv2, R.id.cell_tv3, R.id.cell_tv4, R.id.cell_tv5, R.id.cell_tv6, R.id.cell_tv7, R.id.cell_tv8}
        );
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView dtimeView = (TextView) view.findViewById(R.id.cell_tv1);
                TextView sopView1 = (TextView) view.findViewById(R.id.cell_tv2);
                TextView sopView2 = (TextView) view.findViewById(R.id.cell_tv3);
                TextView sopView3 = (TextView) view.findViewById(R.id.cell_tv4);
                TextView sopView4 = (TextView) view.findViewById(R.id.cell_tv5);
                TextView sopView5 = (TextView) view.findViewById(R.id.cell_tv6);
                dtime = dtimeView.getText().toString();
                sp1 = sopView1.getText().toString();
                sp2 = sopView2.getText().toString();
                sp3 = sopView3.getText().toString();
                sp4 = sopView4.getText().toString();
                sp5 = sopView5.getText().toString();
                Map<String, String> mMap = new HashMap<String, String>();
                mMap = mlist.get(position);
                llt1 = mMap.get("pllt1");
                llt2 = mMap.get("pllt2");
                llt3 = mMap.get("pllt3");
                llt4 = mMap.get("pllt4");
                llt5 = mMap.get("pllt5");
                wit1 = mMap.get("pwit1");
                wit2 = mMap.get("pwit2");
                wit3 = mMap.get("pwit3");
                wit4 = mMap.get("pwit4");
                wit5 = mMap.get("pwit5");
                lltTV1.setText(llt1);
                lltTV2.setText(llt2);
                lltTV3.setText(llt3);
                lltTV4.setText(llt4);
                lltTV5.setText(llt5);
                witTV1.setText(wit1);
                witTV2.setText(wit2);
                witTV3.setText(wit3);
                witTV4.setText(wit4);
                witTV5.setText(wit5);
                Log.w(TAG, "当前选中内容 >>> " + dtime +","+ sp1 +","+ sp2 +","+ sp3 +","+ sp4 +","+ sp5);
            }
        });
    }

    public ArrayList<Map<String, String>> getListData() {
        List<modelTool> mtList = ormt.queryRec(SQL_MODE);
        ArrayList<Map<String, String>> list = new ArrayList<Map<String, String>>();
        for (modelTool mt : mtList) {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("dateTime", mt.dateTime);
            map.put("ps1", mt.pScore1);
            map.put("ps2", mt.pScore2);
            map.put("ps3", mt.pScore3);
            map.put("ps4", mt.pScore4);
            map.put("ps5", mt.pScore5);
            map.put("pllt1", String.valueOf(mt.llt1));
            map.put("pllt2", String.valueOf(mt.llt2));
            map.put("pllt3", String.valueOf(mt.llt3));
            map.put("pllt4", String.valueOf(mt.llt4));
            map.put("pllt5", String.valueOf(mt.llt5));
            map.put("pwit1", String.valueOf(mt.wit1));
            map.put("pwit2", String.valueOf(mt.wit2));
            map.put("pwit3", String.valueOf(mt.wit3));
            map.put("pwit4", String.valueOf(mt.wit4));
            map.put("pwit5", String.valueOf(mt.wit5));
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
            case R.id.dialog_tm_cbtn:
                AlertDialog.Builder aldBuilder1 = new AlertDialog.Builder(ctx);
                aldBuilder1.setTitle("_(:з」∠)_");
                aldBuilder1.setMessage("确定要用选中数据替代现有分数吗");
                aldBuilder1.setNegativeButton("不了", null);
                aldBuilder1.setPositiveButton("对啊", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        onTMDBackCall.TMDEvent(arrangeTheList());
                        dismiss();
                    }
                });
                aldBuilder1.create().show();
                break;
            case R.id.dialog_tm_ccbtn:
                cancel();
                break;
        }

    }

    private String[] arrangeTheList() {
        String[] list = {sp1, sp2, sp3, sp4, sp5, llt1, llt2, llt3, llt4, llt5, wit1, wit2, wit3, wit4, wit5};
        return list;
    }



// 　　　　// 写在OnCreate方法里的静态元素表
//        String[] arr1 = new String[]{"小白","小黄","小花","呵呵"};
//        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(ctx, R.layout.cell_history_back5, arr1);
//        lv.setAdapter(adapter1);


//    public final class ViewHolder{
//        public TextView dtimeView;
//        public TextView sopView1;
//        public TextView sopView2;
//        public TextView sopView3;
//        public TextView sopView4;
//        public TextView sopView5;
//    }


//    public class myAdapter extends BaseAdapter {
//
//        private LayoutInflater mInflater;
//        private List<Map<String, Object>> mData;
//
//        public myAdapter(Context ctxx, List<Map<String, Object>> mData) {
//            this.mInflater = LayoutInflater.from(ctxx);
//            this.mData = mData;
//        }
//
//        @Override
//        public int getCount() {
//            return mData.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            return position;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            return position;
//        }
//
//        @Override
//        public View getView(int position, View convertView, ViewGroup parent) {
//
//            ViewHolder holder = null;
//            //如果缓存convertView为空，则需要创建View
//            if (convertView == null) {
//                //自定义的一个类用来缓存convertview
//                holder = new ViewHolder();
//
//                //根据自定义的Item布局加载布局
//                convertView = mInflater.inflate(R.layout.cell_history_back5, null);
//
//                holder.dtimeView = (TextView) view.findViewById(R.id.cell_tv1);
//                holder.sopView1 = (TextView) view.findViewById(R.id.cell_tv2);
//                holder.sopView2 = (TextView) view.findViewById(R.id.cell_tv3);
//                holder.sopView3 = (TextView) view.findViewById(R.id.cell_tv4);
//                holder.sopView4 = (TextView) view.findViewById(R.id.cell_tv5);
//                holder.sopView5 = (TextView) view.findViewById(R.id.cell_tv6);
//                //将设置好的布局保存到缓存中，并将其设置在Tag里，以便后面方便取出Tag
//                convertView.setTag(holder);
//
//            } else {
//                holder = (ViewHolder)convertView.getTag();
//            }
//
//
//            holder.img.setBackgroundResource((Integer)mData.get(position).get("img"));
//            holder.title.setText((String)mData.get(position).get("title"));
//            holder.info.setText((String)mData.get(position).get("info"));
//
//            //为BTN设置点击监听事件
//            holder.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    showInfo();
//                }
//            });
//
//            return convertView;
//        }
//    }





}
