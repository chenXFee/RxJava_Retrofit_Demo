package com.cxf.demo.Consumption;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cxf.demo.R;
import com.cxf.module_resource.EventBus.DealChangeUIEvent;
import com.cxf.module_resource.EventBus.IEvent;
import com.cxf.moudule_common.Base.BaseActivity;
import com.cxf.moudule_common.rxjava.RxBindUtils;
import com.cxf.moudule_common.rxjava.RxJavaUtil;
import com.cxf.moudule_common.util.IntentUtil;
import com.cxf.moudule_common.util.ToastUtil;


import org.greenrobot.eventbus.EventBus;

public class DealManagerActivity extends BaseActivity {

    private static final int TIME_OUT = 1*60*1000;


    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_deal_manager);
    }

    @Override
    protected void setClickListening() {





        RxBindUtils.rxViewClicks(btn_deal_cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //取消交易

            }
        });

        RxBindUtils.rxViewClicks(btn_deal_card_cancel, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //读取卡后 取消交易
            }
        });

        RxBindUtils.rxViewClicks(btn_deal_card_confirm, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //读取卡后确认交易

            }
        });
    }

    TextView tv_tips;
    TextView tv_moneyAcount;
    TextView tv_cardCode;

    Button btn_deal_cancel;
    Button btn_deal_card_cancel;
    Button btn_deal_card_confirm;

    LinearLayout ll_btnGroup2;


    @Override
    protected void bindViewById() {
        tv_moneyAcount = (TextView)findViewById(R.id.tv_deal_moneyAcount);
        tv_tips = (TextView)findViewById(R.id.tv_deal_tips);
        tv_cardCode = (TextView)findViewById(R.id.tv_deal_carCode);

        btn_deal_cancel = (Button)findViewById(R.id.btn_deal_btngroup1_cancel);
        btn_deal_card_cancel = (Button)findViewById(R.id.btn_deal_brngroup2_cancel);
        btn_deal_card_confirm = (Button)findViewById(R.id.btn_deal_brngroup2_confirm);

        ll_btnGroup2 = (LinearLayout)findViewById(R.id.ll_deal_btngroup2);

    }


    private String acount;
    private String cardCode;



    @Override
    public void initData() {
        bindService();
        setTitle("交易");
        acount = getIntent().getExtras().getString(IntentUtil.WITH_STRING);

        if(null==acount||TextUtils.isEmpty(acount)){
            ToastUtil.showShort(DealManagerActivity.this,"金额错误");
            finish();
        }else{
            tv_moneyAcount.setText(acount);
        }




    }






 

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


    //----------------------//





}
