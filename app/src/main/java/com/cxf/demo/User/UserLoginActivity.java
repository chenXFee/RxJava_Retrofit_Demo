package com.cxf.demo.User;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cxf.demo.MainActivity;
import com.cxf.demo.R;
import com.cxf.moudule_common.Base.BaseActivity;
import com.cxf.moudule_common.rxjava.RxBindUtils;
import com.cxf.moudule_common.util.IntentUtil;
import com.cxf.moudule_common.util.ToastUtil;
import com.start.smartpos.aidl.device.devicemanager.DeviceManagerProvider;

public class UserLoginActivity extends BaseActivity {


    private EditText userAccount;
    private EditText userPassword;
    private Button toLogin;


    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_user_login);
    }

    @Override
    protected void setClickListening() {
        RxBindUtils.rxViewClicks(toLogin, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if("01".equals(userAccount.getText().toString().trim())
                        &&"0000".equals(userPassword.getText().toString().trim())) {
                    IntentUtil.get().goActivityKill(UserLoginActivity.this, MainActivity.class);
                }else{
                    ToastUtil.showShort(UserLoginActivity.this,"请输入正确的账号和密码！");
                }
            }
        });


    }

    @Override
    protected void bindViewById() {
        userAccount = (EditText)findViewById(R.id.et_userAccount);
        userPassword = (EditText)findViewById(R.id.et_userPassword);
        toLogin = (Button)findViewById(R.id.btn_userLogin);
    }

    @Override
    public void initData() {

    }




}
