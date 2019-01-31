package com.drolma.bannkazu.landlord3;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.drolma.bannkazu.R;

/**
 * Created by Drolma on 16/5/23.
 */
public class ManualSet3Dialog extends Dialog implements View.OnClickListener {

    Context ctx;
    private Button cancelBtn;
    private Button confirmBtn;
    private TextView ptv1;
    private TextView ptv2;
    private TextView ptv3;
    private EditText mInput1;
    private EditText mInput2;
    private EditText mInput3;
    private String mInputValue1;
    private String mInputValue2;
    private String mInputValue3;
    private String[] playerNameList;
    private String[] userInputScoresList = {"", "", ""};
    private interfaceMSD3BackCallListener onMSD3BackCall;  // 回调接口的实例

    public ManualSet3Dialog(Context context, String[] nameList, interfaceMSD3BackCallListener Listener) {
        super(context);
        this.ctx = context;
        this.setTitle("手动设定分数");
        playerNameList = nameList;
        onMSD3BackCall = Listener;  // 回调接口在构造函数中设置
    }

    public interface interfaceMSD3BackCallListener {
        public void MSD3Event(String[] listIWantToSendBackToTheActivity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    private void initViews() {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        View view = inflater.inflate(R.layout.dialog_manual_set3, null);
        setContentView(view);
        mInput1 = (EditText) findViewById(R.id.dialog_msa3_scv1);
        mInput2 = (EditText) findViewById(R.id.dialog_msa3_scv2);
        mInput3 = (EditText) findViewById(R.id.dialog_msa3_scv3);
        cancelBtn = (Button) findViewById(R.id.dialog_msa3_cclbtn);
        confirmBtn = (Button) findViewById(R.id.dialog_msa3_cfmbtn);
        cancelBtn.setOnClickListener(this);
        confirmBtn.setOnClickListener(this);
        ptv1 = (TextView) findViewById(R.id.dialog_msa3_t1);
        ptv2 = (TextView) findViewById(R.id.dialog_msa3_t2);
        ptv3 = (TextView) findViewById(R.id.dialog_msa3_t3);
        ptv1.setText(playerNameList[0]);
        ptv2.setText(playerNameList[1]);
        ptv3.setText(playerNameList[2]);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.dialog_msa3_cclbtn:
                this.cancel();
                break;
            case R.id.dialog_msa3_cfmbtn:
                if (ifInputIsLegal()) {
                    getUserInput();
                    onMSD3BackCall.MSD3Event(userInputScoresList); // 用回调函数将值传回时，把需传回的值设置进去
                    dismiss();
                }
                break;
        }
    }

    private boolean ifInputIsLegal() {
        if (mInput1.getText().toString().trim().equals("") ||
                mInput2.getText().toString().trim().equals("") ||
                mInput3.getText().toString().trim().equals("")){
            Toast.makeText(ctx, "还有人没分呐……", Toast.LENGTH_SHORT).show();
            return false;
        } else
            return true;
    }

    private void getUserInput() {
        mInputValue1 = mInput1.getText().toString();
        mInputValue2 = mInput2.getText().toString();
        mInputValue3 = mInput3.getText().toString();
        userInputScoresList[0] = mInputValue1;
        userInputScoresList[1] = mInputValue2;
        userInputScoresList[2] = mInputValue3;
    }


}
