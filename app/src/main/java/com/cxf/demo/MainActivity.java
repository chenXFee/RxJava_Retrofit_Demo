package com.cxf.demo;


import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.cxf.demo.Consumption.MoneyAmountActivity;
import com.cxf.demo.Sign.SignManagerActivity;
import com.cxf.moudule_common.Base.BaseActivity;
import com.cxf.moudule_common.Retrofit.RetrofitService.IRetrofitService;
import com.cxf.moudule_common.Retrofit.RetrofitService.MyResult;
import com.cxf.moudule_common.Retrofit.RetrofitUtil;
import com.cxf.moudule_common.rxjava.RxBindUtils;
import com.cxf.moudule_common.rxjava.RxJavaUtil;
import com.cxf.moudule_common.util.IntentUtil;
import com.start.smartpos.aidl.ServiceProvider;





public class MainActivity extends BaseActivity {


    LinearLayout ll_consumption;
    LinearLayout ll_print;
    LinearLayout ll_singIn;


    @Override
    protected void setActivityContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void setClickListening() {
        RxBindUtils.rxViewClicks(ll_consumption, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //消费页面
                IntentUtil.get().goActivity(MainActivity.this, MoneyAmountActivity.class);
            }
        });

        RxBindUtils.rxViewClicks(ll_print, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //打印页面
                IRetrofitService service = RetrofitUtil.getInstance().create(IRetrofitService.class);
                RxJavaUtil.netWorks(service.getMovie(0, 10), new RxJavaUtil.callBackRetrofitWorks() {
                    @Override
                    public void callBackResponse(Object response) {
                        Log.i("Tag","/*/*/*/*-----"+response);
                    }

                    @Override
                    public void callBackResponseError(Object errorCode, Object errorMessage) {
                        Log.i("Tag","/*/*/*/*-----"+errorCode.toString()+"--*-*"+errorMessage.toString());
                    }
                });




            }
        });

        RxBindUtils.rxViewClicks(ll_singIn, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //签到管理页面
                IntentUtil.get().goActivity(MainActivity.this, SignManagerActivity.class);

            }
        });

    }

    @Override
    protected void bindViewById() {
        ll_consumption = (LinearLayout)findViewById(R.id.ll_function_consumption);
        ll_print = (LinearLayout)findViewById(R.id.ll_function_print);
        ll_singIn = (LinearLayout)findViewById(R.id.ll_function_signin);
    }

    @Override
    public void initData() {

    }


}
