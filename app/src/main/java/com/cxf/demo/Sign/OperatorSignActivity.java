package com.cxf.demo.Sign;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cxf.demo.R;
import com.cxf.moudule_common.Base.BaseActivity;
import com.cxf.moudule_common.rxjava.RxBindUtils;
import com.cxf.moudule_common.util.ToastUtil;

public class OperatorSignActivity extends BaseActivity {

    EditText et_operatorCode;
    EditText et_operatorPwd;
    Button btn_operatorSign;


    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_operator_sign);
    }

    @Override
    protected void setClickListening() {
        RxBindUtils.rxViewClicks(btn_operatorSign, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("01".equals(et_operatorCode.getText().toString().trim())
                        &&"0000".equals(et_operatorPwd.getText().toString().trim())){
                    ToastUtil.showShort(OperatorSignActivity.this,"签到成功");
                    finish();
                }else{
                    ToastUtil.showShort(OperatorSignActivity.this,"操作员01已经签到，签到失败");
                }
            }
        });
    }

    @Override
    protected void bindViewById() {
        et_operatorCode = (EditText)findViewById(R.id.et_operator_code);
        et_operatorPwd = (EditText)findViewById(R.id.et_operator_pwd);
        btn_operatorSign = (Button)findViewById(R.id.btn_operator_signIn);
    }

    @Override
    public void initData() {
        setTitle("操作员签到");
    }

}
