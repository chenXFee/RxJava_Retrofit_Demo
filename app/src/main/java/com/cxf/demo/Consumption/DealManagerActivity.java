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
import com.start.smartpos.aidl.ServiceProvider;
import com.start.smartpos.aidl.device.cardreader.CardInformation;
import com.start.smartpos.aidl.device.cardreader.CardReaderListener;
import com.start.smartpos.aidl.device.cardreader.CardReaderProvider;
import com.start.smartpos.aidl.device.cardreader.RFCardProvider;
import com.start.smartpos.aidl.device.pinpad.PinPadProvider;
import com.start.smartpos.aidl.device.printer.PrinterFormat;
import com.start.smartpos.aidl.device.printer.PrinterProvider;
import com.start.smartpos.aidl.device.system.SystemProvider;

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

    private CardReaderProvider cardReaderProvider = null;
    private RFCardProvider rfCardProvider = null;
    private PrinterProvider printerProvider = null;
    private SystemProvider systemProvider = null;
    private PinPadProvider pinPadProvider = null;


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

    private void initProvider(ServiceProvider serviceProvider){
        if(null==serviceProvider){
            return;
        }
        try{
            cardReaderProvider = serviceProvider.getCardReaderProvider();
            printerProvider = serviceProvider.getPrinterProvider();
            pinPadProvider = serviceProvider.getPinPadProvider();
            systemProvider = serviceProvider.getSystemProvider();
            rfCardProvider = serviceProvider.getRFCardProvider();
            Log.i("tag","info---------获取provider成功");
        }catch (RemoteException remoteException) {
            ToastUtil.showShort(DealManagerActivity.this,"获取provider失败");
            return;
        }catch (Exception e){
            Log.e("tag","error--------"+e.getMessage());
            e.printStackTrace();
        }

    }


    public void bindService() {
        Intent intent = new Intent();
        intent.setAction("com.start.smartpos.AIDL_SERVICE").setPackage("com.start.smartpos");
        boolean isSuccess =bindService(intent, conn, Context.BIND_AUTO_CREATE);
        if(isSuccess){
            Log.i("Tag", "-------连接服务成功！");
        }else{
            Log.e("Tag", "-------连接服务失败！");
        }
    }

    private ServiceProvider mAIDLService=null;
    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.i("Tag", "-------服务绑定成功");
            mAIDLService = ServiceProvider.Stub.asInterface(service);
            initProvider(mAIDLService);
            setCardListener();
            if(null==mAIDLService){
                Log.i("Tag", "-------mAIDLService为空");
            }else{
                Log.i("Tag", "-------mAIDLService不为空");
            }
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e("Tag", "-------服务绑定失败："+name.getPackageName());
            mAIDLService = null;
        }
    };

    @Override
    protected void onDestroy() {
        unbindService(conn);
        super.onDestroy();
    }


    //----------------------//
    private void confirmCard(String cardNo){
        tv_tips.setText("请确认卡号");
        tv_cardCode.setVisibility(View.VISIBLE);
        tv_cardCode.setText(cardNo);
        ll_btnGroup2.setVisibility(View.VISIBLE);
        btn_deal_cancel.setVisibility(View.GONE);
    }

    private void setCardListener(){
        try {
            cardReaderProvider.readCard(TIME_OUT, CardInformation.CARD_MAG, new CardReaderListener.Stub() {
                @Override
                public void onReadCardResult(int i, CardInformation cardInformation) throws RemoteException {
                    cardCode = cardInformation.getCardNo();
                    //广播通知更改ui
                    EventBus.getDefault().post(new DealChangeUIEvent());
                }

            });



        }catch (RemoteException remoteException){
            Log.e("tag","error--------"+remoteException.getMessage());
            return;
        }catch (Exception e){
            Log.e("tag","error--------"+e.getMessage());
            e.printStackTrace();
        }
    }

//    private void actionPrint(){
//        if(null==printerProvider){
//            return;
//        }
//        try{
//            printerProvider.prepareForPrint();
//            printerProvider.printText(PrinterFormat,"asdasasdasd");
//        }catch (RemoteException e){
//            Log.e("tag","error-------"+e.getMessage());
//        }
//
//    }


    @Override
    public void onMessageEvent(IEvent event) {
        if(event instanceof DealChangeUIEvent){
            if(null!=cardCode) {
                confirmCard(cardCode);
            }
        }
    }
}
