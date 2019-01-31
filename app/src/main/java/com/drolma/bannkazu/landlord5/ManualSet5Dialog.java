package com.drolma.bannkazu.landlord5;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.drolma.bannkazu.R;

/**
 * Created by Drolma on 16/5/12.
 */
public class ManualSet5Dialog extends Dialog implements OnClickListener {

    Context ctx;
    private Button cancelBtn;
    private Button confirmBtn;
    private TextView ptv1;
    private TextView ptv2;
    private TextView ptv3;
    private TextView ptv4;
    private TextView ptv5;
    private EditText mInput1;
    private EditText mInput2;
    private EditText mInput3;
    private EditText mInput4;
    private EditText mInput5;
    private String mInputValue1;
    private String mInputValue2;
    private String mInputValue3;
    private String mInputValue4;
    private String mInputValue5;
    private String[] playerNameList;
    private String[] userInputScoresList = {"", "", "", "", ""};
    private interfaceMSDBackCallListener onMSDBackCall;  // 回调接口的实例

    public ManualSet5Dialog(Context context, String[] nameList, interfaceMSDBackCallListener Listener) {
        super(context);
        this.ctx = context;
        this.setTitle("手动设定分数");
        playerNameList = nameList;
        onMSDBackCall = Listener;  // 回调接口在构造函数中设置
    }

    // 定义接口 Callback ,包含回调方法 callback() 此处就是MSDEvent()
    public interface interfaceMSDBackCallListener {
        public void MSDEvent(String[] listIWantToSendBackToTheActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAllTheViews();
    }

    private void initAllTheViews() {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.dialog_manual_set5, null);
        setContentView(view);
        mInput1 = (EditText) findViewById(R.id.dialog_msa_scv1);
        mInput2 = (EditText) findViewById(R.id.dialog_msa_scv2);
        mInput3 = (EditText) findViewById(R.id.dialog_msa_scv3);
        mInput4 = (EditText) findViewById(R.id.dialog_msa_scv4);
        mInput5 = (EditText) findViewById(R.id.dialog_msa_scv5);
        cancelBtn = (Button) findViewById(R.id.dialog_msa_cclbtn);
        confirmBtn = (Button) findViewById(R.id.dialog_msa_cfmbtn);
        cancelBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        ptv1 = (TextView) findViewById(R.id.dialog_msa_t1);
        ptv2 = (TextView) findViewById(R.id.dialog_msa_t2);
        ptv3 = (TextView) findViewById(R.id.dialog_msa_t3);
        ptv4 = (TextView) findViewById(R.id.dialog_msa_t4);
        ptv5 = (TextView) findViewById(R.id.dialog_msa_t5);
        ptv1.setText(playerNameList[0]);
        ptv2.setText(playerNameList[1]);
        ptv3.setText(playerNameList[2]);
        ptv4.setText(playerNameList[3]);
        ptv5.setText(playerNameList[4]);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_msa_cclbtn:
                this.cancel();
                break;
            case R.id.dialog_msa_cfmbtn:
                if (ifInputIsLegal()) {
                    getUserInput();
                    onMSDBackCall.MSDEvent(userInputScoresList); // 用回调函数将值传回时，把需传回的值设置进去
                    dismiss();
                }
                break;
        }
    }

    private boolean ifInputIsLegal() {
        if (mInput1.getText().toString().trim().equals("") ||
                mInput2.getText().toString().trim().equals("") ||
                mInput3.getText().toString().trim().equals("") ||
                mInput4.getText().toString().trim().equals("") ||
                mInput5.getText().toString().trim().equals("")){
            Toast.makeText(ctx, "还有人没分呐……", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private void getUserInput() {
        mInputValue1 = mInput1.getText().toString();
        mInputValue2 = mInput2.getText().toString();
        mInputValue3 = mInput3.getText().toString();
        mInputValue4 = mInput4.getText().toString();
        mInputValue5 = mInput5.getText().toString();
        userInputScoresList[0] = mInputValue1;
        userInputScoresList[1] = mInputValue2;
        userInputScoresList[2] = mInputValue3;
        userInputScoresList[3] = mInputValue4;
        userInputScoresList[4] = mInputValue5;
    }

}
