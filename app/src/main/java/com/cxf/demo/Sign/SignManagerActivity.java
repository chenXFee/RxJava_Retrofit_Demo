package com.cxf.demo.Sign;


import android.view.View;
import android.widget.LinearLayout;

import com.cxf.demo.R;
import com.cxf.moudule_common.Base.BaseActivity;
import com.cxf.moudule_common.rxjava.RxBindUtils;
import com.cxf.moudule_common.util.IntentUtil;

public class SignManagerActivity extends BaseActivity {


    LinearLayout ll_signIn;
    LinearLayout ll_signOut;
    LinearLayout ll_userSignIn;

    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_sign_manager);
    }

    @Override
    protected void setClickListening() {

        RxBindUtils.rxViewClicks(ll_signIn, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //签到
                
            }
        });

        RxBindUtils.rxViewClicks(ll_signOut, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //签退

            }
        });

        RxBindUtils.rxViewClicks(ll_userSignIn, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //操作员签到
                IntentUtil.get().goActivity(SignManagerActivity.this,OperatorSignActivity.class);
            }
        });

    }

    @Override
    protected void bindViewById() {
        ll_signIn = (LinearLayout)findViewById(R.id.ll_signManager_signIn);
        ll_signOut = (LinearLayout)findViewById(R.id.ll_signManager_signOut);
        ll_userSignIn = (LinearLayout)findViewById(R.id.ll_signManager_userSignIn);
    }

    @Override
    public void initData() {
        setTitle("签到管理");
    }

}
